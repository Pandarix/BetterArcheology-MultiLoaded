package net.Pandarix.mixin;

import net.Pandarix.enchantment.WardingEffect;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class WardingMixin
{
    @Inject(method = "hurtServer", at = @At("HEAD"), cancellable = true)
    private void betterarcheology$ward(ServerLevel level, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir)
    {
        if ((Object) this instanceof Player player && WardingEffect.tryWard(level, player, source))
            cir.setReturnValue(false);
    }
}
