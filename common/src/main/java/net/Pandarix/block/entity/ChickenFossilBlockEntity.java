package net.Pandarix.block.entity;

import net.Pandarix.config.BAConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class ChickenFossilBlockEntity extends BlockEntity
{
    public ChickenFossilBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.CHICKEN_FOSSIL.get(), pos, state);
    }

    public static void tick(Level world, BlockPos pos, BlockState state, ChickenFossilBlockEntity blockEntity)
    {
        if (!BAConfig.fossilEffectsEnabled || !BAConfig.chickenFossilEffectsEnabled)
        {
            return;
        }
        //get players in bounding box of 10 blocks
        List<Player> playersInRange = world.getEntitiesOfClass(Player.class, AABB.ofSize(pos.getCenter(), 10, 10, 10));

        //give every player in range slow-falling for 10 seconds, particles are not being displayed for ux
        for (Player player : playersInRange)
        {
            player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 200, 0, false, false));
        }
    }
}
