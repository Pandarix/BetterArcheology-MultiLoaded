package net.Pandarix.fabric;

import com.google.common.collect.ImmutableSet;
import net.Pandarix.BACommon;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PoiHelper;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * This class implements methods that are defined in the common {@link net.Pandarix.Platform} class for specifically for Fabric.
 * <hr>
 * "ExpectPlatform can be applied to static methods, and its content will be replaced by the platform-specific implementation."
 *
 * @see <a href="https://docs.architectury.dev/plugin/expect_platform">Architectury Docs - ExpectPlatform Annotation</a>
 */
@SuppressWarnings("unused")
public class PlatformImpl {
    public static Supplier<VillagerProfession> registerProfession(String name, Supplier<VillagerProfession> profession) {
        VillagerProfession prof = Registry.register(BuiltInRegistries.VILLAGER_PROFESSION, BACommon.createRLoc(name), profession.get());
        return () -> prof;
    }

    public static Supplier<PoiType> registerPoiType(String name, Supplier<Block> block) {
        PoiType poi = PoiHelper.register(
                BACommon.createRLoc(name), 1, 1,
                ImmutableSet.copyOf(block.get().getStateDefinition().getPossibleStates()));
        return () -> poi;
    }

    public static Stream<ItemStack> gliderAccessories(Player player)
    {
        // Accessories has no build for this MC line yet. When re-adding it, check its back slot
        // for a GLIDER stack; the old disabled copy wrongly read the chest slot.
        return Stream.empty();
    }
}
