package net.Pandarix.fabric.datagen.provider;

import net.Pandarix.BACommon;
import net.Pandarix.block.ModBlocks;
import net.Pandarix.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.world.item.Item;

import java.util.Optional;

public class ModModelProvider extends FabricModelProvider
{
    private static final ModelTemplate VASE = new ModelTemplate(
            Optional.of(BACommon.createResource("block/loot_vase_0")),
            Optional.empty(),
            TextureSlot.LAYER0,
            TextureSlot.PARTICLE
    );

    public ModModelProvider(FabricDataOutput output)
    {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator)
    {
        blockStateModelGenerator.createGenericCube(ModBlocks.EVOKER_TRAP.get());
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator)
    {
        basicItem(itemModelGenerator, ModItems.ARTIFACT_SHARDS.get());
        basicItem(itemModelGenerator, ModBlocks.GROWTH_TOTEM.get().asItem());
        basicItem(itemModelGenerator, ModBlocks.RADIANCE_TOTEM.get().asItem());
        basicItem(itemModelGenerator, ModItems.TORRENT_TOTEM.get());
        basicItem(itemModelGenerator, ModItems.UNIDENTIFIED_ARTIFACT.get());
        basicItem(itemModelGenerator, ModBlocks.ROTTEN_DOOR.get().asItem());

        handheldItem(itemModelGenerator, ModItems.SOUL_TOTEM.get());
    }

    private static void basicItem(ItemModelGenerators generator, Item item)
    {
        generator.generateFlatItem(item, ModelTemplates.FLAT_ITEM);
    }

    private static void handheldItem(ItemModelGenerators generator, Item item)
    {
        generator.generateFlatItem(item, ModelTemplates.FLAT_HANDHELD_ITEM);
    }
}