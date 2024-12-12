package net.Pandarix.block.entity;

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
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class VillagerFossilBlockEntity extends BaseContainerBlockEntity implements MenuProvider, StackedContentsCompatible, WorldlyContainer
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
    public void fillStackedContents(StackedContents stackedContents)
    {
        this.items.forEach(stackedContents::accountStack);
    }

    // NETWORKING ──────────────────────────────────────────────────────────────────────────────────
    @Override
    protected void saveAdditional(CompoundTag nbt, HolderLookup.Provider pRegistries)
    {
        super.saveAdditional(nbt, pRegistries);

        ContainerHelper.saveAllItems(nbt, this.items, pRegistries);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries)
    {
        super.loadAdditional(pTag, pRegistries);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(pTag, this.items, pRegistries);
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
    public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries)
    {
        CompoundTag nbt = super.getUpdateTag(pRegistries);
        this.saveAdditional(nbt, pRegistries);
        return nbt;
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
}
