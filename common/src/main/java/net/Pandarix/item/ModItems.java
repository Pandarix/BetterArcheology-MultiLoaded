package net.Pandarix.item;

import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.Pandarix.BACommon;
import net.Pandarix.util.BetterBrushTiers;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

import java.util.function.Supplier;

public class ModItems
{
    // REGISTRY ───────────────────────────────────────────────────────────────────────
    public static final Registrar<Item> ITEMS = BACommon.REGISTRIES.get().get(Registries.ITEM);

    // ITEMS ──────────────────────────────────────────────────────────────────────────
    // ──────── BRUSHES ───────────────────────────────────────────────────────────────
    public static final RegistrySupplier<Item> IRON_BRUSH = registerItem("iron_brush", () -> new BetterBrushItem(propertiesWithTab().durability(128), BetterBrushTiers.IRON));
    public static final RegistrySupplier<Item> DIAMOND_BRUSH = registerItem("diamond_brush", () -> new BetterBrushItem(propertiesWithTab().durability(512), BetterBrushTiers.DIAMOND));
    public static final RegistrySupplier<Item> NETHERITE_BRUSH = registerItem("netherite_brush", () -> new BetterBrushItem(propertiesWithTab().durability(1024).fireResistant(), BetterBrushTiers.NETHERITE));

    // ──────── ARTIFACTS ─────────────────────────────────────────────────────────────
    public static final RegistrySupplier<Item> ARTIFACT_SHARDS = registerItem("artifact_shards", () -> new Item(propertiesWithTab().rarity(Rarity.UNCOMMON)));
    public static final RegistrySupplier<Item> UNIDENTIFIED_ARTIFACT = registerItem("unidentified_artifact", () -> new Item(propertiesWithTab().rarity(Rarity.UNCOMMON)));

    // ──────── LOOT ITEMS ────────────────────────────────────────────────────────────
    public static final RegistrySupplier<Item> BOMB_ITEM = registerItem("bomb", () -> new BombItem(new Item.Properties().rarity(Rarity.COMMON).stacksTo(16)));
    public static final RegistrySupplier<Item> TORRENT_TOTEM = registerItem("torrent_totem", () -> new TorrentTotemItem(propertiesWithTab().rarity(Rarity.UNCOMMON).stacksTo(1).durability(32)));
    public static final RegistrySupplier<Item> SOUL_TOTEM = registerItem("soul_totem", () -> new SoulTotemItem(propertiesWithTab().rarity(Rarity.UNCOMMON).stacksTo(1).durability(32)));

    // REGISTERING ────────────────────────────────────────────────────────────────────
    private static Item.Properties propertiesWithTab()
    {
        return new Item.Properties();
    }

    private static RegistrySupplier<Item> registerItem(String id, Supplier<Item> supplier)
    {
        return ITEMS.register(BACommon.createResource(id), supplier);
    }

    // LOAD ────────────────────────────────────────────────────────────────────────────
    public static void register()
    {
        BACommon.logRegistryEvent(ITEMS);
    }
}
