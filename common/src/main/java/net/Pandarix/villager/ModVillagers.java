package net.Pandarix.villager;

import com.google.common.collect.ImmutableSet;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.Pandarix.BACommon;
import net.Pandarix.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;

public class ModVillagers
{
    // REGISTRY ────────────────────────────────────────────────────────────────────────
    public static final Registrar<PoiType> POI_TYPES = BACommon.REGISTRIES.get().get(Registries.POINT_OF_INTEREST_TYPE);
    public static final Registrar<VillagerProfession> VILLAGER_PROFESSIONS = BACommon.REGISTRIES.get().get(Registries.VILLAGER_PROFESSION);

    // ENTRIES ────────────────────────────────────────────────────────────────────────
    public static final RegistrySupplier<PoiType> ARCHEOLOGY_TABLE_POI = POI_TYPES.register(BACommon.createResource("archeology_table_poi"),
            () -> new PoiType(ImmutableSet.copyOf(ModBlocks.ARCHEOLOGY_TABLE.get().getStateDefinition().getPossibleStates()),
                    1, 1));

    public static final RegistrySupplier<VillagerProfession> ARCHEOLOGIST = VILLAGER_PROFESSIONS.register(
            BACommon.createResource("archeologist"),
            () -> new VillagerProfession(
                    "archeologist",
                    holder -> holder.is(ARCHEOLOGY_TABLE_POI.getKey()),
                    holder -> holder.is(ARCHEOLOGY_TABLE_POI.getKey()),
                    ImmutableSet.of(), ImmutableSet.of(),
                    SoundEvents.BRUSH_SAND)
    );

    // REGISTERING ─────────────────────────────────────────────────────────────────────
    public static void register()
    {
        BACommon.logRegistryEvent(POI_TYPES);
        BACommon.logRegistryEvent(VILLAGER_PROFESSIONS);
        ARCHEOLOGIST.listen(registered -> ModTrades.register());
    }
}
