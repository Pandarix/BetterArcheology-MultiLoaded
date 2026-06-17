package net.Pandarix.fabric.datagen.provider;

import com.google.gson.JsonObject;
import net.Pandarix.BACommon;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModTradeSetProvider implements DataProvider
{
    private final FabricPackOutput output;

    public ModTradeSetProvider(FabricPackOutput output, CompletableFuture<?> registriesFuture)
    {
        this.output = output;
    }

    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput cache)
    {
        List<CompletableFuture<?>> futures = new ArrayList<>();
        futures.add(save(cache, tradeSet(3, "level_1")));
        futures.add(save(cache, tradeSet(2, "level_2")));
        futures.add(save(cache, tradeSet(2, "level_3")));
        futures.add(save(cache, tradeSet(5, "level_4")));
        futures.add(save(cache, tradeSet(3, "level_5")));
        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }

    private record TradeSetData(String level, JsonObject json) {}

    private static TradeSetData tradeSet(int amount, String level)
    {
        JsonObject obj = new JsonObject();
        obj.addProperty("amount", (double) amount);
        obj.addProperty("random_sequence", "betterarcheology:trade_set/archeologist/" + level);
        obj.addProperty("trades", "#betterarcheology:archeologist/" + level);
        return new TradeSetData(level, obj);
    }

    private CompletableFuture<?> save(CachedOutput cache, TradeSetData data)
    {
        Path out = output.getOutputFolder(PackOutput.Target.DATA_PACK)
                .resolve("betterarcheology/trade_set/archeologist/" + data.level() + ".json");
        return DataProvider.saveStable(cache, data.json(), out);
    }

    @Override
    public @NotNull String getName()
    {
        return BACommon.MOD_NAME + " Trade Sets";
    }
}
