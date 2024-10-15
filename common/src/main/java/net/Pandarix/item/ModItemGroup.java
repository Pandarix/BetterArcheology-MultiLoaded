package net.Pandarix.item;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.Pandarix.BACommon;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModItemGroup
{
    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "BetterArcheology" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(BACommon.MOD_ID, Registries.CREATIVE_MODE_TAB);

    // Creates a creative tab with the id "BetterArcheology:example_tab" for the example item, that is placed after the combat tab
    public static final RegistrySupplier<CreativeModeTab> BETTER_ARCHEOLOGY_ITEMGROUP = CREATIVE_MODE_TABS.register("betterarcheology", () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP,0)
            .icon(() -> new ItemStack(ModItems.UNIDENTIFIED_ARTIFACT.get())).title(Component.translatable("itemGroup." + BACommon.MOD_ID))
            .displayItems((parameters, output) ->
            {
                //ITEMS
                output.accept(ModItems.IRON_BRUSH.get());
                output.accept(ModItems.DIAMOND_BRUSH.get());
                output.accept(ModItems.NETHERITE_BRUSH.get());
                //output.accept(ModItems.BOMB_ITEM.get());
                output.accept(ModItems.TORRENT_TOTEM.get());
                output.accept(ModItems.SOUL_TOTEM.get());
                output.accept(ModItems.ARTIFACT_SHARDS.get());
                output.accept(ModItems.UNIDENTIFIED_ARTIFACT.get());
            }).build());
}
