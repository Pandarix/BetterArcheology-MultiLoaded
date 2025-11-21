package net.Pandarix.fabric.datagen.provider;

import net.Pandarix.block.ModBlocks;
import net.Pandarix.item.ModItems;
import net.Pandarix.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
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
        this.builder(ItemTags.DURABILITY_ENCHANTABLE)
                .addOptionalTag(ModTags.Items.BRUSHES);

        this.builder(ModTags.Items.TUNNELING_ITEMS)
                .addOptionalTag(ItemTags.PICKAXES)
                .addOptionalTag(ItemTags.SHOVELS)
                .addOptionalTag(ItemTags.HOES);

        this.builder(ModTags.Items.ELYTRAS)
                .add(Items.ELYTRA.builtInRegistryHolder().key());

        this.builder(ModTags.Items.ROTTEN_LOGS)
                .add(ModBlocks.ROTTEN_LOG.get().asItem().builtInRegistryHolder().key());

        this.builder(ModTags.Items.BRUSHES)
                .add(Items.BRUSH.builtInRegistryHolder().key(), ModItems.IRON_BRUSH.getKey(), ModItems.DIAMOND_BRUSH.getKey(), ModItems.NETHERITE_BRUSH.getKey());

        this.builder(ItemTags.DIRT)
                .add(ModBlocks.FOSSILIFEROUS_DIRT.get().asItem().builtInRegistryHolder().key());

        this.builder(ItemTags.SMALL_FLOWERS)
                .add(ModBlocks.GROWTH_TOTEM.get().asItem().builtInRegistryHolder().key());

        this.builder(ItemTags.DOORS)
                .add(ModBlocks.ROTTEN_DOOR.get().asItem().builtInRegistryHolder().key());
        this.builder(ItemTags.WOODEN_DOORS)
                .add(ModBlocks.ROTTEN_DOOR.get().asItem().builtInRegistryHolder().key());
        this.builder(ItemTags.FENCE_GATES)
                .add(ModBlocks.ROTTEN_FENCE_GATE.get().asItem().builtInRegistryHolder().key());
        this.builder(ItemTags.FENCES)
                .add(ModBlocks.ROTTEN_FENCE.get().asItem().builtInRegistryHolder().key());
        this.builder(ItemTags.WOODEN_FENCES)
                .add(ModBlocks.ROTTEN_FENCE.get().asItem().builtInRegistryHolder().key());
        this.builder(ItemTags.LOGS)
                .add(ModBlocks.ROTTEN_LOG.get().asItem().builtInRegistryHolder().key());
        this.builder(ItemTags.LOGS_THAT_BURN)
                .add(ModBlocks.ROTTEN_LOG.get().asItem().builtInRegistryHolder().key());
        this.builder(ItemTags.PLANKS)
                .add(ModBlocks.ROTTEN_PLANKS.get().asItem().builtInRegistryHolder().key());
        this.builder(ItemTags.WOODEN_PRESSURE_PLATES)
                .add(ModBlocks.ROTTEN_PRESSURE_PLATE.get().asItem().builtInRegistryHolder().key());
        this.builder(ItemTags.SLABS)
                .add(ModBlocks.ROTTEN_SLAB.get().asItem().builtInRegistryHolder().key());
        this.builder(ItemTags.WOODEN_SLABS)
                .add(ModBlocks.ROTTEN_SLAB.get().asItem().builtInRegistryHolder().key());
        this.builder(ItemTags.STAIRS)
                .add(ModBlocks.ROTTEN_STAIRS.get().asItem().builtInRegistryHolder().key());
        this.builder(ItemTags.TRAPDOORS)
                .add(ModBlocks.ROTTEN_TRAPDOOR.get().asItem().builtInRegistryHolder().key());
        this.builder(ItemTags.WOODEN_TRAPDOORS)
                .add(ModBlocks.ROTTEN_TRAPDOOR.get().asItem().builtInRegistryHolder().key());
    }
}
