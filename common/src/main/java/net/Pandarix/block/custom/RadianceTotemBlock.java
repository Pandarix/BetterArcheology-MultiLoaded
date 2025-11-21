package net.Pandarix.block.custom;

import com.mojang.serialization.MapCodec;
import net.Pandarix.block.entity.ModBlockEntities;
import net.Pandarix.block.entity.RadianceTotemBlockEntity;
import net.Pandarix.config.BAConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RadianceTotemBlock extends BaseEntityBlock
{
    public static final BooleanProperty HANGING = BlockStateProperties.HANGING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final IntegerProperty SELECTOR = IntegerProperty.create("selector", 0, 3);
    public static final MapCodec<RadianceTotemBlock> CODEC = simpleCodec(RadianceTotemBlock::new);

    protected static final VoxelShape AABB = Shapes.or(Block.box(5.0D, 0.0D, 5.0D, 11.0D, 7.0D, 11.0D), Block.box(6.0D, 7.0D, 6.0D, 10.0D, 9.0D, 10.0D));
    protected static final VoxelShape HANGING_AABB = Shapes.or(Block.box(5.0D, 1.0D, 5.0D, 11.0D, 8.0D, 11.0D), Block.box(6.0D, 8.0D, 6.0D, 10.0D, 10.0D, 10.0D));

    public RadianceTotemBlock(Properties pProperties)
    {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(SELECTOR, 0));
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type)
    {
        return createTickerHelper(type, ModBlockEntities.RADIANCE_TOTEM.get(), RadianceTotemBlockEntity::tick);
    }

    @Override
    @NotNull
    public RenderShape getRenderShape(BlockState pState)
    {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new RadianceTotemBlockEntity(pos, state);
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom)
    {
        super.animateTick(pState, pLevel, pPos, pRandom);
        if (pRandom.nextBoolean() && pLevel.isClientSide())
        {
            pLevel.addParticle(ParticleTypes.GLOW,
                    pPos.getCenter().x() + randomDirectionModifier(pRandom, 3),
                    pPos.getCenter().y() - 0.25 + randomDirectionModifier(pRandom, 5),
                    pPos.getCenter().z() + randomDirectionModifier(pRandom, 3), 0, -4, 0);
        }
    }

    @Override
    @NotNull
    public InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult)
    {
        if (!pState.is(this))
            return InteractionResult.PASS;

        // if feature is disabled, notify the user and skip
        if (!BAConfig.radianceTotemEnabled || !BAConfig.totemsEnabled)
        {
            if (pLevel.isClientSide())
                pPlayer.displayClientMessage(Component.translatableWithFallback("config.notify.disabled", "This feature has been disabled in the config!"), true);

            return InteractionResult.PASS;
        }
        BlockState newState = pState.cycle(SELECTOR);
        pLevel.setBlock(pPos, newState, 3);

        if (pLevel.isClientSide())
        {
            pPlayer.displayClientMessage(Component.translatable("block.betterarcheology.radiance_totem_message_" + newState.getValue(SELECTOR)).withStyle(ChatFormatting.GREEN), true);
            pLevel.playLocalSound(pPos, SoundEvents.AMETHYST_BLOCK_RESONATE, SoundSource.BLOCKS, 0.5f, 0.5f, false);
            RandomSource random = pLevel.getRandom();
            for (int i = 0; i <= 10; i++)
            {
                pLevel.addParticle(ParticleTypes.GLOW,
                        pPos.getCenter().x() + randomDirectionModifier(random, 3),
                        pPos.getCenter().y() - 0.25 + randomDirectionModifier(random, 5),
                        pPos.getCenter().z() + randomDirectionModifier(random, 3), 0, -4, 0);
            }

        }
        return InteractionResult.SUCCESS_SERVER;
    }

    @Override
    public boolean isRandomlyTicking(BlockState pState)
    {
        return true;
    }

    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom)
    {
        super.tick(pState, pLevel, pPos, pRandom);
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom)
    {
        super.randomTick(pState, pLevel, pPos, pRandom);
    }

    private static float randomDirectionModifier(RandomSource pRandom, int pReduce)
    {
        return ((pRandom.nextFloat() / pReduce) * pRandom.nextIntBetweenInclusive(-1, 1));
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext)
    {
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());

        for (Direction direction : pContext.getNearestLookingDirections())
        {
            if (direction.getAxis() == Direction.Axis.Y)
            {
                BlockState blockstate = this.defaultBlockState().setValue(HANGING, direction == Direction.UP);
                if (blockstate.canSurvive(pContext.getLevel(), pContext.getClickedPos()))
                {
                    return blockstate.setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
                }
            }
        }

        return null;
    }

    @NotNull
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext)
    {
        return pState.getValue(HANGING) ? HANGING_AABB : AABB;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
        pBuilder.add(HANGING, WATERLOGGED, SELECTOR);
    }

    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos)
    {
        Direction direction = getConnectedDirection(pState).getOpposite();
        return Block.canSupportCenter(pLevel, pPos.relative(direction), direction.getOpposite());
    }

    protected static Direction getConnectedDirection(BlockState pState)
    {
        return pState.getValue(HANGING) ? Direction.DOWN : Direction.UP;
    }

    /**
     * Update the provided state given the provided neighbor direction and neighbor state, returning a new state.
     * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
     * returns its solidified counterpart.
     * Note that this method should ideally consider only the specific direction passed in.
     */
    @Override
    protected @NotNull BlockState updateShape(BlockState blockState, LevelReader levelReader, ScheduledTickAccess scheduledTickAccess, BlockPos blockPos, Direction direction, BlockPos blockPos2, BlockState blockState2, RandomSource randomSource)
    {
        if (blockState.getValue(WATERLOGGED))
        {
            scheduledTickAccess.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelReader));
        }

        return getConnectedDirection(blockState).getOpposite() == direction && !blockState.canSurvive(levelReader, blockPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(blockState, levelReader, scheduledTickAccess, blockPos, direction, blockPos2, blockState2, randomSource);
    }

    @NotNull
    public FluidState getFluidState(BlockState pState)
    {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    @Override
    protected boolean isPathfindable(BlockState blockState, PathComputationType pathComputationType)
    {
        return false;
    }

    @Override
    @NotNull
    protected MapCodec<? extends BaseEntityBlock> codec()
    {
        return CODEC;
    }
}
