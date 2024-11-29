package net.Pandarix.item;

import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.Pandarix.BACommon;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModItemGroup
{
    // REGISTRY ────────────────────────────────────────────────────────────────────────
    public static final Registrar<CreativeModeTab> TABS = BACommon.REGISTRIES.get().get(Registries.CREATIVE_MODE_TAB);

    // Creates a creative tab with the id "BetterArcheology:example_tab" for the example item, that is placed after the combat tab
    public static final RegistrySupplier<CreativeModeTab> BETTER_ARCHEOLOGY_ITEMGROUP = TABS.register(
            BACommon.createResource("betterarcheology"), () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP,0)
                .icon(() -> new ItemStack(ModItems.UNIDENTIFIED_ARTIFACT.get()))
                .title(Component.translatable("itemGroup." + BACommon.MOD_ID))
                .build()
    );

    public static void register()
    {
        BACommon.logRegistryEvent(TABS);
    }
}
