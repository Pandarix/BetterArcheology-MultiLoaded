package net.Pandarix.fabric;

import net.Pandarix.BACommon;
import net.Pandarix.recipe.ModRecipes;
import net.Pandarix.villager.ModTrades;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.recipe.v1.sync.RecipeSynchronization;

public final class BAFabric implements ModInitializer
{
    @Override
    public void onInitialize()
    {
        BACommon.init();
        ModTrades.register();

        // Since 1.21.2 the server keeps its recipes to itself. Opt the identifying recipes back
        // into client sync so recipe viewers can list them (NeoForge does this via OnDatapackSyncEvent).
        RecipeSynchronization.synchronizeRecipeSerializer(ModRecipes.IDENTIFYING_SERIALIZER.get());
    }
}
