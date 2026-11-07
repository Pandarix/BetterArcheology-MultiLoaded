package net.Pandarix.block.custom;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class SheepFossilBlock extends FossilBlock
{
    public static final BooleanProperty PLAYING = BooleanProperty.create("playing"); //true while sound is played and for the duration of "PLAY_COOLDOWN_TICKS"
    private static final int PLAY_COOLDOWN_TICKS = 80; //used to prevent sound-spamming
    private static final int MAX_HORN_SOUND = 7;
    public static final IntegerProperty HORN_SOUND = IntegerProperty.create("horn_sound", 0, MAX_HORN_SOUND); //index of the goat horn sound currently used
    private static final int PLAY_HORN_EVENT = 0;
    private static final double NOTE_PARTICLE_HEIGHT = 1.5;

    //Map of hitboxes for every direction the model can be facing
    private static final Map<Direction, VoxelShape> SHEEP_SHAPES_FOR_DIRECTION = ImmutableMap.of(
            Direction.NORTH, Shapes.or(
                    Block.box(4, 0, 4, 12, 17.75, 19),
                    Block.box(4, 9, 0, 12, 17.75, 4),
                    Block.box(3.75, 14, -7.5, 12, 25, 5)),
            Direction.SOUTH, Shapes.or(
                    Block.box(4, 0, -3, 12, 17.75, 12),
                    Block.box(4, 9, 12, 12, 17.75, 16),
                    Block.box(4, 14, 11, 12.25, 25, 23.5)),
            Direction.EAST, Shapes.or(
                    Block.box(-3, 0, 4, 12, 17.75, 12),
                    Block.box(12, 9, 4, 16, 17.75, 12),
                    Block.box(11, 14, 3.75, 23.5, 25, 12)),
            Direction.WEST, Shapes.or(
                    Block.box(4, 0, 4, 19, 17.75, 12),
                    Block.box(0, 9, 4, 4, 17.75, 12),
                    Block.box(-7.5, 14, 4, 5, 25, 12.25)));

    public SheepFossilBlock(BlockBehaviour.Properties settings)
    {
        super(settings, SHEEP_SHAPES_FOR_DIRECTION, true);
        this.registerDefaultState(this.defaultBlockState().setValue(HORN_SOUND, 0).setValue(PLAYING, false));
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block sourceBlock, @Nullable Orientation orientation, boolean movedByPiston)
    {
        boolean powered = level.hasNeighborSignal(pos) || level.hasNeighborSignal(pos.above());
        if (!powered || state.getValue(PLAYING)) return;

        startPlaying(state, level, pos);
    }

    /** Right-click tunes to the next goat horn sound and plays it. */
    @Override
    @NotNull
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult)
    {
        if (state.getValue(PLAYING)) return InteractionResult.FAIL;

        if (!level.isClientSide())
            startPlaying(state.cycle(HORN_SOUND), level, pos);
        return InteractionResult.SUCCESS;
    }

    private void startPlaying(BlockState state, Level level, BlockPos pos)
    {
        level.setBlock(pos, state.setValue(PLAYING, true), Block.UPDATE_ALL);
        // A block event runs triggerEvent on the server and on every client, like vanilla note blocks.
        level.blockEvent(pos, this, PLAY_HORN_EVENT, 0);
        level.scheduleTick(pos, this, PLAY_COOLDOWN_TICKS);
    }

    @Override
    protected boolean triggerEvent(BlockState state, Level level, BlockPos pos, int id, int param)
    {
        int hornSound = state.getValue(HORN_SOUND);
        // The note particle's x speed picks its colour from 0 to 1; one colour per horn sound.
        double noteColour = hornSound / (double) MAX_HORN_SOUND;
        level.addParticle(ParticleTypes.NOTE, pos.getX() + 0.5, pos.getY() + NOTE_PARTICLE_HEIGHT, pos.getZ() + 0.5, noteColour, 0, 0);
        level.playSound(null, pos, SoundEvents.GOAT_HORN_SOUND_VARIANTS.get(hornSound).value(), SoundSource.BLOCKS);
        return true;
    }

    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom)
    {
        super.tick(pState, pLevel, pPos, pRandom);
        pLevel.setBlock(pPos, pState.setValue(PLAYING, false), Block.UPDATE_ALL);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> pBuilder)
    {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(HORN_SOUND, PLAYING);
    }
}
