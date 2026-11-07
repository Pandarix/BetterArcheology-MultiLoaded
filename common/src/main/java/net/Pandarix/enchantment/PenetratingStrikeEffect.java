package net.Pandarix.enchantment;

import net.Pandarix.config.BAConfig;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.ItemStack;

/**
 * Penetrating Strike: the weapon ignores part of the target's enchantment protection.
 */
public final class PenetratingStrikeEffect
{
    /** Called from {@code EnchantmentHelper#getDamageProtection} with the protection it computed. */
    public static float reduceProtection(ServerLevel level, DamageSource source, float protection)
    {
        // Most targets have no protection; skip the enchantment lookup for them.
        if (protection <= 0) return protection;

        ItemStack weapon = source.getWeaponItem();
        if (weapon == null || ModEnchantments.activeLevel(level.registryAccess(), ModEnchantments.PENETRATING_STRIKE_KEY, weapon) < 1) return protection;

        float keptProtection = (float) (1 - BAConfig.penetratingStrikeIgnorance);
        return Math.max(0, protection * keptProtection);
    }

    private PenetratingStrikeEffect()
    {
    }
}
