# Changelog

This file is the default publish changelog: when the Publish workflow's
changelog input is left empty, the newest section (the first `##` heading
and everything until the next one) is uploaded.

## Better Archeology 1.3.8

- Artifacts identified at the Archeology Table are called "Identified Artifact" again. The name was silently lost when identifying became recipe driven
- REI lists the Archeology Table's identifying recipes on NeoForge. Its plugin was only ever registered on Fabric

## Better Archeology 1.3.7

- Fixed the Rusty Bomb entity showing an untranslated name (#8)
- Music disc now tagged `c:music_discs` for cross-mod compatibility (#30)
- Updated worldgen biome compatibility for the current Biomes We've Gone (BWG) — old `byg:` entries kept for legacy modpacks, `biomeswevegone:` entries added for the current biome roster (#33)
- Chest loot enchanted books/gear now draw from the `on_random_loot` enchantment tag instead of the entire enchantment registry, so unrelated/debug enchantments from other mods can no longer roll on our loot (#37)
- Dispensers can now brush armadillos with the modded brushes, matching the vanilla brush (#26)

## Better Archeology 1.3.6

- Fixed brush tooltips: now show the real speed increase — +43% Iron, +100% Diamond, +233% Netherite (#48)
- Identifying at the Archeology Table now actually picks a random artifact — a caching bug made it always produce the same enchanted book (Soaring Winds) (#41)
- Fixed dead issue tracker link in the mod menu
- Curios/Caelus now resolve from the Modrinth maven (old maven host went offline)
