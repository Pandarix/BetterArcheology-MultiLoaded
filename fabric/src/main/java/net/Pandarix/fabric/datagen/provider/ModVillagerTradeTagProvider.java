package net.Pandarix.fabric.datagen.provider;

import net.Pandarix.BACommon;
import net.Pandarix.fabric.datagen.ArcheologistTrades;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.trading.VillagerTrade;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

public class ModVillagerTradeTagProvider extends FabricTagsProvider<VillagerTrade>
{
    public ModVillagerTradeTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture)
    {
        super(output, Registries.VILLAGER_TRADE, registriesFuture);
    }

    @Override
    protected void addTags(@NotNull HolderLookup.Provider registries)
    {
        IntStream.rangeClosed(1, ArcheologistTrades.BY_LEVEL.size()).forEach(level ->
        {
            var levelTag = this.builder(ArcheologistTrades.levelTag(level));
            ArcheologistTrades.tradesAt(level).forEach(name -> levelTag.addOptional(ArcheologistTrades.trade(level, name)));
        });
    }

    @Override
    public @NotNull String getName()
    {
        return BACommon.MOD_NAME + " Villager Trade Tags";
    }
}
