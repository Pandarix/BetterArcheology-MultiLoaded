package net.Pandarix.util;

import net.Pandarix.BACommon;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> ROTTEN_LOGS = tag("rotten_logs");

        private static TagKey<Block> tag(String name) {
            return TagKey.create(Registries.BLOCK, BACommon.createRLoc(name));
        }
    }

    public static class Items {
        public static final TagKey<Item> BRUSHES = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", "brushes"));

        public static final TagKey<Item> ELYTRAS = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", "elytras"));

        public static final TagKey<Item> MUSIC_DISCS = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", "music_discs"));

        public static final TagKey<Item> ROTTEN_LOGS = tag("rotten_logs");

        public static final TagKey<Item> SOARING_WINDS_ITEMS = tag("soaring_winds_items");

        public static final TagKey<Item> TUNNELING_ITEMS = tag("tunneling_items");

        private static TagKey<Item> tag(String name) {
            return TagKey.create(Registries.ITEM, BACommon.createRLoc(name));
        }
    }

    // REGISTERING ─────────────────────────────────────────────────────────────────────
    public static void register() {
        BACommon.LOGGER.info("Registering {} for {}", "Tags", BACommon.MOD_NAME);
    }
}
