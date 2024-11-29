package net.Pandarix.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SkeletonFleeFromBlockEntity extends BlockEntity
{
    //This is used to add a FleeGoal for Mobs, Blocks extending this class can be fled from
    public SkeletonFleeFromBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.SKELETON_FLEE_FROM.get(), pos, state);
    }
}
