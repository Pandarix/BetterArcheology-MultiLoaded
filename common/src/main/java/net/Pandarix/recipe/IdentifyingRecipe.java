package net.Pandarix.recipe;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.item.crafting.*;
import org.jetbrains.annotations.NotNull;

public class IdentifyingRecipe extends SingleItemRecipe
{
    public IdentifyingRecipe(Recipe.CommonInfo commonInfo, Ingredient ingredient, ItemStackTemplate result)
    {
        super(commonInfo, ingredient, result);
    }

    @Override
    public boolean isSpecial()
    {
        return true;
    }

    @Override
    public String group()
    {
        return "";
    }

    /**
     * Names an identified book. The enchantment's own description is deliberately left out: this
     * mod ships the ".desc" translation keys that Enchantment Descriptions reads, so that mod
     * already puts the text on the tooltip and a lore line here would duplicate it.
     */
    private static ItemStack decorate(ItemStack stack)
    {
        if (stack.is(Items.ENCHANTED_BOOK))
        {
            stack.set(DataComponents.ITEM_NAME, Component.translatable("item.betterarcheology.identified_artifact"));
        }

        return stack;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull SingleRecipeInput input)
    {
        return decorate(super.assemble(input));
    }

    /**
     * Output as shown by JEI/REI. Same item {@link #assemble} produces, plus a note that identifying
     * picks one artifact at random, which would be nonsense on the item a player actually holds.
     */
    public ItemStack getResult()
    {
        ItemStack item = decorate(this.result().create());
        item.set(DataComponents.LORE, item.getOrDefault(DataComponents.LORE, ItemLore.EMPTY)
                .withLineAdded(Component.translatable("item.betterarcheology.identified_artifact_info").withStyle(ChatFormatting.AQUA)));
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
