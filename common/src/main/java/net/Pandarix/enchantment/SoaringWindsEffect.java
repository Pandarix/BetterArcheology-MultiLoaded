package net.Pandarix.enchantment;

import net.Pandarix.Platform;
import net.Pandarix.config.BAConfig;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

/**
 * Soaring Winds: a firework-like push when the player starts gliding with an enchanted glider.
 */
public final class SoaringWindsEffect
{
    // Shape of vanilla's firework rocket boost (FireworkRocketEntity), scaled by the configured boost.
    private static final double FIREWORK_BASE_PUSH = 0.1;
    private static final double FIREWORK_TARGET_SPEED = 1.5;
    private static final float FIREWORK_GAIN = 0.5f;
    private static final double VERTICAL_DAMPING = 1.8;

    private static final int POOF_COUNT = 7;
    private static final double POOF_SPREAD_Y = 0.1;
    private static final double POOF_SPREAD_Z = -0.05;
    private static final double POOF_SPEED = 0.1;

    /** Called at the end of {@code Player#startFallFlying}. */
    public static void onStartGliding(Player player)
    {
        if (!canSoar(player)) return;

        double gain = BAConfig.soaringWindsBoost * FIREWORK_GAIN;
        Vec3 look = player.getLookAngle();
        Vec3 velocity = player.getDeltaMovement();
        player.setDeltaMovement(velocity.add(
                look.x * FIREWORK_BASE_PUSH + (look.x * FIREWORK_TARGET_SPEED - velocity.x) * gain,
                look.y * FIREWORK_BASE_PUSH + (look.y * FIREWORK_TARGET_SPEED - velocity.y) * gain / VERTICAL_DAMPING,
                look.z * FIREWORK_BASE_PUSH + (look.z * FIREWORK_TARGET_SPEED - velocity.z) * gain));

        if (player.level() instanceof ServerLevel serverLevel)
            serverLevel.sendParticles(ParticleTypes.POOF, player.getX(), player.getY(), player.getZ(), POOF_COUNT, 0, POOF_SPREAD_Y, POOF_SPREAD_Z, POOF_SPEED);
    }

    /** True when any usable glider the player wears, vanilla slot or accessory slot, carries Soaring Winds. */
    private static boolean canSoar(Player player)
    {
        boolean inEquipmentSlot = EquipmentSlot.VALUES.stream().anyMatch(slot ->
        {
            ItemStack worn = player.getItemBySlot(slot);
            return LivingEntity.canGlideUsing(worn, slot) && isEnchanted(player, worn);
        });
        return inEquipmentSlot || Platform.gliderAccessories(player).anyMatch(stack -> isEnchanted(player, stack));
    }

    private static boolean isEnchanted(Player player, ItemStack glider)
    {
        return ModEnchantments.activeLevel(player.level().registryAccess(), ModEnchantments.SOARING_WINDS_KEY, glider) >= 1;
    }

    private SoaringWindsEffect()
    {
    }
}
