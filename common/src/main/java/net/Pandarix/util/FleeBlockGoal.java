package net.Pandarix.util;

import net.Pandarix.config.BAConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class FleeBlockGoal<T extends BlockEntity> extends Goal
{
    protected final PathfinderMob mob;
    private final double slowSpeed;
    private final double fastSpeed;
    @Nullable
    protected BlockEntity targetBlock;
    @Nullable
    protected Path fleePath;
    protected final PathNavigation pathNav;
    protected final PathNavigation fleeingEntityNavigation;
    protected final Class<T> classToFleeFrom;

    public FleeBlockGoal(PathfinderMob mob, Class<T> fleeFromType, double slowSpeed, double fastSpeed)
    {
        this.mob = mob;
        this.classToFleeFrom = fleeFromType;
        this.slowSpeed = slowSpeed;
        this.fastSpeed = fastSpeed;
        this.fleeingEntityNavigation = mob.getNavigation();
        this.pathNav = mob.getNavigation();
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    public boolean canUse()
    {
        this.targetBlock = this.getClosestBlockEntity(this.mob);
        if (this.targetBlock == null)
        {
            return false;
        } else
        {
            Vec3 vec3d = DefaultRandomPos.getPosAway(this.mob, 16, 7, this.targetBlock.getBlockPos().getCenter());
            if (vec3d == null)
            {
                return false;
            } else if (vec3d.distanceToSqr(targetBlock.getBlockPos().getX(), targetBlock.getBlockPos().getY(), targetBlock.getBlockPos().getZ()) < this.mob.distanceToSqr(this.targetBlock.getBlockPos().getCenter()))
            {
                return false;
            } else
            {
                this.fleePath = this.pathNav.createPath(vec3d.x, vec3d.y, vec3d.z, 0);
                return this.fleePath != null;
            }
        }
    }

    private BlockEntity getClosestBlockEntity(PathfinderMob fleeingEntity)
    {
        BlockEntity closestBlockEntity = null; //set to no Entity for now
        double closestDistanceSq = Double.MAX_VALUE; //initially the biggest value possible
        List<BlockEntity> blockEntities = getBlockEntitiesInRange(fleeingEntity);

        //searches every blockEntity from the list
        for (BlockEntity blockEntity : blockEntities)
        {
            double distanceSq = blockEntity.getBlockPos().distToCenterSqr(fleeingEntity.position()); //calculate distance
            //if this distance is closer than the previous, set the closest Entity to this one
            if (distanceSq < closestDistanceSq)
            {
                closestBlockEntity = blockEntity;
                closestDistanceSq = distanceSq;
            }
        }
        return closestBlockEntity;
    }

    private List<BlockEntity> getBlockEntitiesInRange(PathfinderMob fleeingEntity)
    {
        List<BlockEntity> blockEntities = new ArrayList<>();

        int chunkX = ((int) Math.floor(fleeingEntity.position().x())) >> 4; // Divide by 16 to get chunk coordinates
        int chunkZ = ((int) Math.floor(fleeingEntity.position().z())) >> 4;

        LevelChunk chunk = fleeingEntity.level().getChunk(chunkX, chunkZ);
        Set<BlockPos> blockEntityPos = chunk.getBlockEntitiesPos();

        for (BlockPos blockPos : blockEntityPos)
        {
            BlockEntity blockEntity = fleeingEntity.level().getBlockEntity(blockPos);
            if (blockEntity != null && (blockEntity.getClass() == this.classToFleeFrom) && isWithinDistance(blockEntity.getBlockPos(), fleeingEntity.position()))
            { // Check if within distance
                blockEntities.add(blockEntity);
            }
        }

        return blockEntities;
    }

    private boolean isWithinDistance(BlockPos blockposition, Vec3 position)
    {
        return blockposition.distToCenterSqr(position) - BAConfig.fossilFleeRange <= 0;
    }

    public boolean canContinueToUse()
    {
        return !this.pathNav.isDone();
    }

    public void start()
    {
        this.pathNav.moveTo(this.fleePath, this.slowSpeed);
    }

    public void stop()
    {
        this.targetBlock = null;
    }

    public void tick()
    {
        if (this.targetBlock != null && this.mob.distanceToSqr(this.targetBlock.getBlockPos().getCenter()) < 49.0D)
        {
            this.mob.getNavigation().setSpeedModifier(this.fastSpeed);
        } else
        {
            this.mob.getNavigation().setSpeedModifier(this.slowSpeed);
        }

    }
}
