package net.Pandarix.item;

import net.Pandarix.config.BAConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class TorrentTotemItem extends Item
{
    public TorrentTotemItem(Properties pProperties)
    {
        super(pProperties);
    }

    @Override
    @NotNull
    public InteractionResult use(Level pLevel, Player pPlayer, InteractionHand pUsedHand)
    {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);

        // if feature is disabled, notify the user and skip
        if (!BAConfig.torrentTotemEnabled || !BAConfig.totemsEnabled)
        {
            if (pLevel.isClientSide())
            {
                pPlayer.displayClientMessage(Component.translatableWithFallback("config.notify.disabled", "This feature has been disabled in the config!"), true);
            }
            return InteractionResult.PASS;
        }

        Vec3 rotationVector = pPlayer.getLookAngle();
        Vec3 velocity = pPlayer.getDeltaMovement();
        double boostX = 2 * BAConfig.torrentTotemBoost;
        double boostY = BAConfig.torrentTotemUpwardsBoost ? 0.5 * BAConfig.torrentTotemBoost : 0;

        pPlayer.setDeltaMovement(velocity.add(
                rotationVector.x * 0.1D + (rotationVector.x * 1.5D - velocity.x) * boostX,
                (rotationVector.y * 0.1D + (rotationVector.y * 1.5D - velocity.y)) * boostY,
                rotationVector.z * 0.1D + (rotationVector.z * 1.5D - velocity.z) * boostX)
        );
        pPlayer.startAutoSpinAttack(8, 2, itemStack);

        //sounds
        pLevel.playSound(null, pPlayer, SoundEvents.WATER_AMBIENT, SoundSource.NEUTRAL, 0.1f, (float) pLevel.getRandom().nextDouble() * 0.5f + 0.5f);
        pLevel.playSound(null, pPlayer, SoundEvents.PLAYER_SPLASH_HIGH_SPEED, SoundSource.NEUTRAL, 0.25F, 0.35F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));

        pPlayer.getCooldowns().addCooldown(itemStack, 120);
        itemStack.hurtAndBreak(1, pPlayer, pPlayer.getEquipmentSlotForItem(itemStack));
        return InteractionResult.CONSUME;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, TooltipDisplay tooltipDisplay, Consumer<Component> consumer, TooltipFlag tooltipFlag)
    {
        super.appendHoverText(itemStack, tooltipContext, tooltipDisplay, consumer, tooltipFlag);
        consumer.accept(Component.translatable("item.betterarcheology.torrent_totem_description").withStyle(ChatFormatting.DARK_AQUA));
    }

    @Override
    public int getUseDuration(ItemStack pStack, LivingEntity pEntity)
    {
        return 0;
    }

    @Override
    public @NotNull ItemUseAnimation getUseAnimation(ItemStack itemStack)
    {
        return ItemUseAnimation.BOW;
    }
}