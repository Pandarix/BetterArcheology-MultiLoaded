package net.Pandarix.fabric.datagen.provider;

import net.Pandarix.block.ModBlocks;
import net.Pandarix.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider
{
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider)
    {
        super(output, lookupProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider)
    {
        this.tag(ModTags.Blocks.ROTTEN_LOGS)
                .add(ModBlocks.ROTTEN_LOG.getKey());

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(
                        ModBlocks.CRACKED_MUD_BRICKS.getKey(),
                        ModBlocks.CRACKED_MUD_BRICK_SLAB.getKey(),
                        ModBlocks.CRACKED_MUD_BRICK_STAIRS.getKey(),
                        ModBlocks.VILLAGER_FOSSIL.getKey(),
                        ModBlocks.VILLAGER_FOSSIL_BODY.getKey(),
                        ModBlocks.VILLAGER_FOSSIL_HEAD.getKey(),
                        ModBlocks.OCELOT_FOSSIL.getKey(),
                        ModBlocks.OCELOT_FOSSIL_BODY.getKey(),
                        ModBlocks.OCELOT_FOSSIL_HEAD.getKey(),
                        ModBlocks.SHEEP_FOSSIL.getKey(),
                        ModBlocks.SHEEP_FOSSIL_BODY.getKey(),
                        ModBlocks.SHEEP_FOSSIL_HEAD.getKey(),
                        ModBlocks.CHICKEN_FOSSIL.getKey(),
                        ModBlocks.CHICKEN_FOSSIL_BODY.getKey(),
                        ModBlocks.CHICKEN_FOSSIL_HEAD.getKey(),
                        ModBlocks.CREEPER_FOSSIL.getKey(),
                        ModBlocks.CREEPER_FOSSIL_BODY.getKey(),
                        ModBlocks.CREEPER_FOSSIL_HEAD.getKey(),
                        ModBlocks.WOLF_FOSSIL.getKey(),
                        ModBlocks.WOLF_FOSSIL_BODY.getKey(),
                        ModBlocks.WOLF_FOSSIL_HEAD.getKey(),
                        ModBlocks.GUARDIAN_FOSSIL.getKey(),
                        ModBlocks.GUARDIAN_FOSSIL_BODY.getKey(),
                        ModBlocks.GUARDIAN_FOSSIL_HEAD.getKey(),
                        ModBlocks.EVOKER_TRAP.getKey(),
                        ModBlocks.CHISELED_BONE_BLOCK.getKey(),
                        ModBlocks.RADIANCE_TOTEM.getKey()
                );

        this.tag(BlockTags.MINEABLE_WITH_AXE)
                .add(
                        ModBlocks.ROTTEN_LOG.getKey(),
                        ModBlocks.ROTTEN_PLANKS.getKey(),
                        ModBlocks.ROTTEN_STAIRS.getKey(),
                        ModBlocks.ROTTEN_SLAB.getKey(),
                        ModBlocks.ROTTEN_FENCE.getKey(),
                        ModBlocks.ROTTEN_FENCE_GATE.getKey(),
                        ModBlocks.ROTTEN_TRAPDOOR.getKey(),
                        ModBlocks.ROTTEN_DOOR.getKey(),
                        ModBlocks.ROTTEN_PRESSURE_PLATE.getKey(),
                        ModBlocks.ARCHEOLOGY_TABLE.getKey()
                );

        this.tag(BlockTags.MINEABLE_WITH_SHOVEL)
                .add(ModBlocks.FOSSILIFEROUS_DIRT.getKey());
        this.tag(BlockTags.BAMBOO_PLANTABLE_ON)
                .add(ModBlocks.FOSSILIFEROUS_DIRT.getKey());
        this.tag(BlockTags.DIRT)
                .add(ModBlocks.FOSSILIFEROUS_DIRT.getKey());

        this.tag(BlockTags.LUSH_GROUND_REPLACEABLE)
                .add(ModBlocks.FOSSILIFEROUS_DIRT.getKey());
        this.tag(BlockTags.FLOWERS)
                .add(ModBlocks.GROWTH_TOTEM.getKey());
        this.tag(BlockTags.SMALL_FLOWERS)
                .add(ModBlocks.GROWTH_TOTEM.getKey());

        this.tag(BlockTags.DOORS)
                .add(ModBlocks.ROTTEN_DOOR.getKey());
        this.tag(BlockTags.WOODEN_DOORS)
                .add(ModBlocks.ROTTEN_DOOR.getKey());
        this.tag(BlockTags.FENCE_GATES)
                .add(ModBlocks.ROTTEN_FENCE_GATE.getKey());
        this.tag(BlockTags.FENCES)
                .add(ModBlocks.ROTTEN_FENCE.getKey());
        this.tag(BlockTags.WOODEN_FENCES)
                .add(ModBlocks.ROTTEN_FENCE.getKey());
        this.tag(BlockTags.LOGS)
                .add(ModBlocks.ROTTEN_LOG.getKey());
        this.tag(BlockTags.LOGS_THAT_BURN)
                .add(ModBlocks.ROTTEN_LOG.getKey());
        this.tag(BlockTags.PLANKS)
                .add(ModBlocks.ROTTEN_PLANKS.getKey());
        this.tag(BlockTags.PRESSURE_PLATES)
                .add(ModBlocks.ROTTEN_PRESSURE_PLATE.getKey());
        this.tag(BlockTags.WOODEN_PRESSURE_PLATES)
                .add(ModBlocks.ROTTEN_PRESSURE_PLATE.getKey());
        this.tag(BlockTags.WALL_POST_OVERRIDE)
                .add(ModBlocks.ROTTEN_PRESSURE_PLATE.getKey());
        this.tag(BlockTags.SLABS)
                .add(ModBlocks.ROTTEN_SLAB.getKey());
        this.tag(BlockTags.WOODEN_SLABS)
                .add(ModBlocks.ROTTEN_SLAB.getKey());
        this.tag(BlockTags.STAIRS)
                .add(ModBlocks.ROTTEN_STAIRS.getKey());
        this.tag(BlockTags.TRAPDOORS)
                .add(ModBlocks.ROTTEN_TRAPDOOR.getKey());
        this.tag(BlockTags.WOODEN_TRAPDOORS)
                .add(ModBlocks.ROTTEN_TRAPDOOR.getKey());
        this.tag(BlockTags.UNSTABLE_BOTTOM_CENTER)
                .add(ModBlocks.ROTTEN_FENCE_GATE.getKey());
    }
}