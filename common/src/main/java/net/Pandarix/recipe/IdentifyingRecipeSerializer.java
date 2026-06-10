package net.Pandarix.recipe;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SingleItemRecipe;

public class IdentifyingRecipeSerializer
{
    public static RecipeSerializer<IdentifyingRecipe> create()
    {
        return new RecipeSerializer<>(
                SingleItemRecipe.simpleMapCodec(IdentifyingRecipe::new),
                SingleItemRecipe.simpleStreamCodec(IdentifyingRecipe::new)
        );
    }
}
