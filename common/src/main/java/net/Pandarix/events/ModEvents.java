package net.Pandarix.events;

import dev.architectury.event.events.common.BlockEvent;
import net.Pandarix.BACommon;
import net.Pandarix.enchantment.TunnelingEffect;

public class ModEvents
{
    static
    {
        BlockEvent.BREAK.register(TunnelingEffect::onBlockBreak);
    }

    // LOAD ────────────────────────────────────────────────────────────────────────────
    public static void register()
    {
        BACommon.LOGGER.info("Registering {} for {}", "Events", BACommon.MOD_NAME);
    }
}
