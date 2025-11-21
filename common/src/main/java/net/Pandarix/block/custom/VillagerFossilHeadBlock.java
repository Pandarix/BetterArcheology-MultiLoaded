package net.Pandarix.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class VillagerFossilHeadBlock extends FossilBaseHeadBlock
{
    private static final VoxelShape VILLAGER_HEAD_SHAPE = Block.box(3, 0, 3, 13, 10, 13);

    public VillagerFossilHeadBlock(BlockBehaviour.Properties settings)
    {
        super(settings);
    }

    @Override
    @NotNull
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext)
    {
        return VILLAGER_HEAD_SHAPE;
    }
}
