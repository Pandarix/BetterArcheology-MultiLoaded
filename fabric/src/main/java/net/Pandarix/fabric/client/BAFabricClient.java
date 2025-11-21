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
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
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
        BlockRenderLayerMap.putBlock(ModBlocks.ROTTEN_DOOR.get(), ChunkSectionLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.ROTTEN_TRAPDOOR.get(), ChunkSectionLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.VILLAGER_FOSSIL.get(), ChunkSectionLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.VILLAGER_FOSSIL_BODY.get(), ChunkSectionLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.OCELOT_FOSSIL.get(), ChunkSectionLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.OCELOT_FOSSIL_BODY.get(), ChunkSectionLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.OCELOT_FOSSIL_HEAD.get(), ChunkSectionLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.SHEEP_FOSSIL.get(), ChunkSectionLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.SHEEP_FOSSIL_BODY.get(), ChunkSectionLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.SHEEP_FOSSIL_HEAD.get(), ChunkSectionLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.CHICKEN_FOSSIL.get(), ChunkSectionLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.CHICKEN_FOSSIL_HEAD.get(), ChunkSectionLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.CHICKEN_FOSSIL_BODY.get(), ChunkSectionLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.CREEPER_FOSSIL.get(), ChunkSectionLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.CREEPER_FOSSIL_BODY.get(), ChunkSectionLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.CREEPER_FOSSIL_HEAD.get(), ChunkSectionLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.GROWTH_TOTEM.get(), ChunkSectionLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.RADIANCE_TOTEM.get(), ChunkSectionLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.WOLF_FOSSIL.get(), ChunkSectionLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.WOLF_FOSSIL_HEAD.get(), ChunkSectionLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.WOLF_FOSSIL_BODY.get(), ChunkSectionLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.GUARDIAN_FOSSIL.get(), ChunkSectionLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.GUARDIAN_FOSSIL_BODY.get(), ChunkSectionLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.GUARDIAN_FOSSIL_HEAD.get(), ChunkSectionLayer.CUTOUT);
    }
}
