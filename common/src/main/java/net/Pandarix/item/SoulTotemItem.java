package net.Pandarix.item;

import net.Pandarix.config.BAConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public class SoulTotemItem extends Item
{
    private static final int COOLDOWN_TICKS = 180;
    private static final int DRAIN_POSE_TICKS = 8;
    private static final double EXTRA_REACH = 1.5;
    private static final int TRAIL_PARTICLE_COUNT = 20;
    private static final double TRAIL_SPEED_DIVISOR = 15;
    private static final double PLAYER_CHEST_HEIGHT = 0.6;
    private static final int BURST_SCULK_SOUL_COUNT = 12;
    private static final int BURST_SOUL_COUNT = 3;
    private static final double BURST_HORIZONTAL_SPREAD = 0.5;
    private static final double BURST_VERTICAL_SPREAD = 0.35;
    private static final double BURST_SPEED = 0.03;

    public SoulTotemItem(Properties pProperties)
    {
        super(pProperties);
    }

    // purely visual: the drain happens in use(), the held pose just shows it
    @Override
    public int getUseDuration(ItemStack pStack, LivingEntity pEntity)
    {
        return DRAIN_POSE_TICKS;
    }

    @Override
    public @NotNull ItemUseAnimation getUseAnimation(ItemStack itemStack)
    {
        return ItemUseAnimation.BLOCK;
    }

    @Override
    @NotNull
    public InteractionResult use(Level pLevel, Player pPlayer, InteractionHand pHand)
    {
        if (!BAConfig.soulTotemEnabled || !BAConfig.totemsEnabled)
        {
            if (pLevel.isClientSide())
                pPlayer.sendOverlayMessage(Component.translatableWithFallback("config.notify.disabled", "This feature has been disabled in the config!"));
            return InteractionResult.PASS;
        }

        if (!(findTarget(pPlayer) instanceof LivingEntity target)) return InteractionResult.PASS;
        // the client only predicts the pose; the server decides whether the drain lands
        if (!(pLevel instanceof ServerLevel serverLevel))
        {
            pPlayer.startUsingItem(pHand);
            return InteractionResult.CONSUME;
        }

        float healthBefore = target.getHealth() + target.getAbsorptionAmount();
        if (!target.hurtServer(serverLevel, serverLevel.damageSources().indirectMagic(pPlayer, pPlayer), BAConfig.soulTotemDamage))
            return InteractionResult.FAIL;

        float damageDealt = healthBefore - (target.getHealth() + target.getAbsorptionAmount());
        if (damageDealt > 0)
            pPlayer.heal(damageDealt * BAConfig.soulTotemHealPercentage / 100f);

        serverLevel.playSound(null, pPlayer, SoundEvents.MULE_EAT, SoundSource.PLAYERS, 0.5f, 1f);
        serverLevel.playSound(null, pPlayer, SoundEvents.WITHER_SHOOT, SoundSource.PLAYERS, 0.1f, 0.25f);
        spawnDrainEffects(serverLevel, pPlayer, target);

        ItemStack stack = pPlayer.getItemInHand(pHand);
        pPlayer.getCooldowns().addCooldown(stack, COOLDOWN_TICKS);
        stack.hurtAndBreak(1, pPlayer, pHand);
        pPlayer.startUsingItem(pHand);
        return InteractionResult.CONSUME;
    }

    @Nullable
    private static Entity findTarget(Player player)
    {
        return ProjectileUtil.getHitResultOnViewVector(player, Predicate.not(Entity::isSpectator), player.blockInteractionRange() + EXTRA_REACH) instanceof EntityHitResult entityHit
                ? entityHit.getEntity()
                : null;
    }

    // souls torn out of the target's body, then a trail of them flowing to the player's chest
    private static void spawnDrainEffects(ServerLevel level, Player player, LivingEntity target)
    {
        Vec3 targetCenter = target.getBoundingBox().getCenter();
        Vec3 playerChest = player.position().add(0, player.getBbHeight() * PLAYER_CHEST_HEIGHT, 0);
        double spreadX = target.getBbWidth() * BURST_HORIZONTAL_SPREAD;
        double spreadY = target.getBbHeight() * BURST_VERTICAL_SPREAD;

        level.sendParticles(ParticleTypes.SCULK_SOUL, targetCenter.x, targetCenter.y, targetCenter.z, BURST_SCULK_SOUL_COUNT, spreadX, spreadY, spreadX, BURST_SPEED);
        level.sendParticles(ParticleTypes.SOUL, targetCenter.x, targetCenter.y, targetCenter.z, BURST_SOUL_COUNT, spreadX, spreadY, spreadX, BURST_SPEED);

        Vec3 toPlayer = playerChest.subtract(targetCenter);
        IntStream.rangeClosed(0, TRAIL_PARTICLE_COUNT).forEach(i -> {
            double progress = (double) i / TRAIL_PARTICLE_COUNT;
            Vec3 pos = playerChest.lerp(targetCenter, progress);
            Vec3 velocity = toPlayer.scale(progress / TRAIL_SPEED_DIVISOR);
            // count 0 makes ServerLevel#sendParticles treat the offsets as the particle's velocity
            level.sendParticles(ParticleTypes.SCULK_SOUL, pos.x, pos.y, pos.z, 0, velocity.x, velocity.y, velocity.z, 1);
        });
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, TooltipDisplay tooltipDisplay, Consumer<Component> consumer, TooltipFlag tooltipFlag)
    {
        super.appendHoverText(itemStack, tooltipContext, tooltipDisplay, consumer, tooltipFlag);
        consumer.accept(Component.translatable("item.betterarcheology.soul_totem_description").withStyle(ChatFormatting.DARK_AQUA));
    }
}
