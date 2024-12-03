package net.Pandarix;

import com.google.common.base.Suppliers;
import com.teamresourceful.resourcefulconfig.api.loader.Configurator;
import dev.architectury.event.events.common.BlockEvent;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrarManager;
import dev.architectury.registry.registries.RegistrySupplier;
import net.Pandarix.block.ModBlocks;
import net.Pandarix.block.entity.ModBlockEntities;
import net.Pandarix.compat.jei.recipe.ModRecipes;
import net.Pandarix.config.BAConfig;
import net.Pandarix.entity.ModEntityTypes;
import net.Pandarix.events.ModEvents;
import net.Pandarix.item.ModItemGroup;
import net.Pandarix.item.ModItems;
import net.Pandarix.screen.ModMenuTypes;
import net.Pandarix.util.ModTags;
import net.Pandarix.villager.ModVillagers;
import net.Pandarix.world.structure.ModStructures;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.text.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

public final class BACommon
{
    // GLOBAL CONSTANTS ─────────────────────────────────────────────────────────────────
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "betterarcheology";
    public static final String MOD_NAME = "Better Archeology";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);
    public static final Configurator CONFIGURATOR = new Configurator(MOD_ID);
    public static final Supplier<RegistrarManager> REGISTRIES = Suppliers.memoize(() -> RegistrarManager.get(BACommon.MOD_ID));

    // MOD INITIALIZATION ───────────────────────────────────────────────────────────────
    public static void init()
    {
        CONFIGURATOR.register(BAConfig.class);

        ModStructures.register();
        ModEntityTypes.register();
        ModItemGroup.register();
        ModItems.register();
        ModBlocks.register();
        ModTags.register();
        ModBlockEntities.register();
        ModMenuTypes.register();
        ModRecipes.register();
        ModVillagers.register();
        ModEvents.register();
    }

    // UTIL ────────────────────────────────────────────────────────────────────────────

    /**
     * Creates a {@link ResourceLocation} with the "betterarcheology" mod-id prefix and the given path.
     *
     * @param path Path of the {@link ResourceLocation} to be created
     * @return ResourceLocation of the format "betterarcheology:{@code path}"
     */
    public static ResourceLocation createResource(String path)
    {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    /**
     * Logs an information message announcing the registration of the given registry.
     * Also used to load a class' static {@link RegistrySupplier}s resulting in the actual registration of the entries.
     *
     * @param registry Registry that the calling loaded class provides
     */
    public static void logRegistryEvent(Registrar<?> registry)
    {
        LOGGER.info("Registering {} for {}",
                WordUtils.capitalize(registry.key().location().getPath().replace("_", " ") + "s"), MOD_NAME);
    }
}
