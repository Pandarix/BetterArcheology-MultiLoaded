package net.Pandarix.fabric.datagen.provider;

import net.Pandarix.block.ModBlocks;
import net.Pandarix.item.ModItems;
import net.Pandarix.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider
{
    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture, @Nullable FabricTagProvider.BlockTagProvider blockTagProvider)
    {
        super(output, completableFuture, blockTagProvider);
    }

    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture)
    {
        this(output, completableFuture, null);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider)
    {
        this.tag(ModTags.Items.TUNNELING_ITEMS)
                .addOptionalTag(ItemTags.PICKAXES.location())
                .addOptionalTag(ItemTags.SHOVELS.location())
                .addOptionalTag(ItemTags.HOES.location());

        this.tag(ModTags.Items.ELYTRAS)
                .add(Items.ELYTRA.builtInRegistryHolder().key());

        this.tag(ModTags.Items.ROTTEN_LOGS)
                .add(ModBlocks.ROTTEN_LOG.get().asItem().builtInRegistryHolder().key());

        this.tag(ModTags.Items.BRUSHES)
                .add(Items.BRUSH.builtInRegistryHolder().key(), ModItems.IRON_BRUSH.getKey(), ModItems.DIAMOND_BRUSH.getKey(), ModItems.NETHERITE_BRUSH.getKey());

        this.tag(ItemTags.DIRT)
                .add(ModBlocks.FOSSILIFEROUS_DIRT.get().asItem().builtInRegistryHolder().key());

        this.tag(ItemTags.SMALL_FLOWERS)
                .add(ModBlocks.GROWTH_TOTEM.get().asItem().builtInRegistryHolder().key());

        this.tag(ItemTags.DOORS)
                .add(ModBlocks.ROTTEN_DOOR.get().asItem().builtInRegistryHolder().key());
        this.tag(ItemTags.WOODEN_DOORS)
                .add(ModBlocks.ROTTEN_DOOR.get().asItem().builtInRegistryHolder().key());
        this.tag(ItemTags.FENCE_GATES)
                .add(ModBlocks.ROTTEN_FENCE_GATE.get().asItem().builtInRegistryHolder().key());
        this.tag(ItemTags.FENCES)
                .add(ModBlocks.ROTTEN_FENCE.get().asItem().builtInRegistryHolder().key());
        this.tag(ItemTags.WOODEN_FENCES)
                .add(ModBlocks.ROTTEN_FENCE.get().asItem().builtInRegistryHolder().key());
        this.tag(ItemTags.LOGS)
                .add(ModBlocks.ROTTEN_LOG.get().asItem().builtInRegistryHolder().key());
        this.tag(ItemTags.LOGS_THAT_BURN)
                .add(ModBlocks.ROTTEN_LOG.get().asItem().builtInRegistryHolder().key());
        this.tag(ItemTags.PLANKS)
                .add(ModBlocks.ROTTEN_PLANKS.get().asItem().builtInRegistryHolder().key());
        this.tag(ItemTags.WOODEN_PRESSURE_PLATES)
                .add(ModBlocks.ROTTEN_PRESSURE_PLATE.get().asItem().builtInRegistryHolder().key());
        this.tag(ItemTags.SLABS)
                .add(ModBlocks.ROTTEN_SLAB.get().asItem().builtInRegistryHolder().key());
        this.tag(ItemTags.WOODEN_SLABS)
                .add(ModBlocks.ROTTEN_SLAB.get().asItem().builtInRegistryHolder().key());
        this.tag(ItemTags.STAIRS)
                .add(ModBlocks.ROTTEN_STAIRS.get().asItem().builtInRegistryHolder().key());
        this.tag(ItemTags.TRAPDOORS)
                .add(ModBlocks.ROTTEN_TRAPDOOR.get().asItem().builtInRegistryHolder().key());
        this.tag(ItemTags.WOODEN_TRAPDOORS)
                .add(ModBlocks.ROTTEN_TRAPDOOR.get().asItem().builtInRegistryHolder().key());
    }
}
