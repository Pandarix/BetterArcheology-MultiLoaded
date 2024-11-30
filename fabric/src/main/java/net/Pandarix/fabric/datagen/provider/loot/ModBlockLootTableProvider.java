package net.Pandarix.fabric.datagen.provider.loot;

import net.Pandarix.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.concurrent.CompletableFuture;

public class ModBlockLootTableProvider extends FabricBlockLootTableProvider
{
    public ModBlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup)
    {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate()
    {
        //generate dropSelf for all Fossils
        ModBlocks.BLOCKS.forEach(block ->
        {
            if (block != ModBlocks.RADIANCE_TOTEM && ModBlocks.isFossil(block))
            {
                this.dropSelf(block);
            }
        });

        //GENERIC
        this.dropSelf(ModBlocks.ARCHEOLOGY_TABLE.get());
        this.dropSelf(ModBlocks.CRACKED_MUD_BRICKS.get());
        this.dropSelf(ModBlocks.CRACKED_MUD_BRICK_STAIRS.get());
        this.dropSelf(ModBlocks.CRACKED_MUD_BRICK_SLAB.get());
        this.dropSelf(ModBlocks.EVOKER_TRAP.get());
        this.dropSelf(ModBlocks.GROWTH_TOTEM.get());
        this.dropSelf(ModBlocks.RADIANCE_TOTEM.get());
        this.dropSelf(ModBlocks.VASE.get());
        this.dropSelf(ModBlocks.VASE_CREEPER.get());
        this.dropSelf(ModBlocks.VASE_GREEN.get());

        //MISC
        this.add(ModBlocks.INFESTED_MUD_BRICKS.get(), block ->
                createSilkTouchOnlyTable(ModBlocks.INFESTED_MUD_BRICKS.get()));
        this.add(ModBlocks.CHISELED_BONE_BLOCK.get(), block ->
                this.createSingleItemTableWithSilkTouch(block, Items.BONE, UniformGenerator.between(2, 3)));
        this.dropOther(ModBlocks.SUSPICIOUS_DIRT.get(), Items.AIR);
        this.dropOther(ModBlocks.SUSPICIOUS_RED_SAND.get(), Items.AIR);
        this.dropOther(ModBlocks.FOSSILIFEROUS_DIRT.get(), Items.BONE);

        //ROTTEN
        this.add(ModBlocks.ROTTEN_DOOR.get(), block ->
                this.createDoorTable(ModBlocks.ROTTEN_DOOR.get()));
        this.add(ModBlocks.ROTTEN_FENCE.get(), block ->
                this.createSingleItemTableWithSilkTouch(block, Items.STICK, ConstantValue.exactly(4)));
        this.add(ModBlocks.ROTTEN_FENCE_GATE.get(), block ->
                this.createSingleItemTableWithSilkTouch(block, Items.STICK, ConstantValue.exactly(2)));
        this.add(ModBlocks.ROTTEN_LOG.get(), block ->
                this.createSingleItemTableWithSilkTouch(block, Items.STICK, ConstantValue.exactly(8)));
        this.add(ModBlocks.ROTTEN_PLANKS.get(), block ->
                this.createSingleItemTableWithSilkTouch(block, Items.STICK, ConstantValue.exactly(2)));
        this.add(ModBlocks.ROTTEN_PRESSURE_PLATE.get(), block ->
                this.createSingleItemTableWithSilkTouch(block, Items.STICK, ConstantValue.exactly(1)));
        this.add(ModBlocks.ROTTEN_SLAB.get(), block ->
                this.createSingleItemTableWithSilkTouch(block, Items.STICK, ConstantValue.exactly(2)));
        this.add(ModBlocks.ROTTEN_STAIRS.get(), block ->
                this.createSingleItemTableWithSilkTouch(block, Items.STICK, ConstantValue.exactly(3)));
        this.add(ModBlocks.ROTTEN_TRAPDOOR.get(), block ->
                this.createSingleItemTableWithSilkTouch(block, Items.STICK, ConstantValue.exactly(4)));

        //VASES
        this.add(ModBlocks.LOOT_VASE.get(), block ->
                LootTable.lootTable()
                        .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(ModBlocks.VASE.get()).when(hasSilkTouch())))
                        .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(NestedLootTable.lootTableReference(ModLootTableProvider.SUPPLY_LOOTTABLE_KEY))
                                .add(NestedLootTable.lootTableReference(ModLootTableProvider.TREASURE_LOOTTABLE_KEY))
                                .when(doesNotHaveSilkTouch()))
        );

        this.add(ModBlocks.LOOT_VASE_CREEPER.get(), block ->
                LootTable.lootTable()
                        .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(ModBlocks.VASE_CREEPER.get()).when(hasSilkTouch())))
                        .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(NestedLootTable.lootTableReference(ModLootTableProvider.SUPPLY_LOOTTABLE_KEY))
                                .add(NestedLootTable.lootTableReference(ModLootTableProvider.TREASURE_LOOTTABLE_KEY))
                                .when(doesNotHaveSilkTouch()))
        );

        this.add(ModBlocks.LOOT_VASE_GREEN.get(), block ->
                LootTable.lootTable()
                        .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(ModBlocks.VASE_GREEN.get()).when(hasSilkTouch())))
                        .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(NestedLootTable.lootTableReference(ModLootTableProvider.SUPPLY_LOOTTABLE_KEY))
                                .add(NestedLootTable.lootTableReference(ModLootTableProvider.GREEN_TREASURE_LOOTTABLE_KEY))
                                .when(doesNotHaveSilkTouch()))
        );
    }
}
