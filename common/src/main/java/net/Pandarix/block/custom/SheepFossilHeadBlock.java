package net.Pandarix.block.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SheepFossilHeadBlock extends FossilBaseHeadBlock
{
    private static final VoxelShape SHEEP_HEAD_SHAPE = Block.box(3, 0, 3, 13, 8, 13);

    public SheepFossilHeadBlock(Properties settings)
    {
        super(settings);
    }

    @Override
    @NotNull
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext)
    {
        return SHEEP_HEAD_SHAPE;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> components, TooltipFlag tooltipFlag)
    {
        components.add(Component.translatable("block.betterarcheology.sheep_fossil_head_tooltip").withStyle(ChatFormatting.GRAY).append(Component.translatable("block.betterarcheology.fossil_head_set").withStyle(ChatFormatting.BLUE)));
        super.appendHoverText(stack, context, components, tooltipFlag);
    }
}
