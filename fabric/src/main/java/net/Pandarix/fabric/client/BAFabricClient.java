package net.Pandarix.fabric.client;

import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import net.Pandarix.block.entity.ModBlockEntities;
import net.Pandarix.block.entity.client.ArcheologyTableBlockEntityRenderer;
import net.Pandarix.block.entity.client.SusBlockEntityRenderer;
import net.Pandarix.block.entity.client.VillagerFossilBlockEntityRenderer;
import net.Pandarix.compat.jei.JeiPlugin;
import net.Pandarix.entity.ModEntityTypes;
import net.Pandarix.recipe.IdentifyingRecipe;
import net.Pandarix.screen.FossilInventoryScreen;
import net.Pandarix.screen.IdentifyingScreen;
import net.Pandarix.screen.ModMenuTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.recipe.v1.FabricRecipeAccess;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

import java.util.List;

public final class BAFabricClient implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        registerEntityRenderers();
        registerMenuScreens();

        if (FabricLoader.getInstance().isModLoaded("jei"))
        {
            registerJeiRecipeSource();
        }
    }

    /**
     * JEI has no access to server-only recipes, so hand it the copy the recipe sync API delivered.
     * Resolved lazily: JEI asks on every reload, and there is no connection before joining a world.
     */
    private static void registerJeiRecipeSource()
    {
        JeiPlugin.identifyingRecipes = () ->
        {
            ClientPacketListener connection = Minecraft.getInstance().getConnection();

            if (connection == null)
            {
                return List.of();
            }

            return ((FabricRecipeAccess) connection.recipes()).getSynchronizedRecipes()
                    .getAllOfType(IdentifyingRecipe.Type.INSTANCE);
        };
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
}
