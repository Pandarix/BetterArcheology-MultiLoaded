package net.Pandarix.mixin;

import net.Pandarix.config.BAConfig;
import net.Pandarix.item.ModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class TorrentTotemItemTickMixin
{
    @Inject(method = "checkAutoSpinAttack", at = @At("HEAD"))
    private void injectMethod(AABB a, AABB b, CallbackInfo ci)
    {

        LivingEntity betterarcheology$livingEntity = (LivingEntity) (Object) this;

        if (BAConfig.artifactsEnabled && betterarcheology$livingEntity.getUseItem().is(ModItems.TORRENT_TOTEM.get()))
        {
            Level betterarcheology$level = betterarcheology$livingEntity.level();
            if (betterarcheology$level.isClientSide())
            {
                RandomSource betterarcheology$random = betterarcheology$livingEntity.getRandom();
                Vec3 betterarcheology$pos = betterarcheology$livingEntity.position();
                for (int i = 0; i <= 75; ++i)
                {
                    betterarcheology$level.addParticle(ParticleTypes.SPLASH,
                            betterarcheology$pos.x + (betterarcheology$random.nextDouble() - 0.5),
                            betterarcheology$pos.y + (betterarcheology$random.nextDouble() - 0.5),
                            betterarcheology$pos.z + (betterarcheology$random.nextDouble() - 0.5),
                            (betterarcheology$random.nextDouble() - 0.5) / 2,
                            0,
                            (betterarcheology$random.nextDouble() - 0.5) / 2);
                }
            }
        }
    }
}
