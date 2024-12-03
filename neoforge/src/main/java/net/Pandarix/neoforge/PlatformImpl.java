package net.Pandarix.neoforge;

import com.google.common.collect.ImmutableSet;
import dev.architectury.registry.registries.Registrar;
import net.Pandarix.BACommon;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

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
}
