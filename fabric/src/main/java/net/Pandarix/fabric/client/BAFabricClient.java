package net.Pandarix.fabric.client;

import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import net.Pandarix.block.ModBlocks;
import net.Pandarix.block.entity.ModBlockEntities;
import net.Pandarix.block.entity.client.ArcheologyTableBlockEntityRenderer;
import net.Pandarix.block.entity.client.SusBlockEntityRenderer;
import net.Pandarix.block.entity.client.VillagerFossilBlockEntityRenderer;
import net.Pandarix.entity.ModEntityTypes;
import net.Pandarix.screen.FossilInventoryScreen;
import net.Pandarix.screen.IdentifyingScreen;
import net.Pandarix.screen.ModMenuTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public final class BAFabricClient implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        registerEntityRenderers();
        registerMenuScreens();
        registerBlockRenderLayerMap();
    }

    private static void registerEntityRenderers()
    {
        BlockEntityRendererRegistry.register(ModBlockEntities.ARCHEOLOGY_TABLE.get(), ArcheologyTableBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.VILLAGER_FOSSIL.get(), VillagerFossilBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.SUSBLOCK.get(), SusBlockEntityRenderer::new);

        EntityRendererRegistry.register(ModEntityTypes.BOMB_ENTITY, ThrownItemRenderer::new);
    }

    private static void registerMenuScreens()
    {
        MenuScreens.register(ModMenuTypes.IDENTIFYING_MENU.get(), IdentifyingScreen::new);
        MenuScreens.register(ModMenuTypes.FOSSIL_MENU.get(), FossilInventoryScreen::new);
    }

    private static void registerBlockRenderLayerMap()
    {
        //RENDERING
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ROTTEN_DOOR.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ROTTEN_TRAPDOOR.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.VILLAGER_FOSSIL.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.VILLAGER_FOSSIL_BODY.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.OCELOT_FOSSIL.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.OCELOT_FOSSIL_BODY.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.OCELOT_FOSSIL_HEAD.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SHEEP_FOSSIL.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SHEEP_FOSSIL_BODY.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SHEEP_FOSSIL_HEAD.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CHICKEN_FOSSIL.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CHICKEN_FOSSIL_HEAD.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CHICKEN_FOSSIL_BODY.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CREEPER_FOSSIL.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CREEPER_FOSSIL_BODY.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CREEPER_FOSSIL_HEAD.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GROWTH_TOTEM.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RADIANCE_TOTEM.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WOLF_FOSSIL.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WOLF_FOSSIL_HEAD.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WOLF_FOSSIL_BODY.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GUARDIAN_FOSSIL.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GUARDIAN_FOSSIL_BODY.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GUARDIAN_FOSSIL_HEAD.get(), RenderType.cutout());
    }
}
