package net.Pandarix;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

/**
 * This class defines methods that are implemented platform-specific in the subprojects in PlatformImpl classes.
 * <hr>
 * "{@link ExpectPlatform} can be applied to static methods, and its content will be replaced by the platform-specific implementation."
 *
 * @see <a href="https://docs.architectury.dev/plugin/expect_platform">Architectury Docs - ExpectPlatform Annotation</a>
 */
public class Platform
{
    @ExpectPlatform
    public static Supplier<PoiType> registerPoiType(String name, Supplier<Block> block)
    {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Supplier<VillagerProfession> registerProfession(String name, Supplier<VillagerProfession> profession)
    {
        throw new AssertionError();
    }
}
