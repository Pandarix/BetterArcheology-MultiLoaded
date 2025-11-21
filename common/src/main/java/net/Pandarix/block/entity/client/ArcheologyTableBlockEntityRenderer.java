package net.Pandarix.block.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import it.unimi.dsi.fastutil.HashCommon;
import net.Pandarix.block.entity.ArcheologyTableBlockEntity;
import net.Pandarix.block.entity.state.ArcheologyTableRenderState;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ArcheologyTableBlockEntityRenderer implements BlockEntityRenderer<ArcheologyTableBlockEntity, ArcheologyTableRenderState>
{
    private final ItemModelResolver itemModelResolver;

    public ArcheologyTableBlockEntityRenderer(BlockEntityRendererProvider.Context context)
    {
        this.itemModelResolver = context.itemModelResolver();
    }

    @Override
    public @NotNull ArcheologyTableRenderState createRenderState()
    {
        return new ArcheologyTableRenderState();
    }

    @Override
    public void extractRenderState(@NotNull ArcheologyTableBlockEntity archeologyTableBlockEntity, @NotNull ArcheologyTableRenderState archeologyTableRenderState, float f, @NotNull Vec3 vec3, @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay)
    {
        BlockEntityRenderer.super.extractRenderState(archeologyTableBlockEntity, archeologyTableRenderState, f, vec3, crumblingOverlay);
        archeologyTableRenderState.lightCoords = archeologyTableBlockEntity.getLevel() != null ? LevelRenderer.getLightColor(archeologyTableBlockEntity.getLevel(), archeologyTableBlockEntity.getBlockPos().above()) : 15728880;
        NonNullList<ItemStack> nonNullList = archeologyTableBlockEntity.getItems();
        int i = HashCommon.long2int(archeologyTableBlockEntity.getBlockPos().asLong());

        for (int j = 0; j < nonNullList.size(); ++j)
        {
            ItemStack itemStack = nonNullList.get(j);
            if (!itemStack.isEmpty())
            {
                ItemStackRenderState itemStackRenderState = new ItemStackRenderState();
                this.itemModelResolver.updateForTopItem(itemStackRenderState, itemStack, ItemDisplayContext.FIXED, archeologyTableBlockEntity.getLevel(), archeologyTableBlockEntity, i + j);
                archeologyTableRenderState.items[j] = itemStackRenderState;
            }
            else
            {
                // Set to null for empty slots
                archeologyTableRenderState.items[j] = null;
            }
        }
    }

    @Override
    public void submit(ArcheologyTableRenderState blockEntityRenderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState)
    {
        //gets List of all Items in inventory and stores corresponding indexes
        ItemStackRenderState brushRenderState = blockEntityRenderState.items[0];
        ItemStackRenderState unidentifiedRenderState = blockEntityRenderState.items[1];
        ItemStackRenderState identifiedRenderState = blockEntityRenderState.items[2];

        //BRUSH
        if (brushRenderState != null && !brushRenderState.isEmpty())
        {
            //transform the items rotation, scale and position
            poseStack.pushPose();
            poseStack.translate(0.35f, 1.025f, 0.7f);
            poseStack.scale(0.65f, 0.65f, 0.65f);
            poseStack.mulPose(Axis.XP.rotationDegrees(90));
            poseStack.mulPose(Axis.ZP.rotationDegrees(180));

            if (unidentifiedRenderState != null ||  identifiedRenderState != null)
                poseStack.mulPose(Axis.XP.rotationDegrees(-7.5f));

            //display brush on top of the table
            brushRenderState.submit(poseStack, submitNodeCollector, blockEntityRenderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);

            poseStack.popPose();
        }

        //ARTIFACTS
        //transform the items rotation, scale and position
        poseStack.pushPose();
        poseStack.translate(0.55f, 1.025, 0.4f);
        poseStack.scale(0.55f, 0.55f, 0.55f);
        poseStack.mulPose(Axis.XP.rotationDegrees(90));

        //if there is no identified artifact in the output slot, render the unidentified one
        if (identifiedRenderState == null || identifiedRenderState.isEmpty())
        {
            if (unidentifiedRenderState != null && !unidentifiedRenderState.isEmpty())
            {
                unidentifiedRenderState.submit(poseStack, submitNodeCollector, blockEntityRenderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
            }
        }
        else
        {
            identifiedRenderState.submit(poseStack, submitNodeCollector, blockEntityRenderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
        }

        poseStack.popPose();
    }
}