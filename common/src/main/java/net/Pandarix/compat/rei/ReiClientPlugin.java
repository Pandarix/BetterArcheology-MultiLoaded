package net.Pandarix.compat.rei;

import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.Pandarix.BACommon;
import net.Pandarix.block.ModBlocks;
import net.Pandarix.screen.IdentifyingScreen;

public class ReiClientPlugin implements REIClientPlugin
{
    @Override
    public String getPluginProviderName()
    {
        return BACommon.MOD_NAME + "Client";
    }

    @Override
    public void registerCategories(CategoryRegistry registry)
    {
        registry.add(new REIIdentifyingCategory());
        registry.addWorkstations(IdentifyingDisplay.CATEGORY, EntryStacks.of(ModBlocks.ARCHEOLOGY_TABLE.get().asItem()));
    }

    @Override
    public void registerScreens(ScreenRegistry registry)
    {
        registry.registerContainerClickArea(new Rectangle(51, 48, 74, 24), IdentifyingScreen.class, IdentifyingDisplay.CATEGORY);
    }
}
