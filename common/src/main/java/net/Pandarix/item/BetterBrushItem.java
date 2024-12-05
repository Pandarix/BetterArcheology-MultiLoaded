package net.Pandarix.item;

import net.Pandarix.util.BetterBrushTiers;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BrushItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BetterBrushItem extends BrushItem
{
    private final BetterBrushTiers brushTier;

    public BetterBrushItem(Properties pProperties, BetterBrushTiers pBrushTier)
    {
        super(pProperties);
        brushTier = pBrushTier;
    }

    public int getBrushingSpeed()
    {
        return brushTier.getBrushTickRate();
    }

    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack pStack)
    {
        return UseAnim.BRUSH;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag)
    {
        list.add(Component.literal("+" + brushTier.getSpeedFactor() + "% Brushing Speed").withStyle(ChatFormatting.DARK_GREEN));
        super.appendHoverText(itemStack, tooltipContext, list, tooltipFlag);
    }
}