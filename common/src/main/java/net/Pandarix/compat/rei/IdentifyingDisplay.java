package net.Pandarix.compat.rei;

/*
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.Pandarix.BACommon;
import net.Pandarix.recipe.IdentifyingRecipe;
import net.Pandarix.recipe.ModRecipes;
import net.Pandarix.util.ModTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public record IdentifyingDisplay(RecipeHolder<IdentifyingRecipe> recipe) implements Display
{
    public static final ResourceLocation TEXTURE = BACommon.createRLoc("textures/gui/rei_archeology_table_overlay.png");
    public static final CategoryIdentifier<IdentifyingDisplay> CATEGORY = CategoryIdentifier.of(BACommon.MOD_ID, "identifying");
    public static final DisplaySerializer<? extends IdentifyingDisplay> SERIALIZER = DisplaySerializer.of(
            RecordCodecBuilder.mapCodec((instance) ->
                    instance.group(ResourceLocation.CODEC.fieldOf("recipeId")
                                    .forGetter(display -> display.recipe.id().location()), ModRecipes.IDENTIFYING_SERIALIZER.get().codec().fieldOf("ingredient")
                                    .forGetter(display -> display.recipe.value()))
                            .apply(instance, (recipeId, recipe) -> new IdentifyingDisplay(new RecipeHolder<>(
                                    ResourceKey.create(Registries.RECIPE, recipeId), recipe)))),
            StreamCodec.composite(
                    ResourceLocation.STREAM_CODEC,
                    display -> display.recipe.id().location(), ModRecipes.IDENTIFYING_SERIALIZER.get().streamCodec(),
                    display -> display.recipe.value(),
                    (recipeId, recipe) -> new IdentifyingDisplay(new RecipeHolder<>(
                            ResourceKey.create(Registries.RECIPE, recipeId), recipe))));

    @Override
    public List<EntryIngredient> getInputEntries()
    {
        return List.of(EntryIngredients.ofIngredient(recipe.value().placementInfo().ingredients().getFirst()), EntryIngredients.ofItemTag(ModTags.Items.BRUSHES));
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

    @Override
    public @NotNull DisplaySerializer<? extends Display> getSerializer()
    {
        return SERIALIZER;
    }
}
*/
