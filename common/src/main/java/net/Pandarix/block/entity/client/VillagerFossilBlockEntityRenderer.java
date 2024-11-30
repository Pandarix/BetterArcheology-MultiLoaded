package net.Pandarix.block.entity.client;

import com.mojang.math.Axis;
import net.Pandarix.block.custom.FossilBaseWithEntityBlock;
import net.Pandarix.block.entity.VillagerFossilBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Objects;

public class VillagerFossilBlockEntityRenderer implements BlockEntityRenderer<VillagerFossilBlockEntity>
{
    public VillagerFossilBlockEntityRenderer(BlockEntityRendererProvider.Context context)
    {
    }

    @Override
    public void render(VillagerFossilBlockEntity pBlockEntity, float pPartialTick, com.mojang.blaze3d.vertex.PoseStack pPoseStack,
                       MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay)
    {
        if (pBlockEntity.getLevel() == null)
        {
            return;
        }

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        pPoseStack.pushPose();

        BlockState state = pBlockEntity.getLevel().getBlockState(pBlockEntity.getBlockPos());
        Direction facing = state.getBlock() instanceof FossilBaseWithEntityBlock ? state.getValue(FossilBaseWithEntityBlock.FACING) : Direction.NORTH;

        //rotation based on direction the Block ist facing
        switch (facing)
        {
            case EAST ->
            {
                pPoseStack.translate(0.75f, 0.95f, 0.5f);
                pPoseStack.mulPose(Axis.YP.rotationDegrees(-90));
            }
            case WEST ->
            {
                pPoseStack.translate(0.25f, 0.95f, 0.5f);
                pPoseStack.mulPose(Axis.YP.rotationDegrees(90));
            }
            case NORTH -> pPoseStack.translate(0.5f, 0.95f, 0.25f);
            case SOUTH ->
            {
                pPoseStack.translate(0.5f, 0.95f, 0.75f);
                pPoseStack.mulPose(Axis.YP.rotationDegrees(180));
            }
            default -> pPoseStack.mulPose(Axis.YP.rotationDegrees(-90));
        }

        //scale item to 0.5x size
        pPoseStack.scale(0.5f, 0.5f, 0.5f);

        //render item in inventory to hand position with lightlevel at blockpos
        itemRenderer.renderStatic(pBlockEntity.getHeldItem(), ItemDisplayContext.FIXED, getLightLevel(Objects.requireNonNull(pBlockEntity.getLevel()), pBlockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, pBlockEntity.getLevel(), 1);

        pPoseStack.popPose();
    }

    private int getLightLevel(Level level, BlockPos pos)
    {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }
}
