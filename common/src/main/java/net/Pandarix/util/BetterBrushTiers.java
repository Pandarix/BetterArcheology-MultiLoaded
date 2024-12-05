package net.Pandarix.util;

import java.text.DecimalFormat;

public enum BetterBrushTiers
{
    IRON(8),
    DIAMOND(6),
    NETHERITE(5);

    private final int brushTickRate;

    BetterBrushTiers(int pBrushTickRate)
    {
        this.brushTickRate = pBrushTickRate;
    }

    public int getBrushTickRate()
    {
        return brushTickRate;
    }

    public String getSpeedFactor()
    {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(10f / getBrushTickRate());
    }
}
