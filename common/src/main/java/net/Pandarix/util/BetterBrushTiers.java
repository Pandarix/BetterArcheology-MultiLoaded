package net.Pandarix.util;

import net.Pandarix.config.BAConfig;

import java.text.DecimalFormat;

public enum BetterBrushTiers
{
    IRON,
    DIAMOND,
    NETHERITE;

    public int getBrushTickRate()
    {
        int speed = switch (this)
        {
            case IRON -> BAConfig.ironBrushTickRate;
            case DIAMOND -> BAConfig.diamondBrushTickRate;
            case NETHERITE -> BAConfig.netheriteBrushTickRate;
        };

        return Math.max(1, speed);
    }

    public String getSpeedFactor()
    {
        // percentage increase over the vanilla brush (10 ticks per progress step),
        // e.g. netherite at 3 ticks: 10/3 = 3.33x total speed = +233%
        DecimalFormat df = new DecimalFormat("###");
        return df.format((10f / getBrushTickRate() - 1f) * 100f);
    }
}
