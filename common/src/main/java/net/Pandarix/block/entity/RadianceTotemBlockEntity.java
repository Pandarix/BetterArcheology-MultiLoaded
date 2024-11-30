package net.Pandarix.block.entity;

import net.Pandarix.block.custom.RadianceTotemBlock;
import net.Pandarix.config.BAConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class RadianceTotemBlockEntity extends BlockEntity
{
    public RadianceTotemBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.RADIANCE_TOTEM.get(), pos, state);
    }

    public static void tick(Level world, BlockPos pos, BlockState state, RadianceTotemBlockEntity blockEntity)
    {
        if (!BAConfig.radianceTotemEnabled || !BAConfig.totemsEnabled)
        {
            return;
        }
        // Apply the effects of the totem every 10 ticks (.5 seconds) to reduce server stress
        if (world.getRandom().nextIntBetweenInclusive(1, 10) == 1)
        {
            //get all entities in a 10 block radius
            int totemRadius = BAConfig.radianceTotemRadius * 2;
            List<LivingEntity> livingEntities = world.getEntitiesOfClass(LivingEntity.class, AABB.ofSize(pos.getCenter(), totemRadius, totemRadius, totemRadius));
            applyGlowingEffect(livingEntities, state);

            //damages every hostile monster with a chance of 1/(configValue*2), resulting in an average damage tick every configValue seconds
            if (BAConfig.radianceTotemDamageEnabled &&
                    world.getRandom().nextIntBetweenInclusive(1, BAConfig.radianceTotemDamageTickAverage * 2) == 1)
            {
                for (LivingEntity livingEntity : livingEntities)
                {
                    if (livingEntity instanceof Monster monster)
                    {
                        monster.hurt(monster.damageSources().magic(), BAConfig.radianceTotemDamage);
                        world.playSound(null, monster.position().x(), monster.position().y(), monster.position().z(), SoundEvents.AMETHYST_BLOCK_HIT, SoundSource.HOSTILE, 0.5f, 0.5f);
                    }
                }
            }
        }
    }

    /**
     * Applies glow effect to all entities that are selected in the selector from the block
     *
     * @param livingEntities List of living entities around the Totem
     * @param state          BlockState of the Totem to get the selector from
     */
    private static void applyGlowingEffect(List<LivingEntity> livingEntities, BlockState state)
    {
        // get selector index which type of Entity should glow, default 0
        int selector = 0;
        if (state.getBlock() instanceof RadianceTotemBlock)
        {
            selector = state.getValue(RadianceTotemBlock.SELECTOR);
        }

        Class<?> filteredClass = getFilteredEntityClass(selector);
        livingEntities.forEach(livingEntity ->
        {
            if (filteredClass.isInstance(livingEntity))
            {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 200, 0, false, false));
            }
        });
    }

    /**
     * Gets the corresponding Class of the Entity that is selected by the index set in the Radiance Totem
     *
     * @param selector Index of the Entityfilter
     * @return The Class of the Entities that should glow
     */
    private static Class<?> getFilteredEntityClass(int selector)
    {
        return switch (selector)
        {
            case 1 -> Monster.class;
            case 2 -> Animal.class;
            case 3 -> Player.class;
            default -> LivingEntity.class;
        };
    }
}
