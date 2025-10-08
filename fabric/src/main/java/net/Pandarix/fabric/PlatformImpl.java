package net.Pandarix.fabric;

import com.google.common.collect.ImmutableSet;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.Pandarix.BACommon;
import net.Pandarix.enchantment.ModEnchantments;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.Block;

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
        VillagerProfession prof = Registry.register(BuiltInRegistries.VILLAGER_PROFESSION, BACommon.createRLoc(name), profession.get());
        return () -> prof;
    }

    public static Supplier<PoiType> registerPoiType(String name, Supplier<Block> block)
    {
        PoiType poi = PointOfInterestHelper.register(
                BACommon.createRLoc(name), 1, 1,
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
            Holder.Reference<Enchantment> soaringWinds = player.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(ModEnchantments.SOARING_WINDS_KEY);

            //  VANILLA
            if (player.getItemBySlot(EquipmentSlot.CHEST).getComponents().has(DataComponents.GLIDER)
                    && EnchantmentHelper.getItemEnchantmentLevel(soaringWinds, player.getItemBySlot(EquipmentSlot.CHEST)) >= 1)
            {
                return true;
            }

            // ACCESSORIES
            if (FabricLoader.getInstance().isModLoaded("accessories"))
            {
                AccessoriesCapability capability = AccessoriesCapability.get(player);

                if (capability != null &&
                        capability.isEquipped(itemStack -> itemStack.getComponents().has(DataComponents.GLIDER)))
                {
                    return true;
                }
            }
        } catch (Exception e)
        {
            BACommon.LOGGER.error("Could not find enchantment in registries: " + ModEnchantments.SOARING_WINDS_KEY, e);
        }

        return false;
    }
}
