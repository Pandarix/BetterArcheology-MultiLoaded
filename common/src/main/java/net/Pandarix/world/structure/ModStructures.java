package net.Pandarix.world.structure;

import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.Pandarix.BACommon;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.StructureType;

public class ModStructures
{
    // REGISTRY ────────────────────────────────────────────────────────────────────────
    public static final Registrar<StructureType<?>> STRUCTURES = BACommon.REGISTRIES.get().get(Registries.STRUCTURE_TYPE);

    /**
     * Registers the base structure itself and sets what its path is. In this case,
     * this base structure will have the ResourceLocation of betterarcheology:betterarcheology_structures.
     */
    public static final RegistrySupplier<StructureType<ArcheologyStructures>> ARCHEOLOGY_STRUCTURES = STRUCTURES.register(BACommon.createResource("betterarcheology_structures"), () -> () -> ArcheologyStructures.CODEC);

    // REGISTERING ─────────────────────────────────────────────────────────────────────
    public static void register()
    {
        BACommon.logRegistryEvent(STRUCTURES);
    }
}