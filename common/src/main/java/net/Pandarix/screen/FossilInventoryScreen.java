package net.Pandarix.screen;

import net.Pandarix.BACommon;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class FossilInventoryScreen extends AbstractContainerScreen<FossilInventoryMenu>
{
    private static final ResourceLocation TEXTURE = BACommon.createRLoc("textures/gui/container/fossil_gui.png");

    public FossilInventoryScreen(FossilInventoryMenu handler, Inventory inventory, Component title)
    {
        super(handler, inventory, Component.translatable(title.getString()).withStyle(ChatFormatting.GRAY));
    }

    @Override
    protected void init()
    {
        super.init();
        this.titleLabelX = imageWidth / 2 - 35;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY)
    {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight, 256, 256);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta)
    {
        renderBackground(guiGraphics, mouseX, mouseY, delta);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
}