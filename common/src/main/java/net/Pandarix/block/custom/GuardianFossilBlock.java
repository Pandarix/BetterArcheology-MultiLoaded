package net.Pandarix.block.custom;

import com.google.common.collect.ImmutableMap;
import net.Pandarix.block.entity.GuardianFossilBlockEntity;
import net.Pandarix.block.entity.ModBlockEntities;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class GuardianFossilBlock extends FossilBaseWithEntityBlock implements SimpleWaterloggedBlock
{
    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    //Map of hitboxes for every direction the model can be facing
    private static final Map<Direction, VoxelShape> SHAPES_FOR_DIRECTION = ImmutableMap.of(
            Direction.NORTH, Shapes.or(
                    Block.box(1, 0, -2, 15, 15, 12),
                    Block.box(4, 4, 12, 12, 12, 23),
                    Block.box(7, 2, 23, 9, 14, 32)),
            Direction.SOUTH, Shapes.or(
                    Block.box(1, 0, 4, 15, 15, 18),
                    Block.box(4, 4, -7, 12, 12, 4),
                    Block.box(7, 2, -16, 9, 14, -7)),
            Direction.WEST, Shapes.or(
                    Block.box(-2, 0, 1, 12, 15, 15),
                    Block.box(12, 4, 4, 23, 12, 12),
                    Block.box(23, 2, 7, 32, 14, 9)),
            Direction.EAST, Shapes.or(
                    Block.box(4, 0, 1, 18, 15, 15),
                    Block.box(-7, 4, 4, 4, 12, 12),
                    Block.box(-16, 2, 7, -7, 14, 9)));

    public GuardianFossilBlock(Properties settings)
    {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, Boolean.FALSE).setValue(FACING, Direction.NORTH));
    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext)
    {
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
        return this.defaultBlockState().setValue(WATERLOGGED, fluidstate.isSourceOfType(Fluids.WATER)).setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @NotNull
    public FluidState getFluidState(BlockState pState)
    {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type)
    {
        return createTickerHelper(type, ModBlockEntities.GUARDIAN_FOSSIL.get(), GuardianFossilBlockEntity::tick);
    }

    @NotNull
    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext)
    {
        return SHAPES_FOR_DIRECTION.get(blockState.getValue(FACING));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new GuardianFossilBlockEntity(pos, state);
    }

    @Override
    @NotNull
    public RenderShape getRenderShape(BlockState pState)
    {
        return RenderShape.MODEL;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> component, TooltipFlag flag)
    {
        component.add(Component.translatable("block.betterarcheology.guardian_fossil_tooltip").withStyle(ChatFormatting.GRAY));
        super.appendHoverText(stack, context, component, flag);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
        pBuilder.add(WATERLOGGED, FACING);
    }
}
