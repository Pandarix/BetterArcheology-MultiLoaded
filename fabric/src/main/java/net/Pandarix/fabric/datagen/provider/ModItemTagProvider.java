package net.Pandarix.fabric.datagen.provider;

import net.Pandarix.block.ModBlocks;
import net.Pandarix.item.ModItems;
import net.Pandarix.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagsProvider.ItemTagsProvider
{
    public ModItemTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture, @Nullable FabricTagsProvider.BlockTagsProvider blockTagProvider)
    {
        super(output, completableFuture, blockTagProvider);
    }

    public ModItemTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture)
    {
        this(output, completableFuture, null);
    }

    private static ResourceKey<Item> keyOf(Item item)
    {
        return BuiltInRegistries.ITEM.getResourceKey(item).orElseThrow();
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
                .add(keyOf(Items.ELYTRA));

        this.builder(ModTags.Items.MUSIC_DISCS)
                .add(ModItems.DISC_SWINGS.getKey());

        this.builder(ModTags.Items.ROTTEN_LOGS)
                .add(keyOf(ModBlocks.ROTTEN_LOG.get().asItem()));

        this.builder(ModTags.Items.BRUSHES)
                .add(keyOf(Items.BRUSH), ModItems.IRON_BRUSH.getKey(), ModItems.DIAMOND_BRUSH.getKey(), ModItems.NETHERITE_BRUSH.getKey());

        this.builder(ItemTags.DIRT)
                .add(keyOf(ModBlocks.FOSSILIFEROUS_DIRT.get().asItem()));

        this.builder(ItemTags.SMALL_FLOWERS)
                .add(keyOf(ModBlocks.GROWTH_TOTEM.get().asItem()));

        this.builder(ItemTags.DOORS)
                .add(keyOf(ModBlocks.ROTTEN_DOOR.get().asItem()));
        this.builder(ItemTags.WOODEN_DOORS)
                .add(keyOf(ModBlocks.ROTTEN_DOOR.get().asItem()));
        this.builder(ItemTags.FENCE_GATES)
                .add(keyOf(ModBlocks.ROTTEN_FENCE_GATE.get().asItem()));
        this.builder(ItemTags.FENCES)
                .add(keyOf(ModBlocks.ROTTEN_FENCE.get().asItem()));
        this.builder(ItemTags.WOODEN_FENCES)
                .add(keyOf(ModBlocks.ROTTEN_FENCE.get().asItem()));
        this.builder(ItemTags.LOGS)
                .add(keyOf(ModBlocks.ROTTEN_LOG.get().asItem()));
        this.builder(ItemTags.LOGS_THAT_BURN)
                .add(keyOf(ModBlocks.ROTTEN_LOG.get().asItem()));
        this.builder(ItemTags.PLANKS)
                .add(keyOf(ModBlocks.ROTTEN_PLANKS.get().asItem()));
        this.builder(ItemTags.WOODEN_PRESSURE_PLATES)
                .add(keyOf(ModBlocks.ROTTEN_PRESSURE_PLATE.get().asItem()));
        this.builder(ItemTags.SLABS)
                .add(keyOf(ModBlocks.ROTTEN_SLAB.get().asItem()));
        this.builder(ItemTags.WOODEN_SLABS)
                .add(keyOf(ModBlocks.ROTTEN_SLAB.get().asItem()));
        this.builder(ItemTags.STAIRS)
                .add(keyOf(ModBlocks.ROTTEN_STAIRS.get().asItem()));
        this.builder(ItemTags.TRAPDOORS)
                .add(keyOf(ModBlocks.ROTTEN_TRAPDOOR.get().asItem()));
        this.builder(ItemTags.WOODEN_TRAPDOORS)
                .add(keyOf(ModBlocks.ROTTEN_TRAPDOOR.get().asItem()));
    }
}
