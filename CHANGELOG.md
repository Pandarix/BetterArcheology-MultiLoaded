# Changelog

This file is the default publish changelog: when the Publish workflow's
changelog input is left empty, the newest section (the first `##` heading
and everything until the next one) is uploaded.

## Better Archeology 1.3.8

- Updated to Minecraft 26.2
- Artifacts identified at the Archeology Table are called "Identified Artifact" again. The name was silently lost when identifying became recipe driven
- JEI lists the Archeology Table's identifying recipes again on Fabric. Its plugin entrypoint was dropped during the 1.21.4 port, and since 1.21.2 the recipes also have to be synced to the client explicitly
- REI lists those recipes on NeoForge too. Its plugin was only ever registered on Fabric
- The Totem of Torrents now dashes where you aim, up and down included, like a riptide trident. The dash is equally strong in every direction and has been retuned to Riptide II strength, which makes the horizontal dash slightly shorter than before. Dashing off the ground lifts you clear of the floor first, so it no longer scrapes along the terrain you were standing on. Vertical dashing is on by default; the "Upwards Boosting" config option restricts the dash to the horizontal plane again (#28)
- Added an Argentine Spanish translation, contributed by Texaliuz

## Better Archeology 1.3.7

- Fixed the Rusty Bomb entity showing an untranslated name (#8)
- Music disc now tagged `c:music_discs` for cross-mod compatibility (#30)
- Updated worldgen biome compatibility for the current Biomes We've Gone (BWG) — old `byg:` entries kept for legacy modpacks, `biomeswevegone:` entries added for the current biome roster (#33)
- Chest loot enchanted books/gear now draw from the `on_random_loot` enchantment tag instead of the entire enchantment registry, so unrelated/debug enchantments from other mods can no longer roll on our loot (#37)
- Dispensers can now brush armadillos with the modded brushes, matching the vanilla brush (#26)
- Soaring Winds now also checks Curios back slots for an enchanted elytra on NeoForge (the check was disabled during porting)
- Soaring Winds now works with a usable glider in any equipment slot and respects durability, matching vanilla gliding rules

## Better Archeology 1.3.6

- Fixed brush tooltips: now show the real speed increase — +43% Iron, +100% Diamond, +233% Netherite (#48)
- Identifying at the Archeology Table now actually picks a random artifact — a caching bug made it always produce the same enchanted book (Soaring Winds) (#41)
- Fixed dead issue tracker link in the mod menu
- Curios/Caelus now resolve from the Modrinth maven (old maven host went offline)
