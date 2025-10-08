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
        DecimalFormat df = new DecimalFormat("###");
        return df.format(10f / getBrushTickRate() * 100f);
    }
}
