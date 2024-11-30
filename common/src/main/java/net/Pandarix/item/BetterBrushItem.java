package net.Pandarix.item;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.BrushItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class BetterBrushItem extends BrushItem
{
    private final float brushingSpeed;

    public BetterBrushItem(Properties pProperties, float pBrushingSpeed)
    {
        super(pProperties);
        brushingSpeed = pBrushingSpeed;
    }

    private HitResult calculateHitResult(Player pPlayer)
    {
        return ProjectileUtil.getHitResultOnViewVector(pPlayer, entity -> !entity.isSpectator() && entity.isPickable(), pPlayer.blockInteractionRange());
    }

    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack pStack)
    {
        return UseAnim.BRUSH;
    }

    /**
     * Called as the item is being used by an entity.
     */
    public void onUseTick(@NotNull Level pLevel, @NotNull LivingEntity pLivingEntity, @NotNull ItemStack pStack, int pRemainingUseDuration)
    {
        if (pRemainingUseDuration >= 0 && pLivingEntity instanceof Player player)
        {
            HitResult hitresult = this.calculateHitResult(player);
            if (hitresult instanceof BlockHitResult blockhitresult)
            {
                if (hitresult.getType() == HitResult.Type.BLOCK)
                {
                    int i = this.getUseDuration(pStack, player) - pRemainingUseDuration + 1;
                    boolean flag = i % brushingSpeed == brushingSpeed / 2;
                    if (flag)
                    {
                        BlockPos blockpos = blockhitresult.getBlockPos();
                        BlockState blockstate = pLevel.getBlockState(blockpos);
                        HumanoidArm humanoidarm = pLivingEntity.getUsedItemHand() == InteractionHand.MAIN_HAND ? player.getMainArm() : player.getMainArm().getOpposite();
                        this.spawnDustParticles(pLevel, blockhitresult, blockstate, pLivingEntity.getViewVector(0.0F), humanoidarm);
                        Block $$18 = blockstate.getBlock();
                        SoundEvent soundevent;
                        if ($$18 instanceof BrushableBlock brushableblock)
                        {
                            soundevent = brushableblock.getBrushSound();
                        } else
                        {
                            soundevent = SoundEvents.BRUSH_GENERIC;
                        }

                        pLevel.playSound(player, blockpos, soundevent, SoundSource.BLOCKS);
                        if (!pLevel.isClientSide())
                        {
                            BlockEntity blockentity = pLevel.getBlockEntity(blockpos);
                            if (blockentity instanceof BrushableBlockEntity brushableblockentity)
                            {
                                boolean flag1 = brushableblockentity.brush(pLevel.getGameTime(), player, blockhitresult.getDirection());
                                if (flag1)
                                {
                                    EquipmentSlot equipmentslot = pStack.equals(player.getItemBySlot(EquipmentSlot.OFFHAND)) ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND;
                                    pStack.hurtAndBreak(1, pLivingEntity, equipmentslot);
                                }
                            }
                        }
                    }

                    return;
                }
            }
            pLivingEntity.releaseUsingItem();
        } else
        {
            pLivingEntity.releaseUsingItem();
        }
    }
}