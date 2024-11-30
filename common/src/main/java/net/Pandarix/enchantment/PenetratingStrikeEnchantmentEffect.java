package net.Pandarix.enchantment;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.Pandarix.config.BAConfig;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public record PenetratingStrikeEnchantmentEffect(LevelBasedValue amount) implements EnchantmentEntityEffect
{
    public static final MapCodec<PenetratingStrikeEnchantmentEffect> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    LevelBasedValue.CODEC.fieldOf("amount").forGetter(PenetratingStrikeEnchantmentEffect::amount)
            ).apply(instance, PenetratingStrikeEnchantmentEffect::new));

    /**
     * Only displays audio for the Enchantment.
     * The actual effects were moved to {@link net.Pandarix.betterarcheology.mixin.PenetratingStrikeMixin}.
     */
    @Override
    public void apply(ServerLevel pLevel, int pEnchantmentLevel, EnchantedItemInUse pItem, Entity pEntity, Vec3 pOrigin)
    {
        if (!BAConfig.artifactsEnabled || !BAConfig.penetratingStrikeEnabled)
        {
            return;
        }

        pLevel.playSound(null, pOrigin.x, pOrigin.y, pOrigin.z, SoundEvents.ARMOR_EQUIP_CHAIN, SoundSource.BLOCKS, 1f, .7f);
    }

    @Override
    @NotNull
    public MapCodec<? extends EnchantmentEntityEffect> codec()
    {
        return CODEC;
    }
}

