package net.Pandarix.block.custom;

import com.google.common.collect.ImmutableMap;
import net.Pandarix.block.entity.ChickenFossilBlockEntity;
import net.Pandarix.block.entity.ModBlockEntities;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class ChickenFossilBlock extends FossilBaseWithEntityBlock
{
    //Map of hitboxes for every direction the model can be facing
    private static final Map<Direction, VoxelShape> CHICKEN_SHAPES_FOR_DIRECTION = ImmutableMap.of(Direction.NORTH, Shapes.or(Block.box(5, 0, 1.5, 11, 11.25, 12.25), Block.box(6.5, 11.25, -4, 9.5, 16, 3), Block.box(7, 8.25, 12.25, 9, 10, 22.25)), Direction.SOUTH, Shapes.or(Block.box(5, 0, 3.75, 11, 11.25, 14.5), Block.box(6.5, 11.25, 13, 9.5, 16, 20), Block.box(7, 8.25, -6.25, 9, 10, 3.75)), Direction.WEST, Shapes.or(Block.box(1.5, 0, 5, 12.25, 11.25, 11), Block.box(-4, 11.25, 6.5, 3, 16, 9.5), Block.box(12.25, 8.25, 7, 22.25, 10, 9)), Direction.EAST, Shapes.or(Block.box(3.75, 0, 5, 14.5, 11.25, 11), Block.box(13, 11.25, 6.5, 20, 16, 9.5), Block.box(-6.25, 8.25, 7, 3.75, 10, 9)));

    public ChickenFossilBlock(Properties settings)
    {
        super(settings);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type)
    {
        return createTickerHelper(type, ModBlockEntities.CHICKEN_FOSSIL, ChickenFossilBlockEntity::tick);
    }

    @Override
    @NotNull
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext)
    {
        return CHICKEN_SHAPES_FOR_DIRECTION.get(blockState.getValue(FACING));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new ChickenFossilBlockEntity(pos, state);
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
        component.add(Component.translatable("block.betterarcheology.chicken_fossil_tooltip").withStyle(ChatFormatting.GRAY));
        super.appendHoverText(stack, context, component, flag);
    }
}
