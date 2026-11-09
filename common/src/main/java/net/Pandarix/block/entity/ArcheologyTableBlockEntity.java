package net.Pandarix.block.entity;

import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap;
import net.Pandarix.BACommon;
import net.Pandarix.block.custom.ArchelogyTable;
import net.Pandarix.item.BetterBrushItem;
import net.Pandarix.recipe.IdentifyingRecipe;
import net.Pandarix.screen.IdentifyingMenu;
import net.minecraft.core.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.StackedItemContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeCraftingHolder;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.BrushItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class ArcheologyTableBlockEntity extends BaseContainerBlockEntity implements ItemOwner, WorldlyContainer, RecipeCraftingHolder, StackedContentsCompatible
{
    // INVENTORY ──────────────────────────────────────────────────────────────────────────
    //default inventory size of the archeology table,
    public static final int INV_SIZE = 3;
    //default number of Properties of ArcheologyTableBlockEntity
    public static final int NO_PROP_DELEGATES = 2;
    protected NonNullList<ItemStack> items;
    private static final int[] SLOTS_FOR_UP = new int[]{0};
    private static final int[] SLOTS_FOR_DOWN = new int[]{2};
    private static final int[] SLOTS_FOR_SIDES = new int[]{1};
    private static final Codec<Map<ResourceKey<Recipe<?>>, Integer>> RECIPES_USED_CODEC = Codec.unboundedMap(Recipe.KEY_CODEC, Codec.INT);
    private final Reference2IntOpenHashMap<ResourceKey<Recipe<?>>> recipesUsed;

    // PROGRESS ──────────────────────────────────────────────────────────────────────────
    //synchronises Ints between server and client
    protected final ContainerData data;
    // Progress needed to identify an artifact; each tick adds more the faster the brush in the table is.
    private static final int MAX_PROGRESS = 72;
    private static final float BRUSH_SOUND_VOLUME = 0.25f;
    private static final float BRUSH_SOUND_PITCH = 1f;
    private int progress = 0;

    // The recipe lookup scans every recipe on the server, so the match is kept until the input changes,
    // a craft finishes or /reload replaces the RecipeManager. A null recipe caches "nothing matches".
    private record CachedRecipe(RecipeManager recipeManager, ItemStack input, @Nullable RecipeHolder<IdentifyingRecipe> recipe, ItemStack result) {}
    @Nullable
    private CachedRecipe cachedRecipe;

    public ArcheologyTableBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.ARCHEOLOGY_TABLE.get(), pos, state);
        this.items = NonNullList.withSize(INV_SIZE, ItemStack.EMPTY);
        // Syncs progress (index 0) and the fixed max progress (index 1) to the open screen.
        this.data = new ContainerData()
        {
            @Override
            public int get(int index)
            {
                return switch (index)
                {
                    case 0 -> ArcheologyTableBlockEntity.this.progress;
                    case 1 -> MAX_PROGRESS;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value)
            {
                if (index == 0)
                    ArcheologyTableBlockEntity.this.progress = value;
            }

            @Override
            public int getCount()
            {
                return NO_PROP_DELEGATES;
            }
        };

        this.recipesUsed = new Reference2IntOpenHashMap<>();
    }

    // CRAFTING-RELATED ──────────────────────────────────────────────────────────────────────────
    @Override
    @NotNull
    protected AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory)
    {
        return new IdentifyingMenu(id, inventory, this, this.data);
    }

    private static Optional<RecipeHolder<IdentifyingRecipe>> getRecipeOrRandomMatching(SingleRecipeInput singleRecipeInput, ServerLevel serverLevel)
    {
        @SuppressWarnings("unchecked")
        List<RecipeHolder<IdentifyingRecipe>> possibleRecipes = serverLevel.getServer().getRecipeManager().getRecipes().stream()
                .filter(recipeHolder -> recipeHolder.value().getType() == IdentifyingRecipe.Type.INSTANCE)
                .map(recipeHolder -> recipeHolder.value() instanceof IdentifyingRecipe ? (RecipeHolder<IdentifyingRecipe>) recipeHolder : null).filter(Objects::nonNull)
                .filter(recipeHolder -> recipeHolder.value().matches(singleRecipeInput, serverLevel))
                .toList();

        int size = possibleRecipes.size();
        return switch (size)
        {
            case 0 -> Optional.empty();
            case 1 -> Optional.of(possibleRecipes.getFirst());
            default -> Optional.of(possibleRecipes.get(serverLevel.getRandom().nextInt(size)));
        };
    }

    private CachedRecipe findRecipe(ServerLevel serverLevel, ItemStack input)
    {
        RecipeManager recipeManager = serverLevel.getServer().getRecipeManager();
        if (this.cachedRecipe != null && this.cachedRecipe.recipeManager() == recipeManager && ItemStack.isSameItemSameComponents(input, this.cachedRecipe.input()))
            return this.cachedRecipe;

        SingleRecipeInput recipeInput = new SingleRecipeInput(input);
        RecipeHolder<IdentifyingRecipe> recipe = getRecipeOrRandomMatching(recipeInput, serverLevel).orElse(null);
        ItemStack result = recipe == null ? ItemStack.EMPTY : recipe.value().assemble(recipeInput);
        this.cachedRecipe = new CachedRecipe(recipeManager, input.copyWithCount(1), recipe, result);
        return this.cachedRecipe;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ArcheologyTableBlockEntity table)
    {
        if (!(level instanceof ServerLevel serverLevel)) return;

        ItemStack brush = table.items.getFirst();
        CachedRecipe match = table.findRecipe(serverLevel, table.items.get(1));
        if (!(brush.getItem() instanceof BrushItem) || match.recipe() == null || !table.fitsInOutput(match.result()))
        {
            table.stopBrushing(level, pos, state);
            return;
        }

        int brushSpeed = BetterBrushItem.tickRateOf(brush);
        //play sound once per brush stroke
        if (table.progress % brushSpeed == 0)
            level.playSound(null, pos, SoundEvents.BRUSH_GENERIC, SoundSource.BLOCKS, BRUSH_SOUND_VOLUME, BRUSH_SOUND_PITCH);

        table.progress += Mth.positiveCeilDiv(BrushItem.ANIMATION_DURATION, brushSpeed);
        setDusting(level, pos, state, true);
        // Only marks the chunk for saving: the open screen gets progress through ContainerData, so no block update is sent.
        level.blockEntityChanged(pos);

        if (table.progress >= MAX_PROGRESS)
            table.craftItem(serverLevel, match);
    }

    // Runs every tick on an idle table, so it only writes when something actually changes.
    private void stopBrushing(Level level, BlockPos pos, BlockState state)
    {
        setDusting(level, pos, state, false);
        if (this.progress == 0) return;

        this.progress = 0;
        level.blockEntityChanged(pos);
    }

    private static void setDusting(Level level, BlockPos pos, BlockState state, boolean dusting)
    {
        if (state.getValue(ArchelogyTable.DUSTING) != dusting)
            level.setBlock(pos, state.setValue(ArchelogyTable.DUSTING, dusting), Block.UPDATE_ALL);
    }

    @Override
    public void setRecipeUsed(@Nullable RecipeHolder<?> recipeHolder)
    {
        if (recipeHolder != null)
        {
            ResourceKey<Recipe<?>> resourceLocation = recipeHolder.id();
            this.recipesUsed.addTo(resourceLocation, 1);
        }
    }

    @Nullable
    @Override
    public RecipeHolder<?> getRecipeUsed()
    {
        return null;
    }

    private void craftItem(ServerLevel serverLevel, CachedRecipe match)
    {
        this.items.get(1).shrink(1);
        this.items.getFirst().hurtAndBreak(1, serverLevel, null, item -> serverLevel.playSound(null, this.worldPosition, SoundEvents.ITEM_BREAK.value(), SoundSource.BLOCKS, 0.25f, 1f));
        serverLevel.playSound(null, this.worldPosition, SoundEvents.BRUSH_SAND_COMPLETED, SoundSource.BLOCKS, 0.5f, 1f);
        this.setRecipeUsed(match.recipe());

        ItemStack output = this.items.get(2);
        if (output.isEmpty())
            this.items.set(2, match.result().copy());
        else
            output.grow(match.result().getCount());

        this.progress = 0;
        // Re-roll the recipe for the next craft, in case several recipes match this input.
        this.cachedRecipe = null;
        this.setChanged();
    }

    private boolean fitsInOutput(ItemStack result)
    {
        ItemStack output = this.items.get(2);
        if (result.isEmpty()) return false;
        if (output.isEmpty()) return true;

        return ItemStack.isSameItemSameComponents(result, output) && output.getCount() + result.getCount() <= output.getMaxStackSize();
    }

    // INVENTORY HANDLING ──────────────────────────────────────────────────────────────────────────
    @Override
    public int @NotNull [] getSlotsForFace(Direction direction)
    {
        return switch (direction)
        {
            case DOWN -> SLOTS_FOR_DOWN;
            case UP -> SLOTS_FOR_UP;
            default -> SLOTS_FOR_SIDES;
        };
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, @NotNull ItemStack itemStack, @Nullable Direction direction)
    {
        if (direction == null) return false;

        return switch (direction)
        {
            case Direction.DOWN -> false;
            case Direction.UP -> slot == 0 && itemStack.getItem() instanceof BrushItem;
            default -> slot == 1 && this.items.get(1).isEmpty();
        };
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, @NotNull ItemStack itemStack, @NotNull Direction direction)
    {
        //only extract on the bottom
        return direction == Direction.DOWN && slot == 2;
    }

    @NotNull
    public NonNullList<ItemStack> getItems()
    {
        return this.items;
    }

    @Override
    protected void setItems(@NotNull NonNullList<ItemStack> nonNullList)
    {
        for (int i = 0; i < this.items.size(); i++)
            this.items.set(i, nonNullList.get(i));
    }

    @Override
    public void setItem(int i, ItemStack itemStack)
    {
        ItemStack itemStack2 = this.items.get(i);

        boolean isValid = !itemStack.isEmpty() && ItemStack.isSameItemSameComponents(itemStack2, itemStack);
        this.items.set(i, itemStack);

        itemStack.limitSize(this.getMaxStackSize(itemStack));

        if (i == 0 && !isValid)
            this.setChanged();
    }

    @Override
    public int getContainerSize()
    {
        return this.items.size();
    }

    @Override
    public void fillStackedContents(StackedItemContents stackedItemContents)
    {
        this.items.forEach(stackedItemContents::accountStack);
    }

    // NETWORKING ──────────────────────────────────────────────────────────────────────────
    @Override
    public void setChanged()
    {
        if (this.level != null)
            level.sendBlockUpdated(worldPosition, this.getBlockState(), this.getBlockState(), 3);

        super.setChanged();
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    @NotNull
    public CompoundTag getUpdateTag(HolderLookup.@NotNull Provider pRegistries)
    {
        try (ProblemReporter.ScopedCollector scopedCollector = new ProblemReporter.ScopedCollector(this.problemPath(), BACommon.LOGGER))
        {
            TagValueOutput tagValueOutput = TagValueOutput.createWithContext(scopedCollector, pRegistries);
            this.saveAdditional(tagValueOutput);
            return tagValueOutput.buildResult();
        }
    }

    @Override
    protected void saveAdditional(@NotNull ValueOutput valueOutput)
    {
        super.saveAdditional(valueOutput);

        ContainerHelper.saveAllItems(valueOutput, this.items);

        valueOutput.putShort("archeology_table.progress", (short) this.progress);
        valueOutput.store("RecipesUsed", RECIPES_USED_CODEC, this.recipesUsed);
    }

    @Override
    protected void loadAdditional(@NotNull ValueInput valueInput)
    {
        super.loadAdditional(valueInput);

        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(valueInput, this.items);

        this.progress = valueInput.getShortOr("archeology_table.progress", (short) 0);

        this.recipesUsed.clear();
        this.recipesUsed.putAll(valueInput.read("RecipesUsed", RECIPES_USED_CODEC).orElse(Map.of()));
    }

    // MISC ──────────────────────────────────────────────────────────────────────────
    @Override
    @NotNull
    public Component getDisplayName()
    {
        return Component.translatable(BACommon.createRLoc("archeology_table").toLanguageKey());
    }

    @Override
    @NotNull
    protected Component getDefaultName()
    {
        return getDisplayName();
    }

    @Override
    @NotNull
    public Level level()
    {
        return this.level;
    }

    @Override
    public Vec3 position()
    {
        return Vec3.atCenterOf(this.getBlockPos());
    }

    @Override
    public float getVisualRotationYInDegrees()
    {
        return 0f;
    }
}
