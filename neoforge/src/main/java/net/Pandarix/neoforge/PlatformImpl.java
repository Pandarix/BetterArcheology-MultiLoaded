package net.Pandarix.neoforge;

import com.google.common.collect.ImmutableSet;
import dev.architectury.registry.registries.Registrar;
import net.Pandarix.BACommon;
import net.Pandarix.enchantment.ModEnchantments;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.event.EventHooks;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.ISlotType;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.Map;
import java.util.Optional;
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

    public static boolean hasSoaringWinds(Player player)
    {
        if (player == null)
        {
            return false;
        }

        try
        {
            Holder.Reference<Enchantment> soaringWinds = player.level().registryAccess().asGetterLookup().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(ModEnchantments.SOARING_WINDS_KEY);

            //  if there is an elytra in the chestslot and it has the enchantment
            if (player.getItemBySlot(EquipmentSlot.CHEST).canElytraFly(player)
                    && EnchantmentHelper.getTagEnchantmentLevel(soaringWinds, player.getItemBySlot(EquipmentSlot.CHEST)) >= 1)
            {
                return true;
            }

            // If ElytraSlot mod is installed (means that CuriosAPI must be installed too)
            if (ModList.get().isLoaded("curios"))
            {
                Map<String, ISlotType> playerSlots = CuriosApi.getPlayerSlots(player);
                // check for back-slot
                if (playerSlots != null && playerSlots.containsKey("back"))
                {
                    Optional<ICuriosItemHandler> curios = CuriosApi.getCuriosInventory(player);

                    // searching the backslot for the Elytra
                    return curios.map((itemHandler) ->
                            curios.get().findCurios("back").stream().anyMatch(
                                    (slotResult) -> slotResult.stack().canElytraFly(player)
                                            && EnchantmentHelper.getTagEnchantmentLevel(soaringWinds, slotResult.stack()) >= 1)).orElse(false);
                }
            }
        } catch (Exception e)
        {
            BACommon.LOGGER.error("Could not find enchantment in registries: " + ModEnchantments.SOARING_WINDS_KEY, e);
        }

        return false;
    }
}
