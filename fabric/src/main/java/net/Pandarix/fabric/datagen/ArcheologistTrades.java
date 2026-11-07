package net.Pandarix.fabric.datagen;

import net.Pandarix.BACommon;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.trading.VillagerTrade;

import java.util.List;

/**
 * The archeologist's trades per profession level. Drives the level tags and the trade-set sizes,
 * so adding a trade here (plus its definition in ModVillagerTradeProvider) keeps both in sync.
 */
public final class ArcheologistTrades
{
    // Index 0 = level 1. Names resolve to betterarcheology:archeologist/<level>/<name>.
    public static final List<List<String>> BY_LEVEL = List.of(
            List.of("emerald_for_rotten_planks", "emerald_for_brush", "bones_for_emerald"),
            List.of("emerald_for_mud_bricks", "emerald_for_lantern"),
            List.of("emerald_for_cobweb", "emerald_for_iron_brush"),
            List.of("emerald_for_vase", "emerald_for_vase_creeper", "emerald_for_vase_green", "emerald_for_spyglass", "emerald_for_bomb"),
            List.of("emerald_for_diamond_brush", "emerald_for_artifact_shards", "emerald_for_catacombs_map"));

    public static ResourceKey<VillagerTrade> trade(int level, String name)
    {
        return BACommon.createRKey(Registries.VILLAGER_TRADE, "archeologist/" + level + "/" + name);
    }

    public static TagKey<VillagerTrade> levelTag(int level)
    {
        return TagKey.create(Registries.VILLAGER_TRADE, BACommon.createRLoc("archeologist/level_" + level));
    }

    public static List<String> tradesAt(int level)
    {
        return BY_LEVEL.get(level - 1);
    }

    private ArcheologistTrades()
    {
    }
}
