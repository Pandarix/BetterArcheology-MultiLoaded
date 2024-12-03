package net.Pandarix.enchantment;

import net.Pandarix.BACommon;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
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

    // LOAD ────────────────────────────────────────────────────────────────────────────
    public static void register()
    {
        BACommon.LOGGER.info("Registering {} for {}", "Enchantments", BACommon.MOD_NAME);
    }
}
