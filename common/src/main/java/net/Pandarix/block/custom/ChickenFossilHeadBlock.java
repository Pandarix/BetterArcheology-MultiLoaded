package net.Pandarix.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class ChickenFossilHeadBlock extends FossilBaseHeadBlock
{
    private static final VoxelShape CHICKEN_HEAD_SHAPE = Block.box(4, 0, 4, 12, 4, 12);

    public ChickenFossilHeadBlock(Properties settings)
    {
        super(settings);
    }

    @Override
    @NotNull
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_)
    {
        return CHICKEN_HEAD_SHAPE;
    }
}
