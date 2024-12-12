package net.Pandarix.block.custom;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import net.Pandarix.block.entity.VillagerFossilBlockEntity;
import net.Pandarix.util.ServerPlayerHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class VillagerFossilBlock extends FossilBaseWithEntityBlock
{
    public static final MapCodec<ArchelogyTable> CODEC = simpleCodec(ArchelogyTable::new);

    @Override
    @NotNull
    protected MapCodec<? extends BaseEntityBlock> codec()
    {
        return CODEC;
    }

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final IntegerProperty INVENTORY_LUMINANCE = IntegerProperty.create("inventory_luminance", 0, 15); //used to store the amount of light that the item in its inventory would emit and to emit that luminance itself

    //Map of hitboxes for every direction the model can be facing
    private static final Map<Direction, VoxelShape> VILLAGER_SHAPES_FOR_DIRECTION = ImmutableMap.of(
            Direction.NORTH, Shapes.or(
                    Block.box(4.75, 0, 9, 11, 10, 12),
                    Block.box(4, 10, 7, 12, 20, 12.5),
                    Block.box(3, 20, 2, 11, 29, 7.5)),
            Direction.SOUTH, Shapes.or(
                    Block.box(5, 0, 4, 11.25, 10, 7),
                    Block.box(4, 10, 3.5, 12, 20, 9),
                    Block.box(5, 20, 8.5, 13, 29, 14)),
            Direction.EAST, Shapes.or(
                    Block.box(4, 0, 4.75, 7, 10, 11),
                    Block.box(3.5, 10, 4, 9, 20, 12),
                    Block.box(8.5, 20, 3, 14, 29, 11)),
            Direction.WEST, Shapes.or(
                    Block.box(9, 0, 5, 12, 10, 11.25),
                    Block.box(7, 10, 4, 12.5, 20, 12),
                    Block.box(2, 20, 5, 7.5, 29, 13)));

    public VillagerFossilBlock(BlockBehaviour.Properties settings)
    {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(INVENTORY_LUMINANCE, 0));
    }

    //Drops Items present in the table at the time of destruction//
    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving)
    {
        if (pState.getBlock() != pNewState.getBlock())
        {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof VillagerFossilBlockEntity villagerFossilBlockEntity)
            {
                Containers.dropContents(pLevel, pPos, villagerFossilBlockEntity);
                pLevel.updateNeighbourForOutputSignal(pPos, this);
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    @NotNull
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext)
    {
        return VILLAGER_SHAPES_FOR_DIRECTION.get(pState.getValue(FACING));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(INVENTORY_LUMINANCE);
    }

    //BLOCKENTITY STUFF
    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new VillagerFossilBlockEntity(pos, state);
    }

    @Override
    @NotNull
    public RenderShape getRenderShape(BlockState pState)
    {
        return RenderShape.MODEL;
    }

    @Override
    @NotNull
    public InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult)
    {
        if (!pLevel.isClientSide())
        {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if (entity instanceof VillagerFossilBlockEntity)
            {
                ServerPlayerHelper.tryOpenScreen(pPlayer, (VillagerFossilBlockEntity) entity);
            } else
            {
                throw new IllegalStateException("VillagerFossilBlockEntity Container provider is missing!");
            }
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, Item.TooltipContext pContext, List<Component> component, @NotNull TooltipFlag flag)
    {
        component.add(Component.translatable("block.betterarcheology.villager_fossil_tooltip").withStyle(ChatFormatting.GRAY));
        super.appendHoverText(stack, pContext, component, flag);
    }
}
