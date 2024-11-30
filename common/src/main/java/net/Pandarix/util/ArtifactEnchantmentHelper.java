package net.Pandarix.util;

import net.Pandarix.BACommon;
import net.Pandarix.enchantment.ModEnchantments;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class ArtifactEnchantmentHelper
{
    public static boolean hasSoaringWinds(Player player)
    {
        if (player == null)
        {
            return false;
        }

        try
        {
            Holder.Reference<Enchantment> tunneling = player.level().registryAccess().asGetterLookup().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(ModEnchantments.SOARING_WINDS_KEY);

            //  if there is an elytra in the chestslot and it has the enchantment
            if (player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof ElytraItem
                    && EnchantmentHelper.getItemEnchantmentLevel(tunneling, player.getItemBySlot(EquipmentSlot.CHEST)) >= 1)
            {
                return true;
            }
        } catch (Exception e)
        {
            BACommon.LOGGER.error("Could not find enchantment in registries: " + ModEnchantments.SOARING_WINDS_KEY, e);
        }

/*
        // If ElytraSlot mod is installed (means that CuriosAPI must be installed too)
        if (ModList.get().isLoaded("elytraslot"))
        {
            Map<String, ISlotType> playerSlots = CuriosApi.getPlayerSlots(player);
            // check for back-slot (used by ElytraSlot mod)
            if (playerSlots != null && playerSlots.containsKey("back"))
            {
                Optional<ICuriosItemHandler> curios = CuriosApi.getCuriosInventory(player).resolve();

                // searching the backslot for the Elytra
                return curios.map((itemHandler) ->
                        curios.get().findCurios("back").stream().anyMatch(
                                (slotResult) -> slotResult.stack().getItem() instanceof ElytraItem
                                        && EnchantmentHelper.getTagEnchantmentLevel(ModEnchantments.SOARING_WINDS.get(), slotResult.stack()) >= 1)).orElse(false);
            }
        }*/

        return false;
    }
}
