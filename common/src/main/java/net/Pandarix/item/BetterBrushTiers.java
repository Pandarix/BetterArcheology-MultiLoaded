package net.Pandarix.item;

import net.Pandarix.config.BAConfig;
import net.minecraft.world.item.BrushItem;

import java.text.DecimalFormat;
import java.util.function.IntSupplier;

/**
 * Brush tiers and their configured tick rate. A new tier is one entry here plus its config field.
 */
public enum BetterBrushTiers
{
    IRON(() -> BAConfig.ironBrushTickRate),
    DIAMOND(() -> BAConfig.diamondBrushTickRate),
    NETHERITE(() -> BAConfig.netheriteBrushTickRate);

    private static final int FASTEST_TICK_RATE = 1;

    private final IntSupplier configuredTickRate;

    BetterBrushTiers(IntSupplier configuredTickRate)
    {
        this.configuredTickRate = configuredTickRate;
    }

    /** Ticks between brush strokes; read live so config changes apply without a restart. */
    public int getTickRate()
    {
        return Math.max(FASTEST_TICK_RATE, configuredTickRate.getAsInt());
    }

    /** Percentage faster than the vanilla brush, e.g. 3 ticks: 10 / 3 = 3.33x = +233%. */
    public String getSpeedFactor()
    {
        float speedMultiplier = (float) BrushItem.ANIMATION_DURATION / getTickRate();
        return new DecimalFormat("###").format((speedMultiplier - 1f) * 100f);
    }
}
