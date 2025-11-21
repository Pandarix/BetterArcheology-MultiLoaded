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
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
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
    private int progress = 0;
    private int maxProgress = 72;

    public ArcheologyTableBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.ARCHEOLOGY_TABLE.get(), pos, state);
        this.items = NonNullList.withSize(INV_SIZE, ItemStack.EMPTY);
        //getter und setter für PropertyDelegate based on index (progress, maxProgress)
        this.data = new ContainerData()
        {
            @Override
            public int get(int index)
            {
                return switch (index)
                {
                    case 0 -> ArcheologyTableBlockEntity.this.progress;
                    case 1 -> ArcheologyTableBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value)
            {
                switch (index)
                {
                    case 0 -> ArcheologyTableBlockEntity.this.progress = value;
                    case 1 -> ArcheologyTableBlockEntity.this.maxProgress = value;
                }
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

    private void resetProgress()
    {
        this.progress = 0;
    }

    public int getProgress()
    {
        return progress;
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
            default -> Optional.of(possibleRecipes.get(serverLevel.random.nextInt(size)));
        };
    }

    public static void tick(Level world, BlockPos blockPos, BlockState blockState, ArcheologyTableBlockEntity entity)
    {
        if (world.isClientSide()) return;

        ItemStack brushSlotContent = entity.items.getFirst();
        boolean hasBrush = brushSlotContent.getItem() instanceof BrushItem;
        ItemStack inputSlotContent = entity.items.get(1);

        if (hasBrush && !inputSlotContent.isEmpty())
        {
            ServerLevel serverLevel = (ServerLevel) world;
            SingleRecipeInput singleRecipeInput = new SingleRecipeInput(inputSlotContent);
            Optional<RecipeHolder<IdentifyingRecipe>> recipeHolder = getRecipeOrRandomMatching(singleRecipeInput, serverLevel);

            // Reset and cancel
            if (recipeHolder.isEmpty() || !canBrush(serverLevel.registryAccess(), recipeHolder.get(), singleRecipeInput, entity.items, entity.getMaxStackSize()))
            {
                setBlockBrushing(world, blockPos, blockState, false);
                entity.resetProgress();
                return;
            }

            int brushSpeed = brushSlotContent.getItem() instanceof BetterBrushItem betterBrushItem ? betterBrushItem.getBrushingSpeed() : 10;

            //play sound every 10th tick
            if (entity.progress % brushSpeed == 0)
                world.playSound(null, entity.worldPosition, SoundEvents.BRUSH_GENERIC, SoundSource.BLOCKS, 0.25f, 1f);

            //if the recipe is inside, set state to dusting
            int progressStep = (int) Math.ceil(10f / brushSpeed);
            entity.progress += progressStep; //increase progress
            setBlockBrushing(world, blockPos, blockState, true);
            setChanged(world, blockPos, blockState);

            //if crafting progress is bigger or as big as the maxProgress, then craft the Item, else reset the timer
            if (entity.progress >= entity.maxProgress)
                entity.craftItem(serverLevel, recipeHolder.get(), singleRecipeInput, entity.items);
        } else
        {
            entity.resetProgress(); //resets crafting progress
            setBlockBrushing(world, blockPos, blockState, false);
            entity.setChanged();
        }
    }

    private static void setBlockBrushing(Level world, BlockPos blockPos, BlockState blockState, boolean brushing)
    {
        world.setBlock(blockPos, blockState.setValue(ArchelogyTable.DUSTING, brushing), 3);
        setChanged(world, blockPos, blockState);
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

    private void craftItem(ServerLevel serverLevel, RecipeHolder<IdentifyingRecipe> recipeHolder, SingleRecipeInput singleRecipeInput, NonNullList<ItemStack> contents)
    {
        if (recipeHolder == null || !canBrush(serverLevel.registryAccess(), recipeHolder, singleRecipeInput, contents, this.getMaxStackSize()))
            return;

        //remove input from slot
        ItemStack stack = this.items.get(1);
        stack.shrink(1);
        this.items.set(1, stack);

        ItemStack brush = this.items.getFirst();
        brush.hurtAndBreak(1, serverLevel, null, item -> serverLevel.playSound(null, this.worldPosition, SoundEvents.ITEM_BREAK.value(), SoundSource.BLOCKS, 0.25f, 1f));

        //play sound after crafting
        serverLevel.playSound(null, this.worldPosition, SoundEvents.BRUSH_SAND_COMPLETED, SoundSource.BLOCKS, 0.5f, 1f);
        this.setRecipeUsed(recipeHolder);

        ItemStack resultStack = recipeHolder.value().assemble(singleRecipeInput, serverLevel.registryAccess());
        ItemStack stackInOutput = contents.get(2).copy();

        if (stackInOutput.isEmpty())
        {
            this.items.set(2, resultStack.copy());
        } else if (ItemStack.isSameItemSameComponents(stackInOutput, resultStack))
        {
            stackInOutput.grow(resultStack.getCount());
            this.items.set(2, stackInOutput);
        }

        this.resetProgress(); //resets crafting progress
        this.setChanged();
    }

    private static boolean canBrush(RegistryAccess registryAccess, @Nullable RecipeHolder<IdentifyingRecipe> recipeHolder, SingleRecipeInput singleRecipeInput, NonNullList<ItemStack> nonNullList, int maxStackSize)
    {
        if (nonNullList.getFirst().isEmpty() || recipeHolder == null)
            return false;

        ItemStack potResult = recipeHolder.value().assemble(singleRecipeInput, registryAccess);
        if (potResult.isEmpty())
            return false;

        ItemStack stackInOutput = nonNullList.get(2);
        if (stackInOutput.isEmpty())
            return true;

        if (!ItemStack.isSameItemSameComponents(potResult, stackInOutput))
            return false;

        if (potResult.getCount() < maxStackSize && stackInOutput.getCount() < stackInOutput.getMaxStackSize())
            return true;

        return stackInOutput.getCount() < potResult.getMaxStackSize();
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

        this.progress = valueInput.getShortOr("archeology_table", (short) 0);

        this.recipesUsed.clear();
        this.recipesUsed.putAll(valueInput.read("RecipesUsed", RECIPES_USED_CODEC).orElse(Map.of()));

        this.setChanged();
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
        return this.getBlockPos().getCenter();
    }

    @Override
    public float getVisualRotationYInDegrees()
    {
        return 0f;
    }
}
