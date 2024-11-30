package net.Pandarix.fabric.datagen.provider;

import net.Pandarix.BACommon;
import net.Pandarix.block.ModBlocks;
import net.Pandarix.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.Optional;

public class ModModelProvider extends FabricModelProvider
{
    private static final ModelTemplate BRUSH_TEMPLATE = new ModelTemplate(
            Optional.of(ResourceLocation.withDefaultNamespace("item/brush")),
            Optional.empty(),
            TextureSlot.LAYER0
    );
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
        //blockStateModelGenerator.createRotatableColumn(ModBlocks.CHISELED_BONE_BLOCK.get());
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator)
    {
        ModBlocks.BLOCKS.forEach(block ->
        {
            if (block != ModBlocks.RADIANCE_TOTEM && ModBlocks.isFossil(block))
            {
                //prefixedBlockParent(block, "fossils");
            }
        });

        basicItem(itemModelGenerator, ModItems.ARTIFACT_SHARDS.get());
        basicItem(itemModelGenerator, ModBlocks.GROWTH_TOTEM.get().asItem());
        basicItem(itemModelGenerator, ModBlocks.RADIANCE_TOTEM.get().asItem());
        basicItem(itemModelGenerator, ModItems.TORRENT_TOTEM.get());
        basicItem(itemModelGenerator, ModItems.UNIDENTIFIED_ARTIFACT.get());
        basicItem(itemModelGenerator, ModBlocks.ROTTEN_DOOR.get().asItem());

        brushItem(itemModelGenerator, ModItems.IRON_BRUSH.get());
        brushItem(itemModelGenerator, ModItems.DIAMOND_BRUSH.get());
        brushItem(itemModelGenerator, ModItems.NETHERITE_BRUSH.get());

        handheldItem(itemModelGenerator, ModItems.SOUL_TOTEM.get());
/*
        withExistingParent(ModBlocks.ROTTEN_FENCE.getId().getPath(),
                modLoc("block/rotten_wood/rotten_fence_inventory"));
        withExistingParent(ModBlocks.ROTTEN_TRAPDOOR.getId().getPath(),
                modLoc("block/rotten_wood/rotten_trapdoor_bottom"));*/
    }

    private static void basicItem(ItemModelGenerators generator, Item item)
    {
        generator.generateFlatItem(item, ModelTemplates.FLAT_ITEM);
    }

    private static void handheldItem(ItemModelGenerators generator, Item item)
    {
        generator.generateFlatItem(item, ModelTemplates.FLAT_HANDHELD_ITEM);
    }

    private static void brushItem(ItemModelGenerators generator, Item item)
    {
        ResourceLocation textureLoc = BuiltInRegistries.ITEM.getKey(item).withPrefix("item/brushes/");
        TextureMapping textureMapping = new TextureMapping().put(TextureSlot.LAYER0, textureLoc);

        BRUSH_TEMPLATE.create(ModelLocationUtils.getModelLocation(item), textureMapping, generator.output);
    }

    private static void column(ItemModelGenerators generator, Item item)
    {
        generator.generateFlatItem(item, ModelTemplates.CUBE_COLUMN);
    }

/*    private static void withSimpleParent(ItemModelGenerators generator, Block block)
    {
        generator.
        generator.generateFlatItem(block.asItem(), new ModelTemplate(ModelLocationUtils.getModelLocation(block), Optional.empty(), ));
    }

    private ItemModelBuilder simpleBlockParent(RegistryObject<Block> block)
    {
        return prefixedBlockParent(block, "");
    }

    private ItemModelBuilder prefixedBlockParent(RegistryObject<Block> block, String prefix)
    {
        return withExistingParent(block.getId().getPath(),
                modLoc(
                        "block/"
                                + (prefix.isBlank() ? "" : (prefix + "/"))
                                + block.getId().getPath()
                                + (hasMultipleVariants(prefix) ? "_0" : "")
                ));
    }

    private ItemModelBuilder brushParent(RegistryObject<Item> item)
    {
        return withExistingParent(item.getId().getPath(),
                mcLoc("item/brush")).texture("layer0",
                modLoc("item/brushes/" + item.getId().getPath()));
    }*/

    private static boolean hasMultipleVariants(String path)
    {
        return path.equals("fossils") || path.equals("fossiliferous") || path.equals("suspicious");
    }
}