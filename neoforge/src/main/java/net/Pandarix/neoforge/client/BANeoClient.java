package net.Pandarix.neoforge.client;

import net.Pandarix.BACommon;
import net.Pandarix.block.entity.ModBlockEntities;
import net.Pandarix.block.entity.client.ArcheologyTableBlockEntityRenderer;
import net.Pandarix.block.entity.client.SusBlockEntityRenderer;
import net.Pandarix.block.entity.client.VillagerFossilBlockEntityRenderer;
import net.Pandarix.entity.ModEntityTypes;
import net.Pandarix.screen.FossilInventoryScreen;
import net.Pandarix.screen.IdentifyingScreen;
import net.Pandarix.screen.ModMenuTypes;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@SuppressWarnings("unused")
@EventBusSubscriber(modid = BACommon.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BANeoClient
{
    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerBlockEntityRenderer(ModBlockEntities.ARCHEOLOGY_TABLE.get(),
                ArcheologyTableBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.SUSBLOCK.get(),
                SusBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.VILLAGER_FOSSIL.get(),
                VillagerFossilBlockEntityRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.BOMB_ENTITY.get(), ThrownItemRenderer::new);
    }

    @SubscribeEvent
    public static void onRegisterMenuScreens(RegisterMenuScreensEvent event)
    {
        event.register(ModMenuTypes.FOSSIL_MENU.get(), FossilInventoryScreen::new);
        event.register(ModMenuTypes.IDENTIFYING_MENU.get(), IdentifyingScreen::new);
    }
}
