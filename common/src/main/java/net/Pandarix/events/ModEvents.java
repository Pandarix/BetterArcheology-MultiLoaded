package net.Pandarix.events;

import dev.architectury.event.events.common.BlockEvent;
import net.Pandarix.BACommon;

public class ModEvents
{
    static
    {
        BlockEvent.BREAK.register(TunnelingEventHandler::handleTunneling);
    }

    // LOAD ────────────────────────────────────────────────────────────────────────────
    public static void register()
    {
        BACommon.LOGGER.info("Registering {} for {}", "Events", BACommon.MOD_NAME);
    }
}
