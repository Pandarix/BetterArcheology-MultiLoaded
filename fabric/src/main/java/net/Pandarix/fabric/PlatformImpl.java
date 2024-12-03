package net.Pandarix.fabric;

import com.google.common.collect.ImmutableSet;
import net.Pandarix.BACommon;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.block.Block;
import dev.architectury.injectables.annotations.ExpectPlatform;

import java.util.function.Supplier;

/**
 * This class implements methods that are defined in the common {@link net.Pandarix.Platform} class for specifically for Fabric.
 * <hr>
 * "{@link ExpectPlatform} can be applied to static methods, and its content will be replaced by the platform-specific implementation."
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
}
