package net.Pandarix.screen;

import net.Pandarix.BACommon;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class IdentifyingScreen extends AbstractContainerScreen<IdentifyingMenu>
{

    //saves archeology_table_gui as TEXTURE
    private static final ResourceLocation TEXTURE =
            BACommon.createRLoc("textures/gui/container/archeology_table.png");
    private static final ResourceLocation PROGRESS_TEXTURE =
            BACommon.createRLoc("container/archeology_table/brushing_progress");

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
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        guiGraphics.blit(RenderType::guiTextured, TEXTURE, x, y, 0, 0, imageWidth, imageHeight, 256, 256);

        renderProgressArrow(guiGraphics, x, y);
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y)
    {
        if (menu.isCrafting())
        {
            guiGraphics.blitSprite(RenderType::guiTextured, PROGRESS_TEXTURE, 74, 17, 0, 0, x + 51, y + 48, menu.getScaledProgress(), 17);
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