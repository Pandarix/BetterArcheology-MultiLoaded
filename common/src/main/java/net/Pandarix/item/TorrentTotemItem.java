package net.Pandarix.item;

import net.Pandarix.config.BAConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
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
    // blocks per tick added on use, matching Riptide II. vanilla scales the same way:
    // spin attack strength is 1.5 at Riptide I and 0.75 more per level after that
    private static final double DASH_SPEED = 2.25D;

    // blocks the player is lifted when dashing off the ground, same value vanilla riptide uses
    private static final double GROUND_LIFT = 1.1999999284744263D;

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

        Vec3 direction = pPlayer.getLookAngle();

        // packs that want the old horizontal-only dash flatten the aim before it is scaled,
        // so looking up still dashes forward at full strength instead of fizzling out
        if (!BAConfig.torrentTotemUpwardsBoost) direction = new Vec3(direction.x, 0, direction.z);

        // one speed for every direction, like vanilla riptide: aim picks where the dash goes, never how hard it is
        Vec3 dash = direction.normalize().scale(DASH_SPEED * BAConfig.torrentTotemBoost);

        pPlayer.setDeltaMovement(pPlayer.getDeltaMovement().add(dash));
        pPlayer.startAutoSpinAttack(8, 2, itemStack);

        // vanilla riptide lifts the player off the floor first, so a dash started on the ground
        // does not immediately scrape along the terrain it is standing on
        if (pPlayer.onGround()) pPlayer.move(MoverType.SELF, new Vec3(0.0D, GROUND_LIFT, 0.0D));

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