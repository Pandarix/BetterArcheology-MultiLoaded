package net.Pandarix.compat.rei;

import me.shedaniel.rei.api.common.display.DisplaySerializerRegistry;
import me.shedaniel.rei.api.common.plugins.REICommonPlugin;
import me.shedaniel.rei.api.common.registry.display.ServerDisplayRegistry;
import net.Pandarix.BACommon;
import net.Pandarix.recipe.IdentifyingRecipe;

public class ReiPlugin implements REICommonPlugin
{
    @Override
    public String getPluginProviderName()
    {
        return BACommon.MOD_NAME;
    }

    @Override
    public void registerDisplays(ServerDisplayRegistry registry)
    {
        registry.beginRecipeFiller(IdentifyingRecipe.class).filterType(IdentifyingRecipe.Type.INSTANCE).fill(IdentifyingDisplay::new);
    }

    @Override
    public void registerDisplaySerializer(DisplaySerializerRegistry registry)
    {
        registry.register(IdentifyingDisplay.CATEGORY.getIdentifier(), IdentifyingDisplay.SERIALIZER);
    }
}
