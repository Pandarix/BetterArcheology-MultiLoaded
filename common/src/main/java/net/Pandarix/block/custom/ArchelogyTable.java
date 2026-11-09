package net.Pandarix.block.custom;

import net.Pandarix.block.entity.ArcheologyTableBlockEntity;
import net.Pandarix.block.entity.ModBlockEntities;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.IntStream;

public class ArchelogyTable extends BaseEntityBlock
{
    // Sand dust while brushing: particles per tick and their maximum outward speed.
    private static final int MIN_DUST_PARTICLES = 1;
    private static final int MAX_DUST_PARTICLES = 3;
    private static final double DUST_SPEED = 3.0;

    //indicates if the table is currently "crafting" the identified artifact
    //triggers particle creation
    public static final BooleanProperty DUSTING = BooleanProperty.create("dusting");

    public ArchelogyTable(Properties settings)
    {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(DUSTING, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(DUSTING);
    }

    /* BLOCK ENTITY STUFF */
    @Override
    @NotNull
    public RenderShape getRenderShape(BlockState pState)
    {
        return RenderShape.MODEL;
    }

    @Override
    public void destroy(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState)
    {
        super.destroy(levelAccessor, blockPos, blockState);
    }

    @Override
    @NotNull
    public InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult)
    {
        if (!pLevel.isClientSide())
        {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if (entity instanceof ArcheologyTableBlockEntity)
            {
                if (pPlayer instanceof ServerPlayer serverPlayer)
                    serverPlayer.openMenu((ArcheologyTableBlockEntity) entity);
            } else
            {
                throw new IllegalStateException("Container Provider Missing!");
            }
        }
        return InteractionResult.SUCCESS_SERVER;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new ArcheologyTableBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType)
    {
        return createTickerHelper(pBlockEntityType, ModBlockEntities.ARCHEOLOGY_TABLE.get(), ArcheologyTableBlockEntity::tick);
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom)
    {
        if (pLevel.isClientSide() && pState.getValue(DUSTING))
            addDustParticles(pLevel, pPos, pRandom);

        super.animateTick(pState, pLevel, pPos, pRandom);
    }

    private void addDustParticles(Level pLevel, BlockPos pos, RandomSource random)
    {
        int particleCount = random.nextIntBetweenInclusive(MIN_DUST_PARTICLES, MAX_DUST_PARTICLES);
        BlockParticleOption sandDust = new BlockParticleOption(ParticleTypes.BLOCK, Blocks.SAND.defaultBlockState());
        IntStream.range(0, particleCount).forEach(i -> pLevel.addParticle(sandDust,
                pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5,
                DUST_SPEED * random.nextDouble() * (random.nextBoolean() ? 1 : -1),
                0.0,
                DUST_SPEED * random.nextDouble() * (random.nextBoolean() ? 1 : -1)));
    }
}

