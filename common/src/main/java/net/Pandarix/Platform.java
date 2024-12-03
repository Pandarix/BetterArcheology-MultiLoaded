package net.Pandarix;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

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
