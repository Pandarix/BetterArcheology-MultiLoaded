package net.Pandarix.fabric.datagen.provider;

import net.Pandarix.block.ModBlocks;
import net.Pandarix.fabric.datagen.ModDataGenerators;
import net.Pandarix.item.ModItems;
import net.Pandarix.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider
{
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture)
    {
        super(output, registriesFuture);
    }

    @Override
    protected @NotNull RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput)
    {
        return new RecipeProvider(provider, recipeOutput)
        {
            @Override
            public void buildRecipes()
            {
                buildFossilRecipes();
                buildBrickRecipes();
                buildMiscRecipes();
                this.planksFromLogs(ModBlocks.ROTTEN_PLANKS.get(), ModTags.Items.ROTTEN_LOGS, 4);
                this.generateRecipes(ModDataGenerators.ROTTEN_FAM.getFamily(), FeatureFlagSet.of(FeatureFlags.VANILLA));
            }

            private void buildMiscRecipes()
            {
                this.shaped(RecipeCategory.MISC, ModItems.UNIDENTIFIED_ARTIFACT.get())
                        .pattern("###")
                        .pattern("###")
                        .pattern("###")
                        .define('#', ModItems.ARTIFACT_SHARDS.get())
                        .unlockedBy(getHasName(ModItems.ARTIFACT_SHARDS.get()), has(ModItems.ARTIFACT_SHARDS.get()))
                        .save(this.output);

                this.shaped(RecipeCategory.MISC, ModBlocks.ARCHEOLOGY_TABLE.get())
                        .pattern("BS")
                        .pattern("WW")
                        .pattern("WW")
                        .define('B', ModTags.Items.BRUSHES)
                        .define('S', ModItems.ARTIFACT_SHARDS.get())
                        .define('W', ItemTags.PLANKS)
                        .unlockedBy(getHasName(ModItems.ARTIFACT_SHARDS.get()), has(ModItems.ARTIFACT_SHARDS.get()))
                        .save(this.output);

                this.shaped(RecipeCategory.COMBAT, ModItems.BOMB_ITEM.get())
                        .pattern("III")
                        .pattern("IGI")
                        .pattern("III")
                        .define('I', Items.IRON_NUGGET)
                        .define('G', Items.GUNPOWDER)
                        .unlockedBy(getHasName(Items.GUNPOWDER), has(Items.GUNPOWDER))
                        .save(this.output);

                this.shaped(RecipeCategory.TOOLS, ModItems.IRON_BRUSH.get())
                        .pattern("F")
                        .pattern("I")
                        .pattern("S")
                        .define('F', Items.FEATHER)
                        .define('I', Items.IRON_INGOT)
                        .define('S', Items.STICK)
                        .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                        .save(this.output);

                this.shaped(RecipeCategory.TOOLS, ModItems.DIAMOND_BRUSH.get())
                        .pattern("F")
                        .pattern("D")
                        .pattern("S")
                        .define('F', Items.FEATHER)
                        .define('D', Items.DIAMOND)
                        .define('S', Items.STICK)
                        .unlockedBy(getHasName(Items.DIAMOND), has(Items.DIAMOND))
                        .save(this.output);

                netheriteSmithing(ModItems.DIAMOND_BRUSH.get(), RecipeCategory.TOOLS, ModItems.NETHERITE_BRUSH.get());
            }

            private void buildBrickRecipes()
            {
                this.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CRACKED_MUD_BRICK_SLAB.get(), 6)
                        .pattern("###")
                        .define('#', ModBlocks.CRACKED_MUD_BRICKS.get())
                        .unlockedBy(getHasName(ModBlocks.CRACKED_MUD_BRICKS.get()), has(ModBlocks.CRACKED_MUD_BRICKS.get()))
                        .save(this.output);

                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CRACKED_MUD_BRICK_SLAB.get(), ModBlocks.CRACKED_MUD_BRICKS.get(), 2);

                this.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CRACKED_MUD_BRICK_STAIRS.get(), 4)
                        .pattern("#  ")
                        .pattern("## ")
                        .pattern("###")
                        .define('#', ModBlocks.CRACKED_MUD_BRICKS.get())
                        .unlockedBy(getHasName(ModBlocks.CRACKED_MUD_BRICKS.get()), has(ModBlocks.CRACKED_MUD_BRICKS.get()))
                        .save(this.output);

                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CRACKED_MUD_BRICK_STAIRS.get(), ModBlocks.CRACKED_MUD_BRICKS.get(), 1);

                smeltingResultFromBase(ModBlocks.CRACKED_MUD_BRICKS.get(), Blocks.MUD_BRICKS);
            }

            private void buildFossilRecipes()
            {
                this.shapeless(RecipeCategory.DECORATIONS, ModBlocks.CHICKEN_FOSSIL.get())
                        .group("ba_fossils")
                        .requires(ModBlocks.CHICKEN_FOSSIL_BODY.get())
                        .requires(ModBlocks.CHICKEN_FOSSIL_HEAD.get())
                        .unlockedBy(getHasName(ModBlocks.CHICKEN_FOSSIL_BODY.get()), has(ModBlocks.CHICKEN_FOSSIL_BODY.get()))
                        .unlockedBy(getHasName(ModBlocks.CHICKEN_FOSSIL_HEAD.get()), has(ModBlocks.CHICKEN_FOSSIL_HEAD.get()))
                        .save(this.output);

                this.shapeless(RecipeCategory.DECORATIONS, ModBlocks.CREEPER_FOSSIL.get())
                        .group("ba_fossils")
                        .requires(ModBlocks.CREEPER_FOSSIL_BODY.get())
                        .requires(ModBlocks.CREEPER_FOSSIL_HEAD.get())
                        .unlockedBy(getHasName(ModBlocks.CREEPER_FOSSIL_BODY.get()), has(ModBlocks.CREEPER_FOSSIL_BODY.get()))
                        .unlockedBy(getHasName(ModBlocks.CREEPER_FOSSIL_HEAD.get()), has(ModBlocks.CREEPER_FOSSIL_HEAD.get()))
                        .save(this.output);

                this.shapeless(RecipeCategory.DECORATIONS, ModBlocks.GUARDIAN_FOSSIL.get())
                        .group("ba_fossils")
                        .requires(ModBlocks.GUARDIAN_FOSSIL_HEAD.get())
                        .requires(ModBlocks.GUARDIAN_FOSSIL_BODY.get())
                        .unlockedBy(getHasName(ModBlocks.GUARDIAN_FOSSIL_BODY.get()), has(ModBlocks.GUARDIAN_FOSSIL_BODY.get()))
                        .unlockedBy(getHasName(ModBlocks.GUARDIAN_FOSSIL_HEAD.get()), has(ModBlocks.GUARDIAN_FOSSIL_HEAD.get()))
                        .save(this.output);

                this.shapeless(RecipeCategory.DECORATIONS, ModBlocks.OCELOT_FOSSIL.get())
                        .group("ba_fossils")
                        .requires(ModBlocks.OCELOT_FOSSIL_HEAD.get())
                        .requires(ModBlocks.OCELOT_FOSSIL_BODY.get())
                        .unlockedBy(getHasName(ModBlocks.OCELOT_FOSSIL_BODY.get()), has(ModBlocks.OCELOT_FOSSIL_BODY.get()))
                        .unlockedBy(getHasName(ModBlocks.OCELOT_FOSSIL_HEAD.get()), has(ModBlocks.OCELOT_FOSSIL_HEAD.get()))
                        .save(this.output);

                this.shapeless(RecipeCategory.DECORATIONS, ModBlocks.SHEEP_FOSSIL.get())
                        .group("ba_fossils")
                        .requires(ModBlocks.SHEEP_FOSSIL_HEAD.get())
                        .requires(ModBlocks.SHEEP_FOSSIL_BODY.get())
                        .unlockedBy(getHasName(ModBlocks.SHEEP_FOSSIL_BODY.get()), has(ModBlocks.SHEEP_FOSSIL_BODY.get()))
                        .unlockedBy(getHasName(ModBlocks.SHEEP_FOSSIL_HEAD.get()), has(ModBlocks.SHEEP_FOSSIL_HEAD.get()))
                        .save(this.output);

                this.shapeless(RecipeCategory.DECORATIONS, ModBlocks.VILLAGER_FOSSIL.get())
                        .group("ba_fossils")
                        .requires(ModBlocks.VILLAGER_FOSSIL_HEAD.get())
                        .requires(ModBlocks.VILLAGER_FOSSIL_BODY.get())
                        .unlockedBy(getHasName(ModBlocks.VILLAGER_FOSSIL_BODY.get()), has(ModBlocks.VILLAGER_FOSSIL_BODY.get()))
                        .unlockedBy(getHasName(ModBlocks.VILLAGER_FOSSIL_HEAD.get()), has(ModBlocks.VILLAGER_FOSSIL_HEAD.get()))
                        .save(this.output);

                this.shapeless(RecipeCategory.DECORATIONS, ModBlocks.WOLF_FOSSIL.get())
                        .group("ba_fossils")
                        .requires(ModBlocks.WOLF_FOSSIL_HEAD.get())
                        .requires(ModBlocks.WOLF_FOSSIL_BODY.get())
                        .unlockedBy(getHasName(ModBlocks.WOLF_FOSSIL_BODY.get()), has(ModBlocks.WOLF_FOSSIL_BODY.get()))
                        .unlockedBy(getHasName(ModBlocks.WOLF_FOSSIL_HEAD.get()), has(ModBlocks.WOLF_FOSSIL_HEAD.get()))
                        .save(this.output);
            }
        };
    }

    @Override
    public @NotNull String getName()
    {
        return "BetterArcheologyRecipeprovider";
    }
}