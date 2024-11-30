package net.Pandarix.villager;

import dev.architectury.registry.level.entity.trade.SimpleTrade;
import dev.architectury.registry.level.entity.trade.TradeRegistry;
import net.Pandarix.BACommon;
import net.Pandarix.block.ModBlocks;
import net.Pandarix.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.saveddata.maps.MapDecorationTypes;

import java.util.Optional;

public class ModTrades
{
    static
    {
        TradeRegistry.registerVillagerTrade(
                ModVillagers.ARCHEOLOGIST.get(), 1,

                new SimpleTrade(
                        new ItemCost(Items.EMERALD), Optional.empty(),
                        new ItemStack(ModBlocks.ROTTEN_PLANKS.get(), 6),
                        10, 2, .02f
                ),

                new SimpleTrade(
                        new ItemCost(Items.EMERALD, 3), Optional.empty(),
                        new ItemStack(Items.BRUSH),
                        4, 5, .02f
                ),

                new SimpleTrade(
                        new ItemCost(Items.BONE, 16), Optional.empty(),
                        new ItemStack(Items.EMERALD),
                        16, 20, .02f
                )
        );

        TradeRegistry.registerVillagerTrade(
                ModVillagers.ARCHEOLOGIST.get(), 2,

                new SimpleTrade(
                        new ItemCost(Items.EMERALD), Optional.empty(),
                        new ItemStack(Blocks.MUD_BRICKS),
                        14, 5, .02f
                ),

                new SimpleTrade(
                        new ItemCost(Items.EMERALD, 3), Optional.empty(),
                        new ItemStack(Items.LANTERN),
                        12, 10, .02f
                )
        );

        TradeRegistry.registerVillagerTrade(
                ModVillagers.ARCHEOLOGIST.get(), 3,

                new SimpleTrade(
                        new ItemCost(Items.EMERALD, 4), Optional.empty(),
                        new ItemStack(Blocks.COBWEB, 6),
                        10, 5, .02f
                ),

                new SimpleTrade(
                        new ItemCost(Items.EMERALD, 6), Optional.empty(),
                        new ItemStack(ModItems.IRON_BRUSH.get()),
                        4, 10, .03f
                )
        );

        TradeRegistry.registerVillagerTrade(
                ModVillagers.ARCHEOLOGIST.get(), 4,

                new SimpleTrade(
                        new ItemCost(Items.EMERALD, 4), Optional.empty(),
                        new ItemStack(ModBlocks.VASE_CREEPER.get(), 1),
                        8, 10, .025f
                ),

                new SimpleTrade(
                        new ItemCost(Items.EMERALD, 4), Optional.empty(),
                        new ItemStack(ModBlocks.VASE.get(), 1),
                        8, 10, .025f
                ),

                new SimpleTrade(
                        new ItemCost(Items.EMERALD, 4), Optional.empty(),
                        new ItemStack(ModBlocks.VASE_GREEN.get(), 1),
                        8, 10, .025f
                ),

                new SimpleTrade(
                        new ItemCost(Items.EMERALD, 8), Optional.empty(),
                        new ItemStack(Items.SPYGLASS),
                        8, 10, .02f
                ),

                new SimpleTrade(
                        new ItemCost(Items.EMERALD, 8), Optional.empty(),
                        new ItemStack(ModItems.BOMB_ITEM, 3),
                        6, 10, .05f
                )
        );

        TradeRegistry.registerVillagerTrade(
                ModVillagers.ARCHEOLOGIST.get(), 5,

                new SimpleTrade(
                        new ItemCost(Items.EMERALD, 13), Optional.empty(),
                        new ItemStack(ModItems.DIAMOND_BRUSH),
                        4, 10, .03f
                ),

                new SimpleTrade(
                        new ItemCost(Items.EMERALD, 24), Optional.empty(),
                        new ItemStack(ModItems.ARTIFACT_SHARDS),
                        3, 30, .1f
                ),

                new VillagerTrades.TreasureMapForEmeralds(
                        24, TagKey.create(Registries.STRUCTURE, BACommon.createResource("on_catacombs_explorer_map")),
                        "filled_map.catacombs",
                        MapDecorationTypes.WOODLAND_MANSION, 12, 5
                )
        );
    }

    // REGISTERING ─────────────────────────────────────────────────────────────────────
    public static void register()
    {
        BACommon.LOGGER.info("Registering {} for {}", "Villager Trades", BACommon.MOD_ID);
    }
}
