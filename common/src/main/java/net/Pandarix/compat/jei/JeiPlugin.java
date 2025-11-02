package net.Pandarix.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.Pandarix.BACommon;
import net.Pandarix.block.ModBlocks;
import net.Pandarix.compat.jei.recipe.IdentifyingRecipe;
import net.Pandarix.screen.IdentifyingScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeMap;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

@mezz.jei.api.JeiPlugin
public class JeiPlugin implements IModPlugin
{
    public static RecipeMap recipeMap;

    @Override
    @NotNull
    public ResourceLocation getPluginUid()
    {
        return BACommon.createRLoc("jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration)
    {
        registration.addRecipeCategories(new IdentifyingCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registration)
    {
        if (recipeMap != null)
        {
            registration.addRecipes(IdentifyingCategory.IDENTIFYING_RECIPE_TYPE, new ArrayList<>(recipeMap.byType(IdentifyingRecipe.Type.INSTANCE)));
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
        registration.addCraftingStation(IdentifyingCategory.IDENTIFYING_RECIPE_TYPE, new ItemStack(ModBlocks.ARCHEOLOGY_TABLE.get()));
    }
}
