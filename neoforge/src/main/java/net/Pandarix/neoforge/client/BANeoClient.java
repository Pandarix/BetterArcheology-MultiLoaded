package net.Pandarix.neoforge.client;

import net.Pandarix.BACommon;
import net.Pandarix.block.ModBlocks;
import net.Pandarix.block.entity.ModBlockEntities;
import net.Pandarix.block.entity.client.ArcheologyTableBlockEntityRenderer;
import net.Pandarix.block.entity.client.SusBlockEntityRenderer;
import net.Pandarix.block.entity.client.VillagerFossilBlockEntityRenderer;
import net.Pandarix.entity.ModEntityTypes;
import net.Pandarix.screen.FossilInventoryScreen;
import net.Pandarix.screen.IdentifyingScreen;
import net.Pandarix.screen.ModMenuTypes;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
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

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event)
    {
        //RENDERING
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.ROTTEN_DOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.ROTTEN_TRAPDOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.VILLAGER_FOSSIL.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.VILLAGER_FOSSIL_BODY.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.OCELOT_FOSSIL.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.OCELOT_FOSSIL_BODY.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.OCELOT_FOSSIL_HEAD.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.SHEEP_FOSSIL.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.SHEEP_FOSSIL_BODY.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.SHEEP_FOSSIL_HEAD.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.CHICKEN_FOSSIL.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.CHICKEN_FOSSIL_HEAD.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.CHICKEN_FOSSIL_BODY.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.CREEPER_FOSSIL.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.CREEPER_FOSSIL_BODY.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.CREEPER_FOSSIL_HEAD.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.WOLF_FOSSIL.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.WOLF_FOSSIL_BODY.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.WOLF_FOSSIL_HEAD.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.GUARDIAN_FOSSIL.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.GUARDIAN_FOSSIL_BODY.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.GUARDIAN_FOSSIL_HEAD.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.GROWTH_TOTEM.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.RADIANCE_TOTEM.get(), RenderType.cutout());
    }
}
