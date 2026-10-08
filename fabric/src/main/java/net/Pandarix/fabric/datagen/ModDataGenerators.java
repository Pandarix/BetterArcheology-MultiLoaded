package net.Pandarix.fabric.datagen;

import net.Pandarix.block.ModBlocks;
import net.Pandarix.fabric.datagen.provider.*;
import net.Pandarix.fabric.datagen.provider.loot.ModBlockLootTableProvider;
import net.Pandarix.fabric.datagen.provider.loot.ModLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderOwner;
import net.minecraft.data.BlockFamilies;
import net.minecraft.data.BlockFamily;

public class ModDataGenerators implements DataGeneratorEntrypoint
{
    public static final BlockFamily.Builder ROTTEN_FAM =
            BlockFamilies.familyBuilder(ModBlocks.ROTTEN_PLANKS.get()).fence(ModBlocks.ROTTEN_FENCE.get()).fenceGate(ModBlocks.ROTTEN_FENCE_GATE.get()).pressurePlate(ModBlocks.ROTTEN_PRESSURE_PLATE.get()).slab(ModBlocks.ROTTEN_SLAB.get()).stairs(ModBlocks.ROTTEN_STAIRS.get()).door(ModBlocks.ROTTEN_DOOR.get()).trapdoor(ModBlocks.ROTTEN_TRAPDOOR.get()).recipeGroupPrefix(
                    "wooden").recipeUnlockedBy("has_planks");

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator)
    {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(ModBlockTagProvider::new);
        pack.addProvider(ModItemTagProvider::new);
        pack.addProvider(ModVillagerTradeTagProvider::new);
        pack.addProvider(ModModelProvider::new);
        pack.addProvider(ModLootTableProvider::new);
        pack.addProvider(ModBlockLootTableProvider::new);
        pack.addProvider(ModRecipeProvider::new);
        pack.addProvider(ModEnchantmentProvider::new);
        pack.addProvider(ModVillagerTradeProvider::new);
        pack.addProvider(ModTradeSetProvider::new);
    }

    //datagen wraps each registry lookup (EmptyTagLookupWrapper) and the serialization owner check only accepts holders
    //owned by the wrapped registry - a holder or tag set made from the wrapper fails with "not valid in current registry set"
    public static <T> HolderOwner<T> serializableOwner(HolderLookup.RegistryLookup<T> lookup)
    {
        return lookup instanceof HolderLookup.RegistryLookup.Delegate<T> delegate ? delegate.parent() : lookup;
    }
}
