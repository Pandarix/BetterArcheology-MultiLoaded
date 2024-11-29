package net.Pandarix.block.entity;

import net.Pandarix.betterarcheology.BetterArcheologyConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class GuardianFossilBlockEntity extends BlockEntity
{
    public GuardianFossilBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.GUARDIAN_FOSSIL.get(), pos, state);
    }

    public static void tick(Level world, BlockPos pos, BlockState state, GuardianFossilBlockEntity blockEntity)
    {
        if (!BetterArcheologyConfig.fossilEffectsEnabled.get() || !BetterArcheologyConfig.guardianFossilEffectsEnabled.get())
        {
            return;
        }
        //get players in bounding box of 10 blocks
        List<Player> playersInRange = world.getEntitiesOfClass(Player.class, AABB.ofSize(pos.getCenter(), 10, 10, 10));

        //give every player in range slow-falling for 10 seconds, particles are not being displayed for ux
        for (Player player : playersInRange)
        {
            player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 200, 0, false, false));
        }
    }
}
