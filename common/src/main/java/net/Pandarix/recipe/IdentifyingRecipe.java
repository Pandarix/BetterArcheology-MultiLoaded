package net.Pandarix.recipe;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class IdentifyingRecipe extends SingleItemRecipe
{
    public IdentifyingRecipe(String string, Ingredient ingredient, ItemStack result)
    {
        super(ModRecipes.IDENTIFYING_RECIPE_TYPE.get(), ModRecipes.IDENTIFYING_SERIALIZER.get(), string, ingredient, result);
    }

    @Override
    public boolean isSpecial()
    {
        return true;
    }


    @Override
    public boolean matches(SingleRecipeInput pInput, Level pLevel)
    {
        if (pLevel.isClientSide())
        {
            return false;
        }

        return this.ingredient.test(pInput.getItem(0));
    }

    public ItemStack getResult()
    {
        //Adding the Enchantment Tags
        ItemStack item = this.result.copy();
        if (item.is(Items.ENCHANTED_BOOK))
        {
            //apply custom naming to the book
            item.set(DataComponents.ITEM_NAME, Component.translatable("item.betterarcheology.identified_artifact"));
            item.set(DataComponents.LORE,
                    new ItemLore(List.of(Component.translatable("item.betterarcheology.identified_artifact_info").withColor(ChatFormatting.AQUA.getColor()))));
        }
        return item;
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

    public static class Type implements RecipeType<IdentifyingRecipe>
    {
        public static final Type INSTANCE = new Type();
    }
}
