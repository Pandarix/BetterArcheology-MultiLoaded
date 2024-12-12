package net.Pandarix.compat.jei.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.Pandarix.BACommon;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class IdentifyingRecipe implements Recipe<CraftingInput>
{
    private final Ingredient input;
    private final ItemStack result;
    private static int POSSIBLE_RESULT_COUNT = 0;

    public IdentifyingRecipe(Ingredient inputItems, ItemStack result)
    {
        this.input = inputItems;
        this.result = result;
    }

    @Override
    public boolean matches(CraftingInput pInput, Level pLevel)
    {
        if (pLevel.isClientSide())
        {
            return false;
        }

        return input.test(pInput.getItem(0));
    }

    /**
     * Makes recipe not display in the recipe book
     *
     * @return false
     */
    @Override
    public boolean isSpecial()
    {
        return true;
    }

    @Override
    @NotNull
    public NonNullList<Ingredient> getIngredients()
    {
        return NonNullList.of(Ingredient.EMPTY, input);
    }

    @NotNull
    @Override
    public ItemStack assemble(CraftingInput pInput, HolderLookup.Provider pRegistries)
    {
        return this.getResultItem(pRegistries);
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight)
    {
        return true;
    }

    @NotNull
    @Override
    public ItemStack getResultItem(HolderLookup.Provider pRegistries)
    {
        if (POSSIBLE_RESULT_COUNT == 0)
        {
            try
            {
                POSSIBLE_RESULT_COUNT = pRegistries.lookupOrThrow(Registries.ENCHANTMENT).listElementIds().filter(reference -> reference.registryKey().registry().getNamespace().equals(BACommon.MOD_ID)).toList().size();
            } catch (Exception e)
            {
                BACommon.LOGGER.error("Could not load possible enchantments!", e);
            }
        }
        return getResult(POSSIBLE_RESULT_COUNT);
    }

    public ItemStack getResult(int amountOfEnchantsPossible)
    {
        //Adding the Enchantment Tags
        ItemStack modifiedResultBook = result.copy();
        //apply custom naming to the book
        modifiedResultBook.set(DataComponents.ITEM_NAME, Component.translatable("item.betterarcheology.identified_artifact"));
        modifiedResultBook.set(DataComponents.LORE,
                new ItemLore(List.of(Component.literal(String.format("Chance: 1/%d", amountOfEnchantsPossible)).withColor(ChatFormatting.AQUA.getColor())))
        );
        return modifiedResultBook;
    }

    @Override
    @NotNull
    public RecipeSerializer<?> getSerializer()
    {
        return Serializer.INSTANCE;
    }

    @Override
    @NotNull
    public RecipeType<?> getType()
    {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<IdentifyingRecipe>
    {
        public static final Type INSTANCE = new Type();
    }

    public static class Serializer implements RecipeSerializer<IdentifyingRecipe>
    {
        private static final MapCodec<IdentifyingRecipe> CODEC = RecordCodecBuilder.mapCodec(
                (builder) -> builder.group(
                        Ingredient.CODEC.fieldOf("input").forGetter((IdentifyingRecipe recipe) -> recipe.input),
                        ItemStack.CODEC.fieldOf("result").forGetter((IdentifyingRecipe recipe) -> recipe.result)
                ).apply(builder, IdentifyingRecipe::new)
        );

        public static final Serializer INSTANCE = new Serializer();

        public static final StreamCodec<RegistryFriendlyByteBuf, IdentifyingRecipe> STREAM_CODEC = StreamCodec.of(
                Serializer::toNetwork, Serializer::fromNetwork
        );

        @Override
        public @NotNull MapCodec<IdentifyingRecipe> codec()
        {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, IdentifyingRecipe> streamCodec()
        {
            return STREAM_CODEC;
        }

        public static IdentifyingRecipe fromNetwork(@NotNull RegistryFriendlyByteBuf friendlyByteBuf)
        {
            Ingredient input = Ingredient.CONTENTS_STREAM_CODEC.decode(friendlyByteBuf);
            ItemStack result = ItemStack.OPTIONAL_STREAM_CODEC.decode(friendlyByteBuf);

            return new IdentifyingRecipe(input, result);
        }

        public static void toNetwork(@NotNull RegistryFriendlyByteBuf pBuffer, IdentifyingRecipe pRecipe)
        {
            Ingredient.CONTENTS_STREAM_CODEC.encode(pBuffer, pRecipe.input);
            ItemStack.STREAM_CODEC.encode(pBuffer, pRecipe.result);
        }
    }
}