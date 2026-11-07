package net.Pandarix.entity;

import net.Pandarix.config.BAConfig;
import net.Pandarix.item.ModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

import java.util.stream.IntStream;

public class BombEntity extends ThrowableItemProjectile
{
    // Smoke burst when the bomb hits: particle count and how far each drifts (higher divisor = slower).
    private static final int SMOKE_COUNT = 25;
    private static final double SMOKE_HORIZONTAL_DIVISOR = 5;
    private static final double SMOKE_VERTICAL_DIVISOR = 2;

    public BombEntity(EntityType<? extends BombEntity> entityType, Level level)
    {
        super(entityType, level);
    }

    public BombEntity(Level level, LivingEntity owner, ItemStack stack)
    {
        super(ModEntityTypes.BOMB_ENTITY.get(), owner, level, stack);
    }

    public BombEntity(Level pLevel, double pX, double pY, double pZ, ItemStack stack)
    {
        super(ModEntityTypes.BOMB_ENTITY.get(), pX, pY, pZ, pLevel, stack);
    }

    @Override
    protected @NotNull Item getDefaultItem()
    {
        return ModItems.BOMB_ITEM.get();
    }

    @Override
    public void handleEntityEvent(byte pId)
    {
        if (pId == EntityEvent.DEATH)
        {
            Level level = this.level();
            RandomSource random = level.getRandom();

            IntStream.range(0, SMOKE_COUNT).forEach(i -> level.addParticle(ParticleTypes.LARGE_SMOKE, this.getX(), this.getY(), this.getZ(),
                    random.nextDouble() / SMOKE_HORIZONTAL_DIVISOR * random.nextIntBetweenInclusive(-1, 1),
                    random.nextDouble() / SMOKE_VERTICAL_DIVISOR,
                    random.nextDouble() / SMOKE_HORIZONTAL_DIVISOR * random.nextIntBetweenInclusive(-1, 1)));
        }

    }

    @Override
    public void tick()
    {
        Level level = this.level();
        RandomSource random = level.getRandom();
        if (random.nextBoolean())
        {
            //trail particles for when the bomb is thrown
            level.addParticle(random.nextBoolean() ? ParticleTypes.FLAME : ParticleTypes.SMOKE, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
        }
        super.tick();
    }

    @Override
    protected void onHit(HitResult pResult)
    {
        super.onHit(pResult);
        if (!this.level().isClientSide())
        {
            this.level().broadcastEntityEvent(this, EntityEvent.DEATH);
            this.discard();
        }

        //explode on collision
        this.level().explode(this, this.getX(), this.getY(), this.getZ(), 2.5f, BAConfig.rustyBombTerrainDamage ? Level.ExplosionInteraction.TNT : Level.ExplosionInteraction.NONE);
    }
}
