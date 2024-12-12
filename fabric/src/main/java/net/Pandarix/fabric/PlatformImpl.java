package net.Pandarix.fabric;

import com.google.common.collect.ImmutableSet;
import dev.emi.trinkets.api.SlotGroup;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.Pandarix.BACommon;
import net.Pandarix.enchantment.ModEnchantments;
import net.Pandarix.util.ModTags;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.Block;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * This class implements methods that are defined in the common {@link net.Pandarix.Platform} class for specifically for Fabric.
 * <hr>
 * "ExpectPlatform can be applied to static methods, and its content will be replaced by the platform-specific implementation."
 *
 * @see <a href="https://docs.architectury.dev/plugin/expect_platform">Architectury Docs - ExpectPlatform Annotation</a>
 */
@SuppressWarnings("unused")
public class PlatformImpl
{
    public static Supplier<VillagerProfession> registerProfession(String name, Supplier<VillagerProfession> profession)
    {
        VillagerProfession prof = Registry.register(BuiltInRegistries.VILLAGER_PROFESSION, BACommon.createResource(name), profession.get());
        return () -> prof;
    }

    public static Supplier<PoiType> registerPoiType(String name, Supplier<Block> block)
    {
        PoiType poi = PointOfInterestHelper.register(
                BACommon.createResource(name), 1, 1,
                ImmutableSet.copyOf(block.get().getStateDefinition().getPossibleStates()));
        return () -> poi;
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

            if (player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof ElytraItem
                    && EnchantmentHelper.getItemEnchantmentLevel(soaringWinds, player.getItemBySlot(EquipmentSlot.CHEST)) >= 1)
            {
                return true;
            }

            // If trinkets is installed, check for back-slot
            if (FabricLoader.getInstance().isModLoaded("trinkets"))
            {
                //failsafe
                Map<String, SlotGroup> trinketSlots = TrinketsApi.getPlayerSlots(player);
                if (trinketSlots != null && !trinketSlots.isEmpty() && trinketSlots.containsKey("chest"))
                {
                    // if there is a cape-slot
                    if (trinketSlots.get("chest").getSlots().containsKey("cape"))
                    {
                        // if the player has trinkets Data
                        Optional<TrinketComponent> trinketData = TrinketsApi.getTrinketComponent(player);
                        if (trinketData.isPresent())
                        {
                            // check for a trinkets slot named "cape" with an ElytraItem with Soaring winds on it
                            return trinketData.get().getAllEquipped().stream().anyMatch((pair) ->
                                    Objects.equals(pair.getA().inventory().getSlotType().getName(), "cape")
                                            && pair.getB().is(ModTags.Items.ELYTRAS) && ElytraItem.isFlyEnabled(pair.getB())
                                            && EnchantmentHelper.getItemEnchantmentLevel(soaringWinds, pair.getB()) >= 1
                            );
                        }
                    }
                }
            }
        } catch (Exception e)
        {
            BACommon.LOGGER.error("Could not find Soaring Winds in the Enchantment Registries!", e);
        }
        // if nothing succeeded, false
        return false;
    }
}
