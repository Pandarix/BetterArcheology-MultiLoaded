package net.Pandarix.block.entity.state;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.core.Direction;

public class VillagerFossilRenderState extends BlockEntityRenderState
{
    public ItemStackRenderState[] items = new ItemStackRenderState[1];
    public Direction facing = Direction.NORTH;
}
