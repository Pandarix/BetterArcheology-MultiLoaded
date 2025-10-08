package net.Pandarix.config;

import com.teamresourceful.resourcefulconfig.api.annotations.*;
import net.Pandarix.BACommon;

@ConfigInfo.Provider(BAConfigInfoProvider.class)
@Config(value = BACommon.MOD_ID)
public final class BAConfig
{
    // MISC ───────────────────────────────────────────────────────────────────────────
    @ConfigOption.Separator(
            value = "Misc."
    )
    @ConfigEntry(id = "rustyBombTerrainDamage", translation = "config.betterarcheology.rustyBombTerrainDamage")
    public static boolean rustyBombTerrainDamage = true;

    // BRUSHES ───────────────────────────────────────────────────────────────────────────
    @ConfigOption.Separator(
            value = "Brushes"
    )
    @ConfigOption.Slider
    @ConfigOption.Range(min = 1, max = 10)
    @Comment("Time between brush uses in ticks. Lower values are faster. The vanilla brush has a speed of 10.")
    @ConfigEntry(id = "ironBrushTickRate", translation = "config.betterarcheology.ironBrushTickRate")
    public static int ironBrushTickRate = 7;

    @ConfigOption.Slider
    @ConfigOption.Range(min = 1, max = 10)
    @ConfigEntry(id = "diamondBrushTickRate", translation = "config.betterarcheology.diamondBrushTickRate")
    public static int diamondBrushTickRate = 5;

    @ConfigOption.Slider
    @ConfigOption.Range(min = 1, max = 10)
    @ConfigEntry(id = "netheriteBrushTickRate", translation = "config.betterarcheology.netheriteBrushTickRate")
    public static int netheriteBrushTickRate = 3;

    // ARTIFACTS ───────────────────────────────────────────────────────────────────────
    @ConfigOption.Separator(
            value = "Artifacts"
    )
    @ConfigEntry(
            id = "artifactsEnabled",
            translation = "config.betterarcheology.artifactsEnabled"
    )
    public static boolean artifactsEnabled = true;

    // ──────── PENETRATING STRIKE ─────────────────────────────────────────────────────
    @ConfigEntry(id = "penetratingStrikeEnabled", translation = "config.betterarcheology.penetratingStrikeEnabled")
    public static boolean penetratingStrikeEnabled = true;

    @ConfigEntry(id = "penetratingStrikeIgnorance", translation = "config.betterarcheology.penetratingStrikeIgnorance")
    @ConfigOption.Slider
    @ConfigOption.Range(min = 0d, max = 1d)
    @Comment("Set to % of damage-reduction from Protection Enchantments that should be ignored.")
    public static double penetratingStrikeIgnorance = .33d;

    // ──────── SOARING WINDS ──────────────────────────────────────────────────────────
    @ConfigEntry(id = "soaringWindsEnabled", translation = "config.betterarcheology.soaringWindsEnabled")
    public static boolean soaringWindsEnabled = true;

    @ConfigEntry(id = "soaringWindsBoost", translation = "config.betterarcheology.soaringWindsBoost")
    @ConfigOption.Slider
    @ConfigOption.Range(min = 0.1d, max = 3d)
    public static double soaringWindsBoost = .75d;

    // ──────── TUNNELING ──────────────────────────────────────────────────────────────
    @ConfigEntry(id = "tunnelingEnabled", translation = "config.betterarcheology.tunnelingEnabled")
    public static boolean tunnelingEnabled = true;

    @ConfigEntry(id = "tunnelingEffectiveTool", translation = "config.betterarcheology.tunnelingEffectiveTool")
    @Comment("Only mines the Block below if the Tool used is effective for the Block mined (e.g. Shovel on Stone).")
    public static boolean tunnelingEffectiveTool = true;

    @ConfigEntry(id = "tunnelingTolerance", translation = "config.betterarcheology.tunnelingTolerance")
    @ConfigOption.Slider
    @ConfigOption.Range(min = 0.0d, max = 10d)
    @Comment("The difference of hardness between the two blocks to break that is allowed. Per default, this prevents e.g. mining Obsidian below when mining stone, but allows for ores below to be mined. For reference: Stone has 1.5.")
    public static double tunnelingTolerance = 3.75;

    // TOTEMS ───────────────────────────────────────────────────────────────────────────
    @ConfigOption.Separator(
            value = "Totems"
    )
    @ConfigEntry(id = "totemsEnabled", translation = "config.betterarcheology.totemsEnabled")
    public static boolean totemsEnabled = true;

    // ──────── RADIANCE ─────────────────────────────────────────────────────────────────
    @ConfigEntry(id = "radianceTotemEnabled", translation = "config.betterarcheology.radianceTotemEnabled")
    public static boolean radianceTotemEnabled = true;

