package net.Pandarix.villager;

import net.Pandarix.BACommon;

public class ModTrades
{
    // Villager trades are now fully data-driven in MC 26.x.
    // Trade registration via VillagerTrades.TRADES (removed) and SimpleTrade (Architectury, removed) no longer works.
    // Trades should be defined as data pack JSON files under data/<modid>/villager_trade/.

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
