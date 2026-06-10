package net.Pandarix.screen;

import net.Pandarix.BACommon;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

public class IdentifyingScreen extends AbstractContainerScreen<IdentifyingMenu>
{
    private static final Identifier TEXTURE =
            BACommon.createRLoc("textures/gui/container/archeology_table.png");
    private static final Identifier PROGRESS_TEXTURE =
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
    public void extractContents(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick)
    {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, x, y, 0, 0, imageWidth, imageHeight, 256, 256);
        if (menu.isCrafting())
        {
            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, PROGRESS_TEXTURE, 74, 17, 0, 0, x + 51, y + 48, menu.getScaledProgress(), 17);
        }
        super.extractContents(guiGraphics, mouseX, mouseY, partialTick);
    }
}
