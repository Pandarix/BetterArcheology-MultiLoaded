package net.Pandarix.fabric.datagen.provider;

import net.Pandarix.BACommon;
import net.Pandarix.block.ModBlocks;
import net.Pandarix.fabric.datagen.ModDataGenerators;
import net.Pandarix.item.ModItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.BrushableBlock;
import org.jspecify.annotations.NonNull;

public class ModModelProvider extends FabricModelProvider
{
    public ModModelProvider(FabricDataOutput output)
    {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator)
    {
       blockStateModelGenerator.family(ModDataGenerators.ROTTEN_FAM.getFamily().getBaseBlock()).generateFor(ModDataGenerators.ROTTEN_FAM.getFamily());
       blockStateModelGenerator.woodProvider(ModBlocks.ROTTEN_LOG.get()).logWithHorizontal(ModBlocks.ROTTEN_LOG.get());
    }

    @Override
    public void generateItemModels(@NonNull ItemModelGenerators itemModelGenerator)
    {
        basicItem(itemModelGenerator, ModItems.ARTIFACT_SHARDS.get());
        basicItem(itemModelGenerator, ModBlocks.GROWTH_TOTEM.get().asItem());
        basicItem(itemModelGenerator, ModBlocks.RADIANCE_TOTEM.get().asItem());
        basicItem(itemModelGenerator, ModItems.TORRENT_TOTEM.get());
        basicItem(itemModelGenerator, ModItems.UNIDENTIFIED_ARTIFACT.get());
        handheldItem(itemModelGenerator, ModItems.SOUL_TOTEM.get());

        itemModelGenerator.generateBrush(ModItems.IRON_BRUSH.get());
        itemModelGenerator.generateBrush(ModItems.DIAMOND_BRUSH.get());
        itemModelGenerator.generateBrush(ModItems.NETHERITE_BRUSH.get());

        itemModelGenerator.declareCustomModelItem(ModItems.BOMB_ITEM.get());
        itemModelGenerator.declareCustomModelItem(ModItems.DISC_SWINGS.get());

        itemModelGenerator.declareCustomModelItem(ModBlocks.ARCHEOLOGY_TABLE.get().asItem());
        itemModelGenerator.declareCustomModelItem(ModBlocks.CHISELED_BONE_BLOCK.get().asItem());
        itemModelGenerator.declareCustomModelItem(ModBlocks.CRACKED_MUD_BRICK_SLAB.get().asItem());
        itemModelGenerator.declareCustomModelItem(ModBlocks.CRACKED_MUD_BRICK_STAIRS.get().asItem());
        itemModelGenerator.declareCustomModelItem(ModBlocks.CRACKED_MUD_BRICKS.get().asItem());
        itemModelGenerator.declareCustomModelItem(ModBlocks.EVOKER_TRAP.get().asItem());
        itemModelGenerator.declareCustomModelItem(ModBlocks.INFESTED_MUD_BRICKS.get().asItem());

        itemModelGenerator.declareCustomModelItem(ModBlocks.VASE.get().asItem());
        itemModelGenerator.declareCustomModelItem(ModBlocks.VASE_GREEN.get().asItem());
        itemModelGenerator.declareCustomModelItem(ModBlocks.VASE_CREEPER.get().asItem());
        itemModelGenerator.itemModelOutput.accept(ModBlocks.LOOT_VASE.get().asItem(),
                ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(ModBlocks.VASE.get().asItem())));
        itemModelGenerator.itemModelOutput.accept(ModBlocks.LOOT_VASE_GREEN.get().asItem(),
                ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(ModBlocks.VASE_GREEN.get().asItem())));
        itemModelGenerator.itemModelOutput.accept(ModBlocks.LOOT_VASE_CREEPER.get().asItem(),
                ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(ModBlocks.VASE_CREEPER.get().asItem())));

        // Registrar#forEach iterates the whole block registry, so vanilla blocks have to be filtered out
        // to avoid emitting models into the minecraft namespace that override vanilla ones.
        ModBlocks.BLOCKS.forEach((block) ->
        {
            Identifier id = ModBlocks.BLOCKS.getId(block);

            if (id == null || !id.getNamespace().equals(BACommon.MOD_ID))
                return;

            if (ModBlocks.isFossil(block) || block instanceof BrushableBlock) {
                itemModelGenerator.declareCustomModelItem(block.asItem());
            }
        });
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