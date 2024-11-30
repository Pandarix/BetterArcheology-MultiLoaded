package net.Pandarix.fabric.datagen.provider;

import net.Pandarix.block.ModBlocks;
import net.Pandarix.item.ModItems;
import net.Pandarix.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider
{
    public ModRecipeProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries)
    {
        super(pOutput, pRegistries);
    }

    @Override
    public void buildRecipes(RecipeOutput recipeOutput)
    {
        buildFossilRecipes(recipeOutput);
        buildBrickRecipes(recipeOutput);
        buildMiscRecipes(recipeOutput);
        buildRottenRecipes(recipeOutput);
    }

    private static void buildRottenRecipes(RecipeOutput recipeOutput)
    {
        planksFromLog(recipeOutput, ModBlocks.ROTTEN_PLANKS.get(), ModTags.Items.ROTTEN_LOGS, 4);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModBlocks.ROTTEN_DOOR.get(), 3)
                .pattern("PP")
                .pattern("PP")
                .pattern("PP")
                .define('P', ModBlocks.ROTTEN_PLANKS.get())
                .group("wooden_door")
                .unlockedBy(getHasName(ModBlocks.ROTTEN_PLANKS.get()), has(ModBlocks.ROTTEN_PLANKS.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ROTTEN_FENCE.get(), 3)
                .pattern("W#W")
                .pattern("W#W")
                .define('#', Items.STICK)
                .define('W', ModBlocks.ROTTEN_PLANKS.get())
                .group("wooden_fence")
                .unlockedBy(getHasName(ModBlocks.ROTTEN_PLANKS.get()), has(ModBlocks.ROTTEN_PLANKS.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModBlocks.ROTTEN_FENCE_GATE.get())
                .pattern("#W#")
                .pattern("#W#")
                .define('#', Items.STICK)
                .define('W', ModBlocks.ROTTEN_PLANKS.get())
                .group("wooden_fence_gate")
                .unlockedBy(getHasName(ModBlocks.ROTTEN_PLANKS.get()), has(ModBlocks.ROTTEN_PLANKS.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.ROTTEN_SLAB.get(), 6)
                .pattern("WWW")
                .define('W', ModBlocks.ROTTEN_PLANKS.get())
                .group("wooden_slab")
                .unlockedBy(getHasName(ModBlocks.ROTTEN_PLANKS.get()), has(ModBlocks.ROTTEN_PLANKS.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModBlocks.ROTTEN_PRESSURE_PLATE.get())
                .pattern("WW")
                .define('W', ModBlocks.ROTTEN_PLANKS.get())
                .group("wooden_pressure_plate")
                .unlockedBy(getHasName(ModBlocks.ROTTEN_PLANKS.get()), has(ModBlocks.ROTTEN_PLANKS.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.ROTTEN_STAIRS.get(), 4)
                .pattern("W  ")
                .pattern("WW ")
                .pattern("WWW")
                .define('W', ModBlocks.ROTTEN_PLANKS.get())
                .group("wooden_stairs")
                .unlockedBy(getHasName(ModBlocks.ROTTEN_PLANKS.get()), has(ModBlocks.ROTTEN_PLANKS.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModBlocks.ROTTEN_TRAPDOOR.get(), 2)
                .pattern("WWW")
                .pattern("WWW")
                .define('W', ModBlocks.ROTTEN_PLANKS.get())
                .group("wooden_trapdoor")
                .unlockedBy(getHasName(ModBlocks.ROTTEN_PLANKS.get()), has(ModBlocks.ROTTEN_PLANKS.get()))
                .save(recipeOutput);
    }

    private static void buildMiscRecipes(RecipeOutput recipeOutput)
    {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.UNIDENTIFIED_ARTIFACT.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.ARTIFACT_SHARDS.get())
                .unlockedBy(getHasName(ModItems.ARTIFACT_SHARDS.get()), has(ModItems.ARTIFACT_SHARDS.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ARCHEOLOGY_TABLE.get())
                .pattern("BS")
                .pattern("WW")
                .pattern("WW")
                .define('B', ModTags.Items.BRUSHES)
                .define('S', ModItems.ARTIFACT_SHARDS.get())
                .define('W', ItemTags.PLANKS)
                .unlockedBy(getHasName(ModItems.ARTIFACT_SHARDS.get()), has(ModItems.ARTIFACT_SHARDS.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.BOMB_ITEM.get())
                .pattern("III")
                .pattern("IGI")
                .pattern("III")
                .define('I', Items.IRON_NUGGET)
                .define('G', Items.GUNPOWDER)
                .unlockedBy(getHasName(Items.GUNPOWDER), has(Items.GUNPOWDER))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.IRON_BRUSH.get())
                .pattern("F")
                .pattern("I")
                .pattern("S")
                .define('F', Items.FEATHER)
                .define('I', Items.IRON_INGOT)
                .define('S', Items.STICK)
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.DIAMOND_BRUSH.get())
                .pattern("F")
                .pattern("D")
                .pattern("S")
                .define('F', Items.FEATHER)
                .define('D', Items.DIAMOND)
                .define('S', Items.STICK)
                .unlockedBy(getHasName(Items.DIAMOND), has(Items.DIAMOND))
                .save(recipeOutput);

        netheriteSmithing(recipeOutput, ModItems.DIAMOND_BRUSH.get(), RecipeCategory.TOOLS, ModItems.NETHERITE_BRUSH.get());
    }

    private static void buildBrickRecipes(RecipeOutput recipeOutput)
    {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CRACKED_MUD_BRICK_SLAB.get(), 6)
                .pattern("###")
                .define('#', ModBlocks.CRACKED_MUD_BRICKS.get())
                .unlockedBy(getHasName(ModBlocks.CRACKED_MUD_BRICKS.get()), has(ModBlocks.CRACKED_MUD_BRICKS.get()))
                .save(recipeOutput);

        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CRACKED_MUD_BRICK_SLAB.get(), ModBlocks.CRACKED_MUD_BRICKS.get(), 2);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CRACKED_MUD_BRICK_STAIRS.get(), 4)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .define('#', ModBlocks.CRACKED_MUD_BRICKS.get())
                .unlockedBy(getHasName(ModBlocks.CRACKED_MUD_BRICKS.get()), has(ModBlocks.CRACKED_MUD_BRICKS.get()))
                .save(recipeOutput);

        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CRACKED_MUD_BRICK_STAIRS.get(), ModBlocks.CRACKED_MUD_BRICKS.get(), 1);

        smeltingResultFromBase(recipeOutput, ModBlocks.CRACKED_MUD_BRICKS.get(), Blocks.MUD_BRICKS);
    }

    private static void buildFossilRecipes(RecipeOutput recipeOutput)
    {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ModBlocks.CHICKEN_FOSSIL.get())
                .group("ba_fossils")
                .requires(ModBlocks.CHICKEN_FOSSIL_BODY.get())
                .requires(ModBlocks.CHICKEN_FOSSIL_HEAD.get())
                .unlockedBy(getHasName(ModBlocks.CHICKEN_FOSSIL_BODY.get()), has(ModBlocks.CHICKEN_FOSSIL_BODY.get()))
                .unlockedBy(getHasName(ModBlocks.CHICKEN_FOSSIL_HEAD.get()), has(ModBlocks.CHICKEN_FOSSIL_HEAD.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ModBlocks.CREEPER_FOSSIL.get())
                .group("ba_fossils")
                .requires(ModBlocks.CREEPER_FOSSIL_BODY.get())
                .requires(ModBlocks.CREEPER_FOSSIL_HEAD.get())
                .unlockedBy(getHasName(ModBlocks.CREEPER_FOSSIL_BODY.get()), has(ModBlocks.CREEPER_FOSSIL_BODY.get()))
                .unlockedBy(getHasName(ModBlocks.CREEPER_FOSSIL_HEAD.get()), has(ModBlocks.CREEPER_FOSSIL_HEAD.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ModBlocks.GUARDIAN_FOSSIL.get())
                .group("ba_fossils")
                .requires(ModBlocks.GUARDIAN_FOSSIL_HEAD.get())
                .requires(ModBlocks.GUARDIAN_FOSSIL_BODY.get())
                .unlockedBy(getHasName(ModBlocks.GUARDIAN_FOSSIL_BODY.get()), has(ModBlocks.GUARDIAN_FOSSIL_BODY.get()))
                .unlockedBy(getHasName(ModBlocks.GUARDIAN_FOSSIL_HEAD.get()), has(ModBlocks.GUARDIAN_FOSSIL_HEAD.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ModBlocks.OCELOT_FOSSIL.get())
                .group("ba_fossils")
                .requires(ModBlocks.OCELOT_FOSSIL_HEAD.get())
                .requires(ModBlocks.OCELOT_FOSSIL_BODY.get())
                .unlockedBy(getHasName(ModBlocks.OCELOT_FOSSIL_BODY.get()), has(ModBlocks.OCELOT_FOSSIL_BODY.get()))
                .unlockedBy(getHasName(ModBlocks.OCELOT_FOSSIL_HEAD.get()), has(ModBlocks.OCELOT_FOSSIL_HEAD.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ModBlocks.SHEEP_FOSSIL.get())
                .group("ba_fossils")
                .requires(ModBlocks.SHEEP_FOSSIL_HEAD.get())
                .requires(ModBlocks.SHEEP_FOSSIL_BODY.get())
                .unlockedBy(getHasName(ModBlocks.SHEEP_FOSSIL_BODY.get()), has(ModBlocks.SHEEP_FOSSIL_BODY.get()))
                .unlockedBy(getHasName(ModBlocks.SHEEP_FOSSIL_HEAD.get()), has(ModBlocks.SHEEP_FOSSIL_HEAD.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ModBlocks.VILLAGER_FOSSIL.get())
                .group("ba_fossils")
                .requires(ModBlocks.VILLAGER_FOSSIL_HEAD.get())
                .requires(ModBlocks.VILLAGER_FOSSIL_BODY.get())
                .unlockedBy(getHasName(ModBlocks.VILLAGER_FOSSIL_BODY.get()), has(ModBlocks.VILLAGER_FOSSIL_BODY.get()))
                .unlockedBy(getHasName(ModBlocks.VILLAGER_FOSSIL_HEAD.get()), has(ModBlocks.VILLAGER_FOSSIL_HEAD.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ModBlocks.WOLF_FOSSIL.get())
                .group("ba_fossils")
                .requires(ModBlocks.WOLF_FOSSIL_HEAD.get())
                .requires(ModBlocks.WOLF_FOSSIL_BODY.get())
                .unlockedBy(getHasName(ModBlocks.WOLF_FOSSIL_BODY.get()), has(ModBlocks.WOLF_FOSSIL_BODY.get()))
                .unlockedBy(getHasName(ModBlocks.WOLF_FOSSIL_HEAD.get()), has(ModBlocks.WOLF_FOSSIL_HEAD.get()))
                .save(recipeOutput);
    }
}