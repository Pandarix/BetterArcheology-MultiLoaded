package net.Pandarix.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.Pandarix.BACommon;
import net.Pandarix.block.ModBlocks;
import net.Pandarix.recipe.IdentifyingRecipe;
import net.Pandarix.screen.IdentifyingScreen;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

@mezz.jei.api.JeiPlugin
public class JeiPlugin implements IModPlugin
{
    /**
     * Since 1.21.2 the server no longer syncs its recipes to clients, so JEI cannot see the
     * identifying recipes on its own. Each loader opts into syncing them and points this at the
     * synced copy: NeoForge through RecipesReceivedEvent, Fabric through the recipe sync API.
     */
    public static Supplier<Collection<RecipeHolder<IdentifyingRecipe>>> identifyingRecipes = List::of;

    @Override
    @NotNull
    public Identifier getPluginUid()
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
        registration.addRecipes(IdentifyingCategory.IDENTIFYING_RECIPE_TYPE, new ArrayList<>(identifyingRecipes.get()));
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
