package net.Pandarix.block.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
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

import java.util.List;

public class GuardianFossilBodyBlock extends FossilBaseBodyBlock implements SimpleWaterloggedBlock
{
    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private static final VoxelShape GUARDIAN_SHAPE = Block.box(1, 0, 1, 15, 8, 15);

    public GuardianFossilBodyBlock(Properties settings)
    {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false).setValue(FACING, Direction.NORTH));
    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext)
    {
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
        return this.defaultBlockState().setValue(WATERLOGGED, Boolean.valueOf(fluidstate.isSourceOfType(Fluids.WATER))).setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @NotNull
    public FluidState getFluidState(BlockState pState)
    {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    @NotNull
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext)
    {
        return GUARDIAN_SHAPE;
    }

    @Override
    public void appendHoverText(ItemStack pStack, Item.TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag)
    {
        pTooltipComponents.add(Component.translatable("block.betterarcheology.guardian_fossil_body_tooltip").withStyle(ChatFormatting.GRAY).append(Component.translatable("block.betterarcheology.fossil_body_set").withStyle(ChatFormatting.BLUE)));
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }

    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> pBuilder)
    {
        pBuilder.add(WATERLOGGED, FACING);
    }
}
