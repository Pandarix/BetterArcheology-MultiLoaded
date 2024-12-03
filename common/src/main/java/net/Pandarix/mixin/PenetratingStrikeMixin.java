package net.Pandarix.mixin;

import net.Pandarix.BACommon;
import net.Pandarix.config.BAConfig;
import net.Pandarix.enchantment.ModEnchantments;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentHelper.class)
public class PenetratingStrikeMixin
{
    @Inject(method = "getDamageProtection", at = @At("RETURN"), cancellable = true)
    private static void injectMethod(ServerLevel pLevel, LivingEntity pEntity, DamageSource pDamageSource, CallbackInfoReturnable<Float> cir)
    {
        if (BAConfig.artifactsEnabled && BAConfig.penetratingStrikeEnabled)
        {
            //TODO: test this effect
            try
            {
                Holder.Reference<Enchantment> ba$penetratingStrike = pLevel.registryAccess().asGetterLookup().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(ModEnchantments.PENETRATING_STRIKE_KEY);

                if (pDamageSource.getWeaponItem() != null && EnchantmentHelper.getItemEnchantmentLevel(ba$penetratingStrike, pDamageSource.getWeaponItem()) >= 1)
                {
                    float dmgWithReducedProt = cir.getReturnValue() * (float) (1 - BAConfig.penetratingStrikeIgnorance);
                    cir.setReturnValue(Math.max(0, dmgWithReducedProt));
                }
            } catch (Exception e)
            {
                BACommon.LOGGER.error("Could not test for Penetrating Strike because EnchantmentEntry could not be found", e);
            }
        }
        cir.setReturnValue(cir.getReturnValue());
    }
}
