package net.Pandarix.fabric;

import net.Pandarix.BACommon;
import net.Pandarix.villager.ModTrades;
import net.fabricmc.api.ModInitializer;

public final class BAFabric implements ModInitializer
{
    @Override
    public void onInitialize()
    {
        BACommon.init();
        ModTrades.register();
    }
}
