package net.Pandarix.enchantment;

import net.Pandarix.BACommon;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;

public class ModEnchantments
{
    // KEYS ───────────────────────────────────────────────────────────────────────
    public static final ResourceKey<Enchantment> PENETRATING_STRIKE_KEY = ResourceKey.create(Registries.ENCHANTMENT, BACommon.createResource("penetrating_strike"));
    public static final ResourceKey<Enchantment> SOARING_WINDS_KEY = ResourceKey.create(Registries.ENCHANTMENT, BACommon.createResource("soaring_winds"));
    public static final ResourceKey<Enchantment> TUNNELING_KEY = ResourceKey.create(Registries.ENCHANTMENT, BACommon.createResource("tunneling"));

    // UTIL ───────────────────────────────────────────────────────────────────────
    public static void registerEnchantedBookWith(CreativeModeTab.Output output, Holder<Enchantment> enchantmentEntry)
    {
        ItemStack book = EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantmentEntry, 1));
        book.set(DataComponents.ITEM_NAME, Component.translatable("item.betterarcheology.identified_artifact")
                .withStyle(ChatFormatting.RESET, ChatFormatting.YELLOW));

        output.accept(book);
    }

/*    public static void enchantmentBootstrap(BootstrapContext<Enchantment> context)
    {
        HolderGetter<Item> itemLookup = context.lookup(Registries.ITEM);

        register(context, ModEnchantments.PENETRATING_STRIKE_KEY, Enchantment.enchantment(
                        Enchantment.definition(
                                itemLookup.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                2, // probability of showing up in the enchantment table - will be ignored due to it being treasure
                                1, // max level
                                Enchantment.dynamicCost(1, 10), // cost per level (base)
                                Enchantment.dynamicCost(1, 15), // cost per level (max)
                                7, // anvil applying cost
                                EquipmentSlotGroup.HAND
                        ))
                .withEffect(EnchantmentEffectComponents.POST_ATTACK,
                        EnchantmentTarget.ATTACKER,
                        EnchantmentTarget.VICTIM,
                        new PenetratingStrikeEnchantmentEffect(LevelBasedValue.constant(1))));

        register(context, ModEnchantments.TUNNELING_KEY, Enchantment.enchantment(
                Enchantment.definition(
                        itemLookup.getOrThrow(ModTags.Items.TUNNELING_ITEMS),
                        2, // probability of showing up in the enchantment table - will be ignored due to it being treasure
                        1, // max level
                        Enchantment.dynamicCost(1, 10), // cost per level (base)
                        Enchantment.dynamicCost(1, 15), // cost per level (max)
                        7, // anvil applying cost
                        EquipmentSlotGroup.HAND
                )));

        register(context, ModEnchantments.SOARING_WINDS_KEY, Enchantment.enchantment(
                Enchantment.definition(
                        itemLookup.getOrThrow(ModTags.Items.ELYTRAS),
                        2, // probability of showing up in the enchantment table - will be ignored due to it being treasure
                        1, // max level
                        Enchantment.dynamicCost(1, 10), // cost per level (base)
                        Enchantment.dynamicCost(1, 15), // cost per level (max)
                        7, // anvil applying cost
                        EquipmentSlotGroup.ANY
                )));
    }*/

    // REGISTERING ────────────────────────────────────────────────────────────────────
    private static void register(BootstrapContext<Enchantment> context, ResourceKey<Enchantment> key, Enchantment.Builder builder)
    {
        context.register(key, builder.build(key.location()));
    }

    // LOAD ────────────────────────────────────────────────────────────────────────────
    public static void registerEnchantments()
    {
        BACommon.LOGGER.debug("Registering Enchantments for " + BACommon.MOD_NAME);
    }
}
