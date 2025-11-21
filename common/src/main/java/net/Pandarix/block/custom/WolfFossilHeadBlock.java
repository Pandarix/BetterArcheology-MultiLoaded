package net.Pandarix.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class WolfFossilHeadBlock extends FossilBaseHeadBlock
{
    private static final VoxelShape WOLF_HEAD_SHAPE = Block.box(4, 0, 4, 12, 6, 12);

    public WolfFossilHeadBlock(Properties settings)
    {
        super(settings);
    }

    @Override
    @NotNull
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext)
    {
        return WOLF_HEAD_SHAPE;
    }
}
