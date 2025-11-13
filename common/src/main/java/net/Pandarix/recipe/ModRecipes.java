package net.Pandarix.recipe;

import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.Pandarix.BACommon;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class ModRecipes
{
    // REGISTRY ────────────────────────────────────────────────────────────────────────
    public static final Registrar<RecipeSerializer<?>> SERIALIZERS = BACommon.REGISTRIES.get().get(Registries.RECIPE_SERIALIZER);
    public static final Registrar<RecipeType<?>> RECIPE_TYPES = BACommon.REGISTRIES.get().get(Registries.RECIPE_TYPE);

    public static final RegistrySupplier<RecipeSerializer<IdentifyingRecipe>> IDENTIFYING_SERIALIZER =
            SERIALIZERS.register(BACommon.createRLoc("identifying"), IdentifyingRecipeSerializer::new);
    public static final RegistrySupplier<RecipeType<IdentifyingRecipe>> IDENTIFYING_RECIPE_TYPE =
            RECIPE_TYPES.register(BACommon.createRLoc("identifying"), () -> IdentifyingRecipe.Type.INSTANCE);

    // LOAD ────────────────────────────────────────────────────────────────────────────
    public static void register()
    {
        BACommon.logRegistryEvent(SERIALIZERS);
        BACommon.logRegistryEvent(RECIPE_TYPES);
    }
}
