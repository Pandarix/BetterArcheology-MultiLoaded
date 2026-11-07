package net.Pandarix.enchantment;

import dev.architectury.event.EventResult;
import net.Pandarix.config.BAConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.GameMasterBlock;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Tunneling: breaking a block also breaks the one below it, when both are similarly hard.
 */
public final class TunnelingEffect
{
    private static final int DURABILITY_COST = 1;

    /** Listener for Architectury's {@code BlockEvent.BREAK}. Never cancels the original break. */
    public static EventResult onBlockBreak(Level level, BlockPos pos, BlockState state, ServerPlayer player)
    {
        if (!(level instanceof ServerLevel serverLevel) || player == null || player.isShiftKeyDown()) return EventResult.pass();

        ItemStack tool = player.getMainHandItem();
        if (ModEnchantments.activeLevel(serverLevel.registryAccess(), ModEnchantments.TUNNELING_KEY, tool) < 1) return EventResult.pass();

        BlockPos belowPos = pos.below();
        BlockState below = serverLevel.getBlockState(belowPos);
        if (canBreakBelow(serverLevel, pos, state, belowPos, below, tool, player))
            breakBelow(serverLevel, belowPos, below, tool, player);
        return EventResult.pass();
    }

    private static boolean canBreakBelow(ServerLevel level, BlockPos pos, BlockState state, BlockPos belowPos, BlockState below, ItemStack tool, ServerPlayer player)
    {
        float belowHardness = below.getDestroySpeed(level, belowPos);
        // Negative hardness means unbreakable (bedrock, barriers); game master blocks are command/structure blocks.
        if (below.getBlock() instanceof GameMasterBlock || belowHardness < 0) return false;
        if (player.blockActionRestricted(level, belowPos, player.gameMode.getGameModeForPlayer())) return false;

        boolean correctTool = !BAConfig.tunnelingEffectiveTool || (tool.isCorrectToolForDrops(state) && tool.isCorrectToolForDrops(below));
        boolean similarHardness = Math.abs(belowHardness - state.getDestroySpeed(level, pos)) <= BAConfig.tunnelingTolerance;
        return correctTool && similarHardness;
    }

    private static void breakBelow(ServerLevel level, BlockPos belowPos, BlockState below, ItemStack tool, ServerPlayer player)
    {
        if (player.isCreative())
        {
            removeBlock(level, belowPos, below);
            return;
        }

        ItemStack toolBeforeDamage = tool.copy();
        tool.hurtAndBreak(DURABILITY_COST, player, player.getEquipmentSlotForItem(tool));

        if (removeBlock(level, belowPos, below))
            below.getBlock().playerDestroy(level, player, belowPos, below, level.getBlockEntity(belowPos), toolBeforeDamage);
    }

    /** Removes the block without drops; survival drops come from playerDestroy afterwards. */
    private static boolean removeBlock(ServerLevel level, BlockPos pos, BlockState state)
    {
        boolean removed = level.removeBlock(pos, false);
        if (removed)
            state.getBlock().destroy(level, pos, state);
        return removed;
    }

    private TunnelingEffect()
    {
    }
}
