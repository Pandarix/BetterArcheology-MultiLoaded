package net.Pandarix.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.Pandarix.BACommon;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class IdentifyingScreen extends AbstractContainerScreen<IdentifyingMenu>
{

    //saves archeology_table_gui as TEXTURE
    private static final ResourceLocation TEXTURE =
            BACommon.createResource("textures/gui/archeology_table_gui.png");

    public IdentifyingScreen(IdentifyingMenu inventoryMenu, Inventory inventory, Component title)
    {
        super(inventoryMenu, inventory, title);
    }

    @Override
    protected void init()
    {
        super.init();
        this.titleLabelX = imageWidth / 2 - 43;
        this.titleLabelY += 2;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY)
    {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        renderProgressArrow(guiGraphics, x, y);
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y)
    {
        if (menu.isCrafting())
        {
            guiGraphics.blit(TEXTURE, x + 51, y + 48, 176, 0, menu.getScaledProgress(), 17);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta)
    {
        renderBackground(guiGraphics, mouseX, mouseY, delta);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
}