package net.Pandarix.compat.rei;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.Pandarix.BACommon;
import net.Pandarix.recipe.IdentifyingRecipe;
import net.Pandarix.util.ModTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.List;
import java.util.Optional;

public record IdentifyingDisplay(RecipeHolder<IdentifyingRecipe> recipe) implements Display
{
    public static final ResourceLocation TEXTURE = BACommon.createResource("textures/gui/rei_archeology_table_overlay.png");
    public static final CategoryIdentifier<IdentifyingDisplay> CATEGORY = CategoryIdentifier.of(BACommon.MOD_ID, "identifying");

    @Override
    public List<EntryIngredient> getInputEntries()
    {
        return List.of(EntryIngredients.ofIngredient(recipe.value().getIngredients().getFirst()), EntryIngredients.ofItemTag(ModTags.Items.BRUSHES));
    }

    @Override
    public List<EntryIngredient> getOutputEntries()
    {
        return List.of(EntryIngredients.of(recipe.value().getResult()));
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier()
    {
        return CATEGORY;
    }

    @Override
    public Optional<ResourceLocation> getDisplayLocation()
    {
        return Optional.of(TEXTURE);
    }
}
