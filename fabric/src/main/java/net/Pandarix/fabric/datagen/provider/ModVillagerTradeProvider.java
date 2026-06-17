package net.Pandarix.fabric.datagen.provider;

import net.Pandarix.BACommon;
import net.Pandarix.block.ModBlocks;
import net.Pandarix.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.TradeCost;
import net.minecraft.world.item.trading.VillagerTrade;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.saveddata.maps.MapDecorationTypes;
import net.minecraft.world.level.storage.loot.functions.ExplorationMapFunction;
import net.minecraft.world.level.storage.loot.functions.SetNameFunction;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class ModVillagerTradeProvider extends FabricDynamicRegistryProvider
{
    public ModVillagerTradeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture)
    {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(HolderLookup.Provider registries, Entries entries)
    {
        // ── Level 1 ───────────────────────────────────────────────────────────
        entries.add(trade("archeologist/1/emerald_for_rotten_planks"),
                simpleTrade(Items.EMERALD, 1, ModBlocks.ROTTEN_PLANKS.get(), 6, 10, 2, 0.02f));
        entries.add(trade("archeologist/1/emerald_for_brush"),
                simpleTrade(Items.EMERALD, 3, Items.BRUSH, 1, 4, 5, 0.02f));
        entries.add(trade("archeologist/1/bones_for_emerald"),
                simpleTrade(Items.BONE, 16, Items.EMERALD, 1, 16, 20, 0.02f));

        // ── Level 2 ───────────────────────────────────────────────────────────
        entries.add(trade("archeologist/2/emerald_for_mud_bricks"),
                simpleTrade(Items.EMERALD, 1, Items.MUD_BRICKS, 1, 14, 5, 0.02f));
        entries.add(trade("archeologist/2/emerald_for_lantern"),
                simpleTrade(Items.EMERALD, 3, Items.LANTERN, 1, 12, 10, 0.02f));

        // ── Level 3 ───────────────────────────────────────────────────────────
        entries.add(trade("archeologist/3/emerald_for_cobweb"),
                simpleTrade(Items.EMERALD, 4, Items.COBWEB, 6, 10, 5, 0.02f));
        entries.add(trade("archeologist/3/emerald_for_iron_brush"),
                simpleTrade(Items.EMERALD, 6, ModItems.IRON_BRUSH.get(), 1, 4, 10, 0.03f));

        // ── Level 4 ───────────────────────────────────────────────────────────
        entries.add(trade("archeologist/4/emerald_for_vase"),
                simpleTrade(Items.EMERALD, 4, ModBlocks.VASE.get(), 1, 8, 10, 0.025f));
        entries.add(trade("archeologist/4/emerald_for_vase_creeper"),
                simpleTrade(Items.EMERALD, 4, ModBlocks.VASE_CREEPER.get(), 1, 8, 10, 0.025f));
        entries.add(trade("archeologist/4/emerald_for_vase_green"),
                simpleTrade(Items.EMERALD, 4, ModBlocks.VASE_GREEN.get(), 1, 8, 10, 0.025f));
        entries.add(trade("archeologist/4/emerald_for_spyglass"),
                simpleTrade(Items.EMERALD, 8, Items.SPYGLASS, 1, 8, 10, 0.02f));
        entries.add(trade("archeologist/4/emerald_for_bomb"),
                simpleTrade(Items.EMERALD, 8, ModItems.BOMB_ITEM.get(), 3, 6, 10, 0.05f));

        // ── Level 5 ───────────────────────────────────────────────────────────
        entries.add(trade("archeologist/5/emerald_for_diamond_brush"),
                simpleTrade(Items.EMERALD, 13, ModItems.DIAMOND_BRUSH.get(), 1, 4, 10, 0.03f));
        entries.add(trade("archeologist/5/emerald_for_artifact_shards"),
                simpleTrade(Items.EMERALD, 24, ModItems.ARTIFACT_SHARDS.get(), 1, 3, 30, 0.1f));
        entries.add(trade("archeologist/5/emerald_for_catacombs_map"), catacombsMapTrade());
    }

    private static VillagerTrade simpleTrade(ItemLike wants, int wantsCount, ItemLike gives, int givesCount,
                                             int maxUses, int xp, float reputationDiscount)
    {
        Item givesItem = gives.asItem();
        ItemStackTemplate givesTemplate = givesCount == 1
                ? new ItemStackTemplate(givesItem)
                : new ItemStackTemplate(givesItem, givesCount);
        return new VillagerTrade(
                new TradeCost(wants, wantsCount),
                givesTemplate,
                maxUses, xp, reputationDiscount,
                Optional.empty(), List.of()
        );
    }

    private static VillagerTrade catacombsMapTrade()
    {
        return new VillagerTrade(
                new TradeCost(Items.EMERALD, 24),
                Optional.of(new TradeCost(Items.COMPASS, 1)),
                new ItemStackTemplate(Items.MAP),
                12, 5, 0.05f,
                Optional.empty(),
                List.of(
                        ExplorationMapFunction.makeExplorationMap()
                                .setDestination(TagKey.create(Registries.STRUCTURE,
                                        Identifier.fromNamespaceAndPath("betterarcheology", "on_catacombs_explorer_map")))
                                .setMapDecoration(MapDecorationTypes.WOODLAND_MANSION)
                                .setZoom((byte) 2)
                                .setSearchRadius(50)
                                .setSkipKnownStructures(false)
                                .build(),
                        SetNameFunction.setName(
                                Component.translatable("filled_map.catacombs"),
                                SetNameFunction.Target.ITEM_NAME
                        ).build()
                )
        );
    }

    private static ResourceKey<VillagerTrade> trade(String path)
    {
        return ResourceKey.create(Registries.VILLAGER_TRADE, BACommon.createRLoc(path));
    }

    @Override
    public @NotNull String getName()
    {
        return BACommon.MOD_NAME + " Villager Trades";
    }
}
