package net.Pandarix.mixin;

import net.Pandarix.enchantment.PenetratingStrikeEffect;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentHelper.class)
public class PenetratingStrikeMixin
{
    @Inject(method = "getDamageProtection", at = @At("RETURN"), cancellable = true)
    private static void betterarcheology$penetrate(ServerLevel level, LivingEntity entity, DamageSource source, CallbackInfoReturnable<Float> cir)
    {
        cir.setReturnValue(PenetratingStrikeEffect.reduceProtection(level, source, cir.getReturnValue()));
    }
}
