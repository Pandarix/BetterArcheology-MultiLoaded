package net.Pandarix.block.entity;

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.Pandarix.BACommon;
import net.Pandarix.block.custom.ArchelogyTable;
import net.Pandarix.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeCraftingHolder;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.BrushItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ArcheologyTableBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, RecipeCraftingHolder, StackedContentsCompatible
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
    //loottable for crafting results
    protected static final ResourceKey<LootTable> CRAFTING_LOOT = ResourceKey.create(Registries.LOOT_TABLE, BACommon.createResource("identifying_loot"));
    private final Object2IntOpenHashMap<ResourceLocation> recipesUsed;

    // PROGRESS ──────────────────────────────────────────────────────────────────────────
    //synchronises Ints between server and client
    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 72;

    public ArcheologyTableBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.ARCHEOLOGY_TABLE.get(), pos, state);
        this.items = NonNullList.withSize(INV_SIZE, ItemStack.EMPTY);
        this.recipesUsed = new Object2IntOpenHashMap<>();
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
    }

    // CRAFTING-RELATED ──────────────────────────────────────────────────────────────────────────
    @Override
    @NotNull
    protected AbstractContainerMenu createMenu(int id, Inventory inventory)
    {
        return new IdentifyingMenu(id, inventory, this, this.data);
    }

    private void resetProgress()
    {
        this.progress = 0;
    }

    public static void tick(Level world, BlockPos blockPos, BlockState blockState, ArcheologyTableBlockEntity entity)
    {
        //don't do anything clientside
        if (world.isClientSide())
        {
            return;
        }

        //if the entity has a recipe inside:
        if (hasRecipe(entity))
        {
            //play sound every 10th tick
            if (entity.progress % 10 == 0)
            {
                world.playSound(null, entity.worldPosition, SoundEvents.BRUSH_GENERIC, SoundSource.BLOCKS, 0.25f, 1f);
            }

            //if the recipe is inside, et self state to dusting
            world.setBlock(blockPos, blockState.setValue(ArchelogyTable.DUSTING, true), 3);
            entity.progress++; //increase progress
            setChanged(world, blockPos, blockState);
            //if crafting progress is bigger or as big as the maxProgress, then craft the Item, else reset the timer
            if (entity.progress >= entity.maxProgress)
            {
                entity.craftItem();
            }
        } else
        {
            world.setBlock(blockPos, blockState.setValue(ArchelogyTable.DUSTING, false), 3);
            entity.resetProgress();
            setChanged(world, blockPos, blockState);
        }
    }

    @Override
    public void setRecipeUsed(@Nullable RecipeHolder<?> recipeHolder)
    {
        if (recipeHolder != null) {
            ResourceLocation resourceLocation = recipeHolder.id();
            this.recipesUsed.addTo(resourceLocation, 1);
        }
    }

    @Nullable
    @Override
    public RecipeHolder<?> getRecipeUsed()
    {
        return null;
    }

    private void craftItem()
    {
        //if there is an unidentified artifact in the input slot and the output slot is empty:
        if (hasRecipe(this) && this.items.get(2).isEmpty())
        {
            //remove input from slot
            ItemStack stack = this.items.get(1);
            stack.shrink(1);
            this.items.set(1, stack);
            ItemStack brush = this.items.get(0);
            int newDamage = brush.getDamageValue() + 1; //calculate new Damage Value the item would have

            //if the item is supposed to break or the durability is smaller than zero
            if (newDamage > brush.getMaxDamage())
            {
                ItemStack brushStack = this.items.get(0);
                brushStack.shrink(1);
                this.items.set(0, brushStack);   //remove the Item
                assert this.level != null;
                if (!this.level.isClientSide())
                {
                    //play break sound
                    this.level.playSound(null, this.worldPosition, SoundEvents.ITEM_BREAK, SoundSource.BLOCKS, 0.25f, 1f);
                }
            } else
            {
                //if not, set the damage to the calculated damage above
                brush.setDamageValue(newDamage);
            }

            //if on server
            if (this.level != null && !this.level.isClientSide())
            {
                //play sound after crafting
                this.level.playSound(null, this.worldPosition, SoundEvents.BRUSH_SAND_COMPLETED, SoundSource.BLOCKS, 0.5f, 1f);
            }
            this.items.set(2, generateCraftingLoot(this, this.level)); //set crafted output in the output slot
            this.resetProgress(); //resets crafting progress
            this.setChanged();
        }
    }

    private ItemStack generateCraftingLoot(BlockEntity entity, Level level)
    {
        //if on server and there is a Server(World)
        if (level != null && !level.isClientSide() && level.getServer() != null)
        {
            //gets loot-table based on .json loot
            LootTable lootTable = level.getServer().reloadableRegistries().getLootTable(CRAFTING_LOOT);
            //parameters for determining loot such as luck, origin and position
            LootParams lootparams = (new LootParams.Builder((ServerLevel) level)).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(entity.getBlockPos())).withLuck(0).create(LootContextParamSets.CHEST);

            //returns ArrayList of ItemStacks that get generated by the LootTable
            ObjectArrayList<ItemStack> objectArrayList = lootTable.getRandomItems(lootparams, level.random.nextLong());

            //return first LootTable entry as crafting output
            if (objectArrayList.isEmpty())
            {
                return ItemStack.EMPTY;
            }
            if (objectArrayList.size() == 1)
            {
                return objectArrayList.getFirst();
            }
        }
        return ItemStack.EMPTY;
    }

    private static boolean hasRecipe(ArcheologyTableBlockEntity entity)
    {
        boolean hasShardInFirstSlot = entity.items.get(1).is(ModItems.UNIDENTIFIED_ARTIFACT.get());                     //Input
        Item itemInSlot0 = entity.items.get(0).getItem();
        boolean hasBrushInSlot = itemInSlot0 instanceof BrushItem;
        return hasShardInFirstSlot && hasBrushInSlot && canInsertAmountIntoOutputSlot(entity.items) && canInsertItemIntoOutputSlot(entity.items, entity.items.get(2).getItem());
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
    public boolean canPlaceItemThroughFace(int slot, ItemStack itemStack, @Nullable Direction direction)
    {
        //no insertion into the output slot
        if (direction == Direction.DOWN)
        {
            return false;
        }
        //if the top is targeted and the item is a Brush, insert
        if (direction == Direction.UP)
        {
            return slot == 0 && itemStack.getItem() instanceof BrushItem;
        }
        //for the sides: if it is an unidentified artifact
        return slot == 1 && itemStack.is(ModItems.UNIDENTIFIED_ARTIFACT.get());
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack itemStack, Direction direction)
    {
        //only extract on the bottom
        return direction == Direction.DOWN && slot == 2;
    }

    private static boolean canInsertItemIntoOutputSlot(NonNullList<ItemStack> handler, Item output)
    {
        return handler.get(2).getItem() == output || handler.get(2).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(NonNullList<ItemStack> items)
    {
        return items.get(2).getMaxStackSize() > items.get(2).getCount();
    }

    @NotNull
    protected NonNullList<ItemStack> getItems()
    {
        return this.items;
    }

    protected void setItems(NonNullList<ItemStack> nonNullList)
    {
        this.items = nonNullList;
    }

    public void setItem(int i, ItemStack itemStack)
    {
        ItemStack itemStack2 = this.items.get(i);
        boolean isValid = !itemStack.isEmpty() && ItemStack.isSameItemSameComponents(itemStack2, itemStack);
        this.items.set(i, itemStack);
        itemStack.limitSize(this.getMaxStackSize(itemStack));
        if (i == 0 && !isValid)
        {
            this.setChanged();
        }

    }

    public int getContainerSize()
    {
        return this.items.size();
    }

    @Override
    public void fillStackedContents(StackedContents stackedContents)
    {
        this.items.forEach(stackedContents::accountStack);
    }

    // NETWORKING ──────────────────────────────────────────────────────────────────────────
    @Override
    public void setChanged()
    {
        if (this.level != null)
        {
            level.sendBlockUpdated(worldPosition, this.getBlockState(), this.getBlockState(), 3);
        }
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
    public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries)
    {
        CompoundTag nbt = super.getUpdateTag(pRegistries);
        this.saveAdditional(nbt, pRegistries);
        return nbt;
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries)
    {
        super.saveAdditional(pTag, pRegistries);

        ContainerHelper.saveAllItems(pTag, this.items, pRegistries);

        pTag.putInt("archeology_table.progress", progress);

        CompoundTag recipesUsed = new CompoundTag();
        this.recipesUsed.forEach((resourceLocation, integer) ->
                recipesUsed.putInt(resourceLocation.toString(), integer));
        pTag.put("RecipesUsed", recipesUsed);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries)
    {
        super.loadAdditional(pTag, pRegistries);

        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(pTag, this.items, pRegistries);

        progress = pTag.getInt("archeology_table");

        CompoundTag recipesUsed = pTag.getCompound("RecipesUsed");
        recipesUsed.getAllKeys().forEach(string ->
                this.recipesUsed.put(ResourceLocation.parse(string), recipesUsed.getInt(string)));

        setChanged();
    }

    // MISC ──────────────────────────────────────────────────────────────────────────
    @Override
    @NotNull
    public Component getDisplayName()
    {
        return Component.translatable(BACommon.createResource("archeology_table").toLanguageKey());
    }

    @Override
    @NotNull
    protected Component getDefaultName()
    {
        return getDisplayName();
    }
}
