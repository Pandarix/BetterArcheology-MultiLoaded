package net.Pandarix.fabric.datagen.provider;

import net.Pandarix.BACommon;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.trading.VillagerTrade;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModVillagerTradeTagProvider extends FabricTagsProvider<VillagerTrade>
{
    public ModVillagerTradeTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture)
    {
        super(output, Registries.VILLAGER_TRADE, registriesFuture);
    }

    @Override
    protected void addTags(@NotNull HolderLookup.Provider registries)
    {
        this.builder(level(1))
                .addOptional(trade("archeologist/1/emerald_for_rotten_planks"))
                .addOptional(trade("archeologist/1/emerald_for_brush"))
                .addOptional(trade("archeologist/1/bones_for_emerald"));

        this.builder(level(2))
                .addOptional(trade("archeologist/2/emerald_for_mud_bricks"))
                .addOptional(trade("archeologist/2/emerald_for_lantern"));

        this.builder(level(3))
                .addOptional(trade("archeologist/3/emerald_for_cobweb"))
                .addOptional(trade("archeologist/3/emerald_for_iron_brush"));

        this.builder(level(4))
                .addOptional(trade("archeologist/4/emerald_for_vase"))
                .addOptional(trade("archeologist/4/emerald_for_vase_creeper"))
                .addOptional(trade("archeologist/4/emerald_for_vase_green"))
                .addOptional(trade("archeologist/4/emerald_for_spyglass"))
                .addOptional(trade("archeologist/4/emerald_for_bomb"));

        this.builder(level(5))
                .addOptional(trade("archeologist/5/emerald_for_diamond_brush"))
                .addOptional(trade("archeologist/5/emerald_for_artifact_shards"))
                .addOptional(trade("archeologist/5/emerald_for_catacombs_map"));
    }

    private static TagKey<VillagerTrade> level(int n)
    {
        return TagKey.create(Registries.VILLAGER_TRADE, BACommon.createRLoc("archeologist/level_" + n));
    }

    private static ResourceKey<VillagerTrade> trade(String path)
    {
        return ResourceKey.create(Registries.VILLAGER_TRADE, BACommon.createRLoc(path));
    }

    @Override
    public @NotNull String getName()
    {
        return BACommon.MOD_NAME + " Villager Trade Tags";
    }
}
