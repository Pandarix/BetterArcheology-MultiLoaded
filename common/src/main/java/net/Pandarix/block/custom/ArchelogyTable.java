package net.Pandarix.block.custom;

import com.mojang.serialization.MapCodec;
import net.Pandarix.block.entity.ArcheologyTableBlockEntity;
import net.Pandarix.block.entity.ModBlockEntities;
import net.Pandarix.item.BetterBrushItem;
import net.Pandarix.util.ServerPlayerHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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

public class ArchelogyTable extends BaseEntityBlock
{
    public static final MapCodec<ArchelogyTable> CODEC = simpleCodec(ArchelogyTable::new);

    @Override
    @NotNull
    protected MapCodec<? extends BaseEntityBlock> codec()
    {
        return CODEC;
    }

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
                ServerPlayerHelper.tryOpenScreen(pPlayer, (ArcheologyTableBlockEntity) entity);
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

    public void addDustParticles(Level pLevel, BlockPos pos, RandomSource random)
    {
        ArcheologyTableBlockEntity entity = (ArcheologyTableBlockEntity) pLevel.getBlockEntity(pos);
        if (entity == null) return;

        ItemStack brush = entity.getItems().getFirst();
        int brushSpeed = brush.getItem() instanceof BetterBrushItem brushItem ? brushItem.getBrushingSpeed() : 10;

        //play sound every Xth tick
        if (entity.getProgress() % brushSpeed == 0)
            pLevel.playSound(null, pos, SoundEvents.BRUSH_GENERIC, SoundSource.BLOCKS, 0.25f, 1f);

        int i = random.nextIntBetweenInclusive(1, 3); //number of particles to be created
        BlockParticleOption blockStateParticleEffect = new BlockParticleOption(ParticleTypes.BLOCK, Blocks.SAND.defaultBlockState());
        for (int j = 0; j < i; ++j)
        {
            pLevel.addParticle(blockStateParticleEffect,
                    pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5,
                    3.0 * random.nextDouble() * (random.nextBoolean() ? 1 : -1),
                    0.0,
                    3.0 * random.nextDouble() * (random.nextBoolean() ? 1 : -1));
        }
    }
}

