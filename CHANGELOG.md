# Changelog

This file is the default publish changelog: when the Publish workflow's
changelog input is left empty, the newest section (the first `##` heading
and everything until the next one) is uploaded.

## Better Archeology 1.4.0

- Added a Brazilian Portuguese translation, by Leirbag
- Added Warding, a chestplate enchantment found by identifying artifacts. Each level gives a 12.5% chance to ward off an attack a shield could block, with a wall of enchanting glyphs between you and the attacker, at the cost of 1 durability on the chestplate. It does nothing while you hold a shield
- All fossils, heads and bodies can now be waterlogged, so they can be placed underwater and generate filled with water instead of carving out an air pocket
- Chicken, guardian, ocelot, villager and wolf fossils now turn with the rest of a structure that is rotated or mirrored, instead of always keeping their saved facing
- The sheep fossil plays its goat horn from a redstone signal again
- Right-clicking a chicken, guardian, ocelot or wolf fossil no longer swallows the click, so blocks can be placed against them
- The Archeologist's catacombs explorer map now finds catacombs. Catacombs generate in their own structure set with the same placement as woodland mansions: about as often as before, but evenly spread. The map searches as far as a woodland mansion map. Already generated chunks keep their structures
- Tunneling no longer breaks unbreakable blocks such as bedrock, or command and structure blocks, below the mined block. This could happen when the "effective tool only" option was turned off

## Better Archeology 1.3.9

- Updated to Minecraft 26.3
- Jungle temple chests can now contain goat horns. They always should have, but the loot entry pointed at a tag that doesn't exist

## Better Archeology 1.3.8

- Updated to Minecraft 26.2
- Artifacts identified at the Archeology Table are called "Identified Artifact" again. The name was silently lost when identifying became recipe driven
- JEI lists the Archeology Table's identifying recipes again on Fabric. Its plugin entrypoint was dropped during the 1.21.4 port, and since 1.21.2 the recipes also have to be synced to the client explicitly
- REI lists those recipes on NeoForge too. Its plugin was only ever registered on Fabric
- The Totem of Torrents now dashes where you aim, up and down included, like a riptide trident. The dash is equally strong in every direction and has been retuned to Riptide II strength, which makes the horizontal dash slightly shorter than before. Dashing off the ground lifts you clear of the floor first, so it no longer scrapes along the terrain you were standing on. Vertical dashing is on by default; the "Upwards Boosting" config option restricts the dash to the horizontal plane again (#28)
- Soaring Winds can now be applied to modded gliding chestplates. It draws from the `betterarcheology:soaring_winds_items` item tag instead of `c:elytras` directly, so packs can widen it — e.g. to an Apotheosis chestplate with the elytra flight augment (#4)
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
