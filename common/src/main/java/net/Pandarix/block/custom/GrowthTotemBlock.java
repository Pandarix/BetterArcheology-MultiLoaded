package net.Pandarix.block.custom;

import net.Pandarix.config.BAConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SpellParticleOption;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ARGB;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class GrowthTotemBlock extends FlowerBlock
{
    public GrowthTotemBlock(Holder<MobEffect> mobEffect, int pEffectDuration, Properties pProperties)
    {
        super(mobEffect, pEffectDuration, pProperties);
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom)
    {
        super.animateTick(pState, pLevel, pPos, pRandom);
        if (!BAConfig.totemsEnabled || !BAConfig.growthTotemEnabled)
            return;

        if (pLevel.isClientSide() && pRandom.nextIntBetweenInclusive(0, 40) == 0)
        {
            for (int i = -BAConfig.growthTotemGrowRadius; i <= BAConfig.growthTotemGrowRadius; i++)
            {
                for (int j = -BAConfig.growthTotemGrowRadius; j <= BAConfig.growthTotemGrowRadius; j++)
                {
                    if (pRandom.nextIntBetweenInclusive(0, 3) == 3)
                    {
                        SpellParticleOption particleOption = SpellParticleOption.create(ParticleTypes.INSTANT_EFFECT, ARGB.white(1), 0);

                        Vec3 center = pPos.offset(i, 0, j).getCenter();
                        pLevel.addParticle(particleOption,
                                center.x + randomDirectionModifier(pRandom, 3),
                                pPos.getY(),
                                center.z + randomDirectionModifier(pRandom, 3),
                                0, -5, 0
                        );
                    }
                }
            }
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState pState)
    {
        return true;
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom)
    {
        if (!BAConfig.totemsEnabled || !BAConfig.growthTotemEnabled)
            return;

        if (pRandom.nextIntBetweenInclusive(1, 100) > BAConfig.growthTotemGrowChance)
            return;

        for (int i = -BAConfig.growthTotemGrowRadius; i <= BAConfig.growthTotemGrowRadius; i++)
        {
            for (int j = -BAConfig.growthTotemGrowRadius; j <= BAConfig.growthTotemGrowRadius; j++)
            {
                BlockPos pos = pPos.offset(i, 0, j);
                BlockState state = pLevel.getBlockState(pos);

                if (state.getBlock() instanceof CropBlock cropBlock)
                {
                    if (cropBlock.isValidBonemealTarget(pLevel, pos, state))
                    {
                        if (cropBlock.isBonemealSuccess(pLevel, pLevel.random, pos, state))
                        {
                            cropBlock.performBonemeal(pLevel, pLevel.random, pos, state);
                            if (pRandom.nextBoolean())
                                pLevel.playSound(null, pos, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.BLOCKS);
                        }
                    }
                }
            }
        }
        super.randomTick(pState, pLevel, pPos, pRandom);
    }

    @Override
    protected void entityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity, InsideBlockEffectApplier insideBlockEffectApplier, boolean bl)
    {
        super.entityInside(blockState, level, blockPos, entity, insideBlockEffectApplier, bl);
        if (entity instanceof LivingEntity livingEntity)
            livingEntity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 100));
    }

    private static float randomDirectionModifier(RandomSource pRandom, int pReduce)
    {
        return ((pRandom.nextFloat() / pReduce) * pRandom.nextIntBetweenInclusive(-1, 1));
    }
}
