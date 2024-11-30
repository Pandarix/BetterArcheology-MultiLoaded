package net.Pandarix.fabric.client;

import net.Pandarix.BACommonClient;
import net.Pandarix.screen.FossilInventoryScreen;
import net.Pandarix.screen.IdentifyingScreen;
import net.Pandarix.screen.ModMenuTypes;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;

public final class BAFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        BACommonClient.init();
        registerMenuScreens();
    }

    private static void registerMenuScreens()
    {
        MenuScreens.register(ModMenuTypes.IDENTIFYING_MENU.get(), IdentifyingScreen::new);
        MenuScreens.register(ModMenuTypes.FOSSIL_MENU.get(), FossilInventoryScreen::new);
    }
}
