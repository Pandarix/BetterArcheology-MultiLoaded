package net.Pandarix.enchantment;

import net.Pandarix.config.BAConfig;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.stream.IntStream;

/**
 * Warding: an invisible shield on the chestplate. It only wards off what a shield could block,
 * and stays inactive while a real shield is held, so the two are either-or.
 */
public final class WardingEffect
{
    // The wall is square: WALL_POINTS x WALL_POINTS glyphs spread over WALL_SIZE x WALL_SIZE blocks.
    private static final int WALL_POINTS = 7;
    private static final double WALL_SIZE = 1.6d;
    private static final double WALL_DISTANCE = 0.6d;
    private static final float SOUND_VOLUME = 1.0f;
    private static final float SOUND_PITCH = 1.4f;
    private static final int DURABILITY_COST = 1;

    /** Called at the start of {@code LivingEntity#hurtServer}. Returns true when the hit was warded off. */
    public static boolean tryWard(ServerLevel level, Player player, DamageSource source)
    {
        if (source.is(DamageTypeTags.BYPASSES_SHIELD) || holdsShield(player)) return false;

        ItemStack chestplate = player.getItemBySlot(EquipmentSlot.CHEST);
        int wardingLevel = ModEnchantments.activeLevel(level.registryAccess(), ModEnchantments.WARDING_KEY, chestplate);
        if (wardingLevel < 1) return false;
        if (player.getRandom().nextFloat() >= wardingLevel * BAConfig.wardingChancePerLevel) return false;

        spawnGlyphWall(level, player, source);
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, SOUND_VOLUME, SOUND_PITCH);
        chestplate.hurtAndBreak(DURABILITY_COST, player, EquipmentSlot.CHEST);
        return true;
    }

    private static boolean holdsShield(Player player)
    {
        return player.getMainHandItem().has(DataComponents.BLOCKS_ATTACKS) || player.getOffhandItem().has(DataComponents.BLOCKS_ATTACKS);
    }

    /** A vertical plane of enchanting glyphs between the player and the damage source. */
    private static void spawnGlyphWall(ServerLevel level, Player player, DamageSource source)
    {
        Vec3 direction = horizontalDirection(player, source);
        Vec3 side = new Vec3(-direction.z, 0, direction.x);
        Vec3 centre = player.position()
                .add(0, player.getBbHeight() / 2, 0)
                .add(direction.scale(WALL_DISTANCE));

        IntStream.range(0, WALL_POINTS).forEach(column -> IntStream.range(0, WALL_POINTS).forEach(row ->
        {
            Vec3 point = centre.add(side.scale(spread(column))).add(0, spread(row), 0);
            // With a count of 0 the offset is the velocity, which the enchant particle uses as the
            // offset it drifts in from, so the glyphs flow into the wall from one block outside.
            level.sendParticles(ParticleTypes.ENCHANT, point.x, point.y, point.z, 0, direction.x, direction.y, direction.z, 1.0d);
        }));
    }

    /** Offset of the index-th evenly spaced wall point, centred on 0 and spanning the wall size. */
    private static double spread(int index)
    {
        return (index / (double) (WALL_POINTS - 1) - 0.5d) * WALL_SIZE;
    }

    /** Towards the source, or the player's facing when the source has no usable position (cactus, berry bush). */
    private static Vec3 horizontalDirection(Player player, DamageSource source)
    {
        Vec3 sourcePosition = source.getSourcePosition();
        if (sourcePosition != null)
        {
            // ZERO when the source is straight above or below, which has no horizontal direction
            Vec3 towardsSource = sourcePosition.subtract(player.position()).horizontal().normalize();
            if (!towardsSource.equals(Vec3.ZERO)) return towardsSource;
        }
        return Vec3.directionFromRotation(0, player.getYRot());
    }

    private WardingEffect()
    {
    }
}
