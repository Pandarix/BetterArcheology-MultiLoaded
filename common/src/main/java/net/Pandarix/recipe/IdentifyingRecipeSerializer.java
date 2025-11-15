package net.Pandarix.recipe;

import net.minecraft.world.item.crafting.SingleItemRecipe;

public class IdentifyingRecipeSerializer extends SingleItemRecipe.Serializer<IdentifyingRecipe>
{
    public IdentifyingRecipeSerializer()
    {
        super(IdentifyingRecipe::new);
    }
}
