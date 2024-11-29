package net.Pandarix.item;

import net.Pandarix.config.BAConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Predicate;

public class SoulTotemItem extends Item
{
    public SoulTotemItem(Properties pProperties)
    {
        super(pProperties);
    }

    @Override
    public int getUseDuration(ItemStack pStack, LivingEntity pEntity)
    {
        return 1;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(ItemStack pStack)
    {
        return UseAnim.BLOCK;
    }

    @Override
    @NotNull
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand)
    {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        // if feature is disabled, notify the user and skip
        if (!BAConfig.soulTotemEnabled || !BAConfig.totemsEnabled)
        {
            if (pLevel.isClientSide())
            {
                pPlayer.displayClientMessage(Component.translatableWithFallback("config.notify.disabled", "This feature has been disabled in the config!"), true);
            }
            return InteractionResultHolder.pass(itemstack);
        }

        pPlayer.startUsingItem(pHand);
        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity)
    {
        if (!(pLivingEntity instanceof Player player))
        {
            return pStack;
        }

        HitResult hitresult = ProjectileUtil.getHitResultOnViewVector(player, Predicate.not(Entity::isSpectator), player.blockInteractionRange());

        //if an entity is being targeted
        if (hitresult instanceof EntityHitResult entityHitResult)
        {
            if (entityHitResult.getType() == HitResult.Type.ENTITY)
            {

                Entity entity = entityHitResult.getEntity();
                //if the entity could be attacked
                if (entity.isAttackable())
                {
                    //spawn particles from player to target on client
                    //particles move towards the player
                    if (pLevel.isClientSide())
                    {
                        Vec3 playerPos = player.position();
                        Vec3 targetPos = entity.position();
                        Vec3 toPlayerPos = playerPos.subtract(targetPos);
                        for (float f = 0; f <= 1; f += 0.05)
                        {
                            pLevel.addParticle(ParticleTypes.SCULK_SOUL,
                                    lerp(playerPos.x, targetPos.x, f),
                                    lerp(playerPos.y, targetPos.y, f) + 1,
                                    lerp(playerPos.z, targetPos.z, f),
                                    toPlayerPos.x * f / 15, toPlayerPos.y * f / 15, toPlayerPos.z * f / 15);
                        }
                    } else
                    {
                        //play sound effects
                        pLevel.playSound(null, player, SoundEvents.MULE_EAT, SoundSource.PLAYERS, 0.5f, 1f);
                        pLevel.playSound(null, player, SoundEvents.WITHER_SHOOT, SoundSource.PLAYERS, 0.1f, 0.25f);
                        //damage target and heal user
                        entity.hurt(entity.damageSources().playerAttack(player), 4);
                        player.heal(4);
                        //set cooldown and damage stack
                        player.getCooldowns().addCooldown(this, 180);
                        pStack.hurtAndBreak(1, player, pLivingEntity.getEquipmentSlotForItem(pStack));
                    }
                }

            }
        }
        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
    }

    //custom lerp function
    private double lerp(double a, double b, float f)
    {
        return a + f * (b - a);
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag)
    {
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
        pTooltipComponents.add(Component.translatable("item.betterarcheology.soul_totem_description").withStyle(ChatFormatting.DARK_AQUA));
    }
}
