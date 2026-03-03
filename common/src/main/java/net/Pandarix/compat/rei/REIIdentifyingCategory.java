package net.Pandarix.compat.rei;

import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.Pandarix.block.ModBlocks;
import net.Pandarix.util.ModTags;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class REIIdentifyingCategory implements DisplayCategory<IdentifyingDisplay>
{
    @Override
    public CategoryIdentifier<? extends IdentifyingDisplay> getCategoryIdentifier()
    {
        return IdentifyingDisplay.CATEGORY;
    }

    @Override
    public Component getTitle()
    {
        return ModBlocks.ARCHEOLOGY_TABLE.get().getName();
    }

    @Override
    public Renderer getIcon()
    {
        return EntryStacks.of(ModBlocks.ARCHEOLOGY_TABLE.get());
    }

    @Override
    public List<Widget> setupDisplay(IdentifyingDisplay display, Rectangle bounds)
    {
        List<Widget> widgets = new ArrayList<>();

        widgets.add(Widgets.createTexturedWidget(IdentifyingDisplay.TEXTURE, bounds.x, bounds.y, 150, 66));

        widgets.add(Widgets.createSlot(new Point(bounds.x + 68, bounds.y + 12)).markInput().disableBackground()
                .entries(EntryIngredients.ofItemTag(ModTags.Items.BRUSHES)));

        widgets.add(Widgets.createSlot(new Point(bounds.x + 18, bounds.y + 31)).markInput().disableBackground().entries(display.getInputEntries().getFirst()));

        widgets.add(Widgets.createSlot(new Point(bounds.x + 116, bounds.y + 31)).markOutput().disableBackground().entries(display.getOutputEntries().getFirst()));

        return widgets;
    }
}
