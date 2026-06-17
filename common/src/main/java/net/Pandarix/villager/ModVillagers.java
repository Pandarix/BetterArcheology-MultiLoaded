package net.Pandarix.villager;

import com.google.common.collect.ImmutableSet;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.Pandarix.BACommon;
import net.Pandarix.Platform;
import net.Pandarix.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.item.trading.TradeSet;

import java.util.function.Supplier;

public class ModVillagers
{
    // PLATFORM-SPECIFIC REGISTRATION ───────────────────────────────────────────────────
    public static final Supplier<PoiType> ARCHEOLOGY_TABLE_POI = Platform.registerPoiType(
            "archeology_table_poi", ModBlocks.ARCHEOLOGY_TABLE);

    public static final Supplier<VillagerProfession> ARCHEOLOGIST = Platform.registerProfession(
            "archeologist", () -> {
                Int2ObjectOpenHashMap<ResourceKey<TradeSet>> trades = new Int2ObjectOpenHashMap<>();
                for (int level = 1; level <= 5; level++) {
                    trades.put(level, ResourceKey.create(Registries.TRADE_SET, BACommon.createRLoc("archeologist/level_" + level)));
                }
                return new VillagerProfession(
                        Component.translatable("entity.minecraft.villager.archeologist"),
                        entry -> entry.value().equals(ARCHEOLOGY_TABLE_POI.get()),
                        entry -> entry.value().equals(ARCHEOLOGY_TABLE_POI.get()),
                        ImmutableSet.of(), ImmutableSet.of(),
                        SoundEvents.BRUSH_SAND,
                        trades
                );
            }
    );

    // LOAD ────────────────────────────────────────────────────────────────────────────
    public static void register()
    {
        BACommon.LOGGER.info("Registering {} for {}", "Villager Professions", BACommon.MOD_NAME);
    }
}
