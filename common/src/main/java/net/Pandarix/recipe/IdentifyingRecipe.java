package net.Pandarix.recipe;

import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

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
    public @NotNull ItemStack assemble(@NotNull SingleRecipeInput input, HolderLookup.@NotNull Provider registries)
    {
        return decorate(super.assemble(input, registries));
    }

    /**
     * Output as shown by JEI/REI. Same item {@link #assemble} produces, plus a note that identifying
     * picks one artifact at random - which would be nonsense on the item a player actually holds.
     */
    public ItemStack getResult()
    {
        ItemStack item = decorate(this.result.copy());
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

    public static class Type implements RecipeType<IdentifyingRecipe>
    {
        public static final Type INSTANCE = new Type();
    }
}
