package net.Pandarix.fabric.datagen.provider;

import net.Pandarix.enchantment.ModEnchantments;
import net.Pandarix.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModEnchantmentProvider extends FabricDynamicRegistryProvider
{
    public ModEnchantmentProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture)
    {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(HolderLookup.Provider registries, Entries entries)
    {
        HolderGetter<Item> itemLookup = registries.asGetterLookup().lookupOrThrow(Registries.ITEM);

        register(entries, ModEnchantments.PENETRATING_STRIKE_KEY, new Enchantment.Builder(
                Enchantment.definition(
                        itemLookup.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                        2, // probability of showing up in the enchantment table - will be ignored due to it being treasure
                        1, // max level
                        Enchantment.dynamicCost(1, 10), // cost per level (base)
                        Enchantment.dynamicCost(1, 15), // cost per level (max)
                        7, // anvil applying cost
                        EquipmentSlotGroup.HAND
                )));

        register(entries, ModEnchantments.TUNNELING_KEY, new Enchantment.Builder(
                Enchantment.definition(
                        itemLookup.getOrThrow(ModTags.Items.TUNNELING_ITEMS),
                        2, // probability of showing up in the enchantment table - will be ignored due to it being treasure
                        1, // max level
                        Enchantment.dynamicCost(1, 10), // cost per level (base)
                        Enchantment.dynamicCost(1, 15), // cost per level (max)
                        7, // anvil applying cost
                        EquipmentSlotGroup.HAND
                )));

        register(entries, ModEnchantments.SOARING_WINDS_KEY, new Enchantment.Builder(
                Enchantment.definition(
                        itemLookup.getOrThrow(ModTags.Items.ELYTRAS),
                        2, // probability of showing up in the enchantment table - will be ignored due to it being treasure
                        1, // max level
                        Enchantment.dynamicCost(1, 10), // cost per level (base)
                        Enchantment.dynamicCost(1, 15), // cost per level (max)
                        7, // anvil applying cost
                        EquipmentSlotGroup.ANY
                )));
    }

    private static void register(Entries entries, ResourceKey<Enchantment> key, Enchantment.Builder builder, ResourceCondition... resourceConditions)
    {
        entries.add(key, builder.build(key.location()), resourceConditions);
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Enchantment Generator";
    }
}
