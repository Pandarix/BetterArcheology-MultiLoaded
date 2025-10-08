package net.Pandarix.item;

import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.Pandarix.BACommon;
import net.Pandarix.sound.ModSounds;
import net.Pandarix.util.BetterBrushTiers;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

import java.util.function.Supplier;

public class ModItems
{
    // REGISTRY ───────────────────────────────────────────────────────────────────────
    public static final Registrar<Item> ITEMS = BACommon.REGISTRIES.get().get(Registries.ITEM);

    // ITEMS ──────────────────────────────────────────────────────────────────────────
    // ──────── BRUSHES ───────────────────────────────────────────────────────────────
    public static final RegistrySupplier<Item> DISC_SWINGS = registerItem("music_disc_swings", () -> new Item(propsWithId("music_disc_swings")
            .jukeboxPlayable(ModSounds.SWINGS_SONG_KEY).stacksTo(1).rarity(Rarity.RARE)));

    // ──────── BRUSHES ───────────────────────────────────────────────────────────────
    public static final RegistrySupplier<Item> IRON_BRUSH = registerItem("iron_brush", () -> new BetterBrushItem(propsWithId("iron_brush")
            .durability(128), BetterBrushTiers.IRON));
    public static final RegistrySupplier<Item> DIAMOND_BRUSH = registerItem("diamond_brush", () -> new BetterBrushItem(propsWithId("diamond_brush")
            .durability(512), BetterBrushTiers.DIAMOND));
    public static final RegistrySupplier<Item> NETHERITE_BRUSH = registerItem("netherite_brush", () -> new BetterBrushItem(propsWithId("netherite_brush")
            .durability(1024).fireResistant(), BetterBrushTiers.NETHERITE));

    // ──────── ARTIFACTS ─────────────────────────────────────────────────────────────
    public static final RegistrySupplier<Item> ARTIFACT_SHARDS = registerItem("artifact_shards", () -> new Item(propsWithId("artifact_shards")
            .rarity(Rarity.UNCOMMON)));
    public static final RegistrySupplier<Item> UNIDENTIFIED_ARTIFACT = registerItem("unidentified_artifact", () -> new Item(propsWithId("unidentified_artifact")
            .rarity(Rarity.UNCOMMON)));

    // ──────── LOOT ITEMS ────────────────────────────────────────────────────────────
    public static final RegistrySupplier<Item> BOMB_ITEM = registerItem("bomb", () -> new BombItem(propsWithId("bomb")
            .rarity(Rarity.COMMON).stacksTo(16)));
    public static final RegistrySupplier<Item> TORRENT_TOTEM = registerItem("torrent_totem", () -> new TorrentTotemItem(propsWithId("torrent_totem")
            .rarity(Rarity.UNCOMMON).stacksTo(1).durability(32)));
    public static final RegistrySupplier<Item> SOUL_TOTEM = registerItem("soul_totem", () -> new SoulTotemItem(propsWithId("soul_totem")
            .rarity(Rarity.UNCOMMON).stacksTo(1).durability(32)));

    // UTIL ───────────────────────────────────────────────────────────────────────────
    public static Item.Properties propsWithId(String id)
    {
        return new Item.Properties().setId(BACommon.createRKey(Registries.ITEM, id));
    }

    // REGISTERING ────────────────────────────────────────────────────────────────────
    private static <I extends Item> RegistrySupplier<I> registerItem(String id, Supplier<I> item)
    {
        ResourceLocation loc = BACommon.createRLoc(id);
        return ITEMS.register(loc, item);
    }

    // LOAD ────────────────────────────────────────────────────────────────────────────
    public static void register()
    {
        BACommon.logRegistryEvent(ITEMS);
    }
}
