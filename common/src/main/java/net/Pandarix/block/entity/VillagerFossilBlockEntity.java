package net.Pandarix.block.entity;

import net.Pandarix.BACommon;
import net.Pandarix.block.custom.VillagerFossilBlock;
import net.Pandarix.screen.FossilInventoryMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.StackedItemContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.ItemStack;
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

public class VillagerFossilBlockEntity extends BaseContainerBlockEntity implements ItemOwner, MenuProvider, StackedContentsCompatible, WorldlyContainer
{
    protected NonNullList<ItemStack> items;

    public VillagerFossilBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.VILLAGER_FOSSIL.get(), pos, state);
        this.items = NonNullList.withSize(1, ItemStack.EMPTY);
    }

    // INVENTORY HANDLING ──────────────────────────────────────────────────────────────────────────
    @Override
    public @NotNull NonNullList<ItemStack> getItems()
    {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> nonNullList)
    {
        for (int i = 0; i < this.items.size(); i++)
        {
            this.items.set(i, nonNullList.get(i));
        }
    }

    @Override
    public void setItem(int i, ItemStack itemStack)
    {
        this.items.set(i, itemStack);
        itemStack.limitSize(this.getMaxStackSize(itemStack));
        this.setChanged();
    }

    public void setInventory(List<ItemStack> inventory)
    {
        for (int i = 0; i < inventory.size(); i++)
        {
            items.set(i, inventory.get(i));

            if (this.level != null)
            {
                int luminance = Block.byItem(this.getItems().getFirst().getItem()).defaultBlockState().getLightEmission();
                this.level.setBlock(this.getBlockPos(), level.getBlockState(this.getBlockPos()).setValue(VillagerFossilBlock.INVENTORY_LUMINANCE, luminance), 3);
            }
        }
        this.setChanged();
    }

    @Override
    public int getContainerSize()
    {
        return 1;
    }

    @Override
    public int @NotNull [] getSlotsForFace(Direction direction)
    {
        return new int[0];
    }

    @Override
    public boolean canPlaceItemThroughFace(int i, ItemStack itemStack, @Nullable Direction direction)
    {
        return true;
    }

    @Override
    public boolean canTakeItemThroughFace(int i, ItemStack itemStack, Direction direction)
    {
        return true;
    }

    @Override
    public void fillStackedContents(StackedItemContents stackedItemContents)
    {
        this.items.forEach(stackedItemContents::accountStack);
    }

    // NETWORKING ──────────────────────────────────────────────────────────────────────────────────
    @Override
    protected void saveAdditional(ValueOutput valueOutput)
    {
        super.saveAdditional(valueOutput);
        ContainerHelper.saveAllItems(valueOutput, this.items);
    }

    @Override
    protected void loadAdditional(ValueInput valueInput)
    {
        super.loadAdditional(valueInput);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(valueInput, this.items);
        setChanged();
    }

    @Override
    public void setChanged()
    {
        if (this.level != null)
        {
            int luminance = Block.byItem(this.getItems().getFirst().getItem()).defaultBlockState().getLightEmission();
            BlockState newState = this.getBlockState().setValue(VillagerFossilBlock.INVENTORY_LUMINANCE, luminance);
            this.level.setBlock(this.getBlockPos(), newState, 3);
            level.sendBlockUpdated(worldPosition, this.getBlockState(), newState, 3);
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
    public CompoundTag getUpdateTag(HolderLookup.@NotNull Provider pRegistries)
    {
        try (ProblemReporter.ScopedCollector scopedCollector = new ProblemReporter.ScopedCollector(this.problemPath(), BACommon.LOGGER))
        {
            TagValueOutput tagValueOutput = TagValueOutput.createWithContext(scopedCollector, pRegistries);
            this.saveAdditional(tagValueOutput);
            return tagValueOutput.buildResult();
        }
    }

    // MISC ──────────────────────────────────────────────────────────────────────────────────
    @Override
    @NotNull
    public Component getDisplayName()
    {
        return Component.translatable("block.betterarcheology.villager_fossil");
    }

    @Override
    protected @NotNull Component getDefaultName()
    {
        return getDisplayName();
    }

    @Override
    @NotNull
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory)
    {
        return new FossilInventoryMenu(containerId, inventory, this);
    }

    @Override
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
