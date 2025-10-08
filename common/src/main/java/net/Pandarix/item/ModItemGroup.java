package net.Pandarix.item;

import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.Pandarix.BACommon;
import net.Pandarix.block.ModBlocks;
import net.Pandarix.enchantment.ModEnchantments;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public class ModItemGroup
{
    // REGISTRY ────────────────────────────────────────────────────────────────────────
    public static final Registrar<CreativeModeTab> TABS = BACommon.REGISTRIES.get().get(Registries.CREATIVE_MODE_TAB);

    // ITEM GROUP ──────────────────────────────────────────────────────────────────────
    public static final RegistrySupplier<CreativeModeTab> BETTER_ARCHEOLOGY_ITEMGROUP = TABS.register(
            BACommon.createRLoc("betterarcheology"), () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
                    .icon(() -> new ItemStack(ModItems.UNIDENTIFIED_ARTIFACT.get()))
                    .title(Component.translatable("itemGroup." + BACommon.MOD_ID))
                    .displayItems((parameters, output) ->
                            {
                                //ITEMS
                                output.accept(ModItems.IRON_BRUSH.get());
                                output.accept(ModItems.DIAMOND_BRUSH.get());
                                output.accept(ModItems.NETHERITE_BRUSH.get());
                                output.accept(ModItems.BOMB_ITEM.get());
                                output.accept(ModItems.TORRENT_TOTEM.get());
                                output.accept(ModItems.SOUL_TOTEM.get());
                                output.accept(ModBlocks.GROWTH_TOTEM.get());
                                output.accept(ModBlocks.RADIANCE_TOTEM.get());
                                output.accept(ModItems.ARTIFACT_SHARDS.get());
                                output.accept(ModItems.UNIDENTIFIED_ARTIFACT.get());
                                //BLOCKS
                                output.accept(ModBlocks.ARCHEOLOGY_TABLE.get());
                                //fossils
                                output.accept(ModBlocks.CREEPER_FOSSIL.get());
                                output.accept(ModBlocks.CREEPER_FOSSIL_BODY.get());
                                output.accept(ModBlocks.CREEPER_FOSSIL_HEAD.get());
                                output.accept(ModBlocks.VILLAGER_FOSSIL.get());
                                output.accept(ModBlocks.VILLAGER_FOSSIL_BODY.get());
                                output.accept(ModBlocks.VILLAGER_FOSSIL_HEAD.get());
                                output.accept(ModBlocks.CHICKEN_FOSSIL.get());
                                output.accept(ModBlocks.CHICKEN_FOSSIL_BODY.get());
                                output.accept(ModBlocks.CHICKEN_FOSSIL_HEAD.get());
                                output.accept(ModBlocks.OCELOT_FOSSIL.get());
                                output.accept(ModBlocks.OCELOT_FOSSIL_BODY.get());
                                output.accept(ModBlocks.OCELOT_FOSSIL_HEAD.get());
                                output.accept(ModBlocks.WOLF_FOSSIL.get());
                                output.accept(ModBlocks.WOLF_FOSSIL_BODY.get());
                                output.accept(ModBlocks.WOLF_FOSSIL_HEAD.get());
                                output.accept(ModBlocks.SHEEP_FOSSIL.get());
                                output.accept(ModBlocks.SHEEP_FOSSIL_BODY.get());
                                output.accept(ModBlocks.SHEEP_FOSSIL_HEAD.get());
                                output.accept(ModBlocks.GUARDIAN_FOSSIL.get());
                                output.accept(ModBlocks.GUARDIAN_FOSSIL_HEAD.get());
                                output.accept(ModBlocks.GUARDIAN_FOSSIL_BODY.get());
                                //blocks
                                output.accept(ModBlocks.SUSPICIOUS_RED_SAND.get());
                                output.accept(ModBlocks.SUSPICIOUS_DIRT.get());
                                output.accept(ModBlocks.FOSSILIFEROUS_DIRT.get());
                                output.accept(ModBlocks.CHISELED_BONE_BLOCK.get());
                                //wood
                                output.accept(ModBlocks.ROTTEN_LOG.get());
                                output.accept(ModBlocks.ROTTEN_PLANKS.get());
                                output.accept(ModBlocks.ROTTEN_STAIRS.get());
                                output.accept(ModBlocks.ROTTEN_SLAB.get());
                                output.accept(ModBlocks.ROTTEN_TRAPDOOR.get());
                                output.accept(ModBlocks.ROTTEN_DOOR.get());
                                output.accept(ModBlocks.ROTTEN_PRESSURE_PLATE.get());
                                output.accept(ModBlocks.ROTTEN_FENCE.get());
                                output.accept(ModBlocks.ROTTEN_FENCE_GATE.get());
                                //bricks
                                output.accept(ModBlocks.INFESTED_MUD_BRICKS.get());
                                output.accept(ModBlocks.CRACKED_MUD_BRICKS.get());
                                output.accept(ModBlocks.CRACKED_MUD_BRICK_STAIRS.get());
                                output.accept(ModBlocks.CRACKED_MUD_BRICK_SLAB.get());
                                //vases
                                output.accept(ModBlocks.VASE.get());
                                output.accept(ModBlocks.VASE_CREEPER.get());
                                output.accept(ModBlocks.VASE_GREEN.get());
                                output.accept(ModBlocks.EVOKER_TRAP.get());

                                //enchantments
                                try
                                {
                                    HolderGetter<Enchantment> getter = parameters.holders().lookupOrThrow(Registries.ENCHANTMENT);

                                    ModEnchantments.registerEnchantedBookWith(output, getter.getOrThrow(ModEnchantments.TUNNELING_KEY));
                                    ModEnchantments.registerEnchantedBookWith(output, getter.getOrThrow(ModEnchantments.PENETRATING_STRIKE_KEY));
                                    ModEnchantments.registerEnchantedBookWith(output, getter.getOrThrow(ModEnchantments.SOARING_WINDS_KEY));
                                } catch (Exception e)
                                {
                                    BACommon.LOGGER.error("Could not add Enchanted Book to creative tab!", e);
                                }

                                //disc
                                output.accept(ModItems.DISC_SWINGS.get());
                            }
                    ).build());

    // LOAD ────────────────────────────────────────────────────────────────────────────
    public static void register()
    {
        BACommon.logRegistryEvent(TABS);
    }
}
