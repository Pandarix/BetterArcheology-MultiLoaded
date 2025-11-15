package net.Pandarix.compat.jei;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.Pandarix.BACommon;
import net.Pandarix.block.ModBlocks;
import net.Pandarix.item.ModItems;
import net.Pandarix.recipe.IdentifyingRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class IdentifyingCategory implements IRecipeCategory<IdentifyingRecipe>
{
    public static final ResourceLocation UID = BACommon.createResource("identifying");
    public static final ResourceLocation TEXTURE = BACommon.createResource("textures/gui/archeology_table_overlay.png");

    public static final RecipeType<IdentifyingRecipe> IDENTIFYING_RECIPE_TYPE = new RecipeType<>(UID, IdentifyingRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public IdentifyingCategory(IGuiHelper helper)
    {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.ARCHEOLOGY_TABLE.get()));
    }

    @Override
    public @NotNull RecipeType<IdentifyingRecipe> getRecipeType()
    {
        return IDENTIFYING_RECIPE_TYPE;
    }

    @Override
    @NotNull
    public Component getTitle()
    {
        return Component.translatable("block.betterarcheology.archeology_table");
    }

    @Override
    @NotNull
    public IDrawable getBackground()
    {
        return this.background;
    }

    @Override
    @NotNull
    public IDrawable getIcon()
    {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, IdentifyingRecipe recipe, IFocusGroup focuses)
    {
        builder.addSlot(RecipeIngredientRole.INPUT, 80, 20).addItemStacks(
                List.of(Items.BRUSH.getDefaultInstance(), ModItems.IRON_BRUSH.get().getDefaultInstance(), ModItems.DIAMOND_BRUSH.get().getDefaultInstance(), ModItems.NETHERITE_BRUSH.get().getDefaultInstance()));

        builder.addSlot(RecipeIngredientRole.INPUT, 26, 48).addIngredients(recipe.getIngredients().get(0));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 134, 48).addItemStack(recipe.getResult());
    }
}
