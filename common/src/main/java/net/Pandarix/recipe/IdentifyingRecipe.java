package net.Pandarix.recipe;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.item.crafting.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class IdentifyingRecipe extends SingleItemRecipe
{
    public IdentifyingRecipe(String string, Ingredient ingredient, ItemStack result)
    {
        super(string, ingredient, result);
    }

    @Override
    public boolean isSpecial()
    {
        return true;
    }

    public ItemStack getResult()
    {
        //Adding the Enchantment Tags
        ItemStack modifiedResultBook = this.result().copy();
        //apply custom naming to the book
        modifiedResultBook.set(DataComponents.ITEM_NAME, Component.translatable("item.betterarcheology.identified_artifact"));
        modifiedResultBook.set(DataComponents.LORE,
                new ItemLore(List.of(Component.translatable("item.betterarcheology.identified_artifact").withColor(ChatFormatting.AQUA.getColor()))));
        return modifiedResultBook;
    }

    @Override
    public @NotNull RecipeSerializer<? extends SingleItemRecipe> getSerializer()
    {
        return ModRecipes.IDENTIFYING_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<? extends SingleItemRecipe> getType()
    {
        return Type.INSTANCE;
    }

    @Override
    public @NotNull RecipeBookCategory recipeBookCategory()
    {
        return RecipeBookCategories.CRAFTING_MISC;
    }

    public static class Type implements RecipeType<IdentifyingRecipe>
    {
        public static final Type INSTANCE = new Type();
    }
}
