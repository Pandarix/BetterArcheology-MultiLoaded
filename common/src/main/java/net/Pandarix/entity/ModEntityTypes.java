package net.Pandarix.entity;

import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.Pandarix.BACommon;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ModEntityTypes
{
    // REGISTRY ────────────────────────────────────────────────────────────────────────
    public static final Registrar<EntityType<?>> ENTITY_TYPES = BACommon.REGISTRIES.get().get(Registries.ENTITY_TYPE);

    public static final RegistrySupplier<EntityType<BombEntity>> BOMB_ENTITY =
            ENTITY_TYPES.register(BACommon.createResource("bombentity"),
                    () -> EntityType.Builder.<BombEntity>of(BombEntity::new, MobCategory.MISC)
                            .sized(0.25F, 0.25F)
                            .clientTrackingRange(4).updateInterval(10)
                            .build(BACommon.createResource("bombentity").toString()));

    public static void register()
    {
        BACommon.logRegistryEvent(ENTITY_TYPES);
    }
}
