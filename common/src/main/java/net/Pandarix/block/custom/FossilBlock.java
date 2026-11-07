package net.Pandarix.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * Every fossil block: assembled fossils, heads and bodies. Faces away from the player when
 * placed, rotates with structures and can be waterlogged.
 */
public class FossilBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock
{
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final float BREAK_SOUND_VOLUME = 0.1f;
    private static final float BREAK_SOUND_PITCH = 0.35f;

    private final Map<Direction, VoxelShape> shapes;
    private final boolean rattlesWhenBroken;

    public FossilBlock(Properties settings, Map<Direction, VoxelShape> shapes, boolean rattlesWhenBroken)
    {
        super(settings);
        this.shapes = shapes;
        this.rattlesWhenBroken = rattlesWhenBroken;
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false));
    }

    @Override
    @NotNull
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return shapes.get(state.getValue(FACING));
    }

    @Override
    public void destroy(LevelAccessor level, BlockPos pos, BlockState state)
    {
        if (rattlesWhenBroken && !level.isClientSide())
            level.playSound(null, pos, SoundEvents.SKELETON_HURT, SoundSource.BLOCKS, BREAK_SOUND_VOLUME, BREAK_SOUND_PITCH);
        super.destroy(level, pos, state);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx)
    {
        boolean inWater = ctx.getLevel().getFluidState(ctx.getClickedPos()).getType() == Fluids.WATER;
        return this.defaultBlockState()
                .setValue(FACING, ctx.getHorizontalDirection().getOpposite())
                .setValue(WATERLOGGED, inWater);
    }

    @Override
    @NotNull
    protected FluidState getFluidState(BlockState state)
    {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    @NotNull
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random)
    {
        // Keeps water inside the fossil flowing into neighbours, as vanilla waterloggable blocks do.
        if (state.getValue(WATERLOGGED))
            ticks.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        return super.updateShape(state, level, ticks, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, WATERLOGGED);
    }
}
