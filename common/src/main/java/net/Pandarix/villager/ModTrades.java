package net.Pandarix.villager;

import dev.architectury.registry.level.entity.trade.SimpleTrade;
import dev.architectury.registry.registries.RegistrySupplier;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
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
        // Map of all trades for later storage
        Int2ObjectMap<VillagerTrades.ItemListing[]> archeologistTrades = new Int2ObjectOpenHashMap<>();
        // LVL 1 TRADES
        archeologistTrades.put(1, new VillagerTrades.ItemListing[]
                {
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
                }
        );
        // LVL 2 TRADES
        archeologistTrades.put(2, new VillagerTrades.ItemListing[]
                {
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
                }
        );
        // LVL 3 TRADES
        archeologistTrades.put(3, new VillagerTrades.ItemListing[]
                {
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
                }
        );
        // LVL 4 TRADES
        archeologistTrades.put(4, new VillagerTrades.ItemListing[]
                {
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
                }
        );
        // LVL 5 TRADES
        archeologistTrades.put(5, new VillagerTrades.ItemListing[]
                {
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
                                24, TagKey.create(Registries.STRUCTURE, BACommon.createRLoc("on_catacombs_explorer_map")),
                                "filled_map.catacombs",
                                MapDecorationTypes.WOODLAND_MANSION, 12, 5
                        )
                }
        );

        VillagerTrades.TRADES.put(ModVillagers.ARCHEOLOGIST.get(), archeologistTrades);
    }

    // REGISTERING ─────────────────────────────────────────────────────────────────────

    /**
     * Logs an information message announcing the registration of the given registry.
     * Also used to load a class' static {@link RegistrySupplier}s resulting in the actual registration of the entries.
     * <hr>
     * This has to be called platform-specific to work properly
     * NeoForge uses the {@code ServerAboutToStartEvent}
     * Fabric uses the mod's {@code onInitialize}
     */
    public static void register()
    {
        BACommon.LOGGER.info("Registering {} for {}", "Villager Trades", BACommon.MOD_NAME);
    }
}
