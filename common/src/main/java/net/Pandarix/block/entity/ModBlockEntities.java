package net.Pandarix.block.entity;


import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.Pandarix.BACommon;
import net.Pandarix.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntities
{
    // REGISTRY ────────────────────────────────────────────────────────────────────────
    public static final Registrar<BlockEntityType<?>> BLOCK_ENTITIES = BACommon.REGISTRIES.get().get(Registries.BLOCK_ENTITY_TYPE);

    // TYPES ───────────────────────────────────────────────────────────────────────────
    public static final RegistrySupplier<BlockEntityType<ArcheologyTableBlockEntity>> ARCHEOLOGY_TABLE = BLOCK_ENTITIES.register(BACommon.createResource("archeology_table"), () -> BlockEntityType.Builder.of(ArcheologyTableBlockEntity::new, ModBlocks.ARCHEOLOGY_TABLE.get()).build(null));

    public static final RegistrySupplier<BlockEntityType<VillagerFossilBlockEntity>> VILLAGER_FOSSIL = BLOCK_ENTITIES.register(BACommon.createResource("villager_fossil"), () -> BlockEntityType.Builder.of(VillagerFossilBlockEntity::new, ModBlocks.VILLAGER_FOSSIL.get()).build(null));

    public static final RegistrySupplier<BlockEntityType<ChickenFossilBlockEntity>> CHICKEN_FOSSIL = BLOCK_ENTITIES.register(BACommon.createResource("chicken_fossil"), () -> BlockEntityType.Builder.of(ChickenFossilBlockEntity::new, ModBlocks.CHICKEN_FOSSIL.get()).build(null));

    public static final RegistrySupplier<BlockEntityType<GuardianFossilBlockEntity>> GUARDIAN_FOSSIL = BLOCK_ENTITIES.register(BACommon.createResource("guardian_fossil"), () -> BlockEntityType.Builder.of(GuardianFossilBlockEntity::new, ModBlocks.GUARDIAN_FOSSIL.get()).build(null));

    public static final RegistrySupplier<BlockEntityType<FleeFromBlockEntity>> FLEE_FROM = BLOCK_ENTITIES.register(BACommon.createResource("flee_from"), () -> BlockEntityType.Builder.of(FleeFromBlockEntity::new, ModBlocks.OCELOT_FOSSIL.get()).build(null));

    public static final RegistrySupplier<BlockEntityType<SkeletonFleeFromBlockEntity>> SKELETON_FLEE_FROM = BLOCK_ENTITIES.register(BACommon.createResource("skeleton_flee_from"), () -> BlockEntityType.Builder.of(SkeletonFleeFromBlockEntity::new, ModBlocks.WOLF_FOSSIL.get()).build(null));

    public static final RegistrySupplier<BlockEntityType<RadianceTotemBlockEntity>> RADIANCE_TOTEM = BLOCK_ENTITIES.register(BACommon.createResource("radiance_totem"), () -> BlockEntityType.Builder.of(RadianceTotemBlockEntity::new, ModBlocks.RADIANCE_TOTEM.get()).build(null));

    public static final RegistrySupplier<BlockEntityType<SusBlockEntity>> SUSBLOCK = BLOCK_ENTITIES.register(BACommon.createResource("sus_block"), () -> BlockEntityType.Builder.of(SusBlockEntity::new, ModBlocks.SUSPICIOUS_DIRT.get(), ModBlocks.SUSPICIOUS_RED_SAND.get(), ModBlocks.FOSSILIFEROUS_DIRT.get()).build(null));

    // REGISTERING ─────────────────────────────────────────────────────────────────────
    public static void register()
    {
        BACommon.logRegistryEvent(BLOCK_ENTITIES);
    }
}
