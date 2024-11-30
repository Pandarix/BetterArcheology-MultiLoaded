package net.Pandarix;

import com.google.common.base.Suppliers;
import com.mojang.logging.LogUtils;
import com.teamresourceful.resourcefulconfig.api.loader.Configurator;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrarManager;
import net.Pandarix.block.ModBlocks;
import net.Pandarix.block.entity.ModBlockEntities;
import net.Pandarix.config.BAConfig;
import net.Pandarix.entity.ModEntityTypes;
import net.Pandarix.item.ModItemGroup;
import net.Pandarix.item.ModItems;
import net.Pandarix.screen.ModMenuTypes;
import net.Pandarix.world.structure.ModStructures;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.text.WordUtils;
import org.slf4j.Logger;

import java.util.function.Supplier;

public final class BACommon
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "betterarcheology";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final Configurator CONFIGURATOR = new Configurator(MOD_ID);
    public static final Supplier<RegistrarManager> REGISTRIES = Suppliers.memoize(() -> RegistrarManager.get(BACommon.MOD_ID));

    public static void init() {
        CONFIGURATOR.register(BAConfig.class);

        ModStructures.register();
        ModEntityTypes.register();
        ModItemGroup.register();
        ModItems.register();
        ModBlocks.register();
        ModBlockEntities.register();
        ModMenuTypes.register();
    }

    public static ResourceLocation createResource(String path)
    {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static void logRegistryEvent(Registrar<?> registry)
    {
        LOGGER.info("Registering {} for {}",
                WordUtils.capitalize(registry.key().location().getPath().replace("_", " ") + "s"), MOD_ID);
    }
}
