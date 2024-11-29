package net.Pandarix.block.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.Pandarix.betterarcheology.block.entity.ArcheologyTableBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;

import java.util.List;
import java.util.Objects;

public class ArcheologyTableBlockEntityRenderer implements BlockEntityRenderer<ArcheologyTableBlockEntity>
{
    public ArcheologyTableBlockEntityRenderer(BlockEntityRendererProvider.Context context)
    {
    }

    @Override
    public void render(ArcheologyTableBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay)
    {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        //gets List of all Items in inventory and stores corresponding indexes
        List<ItemStack> inventoryContents = pBlockEntity.getInventoryContents();
        ItemStack brush = inventoryContents.get(0);
        ItemStack unidentified = inventoryContents.get(1);
        ItemStack identified = inventoryContents.get(2);

        //BRUSH
        //transform the items rotation, scale and position
        pPoseStack.pushPose();
        pPoseStack.translate(0.35f, 1.025f, 0.7f);
        pPoseStack.scale(0.65f, 0.65f, 0.65f);
        pPoseStack.mulPose(Axis.XP.rotationDegrees(90));

        //display brush on top of the table
        itemRenderer.renderStatic(brush, ItemDisplayContext.GUI, getLightLevel(Objects.requireNonNull(pBlockEntity.getLevel()), pBlockEntity.getBlockPos().above()), OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, pBlockEntity.getLevel(), 1);

        pPoseStack.popPose();

        //ARTIFACTS
        //transform the items rotation, scale and position
        pPoseStack.pushPose();
        pPoseStack.translate(0.55f, 1.025, 0.4f);
        pPoseStack.scale(0.55f, 0.55f, 0.55f);
        pPoseStack.mulPose(Axis.XP.rotationDegrees(90));

        //if there is no identified artifact in the output slot, render the unidentified one
        if (identified.isEmpty())
        {
            itemRenderer.renderStatic(unidentified, ItemDisplayContext.GUI, getLightLevel(Objects.requireNonNull(pBlockEntity.getLevel()), pBlockEntity.getBlockPos().above()), OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, pBlockEntity.getLevel(), 1);
        } else
        {
            itemRenderer.renderStatic(identified, ItemDisplayContext.GUI, getLightLevel(Objects.requireNonNull(pBlockEntity.getLevel()), pBlockEntity.getBlockPos().above()), OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, pBlockEntity.getLevel(), 1);
        }

        pPoseStack.popPose();
    }

    private int getLightLevel(Level world, BlockPos pos)
    {
        int bLight = world.getBrightness(LightLayer.BLOCK, pos);
        int sLight = world.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }
}
