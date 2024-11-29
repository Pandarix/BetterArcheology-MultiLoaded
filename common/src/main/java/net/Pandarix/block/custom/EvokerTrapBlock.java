package net.Pandarix.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.projectile.EvokerFangs;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EvokerTrapBlock extends HorizontalDirectionalBlock
{
    public static final MapCodec<EvokerTrapBlock> CODEC = simpleCodec(EvokerTrapBlock::new);

    @Override
    @NotNull
    protected MapCodec<? extends HorizontalDirectionalBlock> codec()
    {
        return CODEC;
    }

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty TRIGGERED = BooleanProperty.create("triggered");
    private static final int fangCooldown = 40; //cooldown used to prevent Fang-spamming
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active"); //active as long as fangs are spawning and for the duration of fangcooldown

    public EvokerTrapBlock(Properties settings)
    {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(TRIGGERED, false).setValue(ACTIVE, false));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx)
    {
        return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite());
    }

    @Override
    public void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, Block block, BlockPos sourcePos, boolean notify)
    {
        super.neighborChanged(blockState, level, blockPos, block, sourcePos, notify);
        boolean powered = level.hasNeighborSignal(blockPos) || level.hasNeighborSignal(blockPos.above());
        boolean active = blockState.getValue(ACTIVE);

        //if the Block is receiving a redstone signal and is not already activated
        if (powered && !active)
        {
            //set self to active state and spawn fangs
            level.setBlock(blockPos, blockState.setValue(ACTIVE, true), 3);
            spawnFangs(blockState, level, blockPos, level.getRandom());
            //set cooldown for active-state to be reset
            level.scheduleTick(blockPos, this, fangCooldown);
        } else if (!powered && active)
        {
            level.setBlock(blockPos, blockState.setValue(TRIGGERED, false), 4);
        }
    }

    //spawns 3 evoker fangs in a straight line in the direction the block is facing
    private void spawnFangs(BlockState state, Level level, BlockPos pos, RandomSource random)
    {
        if (level.isClientSide())
        {
            return;
        }

        int maxFangs = 3;

        //spawns fangs with individual positional increase depending on the direction the block is facing
        switch (state.getValue(FACING))
        {
            case NORTH ->
            {
                for (int i = 0; i < maxFangs; ++i)
                {
                    level.addFreshEntity(new EvokerFangs(level, pos.getX() + 0.5, pos.getY(), pos.getZ() - 0.5 - i * 1.5, (float) Math.toRadians(90), 0, null));
                }
            }
            case SOUTH ->
            {
                for (int i = 0; i < maxFangs; ++i)
                {
                    level.addFreshEntity(new EvokerFangs(level, pos.getX() + 0.5, pos.getY(), pos.getZ() + 1.5 + i * 1.5, (float) Math.toRadians(90), 0, null));
                }
            }
            case EAST ->
            {
                for (int i = 0; i < maxFangs; ++i)
                {
                    level.addFreshEntity(new EvokerFangs(level, pos.getX() + 1.5 + i * 1.5, pos.getY(), pos.getZ() + 0.5, 0, 0, null));
                }
            }
            case WEST ->
            {
                for (int i = 0; i < maxFangs; ++i)
                {
                    level.addFreshEntity(new EvokerFangs(level, pos.getX() - 0.5 - i * 1.5, pos.getY(), pos.getZ() + 0.5, 0, 0, null));
                }
            }
            default ->
            {
                for (int i = 0; i < maxFangs; ++i)
                {
                    level.addFreshEntity(new EvokerFangs(level, pos.getX() + 0.5, pos.getY(), pos.getZ() - 0.5 - i * 1.5, 0, 0, null));
                }
            }
        }
    }

    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom)
    {
        super.tick(pState, pLevel, pPos, pRandom);
        pLevel.setBlock(pPos, pState.setValue(ACTIVE, false), 3);
    }

    @Override
    @NotNull
    public BlockState rotate(BlockState state, Rotation rotation)
    {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    @NotNull
    public BlockState mirror(BlockState state, Mirror mirror)
    {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(FACING, TRIGGERED, ACTIVE);
    }
}
