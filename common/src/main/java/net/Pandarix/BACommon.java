package net.Pandarix;

import com.mojang.logging.LogUtils;
import com.teamresourceful.resourcefulconfig.api.loader.Configurator;
import net.Pandarix.config.BAConfig;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

public final class BACommon
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "betterarcheology";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final Configurator CONFIGURATOR = new Configurator(MOD_ID);

    public static void init() {
        CONFIGURATOR.register(BAConfig.class);
        // Write common init code here.
    }

    public static ResourceLocation createResource(String path)
    {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
