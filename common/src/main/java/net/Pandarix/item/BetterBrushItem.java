package net.Pandarix.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BrushItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class BetterBrushItem extends BrushItem
{
    private final BetterBrushTiers brushTier;

    public BetterBrushItem(Properties pProperties, BetterBrushTiers pBrushTier)
    {
        super(pProperties);
        brushTier = pBrushTier;
    }

    /** Ticks between brush strokes; lower is faster. */
    public int getTickRate()
    {
        return brushTier.getTickRate();
    }

    /** Ticks between brush strokes for any brush, vanilla's included. */
    public static int tickRateOf(ItemStack brush)
    {
        return brush.getItem() instanceof BetterBrushItem betterBrush ? betterBrush.getTickRate() : ANIMATION_DURATION;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, TooltipDisplay tooltipDisplay, Consumer<Component> consumer, TooltipFlag tooltipFlag)
    {
        if (getTickRate() == ANIMATION_DURATION) return;

        consumer.accept(Component.literal("+" + brushTier.getSpeedFactor() + "% Brushing Speed").withStyle(ChatFormatting.DARK_GREEN));
        super.appendHoverText(itemStack, tooltipContext, tooltipDisplay, consumer, tooltipFlag);
    }
}