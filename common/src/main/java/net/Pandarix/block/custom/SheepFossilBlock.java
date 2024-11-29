package net.Pandarix.block.custom;

import com.google.common.collect.ImmutableMap;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class SheepFossilBlock extends FossilBaseBlock
{
    public static final BooleanProperty PLAYING = BooleanProperty.create("playing"); //true while sound is played and for the duration of "playCooldown"
    private static final int playCooldown = 80; //used to prevent sound-spamming
    public static final IntegerProperty HORN_SOUND = IntegerProperty.create("horn_sound", 0, 7); //index of the goat horn sound currently used

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
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(HORN_SOUND, 0).setValue(PLAYING, false));
    }

    @Override
    @NotNull
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext)
    {
        return SHEEP_SHAPES_FOR_DIRECTION.get(pState.getValue(FACING));
    }

    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify)
    {
        boolean powered = level.hasNeighborSignal(pos) || level.hasNeighborSignal(pos.above());
        boolean playing = state.getValue(PLAYING);

        if (powered && !playing)
        {
            //play sound and set state to playing
            if (!level.isClientSide())
            {
                level.playSound(null, pos, SoundEvents.GOAT_HORN_SOUND_VARIANTS.get(state.getValue(HORN_SOUND)).value(), SoundSource.BLOCKS);
            }
            level.setBlock(pos, state.setValue(PLAYING, true), 3);
            //set cooldown for playing to be reset
            level.scheduleTick(pos, this, playCooldown);
        }
    }

    //used to tune the SheepFossilBlock to an Index of the GoatHornsSounds
    @Override
    @NotNull
    public InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult)
    {
        //if sound is already being played, abort
        if (pState.getValue(PLAYING))
        {
            return InteractionResult.FAIL;
        }

        if (!pLevel.isClientSide())
        {
            //increase index or set to 0 if at max
            if (pState.getValue(HORN_SOUND) + 1 <= 7)
            {
                pLevel.setBlock(pPos, pState.setValue(HORN_SOUND, pState.getValue(HORN_SOUND) + 1).setValue(PLAYING, true), 3);
            } else
            {
                pLevel.setBlock(pPos, pState.setValue(HORN_SOUND, 0).setValue(PLAYING, true), 3);
            }

            //play sound and set cooldown to reset "playing" property
            pLevel.playSound(null, pPos, SoundEvents.GOAT_HORN_SOUND_VARIANTS.get(pLevel.getBlockState(pPos).getValue(HORN_SOUND)).value(), SoundSource.BLOCKS);
            pLevel.scheduleTick(pPos, this, playCooldown);
        } else
        {
            //if on clientside, display a note particle
            pLevel.addParticle(ParticleTypes.NOTE, pPos.getX() + 0.5, pPos.getY() + 1.5, pPos.getZ() + 0.5, 0, 0.2, 0);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom)
    {
        super.tick(pState, pLevel, pPos, pRandom);
        pLevel.setBlock(pPos, pState.setValue(PLAYING, false), 3);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> pBuilder)
    {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(HORN_SOUND, PLAYING);
    }

    @Override
    public void appendHoverText(ItemStack pStack, Item.TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag)
    {
        pTooltipComponents.add(Component.translatable("block.betterarcheology.sheep_fossil_tooltip").withStyle(ChatFormatting.GRAY));
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }
}
