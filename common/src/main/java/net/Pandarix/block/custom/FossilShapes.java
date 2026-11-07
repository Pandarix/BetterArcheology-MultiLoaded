package net.Pandarix.block.custom;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;

/**
 * Hitboxes of the fossil head and body parts, one map per part keyed by the facing of the block.
 */
public final class FossilShapes
{
    // ─── HEADS ───────────────────────────────────────────────────────────────────────
    public static final Map<Direction, VoxelShape> CHICKEN_HEAD = uniform(Block.box(4, 0, 4, 12, 4, 12));
    public static final Map<Direction, VoxelShape> CREEPER_HEAD = uniform(Block.box(3, 0, 3, 13, 8, 13));
    public static final Map<Direction, VoxelShape> GUARDIAN_HEAD = uniform(Block.box(1, 0, 1, 15, 15, 15));
    public static final Map<Direction, VoxelShape> OCELOT_HEAD = uniform(Block.box(4, 0, 4, 12, 4, 12));
    public static final Map<Direction, VoxelShape> SHEEP_HEAD = uniform(Block.box(3, 0, 3, 13, 8, 13));
    public static final Map<Direction, VoxelShape> VILLAGER_HEAD = uniform(Block.box(3, 0, 3, 13, 10, 13));
    public static final Map<Direction, VoxelShape> WOLF_HEAD = uniform(Block.box(4, 0, 4, 12, 6, 12));

    // ─── BODIES ──────────────────────────────────────────────────────────────────────
    public static final Map<Direction, VoxelShape> CHICKEN_BODY = uniform(Block.box(1, 0, 1, 15, 6, 15));
    public static final Map<Direction, VoxelShape> GUARDIAN_BODY = uniform(Block.box(1, 0, 1, 15, 8, 15));
    public static final Map<Direction, VoxelShape> WOLF_BODY = uniform(Block.box(1, 0, 1, 15, 8, 15));

    public static final Map<Direction, VoxelShape> CREEPER_BODY = axisAligned(
            Shapes.or(
                    Block.box(3.5, 5.25, 5.5, 12.5, 17.25, 10.5),
                    Block.box(3, 0, 9.5, 13, 6.5, 14.5),
                    Block.box(3, 0, 1.5, 13, 6.5, 6.5)),
            Shapes.or(
                    Block.box(5.5, 5.25, 3.5, 10.5, 17.25, 12.5),
                    Block.box(1.5, 0, 3, 6.5, 6.5, 13),
                    Block.box(9.5, 0, 3, 14.5, 6.5, 13)));

    public static final Map<Direction, VoxelShape> OCELOT_BODY = axisAligned(
            Block.box(4, 0, 0, 12, 7, 16),
            Block.box(0, 0, 4, 16, 7, 12));

    public static final Map<Direction, VoxelShape> SHEEP_BODY = Map.of(
            Direction.NORTH, Block.box(0, 0, 2, 16, 8, 16),
            Direction.SOUTH, Block.box(0, 0, 0, 16, 8, 14),
            Direction.EAST, Block.box(2, 0, 0, 16, 8, 16),
            Direction.WEST, Block.box(0, 0, 0, 14, 8, 16));

    public static final Map<Direction, VoxelShape> VILLAGER_BODY = Map.of(
            Direction.NORTH, Block.box(0, 0, 8, 16, 12, 15),
            Direction.SOUTH, Block.box(0, 0, 1, 16, 12, 8),
            Direction.EAST, Block.box(1, 0, 0, 8, 12, 16),
            Direction.WEST, Block.box(8, 0, 0, 15, 12, 16));

    // UTIL ───────────────────────────────────────────────────────────────────────────
    /** The same hitbox for every facing. */
    public static Map<Direction, VoxelShape> uniform(VoxelShape shape)
    {
        return axisAligned(shape, shape);
    }

    /** One hitbox when facing north or south, another when facing east or west. */
    public static Map<Direction, VoxelShape> axisAligned(VoxelShape northSouth, VoxelShape eastWest)
    {
        return Map.of(
                Direction.NORTH, northSouth,
                Direction.SOUTH, northSouth,
                Direction.EAST, eastWest,
                Direction.WEST, eastWest);
    }
}
