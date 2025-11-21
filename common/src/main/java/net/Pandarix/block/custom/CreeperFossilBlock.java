package net.Pandarix.block.custom;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class CreeperFossilBlock extends FossilBaseBlock
{
    //Map of hitboxes for every direction the model can be facing
    private static final Map<Direction, VoxelShape> CREEPER_SHAPES_FOR_DIRECTION = ImmutableMap.of(
            Direction.NORTH, Shapes.or(
                    Block.box(3.5, 17.25, 3.5, 12.5, 26.25, 12.5),
                    Block.box(3.5, 5.25, 5.5, 12.5, 17.25, 10.5),
                    Block.box(3, 0, 9.5, 13, 6.5, 14.5),
                    Block.box(3, 0, 1.5, 13, 6.5, 6.5)),
            Direction.SOUTH, Shapes.or(
                    Block.box(3.5, 17.25, 3.5, 12.5, 26.25, 12.5),
                    Block.box(3.5, 5.25, 5.5, 12.5, 17.25, 10.5),
                    Block.box(3, 0, 9.5, 13, 6.5, 14.5),
                    Block.box(3, 0, 1.5, 13, 6.5, 6.5)),
            Direction.WEST, Shapes.or(
                    Block.box(3.5, 17.25, 3.5, 12.5, 26.25, 12.5),
                    Block.box(5.5, 5.25, 3.5, 10.5, 17.25, 12.5),
                    Block.box(1.5, 0, 3, 6.5, 6.5, 13),
                    Block.box(9.5, 0, 3, 14.5, 6.5, 13)),
            Direction.EAST, Shapes.or(
                    Block.box(3.5, 17.25, 3.5, 12.5, 26.25, 12.5),
                    Block.box(5.5, 5.25, 3.5, 10.5, 17.25, 12.5),
                    Block.box(1.5, 0, 3, 6.5, 6.5, 13),
                    Block.box(9.5, 0, 3, 14.5, 6.5, 13)));

    public CreeperFossilBlock(Properties settings)
    {
        super(settings);
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos pos, RandomSource random)
    {
        super.animateTick(blockState, level, pos, random);

        ParticleOptions particle = random.nextBoolean() ? ParticleTypes.SMALL_FLAME : ParticleTypes.SMOKE; //50:50 chance for either spawning Smoke or Flames
        Vec3 center = pos.getCenter();

        if (level.isClientSide())
        {
            //spawns particle at center of Block with random offset & velocity
            level.addParticle(particle,
                    center.x() + random.nextFloat() * getRandomSign(random),
                    center.y() + random.nextFloat() * getRandomSign(random),
                    center.z() + random.nextFloat() * getRandomSign(random),
                    random.nextFloat() / 50f * getRandomSign(random),
                    random.nextFloat() / 30f,
                    random.nextFloat() / 50f * getRandomSign(random)
            );
        }
    }

    private static int getRandomSign(RandomSource rand)
    {
        return (rand.nextBoolean() ? 1 : -1);
    }

    @Override
    @NotNull
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext)
    {
        return CREEPER_SHAPES_FOR_DIRECTION.get(blockState.getValue(FACING));
    }
}
