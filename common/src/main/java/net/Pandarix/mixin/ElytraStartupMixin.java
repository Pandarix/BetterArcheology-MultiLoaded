package net.Pandarix.mixin;

import net.Pandarix.enchantment.SoaringWindsEffect;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class ElytraStartupMixin
{
    @Inject(method = "startFallFlying", at = @At(value = "TAIL"))
    private void betterarcheology$soar(CallbackInfo ci)
    {
        SoaringWindsEffect.onStartGliding((Player) (Object) this);
    }
}
