package net.Pandarix.block.custom;

import com.google.common.collect.ImmutableMap;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import java.util.Map;

public class VillagerFossilBodyBlock extends FossilBaseBodyBlock
{
    //Map of hitboxes for every direction the model can be facing
    private static final Map<Direction, VoxelShape> SHAPES_FOR_DIRECTION = ImmutableMap.of(
            Direction.NORTH, Block.box(0, 0, 8, 16, 12, 15),
            Direction.SOUTH, Block.box(0, 0, 1, 16, 12, 8),
            Direction.EAST, Block.box(1, 0, 0, 8, 12, 16),
            Direction.WEST, Block.box(8, 0, 0, 15, 12, 16));

    public VillagerFossilBodyBlock(Properties settings)
    {
        super(settings);
    }

    @Override
    @NotNull
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext)
    {
        return SHAPES_FOR_DIRECTION.get(pState.getValue(FACING));
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> components, TooltipFlag tooltipFlag)
    {
        components.add(Component.translatable("block.betterarcheology.villager_fossil_body_tooltip").withStyle(ChatFormatting.GRAY).append(Component.translatable("block.betterarcheology.fossil_body_set").withStyle(ChatFormatting.BLUE)));
        super.appendHoverText(stack, context, components, tooltipFlag);
    }
}
