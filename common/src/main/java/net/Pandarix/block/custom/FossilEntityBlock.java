package net.Pandarix.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * Fossil with a block entity. Carries over what vanilla's BaseEntityBlock adds, since Java allows
 * only one superclass and FossilBlock holds the shared fossil behaviour.
 */
public abstract class FossilEntityBlock extends FossilBlock implements EntityBlock
{
    protected FossilEntityBlock(Properties settings, Map<Direction, VoxelShape> shapes)
    {
        super(settings, shapes, false);
    }

    @Override
    protected boolean triggerEvent(BlockState state, Level level, BlockPos pos, int id, int param)
    {
        super.triggerEvent(state, level, pos, id, param);
        BlockEntity blockEntity = level.getBlockEntity(pos);
        return blockEntity != null && blockEntity.triggerEvent(id, param);
    }

    @Nullable
    @Override
    protected MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos)
    {
        return level.getBlockEntity(pos) instanceof MenuProvider menuProvider ? menuProvider : null;
    }

    @Nullable
    @SuppressWarnings("unchecked")
    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> actual, BlockEntityType<E> expected, BlockEntityTicker<? super E> ticker)
    {
        return expected == actual ? (BlockEntityTicker<A>) ticker : null;
    }
}
