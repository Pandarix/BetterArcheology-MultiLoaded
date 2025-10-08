package net.Pandarix.compat.jei;
// Disabled because JEI is not available for 1.21.4
/*
import mezz.jei.api.IModPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.Pandarix.BACommon;
import net.Pandarix.block.ModBlocks;
import net.Pandarix.compat.jei.recipe.IdentifyingRecipe;
import net.Pandarix.screen.IdentifyingScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@mezz.jei.api.JeiPlugin
public class JeiPlugin implements IModPlugin
{
    @Override
    @NotNull
    public ResourceLocation getPluginUid()
    {
        return BACommon.createResource("jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration)
    {
        registration.addRecipeCategories(new IdentifyingCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registration)
    {
        if (Minecraft.getInstance().level != null)
        {
            RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

            List<IdentifyingRecipe> identifyingRecipes = new ArrayList<>();
            recipeManager.getAllRecipesFor(IdentifyingRecipe.Type.INSTANCE).forEach(recipe -> identifyingRecipes.add(recipe.value()));

            registration.addRecipes(IdentifyingCategory.IDENTIFYING_RECIPE_TYPE, identifyingRecipes);
        }
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration)
    {
        registration.addRecipeClickArea(IdentifyingScreen.class, 51, 48, 74, 24,
                IdentifyingCategory.IDENTIFYING_RECIPE_TYPE);
    }

    @Override
    public void registerRecipeCatalysts(@NotNull IRecipeCatalystRegistration registration)
    {
        IModPlugin.super.registerRecipeCatalysts(registration);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.ARCHEOLOGY_TABLE.get()), IdentifyingCategory.IDENTIFYING_RECIPE_TYPE);
    }
}*/
