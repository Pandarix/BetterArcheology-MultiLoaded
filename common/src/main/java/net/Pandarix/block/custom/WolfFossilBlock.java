package net.Pandarix.block.custom;

import com.google.common.collect.ImmutableMap;
import net.Pandarix.block.entity.SkeletonFleeFromBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class WolfFossilBlock extends FossilBaseWithEntityBlock
{
    //Map of hitboxes for every direction the model can be facing
    private static final Map<Direction, VoxelShape> WOLF_SHAPES_FOR_DIRECTION = ImmutableMap.of(
            Direction.NORTH, Shapes.or(
                    Block.box(4, 0, 2, 12, 16, 17),
                    Block.box(4, 9, -6, 12, 16, 2)),
            Direction.SOUTH, Shapes.or(
                    Block.box(4, 0, -1, 12, 16, 14),
                    Block.box(4, 9, 14, 12, 16, 22)),
            Direction.EAST, Shapes.or(
                    Block.box(-1, 0, 4, 14, 16, 12),
                    Block.box(14, 9, 4, 22, 16, 12)),
            Direction.WEST, Shapes.or(
                    Block.box(2, 0, 4, 17, 16, 12),
                    Block.box(-6, 9, 4, 2, 16, 12)));

    public WolfFossilBlock(Properties settings)
    {
        super(settings);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new SkeletonFleeFromBlockEntity(pos, state);
    }

    @Override
    @NotNull
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext)
    {
        return WOLF_SHAPES_FOR_DIRECTION.get(pState.getValue(FACING));
    }

    @Override
    @NotNull
    public RenderShape getRenderShape(BlockState pState)
    {
        return RenderShape.MODEL;
    }
}
