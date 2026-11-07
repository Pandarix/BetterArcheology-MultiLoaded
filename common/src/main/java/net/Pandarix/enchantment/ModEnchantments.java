package net.Pandarix.enchantment;

import net.Pandarix.BACommon;
import net.Pandarix.config.BAConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;

import java.util.Map;
import java.util.function.BooleanSupplier;

public class ModEnchantments
{
    // KEYS ───────────────────────────────────────────────────────────────────────
    public static final ResourceKey<Enchantment> PENETRATING_STRIKE_KEY = BACommon.createRKey(Registries.ENCHANTMENT, "penetrating_strike");
    public static final ResourceKey<Enchantment> SOARING_WINDS_KEY = BACommon.createRKey(Registries.ENCHANTMENT, "soaring_winds");
    public static final ResourceKey<Enchantment> TUNNELING_KEY = BACommon.createRKey(Registries.ENCHANTMENT, "tunneling");
    public static final ResourceKey<Enchantment> WARDING_KEY = BACommon.createRKey(Registries.ENCHANTMENT, "warding");

    // Config switch of each of our enchantments; checked together with artifactsEnabled.
    private static final Map<ResourceKey<Enchantment>, BooleanSupplier> ENABLED = Map.of(
            PENETRATING_STRIKE_KEY, () -> BAConfig.penetratingStrikeEnabled,
            SOARING_WINDS_KEY, () -> BAConfig.soaringWindsEnabled,
            TUNNELING_KEY, () -> BAConfig.tunnelingEnabled,
            WARDING_KEY, () -> BAConfig.wardingEnabled);

    // UTIL ───────────────────────────────────────────────────────────────────────
    /** Level of one of our enchantments on the stack, or 0 while it is switched off in the config. */
    public static int activeLevel(HolderLookup.Provider registries, ResourceKey<Enchantment> enchantment, ItemStack stack)
    {
        if (!BAConfig.artifactsEnabled || !ENABLED.get(enchantment).getAsBoolean()) return 0;

        return getLevel(registries, enchantment, stack);
    }

    /** Level of the enchantment on the stack; 0 when absent, including when a datapack removed it. */
    public static int getLevel(HolderLookup.Provider registries, ResourceKey<Enchantment> enchantment, ItemStack stack)
    {
        return registries.lookupOrThrow(Registries.ENCHANTMENT).get(enchantment)
                .map(holder -> EnchantmentHelper.getItemEnchantmentLevel(holder, stack))
                .orElse(0);
    }

    public static void registerEnchantedBookWith(CreativeModeTab.Output output, Holder<Enchantment> enchantmentEntry)
    {
        ItemStack book = EnchantmentHelper.createBook(new EnchantmentInstance(enchantmentEntry, enchantmentEntry.value().getMaxLevel()));
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
