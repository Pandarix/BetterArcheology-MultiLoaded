package net.Pandarix.block.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import it.unimi.dsi.fastutil.HashCommon;
import net.Pandarix.block.custom.VillagerFossilBlock;
import net.Pandarix.block.entity.VillagerFossilBlockEntity;
import net.Pandarix.block.entity.state.VillagerFossilRenderState;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VillagerFossilBlockEntityRenderer implements BlockEntityRenderer<VillagerFossilBlockEntity, VillagerFossilRenderState>
{
    private final ItemModelResolver itemModelResolver;

    public VillagerFossilBlockEntityRenderer(BlockEntityRendererProvider.Context context)
    {
        this.itemModelResolver = context.itemModelResolver();
    }

    @Override
    public @NotNull VillagerFossilRenderState createRenderState()
    {
        return new VillagerFossilRenderState();
    }

    @Override
    public void extractRenderState(@NotNull VillagerFossilBlockEntity villagerFossilBlockEntity, @NotNull VillagerFossilRenderState villagerFossilRenderState, float f, @NotNull Vec3 vec3, @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay)
    {
        BlockEntityRenderer.super.extractRenderState(villagerFossilBlockEntity, villagerFossilRenderState, f, vec3, crumblingOverlay);
        Direction facing = villagerFossilBlockEntity.getBlockState().getValue(VillagerFossilBlock.FACING);
        villagerFossilRenderState.lightCoords = villagerFossilBlockEntity.getLevel() != null ? LevelRenderer.getLightColor(villagerFossilBlockEntity.getLevel(), villagerFossilBlockEntity.getBlockPos().relative(facing)) : 15728880;

        ItemStackRenderState itemStackRenderState = new ItemStackRenderState();
        this.itemModelResolver.updateForTopItem(itemStackRenderState, villagerFossilBlockEntity.getItem(0), ItemDisplayContext.FIXED, villagerFossilBlockEntity.getLevel(), villagerFossilBlockEntity, HashCommon.long2int(villagerFossilBlockEntity.getBlockPos().asLong()));
        villagerFossilRenderState.items[0] = itemStackRenderState;
    }

    @Override
    public void submit(VillagerFossilRenderState villagerFossilRenderState, PoseStack poseStack, @NotNull SubmitNodeCollector submitNodeCollector, @NotNull CameraRenderState cameraRenderState)
    {
        ItemStackRenderState itemRenderState = villagerFossilRenderState.items[0];

        poseStack.pushPose();

        Direction facing = villagerFossilRenderState.blockState.getValue(VillagerFossilBlock.FACING);

        //rotation based on direction the Block ist facing
        switch (facing)
        {
            case EAST ->
            {
                poseStack.translate(0.75f, 0.95f, 0.5f);
                poseStack.mulPose(Axis.YP.rotationDegrees(-90));
            }
            case WEST ->
            {
                poseStack.translate(0.25f, 0.95f, 0.5f);
                poseStack.mulPose(Axis.YP.rotationDegrees(90));
            }
            case NORTH -> poseStack.translate(0.5f, 0.95f, 0.25f);
            case SOUTH ->
            {
                poseStack.translate(0.5f, 0.95f, 0.75f);
                poseStack.mulPose(Axis.YP.rotationDegrees(180));
            }
            default -> poseStack.mulPose(Axis.YP.rotationDegrees(-90));
        }

        //scale item to 0.5x size
        poseStack.scale(0.5f, 0.5f, 0.5f);

        //render item in inventory to hand position with lightlevel at blockpos
        itemRenderState.submit(poseStack, submitNodeCollector, villagerFossilRenderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
        poseStack.popPose();
    }
}
