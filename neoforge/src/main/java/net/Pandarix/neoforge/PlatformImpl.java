package net.Pandarix.neoforge;

import com.google.common.collect.ImmutableSet;
import dev.architectury.registry.registries.Registrar;
import net.Pandarix.BACommon;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.ModList;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.function.Supplier;
import java.util.stream.Stream;

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
        return PROFESSIONS.register(BACommon.createRLoc(name), profession);
    }

    public static Supplier<PoiType> registerPoiType(String name, Supplier<Block> block)
    {
        return POI_TYPES.register(BACommon.createRLoc(name), () -> new PoiType(ImmutableSet.copyOf(block.get().getStateDefinition().getPossibleStates()), 1, 1));
    }
    public static Stream<ItemStack> gliderAccessories(Player player)
    {
        // Accessories is disabled until it updates past 1.21.1; Curios is the only accessory API here.
        if (!ModList.get().isLoaded("curios")) return Stream.empty();

        return CuriosApi.getCuriosInventory(player).stream()
                .flatMap(curios -> curios.findCurios("back").stream())
                .map(SlotResult::stack)
                .filter(stack -> stack.has(DataComponents.GLIDER));
    }
}
