package net.Pandarix.mixin;

import net.Pandarix.BACommon;
import net.Pandarix.config.BAConfig;
import net.Pandarix.enchantment.ModEnchantments;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class WardingMixin
{
    // the wall is square: POINTS x POINTS glyphs spread over SIZE x SIZE blocks
    @Unique
    private static final int betterarcheology$WARDING_PARTICLE_WALL_POINTS = 7;
    @Unique
    private static final double betterarcheology$WARDING_PARTICLE_WALL_SIZE = 1.6d;
    @Unique
    private static final double betterarcheology$WARDING_PARTICLE_WALL_DISTANCE = 0.6d;

    // Warding acts as an invisible shield: it only wards off what a shield could block,
    // and stays inactive while a real shield is held, so the two are either-or.
    @Inject(method = "hurtServer", at = @At("HEAD"), cancellable = true)
    private void betterarcheology$ward(ServerLevel level, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir)
    {
        if (!BAConfig.artifactsEnabled || !BAConfig.wardingEnabled) return;
        if (!((Object) this instanceof Player player)) return;
        if (source.is(DamageTypeTags.BYPASSES_SHIELD)) return;
        if (betterarcheology$holdsShield(player)) return;

        ItemStack chestplate = player.getItemBySlot(EquipmentSlot.CHEST);
        int wardingLevel = betterarcheology$getWardingLevel(level, chestplate);
        if (wardingLevel < 1) return;
        if (player.getRandom().nextFloat() >= wardingLevel * BAConfig.wardingChancePerLevel) return;

        betterarcheology$spawnGlyphWall(level, player, source);
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, 1.0f, 1.4f);
        chestplate.hurtAndBreak(1, player, EquipmentSlot.CHEST);

        cir.setReturnValue(false);
    }

    @Unique
    private static boolean betterarcheology$holdsShield(Player player)
    {
        return player.getMainHandItem().has(DataComponents.BLOCKS_ATTACKS) || player.getOffhandItem().has(DataComponents.BLOCKS_ATTACKS);
    }

    @Unique
    private static int betterarcheology$getWardingLevel(ServerLevel level, ItemStack chestplate)
    {
        if (!chestplate.isEnchanted()) return 0;

        try
        {
            Holder.Reference<Enchantment> warding = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(ModEnchantments.WARDING_KEY);
            return EnchantmentHelper.getItemEnchantmentLevel(warding, chestplate);
        } catch (Exception e)
        {
            BACommon.LOGGER.error("Could not test for Warding because EnchantmentEntry could not be found", e);
            return 0;
        }
    }

    // Builds a vertical plane of enchanting glyphs between the player and the damage source.
    // Falls back to the player's facing when the source has no position (e.g. cactus or sweet berry bushes).
    @Unique
    private static void betterarcheology$spawnGlyphWall(ServerLevel level, Player player, DamageSource source)
    {
        Vec3 direction = betterarcheology$horizontalDirection(player, source);
        Vec3 side = new Vec3(-direction.z, 0, direction.x);
        Vec3 centre = player.position()
                .add(0, player.getBbHeight() / 2, 0)
                .add(direction.scale(betterarcheology$WARDING_PARTICLE_WALL_DISTANCE));

        for (int column = 0; column < betterarcheology$WARDING_PARTICLE_WALL_POINTS; column++)
        {
            Vec3 columnBase = centre.add(side.scale(betterarcheology$spread(column)));
            for (int row = 0; row < betterarcheology$WARDING_PARTICLE_WALL_POINTS; row++)
            {
                // With a count of 0 the offset is passed as velocity, which the enchant particle uses as
                // the offset it drifts in from - so the glyphs flow into the wall from one block outside.
                level.sendParticles(ParticleTypes.ENCHANT, columnBase.x, columnBase.y + betterarcheology$spread(row), columnBase.z, 0, direction.x, direction.y, direction.z, 1.0d);
            }
        }
    }

    // Offset of the index-th evenly spaced wall point, centred on 0 and spanning the wall size.
    @Unique
    private static double betterarcheology$spread(int index)
    {
        return (index / (double) (betterarcheology$WARDING_PARTICLE_WALL_POINTS - 1) - 0.5d) * betterarcheology$WARDING_PARTICLE_WALL_SIZE;
    }

    @Unique
    private static Vec3 betterarcheology$horizontalDirection(Player player, DamageSource source)
    {
        Vec3 sourcePosition = source.getSourcePosition();
        if (sourcePosition != null)
        {
            // ZERO when the source sits right above or below the player, which has no horizontal direction
            Vec3 towardsSource = sourcePosition.subtract(player.position()).horizontal().normalize();
            if (!towardsSource.equals(Vec3.ZERO)) return towardsSource;
        }

        // pitch 0: the facing flattened to the horizontal plane, never zero-length
        return Vec3.directionFromRotation(0, player.getYRot());
    }
}
