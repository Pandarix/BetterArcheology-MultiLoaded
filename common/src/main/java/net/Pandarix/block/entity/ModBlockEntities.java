package net.Pandarix.block.entity;


import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.Pandarix.BACommon;
import net.Pandarix.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;

import java.util.Set;

public class ModBlockEntities
{
    // REGISTRY ────────────────────────────────────────────────────────────────────────
    public static final Registrar<BlockEntityType<?>> BLOCK_ENTITIES = BACommon.REGISTRIES.get().get(Registries.BLOCK_ENTITY_TYPE);

    // TYPES ───────────────────────────────────────────────────────────────────────────
    public static final RegistrySupplier<BlockEntityType<ArcheologyTableBlockEntity>> ARCHEOLOGY_TABLE = BLOCK_ENTITIES.register(BACommon.createRLoc("archeology_table"), () -> new BlockEntityType<>(ArcheologyTableBlockEntity::new, Set.of(ModBlocks.ARCHEOLOGY_TABLE.get())));

    public static final RegistrySupplier<BlockEntityType<VillagerFossilBlockEntity>> VILLAGER_FOSSIL = BLOCK_ENTITIES.register(BACommon.createRLoc("villager_fossil"), () -> new BlockEntityType<>(VillagerFossilBlockEntity::new, Set.of(ModBlocks.VILLAGER_FOSSIL.get())));

    public static final RegistrySupplier<BlockEntityType<ChickenFossilBlockEntity>> CHICKEN_FOSSIL = BLOCK_ENTITIES.register(BACommon.createRLoc("chicken_fossil"), () -> new BlockEntityType<>(ChickenFossilBlockEntity::new, Set.of(ModBlocks.CHICKEN_FOSSIL.get())));

    public static final RegistrySupplier<BlockEntityType<GuardianFossilBlockEntity>> GUARDIAN_FOSSIL = BLOCK_ENTITIES.register(BACommon.createRLoc("guardian_fossil"), () -> new BlockEntityType<>(GuardianFossilBlockEntity::new, Set.of(ModBlocks.GUARDIAN_FOSSIL.get())));

    public static final RegistrySupplier<BlockEntityType<FleeFromBlockEntity>> FLEE_FROM = BLOCK_ENTITIES.register(BACommon.createRLoc("flee_from"), () -> new BlockEntityType<>(FleeFromBlockEntity::new, Set.of(ModBlocks.OCELOT_FOSSIL.get())));

    public static final RegistrySupplier<BlockEntityType<SkeletonFleeFromBlockEntity>> SKELETON_FLEE_FROM = BLOCK_ENTITIES.register(BACommon.createRLoc("skeleton_flee_from"), () -> new BlockEntityType<>(SkeletonFleeFromBlockEntity::new, Set.of(ModBlocks.WOLF_FOSSIL.get())));

    public static final RegistrySupplier<BlockEntityType<RadianceTotemBlockEntity>> RADIANCE_TOTEM = BLOCK_ENTITIES.register(BACommon.createRLoc("radiance_totem"), () -> new BlockEntityType<>(RadianceTotemBlockEntity::new, Set.of(ModBlocks.RADIANCE_TOTEM.get())));

    public static final RegistrySupplier<BlockEntityType<BrushableBlockEntity>> SUSBLOCK = BLOCK_ENTITIES.register(BACommon.createRLoc("sus_block"), () -> new BlockEntityType<>(SusBlockEntity::new, Set.of(ModBlocks.SUSPICIOUS_DIRT.get(), ModBlocks.SUSPICIOUS_RED_SAND.get(),
            ModBlocks.FOSSILIFEROUS_DIRT.get())));

    // REGISTERING ─────────────────────────────────────────────────────────────────────
    public static void register()
    {
        BACommon.logRegistryEvent(BLOCK_ENTITIES);
    }
}
