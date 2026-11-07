package net.Pandarix.fabric.datagen.provider;

import net.Pandarix.BACommon;
import net.Pandarix.fabric.datagen.ArcheologistTrades;
import net.Pandarix.fabric.datagen.ModDataGenerators;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderOwner;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.trading.TradeSet;
import net.minecraft.world.item.trading.VillagerTrade;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

/**
 * Archeologist trade sets, one per profession level, each drawing from that level's trade tag.
 */
public class ModTradeSetProvider extends FabricDynamicRegistryProvider
{
    private static final boolean ALLOW_DUPLICATES = false;

    public ModTradeSetProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture)
    {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(HolderLookup.Provider registries, Entries entries)
    {
        // The trade tags are our own data, not bound in the datagen lookup, so reference them by key.
        HolderOwner<VillagerTrade> tradeOwner = ModDataGenerators.serializableOwner(registries.lookupOrThrow(Registries.VILLAGER_TRADE));

        IntStream.rangeClosed(1, ArcheologistTrades.BY_LEVEL.size()).forEach(level ->
        {
            ResourceKey<TradeSet> key = BACommon.createRKey(Registries.TRADE_SET, "archeologist/level_" + level);
            HolderSet<VillagerTrade> trades = HolderSet.emptyNamed(tradeOwner, ArcheologistTrades.levelTag(level));
            // Offer every trade of the level.
            int amount = ArcheologistTrades.tradesAt(level).size();
            // Same random sequence vanilla's TradeSets.register derives from the key.
            Optional<Identifier> randomSequence = Optional.of(key.identifier().withPrefix("trade_set/"));

            entries.add(key, new TradeSet(trades, ContextIntProviders.exactly(amount), ALLOW_DUPLICATES, randomSequence));
        });
    }

    @Override
    public @NotNull String getName()
    {
        return BACommon.MOD_NAME + " Trade Sets";
    }
}
