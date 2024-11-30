package net.Pandarix.compat.jei.recipe;

import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.Pandarix.BACommon;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class ModRecipes
{
    // REGISTRY ────────────────────────────────────────────────────────────────────────
    public static final Registrar<RecipeSerializer<?>> SERIALIZERS = BACommon.REGISTRIES.get().get(Registries.RECIPE_SERIALIZER);

    public static final RegistrySupplier<RecipeSerializer<IdentifyingRecipe>> IDENTIFYING_SERIALIZER =
            SERIALIZERS.register(BACommon.createResource("identifying"), () -> IdentifyingRecipe.Serializer.INSTANCE);
}