    @ConfigEntry(id = "radianceTotemDamageEnabled", translation = "config.betterarcheology.radianceTotemDamageEnabled")
    public static boolean radianceTotemDamageEnabled = true;

    @ConfigEntry(id = "radianceTotemDamage", translation = "config.betterarcheology.radianceTotemDamage")
    @ConfigOption.Range(min = 1, max = 40)
    @Comment("Sets the damage in 1/2 hearts that will be dealt to hostile mobs when a damage tick occurs.")
    public static int radianceTotemDamage = 4;

    @ConfigEntry(id = "radianceTotemDamageTickAverage", translation = "config.betterarcheology.radianceTotemDamageTickAverage")
    @ConfigOption.Range(min = 1, max = 60)
    @Comment("Sets the average time between damage ticks of the Radiance Totem in seconds. The totem will still damage mobs randomly, but the average time between damage ticks will be this value.")
    public static int radianceTotemDamageTickAverage = 3;

    @ConfigEntry(id = "radianceTotemRadius", translation = "config.betterarcheology.radianceTotemRadius")
    @ConfigOption.Range(min = 1, max = 90)
    public static int radianceTotemRadius = 10;

    // ──────── TORRENT ─────────────────────────────────────────────────────────────────
    @ConfigEntry(id = "torrentTotemEnabled", translation = "config.betterarcheology.torrentTotemEnabled")
    public static boolean torrentTotemEnabled = true;

    @ConfigEntry(id = "torrentTotemBoost", translation = "config.betterarcheology.torrentTotemBoost")
    @ConfigOption.Slider
    @ConfigOption.Range(min = .1d, max = 3d)
    public static double torrentTotemBoost = 1d;

    @ConfigEntry(id = "torrentTotemUpwardsBoost", translation = "config.betterarcheology.torrentTotemUpwardsBoost")
    public static boolean torrentTotemUpwardsBoost = false;

    // ──────── SOUL ───────────────────────────────────────────────────────────────────
    @ConfigEntry(id = "soulTotemEnabled", translation = "config.betterarcheology.soulTotemEnabled")
    public static boolean soulTotemEnabled = true;

    // ──────── GROWTH ─────────────────────────────────────────────────────────────────
    @ConfigEntry(id = "growthTotemEnabled", translation = "config.betterarcheology.growthTotemEnabled")
    public static boolean growthTotemEnabled = true;

    @ConfigEntry(id = "growthTotemGrowRadius", translation = "config.betterarcheology.growthTotemGrowRadius")
    @ConfigOption.Range(min = 1, max = 50)
    public static int growthTotemGrowRadius = 5;

    @ConfigEntry(id = "growthTotemGrowChance", translation = "config.betterarcheology.growthTotemGrowChance")
    @ConfigOption.Range(min = 1, max = 100)
    @Comment("The growth totem uses the randomTick to determine when it should grow crops. This value determines the chance in % that a random tick actually grows crops to potentially decrease its yield. For example, a 20% chance bonemeals a crop ~10.5 times an hour")
    public static int growthTotemGrowChance = 20;

    // FOSSILS ──────────────────────────────────────────────────────────────────────────
    @ConfigOption.Separator(
            value = "Fossils"
    )
    @ConfigEntry(id = "fossilEffectsEnabled", translation = "config.betterarcheology.fossilEffectsEnabled")
    public static boolean fossilEffectsEnabled = true;

    // ──────── FLEEING ─────────────────────────────────────────────────────────────────
    @ConfigEntry(id = "fossilFleeRange", translation = "config.betterarcheology.fossilFleeRange")
    @ConfigOption.Range(min = 10, max = 50)
    public static int fossilFleeRange = 20;

    // ──────── CHICKEN ─────────────────────────────────────────────────────────────────
    @ConfigEntry(id = "chickenFossilEffectsEnabled", translation = "config.betterarcheology.chickenFossilEffectsEnabled")
    public static boolean chickenFossilEffectsEnabled = true;

    // ──────── OCELOT ─────────────────────────────────────────────────────────────────
    @ConfigEntry(id = "ocelotFossilEffectsEnabled", translation = "config.betterarcheology.ocelotFossilEffectsEnabled")
    public static boolean ocelotFossilEffectsEnabled = true;

    // ──────── WOLF ───────────────────────────────────────────────────────────────────
    @ConfigEntry(id = "wolfFossilEffectsEnabled", translation = "config.betterarcheology.wolfFossilEffectsEnabled")
    public static boolean wolfFossilEffectsEnabled = true;

    // ──────── GUARDIAN ───────────────────────────────────────────────────────────────
    @ConfigEntry(id = "guardianFossilEffectsEnabled", translation = "config.betterarcheology.guardianFossilEffectsEnabled")
    public static boolean guardianFossilEffectsEnabled = true;
}