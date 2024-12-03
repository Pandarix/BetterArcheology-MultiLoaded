package net.Pandarix.neoforge;

import com.google.common.collect.ImmutableSet;
import dev.architectury.platform.Mod;
import dev.architectury.registry.registries.Registrar;
import net.Pandarix.BACommon;
import net.Pandarix.enchantment.ModEnchantments;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.event.level.BlockEvent;

import java.util.Set;
import java.util.function.Supplier;

/**
 * This class implements methods that are defined in the common {@link net.Pandarix.Platform} class for specifically for NeoForge.
 * <hr>
 * "ExpectPlatform can be applied to static methods, and its content will be replaced by the platform-specific implementation."
 *
 * @see <a href="https://docs.architectury.dev/plugin/expect_platform">Architectury Docs - ExpectPlatform Annotation</a>
 */
@SuppressWarnings("unused")
public class PlatformImpl
{
    public static final Registrar<PoiType> POI_TYPES = BACommon.REGISTRIES.get().get(Registries.POINT_OF_INTEREST_TYPE);
    public static final Registrar<VillagerProfession> PROFESSIONS = BACommon.REGISTRIES.get().get(Registries.VILLAGER_PROFESSION);

    public static Supplier<VillagerProfession> registerProfession(String name, Supplier<VillagerProfession> profession)
    {
        return PROFESSIONS.register(BACommon.createResource(name), profession);
    }

    public static Supplier<PoiType> registerPoiType(String name, Supplier<Block> block)
    {
        return POI_TYPES.register(BACommon.createResource(name), () -> new PoiType(ImmutableSet.copyOf(block.get().getStateDefinition().getPossibleStates()), 1, 1));
    }

    public static void performTunneling(ServerLevel serverLevel, BlockPos blockPos, BlockState blockState, Block block, ServerPlayer serverPlayer, ItemStack stack)
    {
        GameType type = serverPlayer.gameMode.getGameModeForPlayer();

        if (serverPlayer.blockActionRestricted(serverLevel, blockPos, type))
        {
            return;
        }

        if (serverPlayer.isCreative())
        {
            performBreak(serverLevel, serverPlayer, blockPos, blockState, false);
        } else
        {
            ItemStack copiedStack = stack.copy();

            if (stack.isEmpty() && !copiedStack.isEmpty())
            {
                EventHooks.onPlayerDestroyItem(serverPlayer, copiedStack, InteractionHand.MAIN_HAND);
            }

            boolean canHarvest = blockState.canHarvestBlock(serverLevel, blockPos, serverPlayer);
            boolean successfulBreak = performBreak(serverLevel, serverPlayer, blockPos, blockState, canHarvest);

            if (canHarvest && successfulBreak)
            {
                BlockEntity blockEntity = serverLevel.getBlockEntity(blockPos);
                block.playerDestroy(serverLevel, serverPlayer, blockPos, blockState, blockEntity, copiedStack);
            }
        }
    }

    // Performs the basic removal, no further calls needed if the player is just in creative
    private static boolean performBreak(ServerLevel serverLevel, ServerPlayer player, BlockPos pos, BlockState blockState, boolean canHarvest)
    {
        boolean removed = blockState.onDestroyedByPlayer(serverLevel, pos, player, canHarvest, serverLevel.getFluidState(pos));

        if (removed)
        {
            blockState.getBlock().destroy(serverLevel, pos, blockState);
        }
        return removed;
    }
}
