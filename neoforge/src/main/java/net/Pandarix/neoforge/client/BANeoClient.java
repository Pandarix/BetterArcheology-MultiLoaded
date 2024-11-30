package net.Pandarix.neoforge.client;

import net.Pandarix.BACommon;
import net.Pandarix.BACommonClient;
import net.Pandarix.screen.FossilInventoryScreen;
import net.Pandarix.screen.IdentifyingScreen;
import net.Pandarix.screen.ModMenuTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@SuppressWarnings("unused")
@EventBusSubscriber(modid = BACommon.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BANeoClient
{
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
        BACommonClient.init();
    }

    @SubscribeEvent
    public static void onRegisterMenuScreens(RegisterMenuScreensEvent event)
    {
        event.register(ModMenuTypes.FOSSIL_MENU.get(), FossilInventoryScreen::new);
        event.register(ModMenuTypes.IDENTIFYING_MENU.get(), IdentifyingScreen::new);
    }
}
