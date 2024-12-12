package net.Pandarix.screen;

import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.Pandarix.BACommon;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public class ModMenuTypes
{
    // REGISTRY ────────────────────────────────────────────────────────────────────────
    public static final Registrar<MenuType<?>> MENUS = BACommon.REGISTRIES.get().get(Registries.MENU);

    // MENUS ───────────────────────────────────────────────────────────────────────────
    public static final RegistrySupplier<MenuType<FossilInventoryMenu>> FOSSIL_MENU = registerMenuType("fossil", FossilInventoryMenu::new);
    public static final RegistrySupplier<MenuType<IdentifyingMenu>> IDENTIFYING_MENU = registerMenuType("identifying", IdentifyingMenu::new);

    // REGISTRATION ────────────────────────────────────────────────────────────────────
    private static <T extends AbstractContainerMenu> RegistrySupplier<MenuType<T>> registerMenuType(String name, MenuType.MenuSupplier<T> menu)
    {
        return MENUS.register(BACommon.createResource(name), () -> new MenuType<>(menu, FeatureFlags.VANILLA_SET));
    }

    // LOAD ────────────────────────────────────────────────────────────────────────────
    public static void register()
    {
        BACommon.logRegistryEvent(MENUS);
    }
}
