package net.Pandarix.block;

import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.Pandarix.BACommon;
import net.Pandarix.block.custom.*;
import net.Pandarix.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class ModBlocks
{
    // REGISTRY ────────────────────────────────────────────────────────────────────────
    public static final Registrar<Block> BLOCKS = BACommon.REGISTRIES.get().get(Registries.BLOCK);

    //-----------SUS VARIANTS-------------//
    public static final RegistrySupplier<Block> SUSPICIOUS_RED_SAND = registerBlock("suspicious_red_sand", () -> new SusBlock(Blocks.RED_SAND,
            BlockBehaviour.Properties.of().mapColor(MapColor.SAND).instrument(NoteBlockInstrument.SNARE).strength(0.25F).sound(SoundType.SUSPICIOUS_SAND).pushReaction(PushReaction.DESTROY), SoundEvents.BRUSH_SAND, SoundEvents.BRUSH_SAND_COMPLETED));

    public static final RegistrySupplier<Block> SUSPICIOUS_DIRT = registerBlock("suspicious_dirt", () -> new SusBlock(Blocks.DIRT, BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.SNARE).strength(0.25F).sound(SoundType.SUSPICIOUS_GRAVEL).pushReaction(PushReaction.DESTROY), SoundEvents.BRUSH_GRAVEL, SoundEvents.BRUSH_GRAVEL_COMPLETED));

    //---------FOSSILIFEROUS BLOCKS-----------//
    public static final RegistrySupplier<Block> FOSSILIFEROUS_DIRT = registerBlock("fossiliferous_dirt", () -> new SusBlock(Blocks.DIRT, BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.SNARE).strength(0.25F).sound(SoundType.SUSPICIOUS_GRAVEL).pushReaction(PushReaction.DESTROY), SoundEvents.BRUSH_GRAVEL, SoundEvents.BRUSH_GRAVEL_COMPLETED));

    public static final RegistrySupplier<Block> CHISELED_BONE_BLOCK = registerBlock("chiseled_bone_block",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).strength(0.3F).instrument(NoteBlockInstrument.XYLOPHONE).sound(SoundType.BONE_BLOCK)));

    //-------------FOSSILS---------------//
    //Villager
    public static final RegistrySupplier<Block> VILLAGER_FOSSIL = registerRareBlock("villager_fossil", () -> new VillagerFossilBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).instrument(NoteBlockInstrument.XYLOPHONE).requiresCorrectToolForDrops().strength(2.0F).sound(SoundType.BONE_BLOCK).lightLevel((state) -> state.getValue(VillagerFossilBlock.INVENTORY_LUMINANCE))));

    public static final RegistrySupplier<Block> VILLAGER_FOSSIL_HEAD = registerRareBlock("villager_fossil_head", () -> new VillagerFossilHeadBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.SKELETON).strength(1.0F).pushReaction(PushReaction.DESTROY).sound(SoundType.BONE_BLOCK)));

    public static final RegistrySupplier<Block> VILLAGER_FOSSIL_BODY = registerRareBlock("villager_fossil_body", () -> new VillagerFossilBodyBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.SKELETON).strength(1.0F).pushReaction(PushReaction.DESTROY).sound(SoundType.BONE_BLOCK)));

    //Ocelot
    public static final RegistrySupplier<Block> OCELOT_FOSSIL = registerRareBlock("ocelot_fossil", () -> new OcelotFossilBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.SKELETON).strength(1.0F).pushReaction(PushReaction.DESTROY).sound(SoundType.BONE_BLOCK)));

    public static final RegistrySupplier<Block> OCELOT_FOSSIL_HEAD = registerRareBlock("ocelot_fossil_head", () -> new OcelotFossilHeadBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.SKELETON).strength(1.0F).pushReaction(PushReaction.DESTROY).sound(SoundType.BONE_BLOCK)));

    public static final RegistrySupplier<Block> OCELOT_FOSSIL_BODY = registerRareBlock("ocelot_fossil_body", () -> new OcelotFossilBodyBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.SKELETON).strength(1.0F).pushReaction(PushReaction.DESTROY).sound(SoundType.BONE_BLOCK)));

    //Guardian
    public static final RegistrySupplier<Block> GUARDIAN_FOSSIL = registerRareBlock("guardian_fossil", () -> new GuardianFossilBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.SKELETON).strength(1.0F).pushReaction(PushReaction.DESTROY).sound(SoundType.BONE_BLOCK)));

    public static final RegistrySupplier<Block> GUARDIAN_FOSSIL_HEAD = registerRareBlock("guardian_fossil_head", () -> new GuardianFossilHeadBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.SKELETON).strength(1.0F).pushReaction(PushReaction.DESTROY).sound(SoundType.BONE_BLOCK)));

    public static final RegistrySupplier<Block> GUARDIAN_FOSSIL_BODY = registerRareBlock("guardian_fossil_body", () -> new GuardianFossilBodyBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.SKELETON).strength(1.0F).pushReaction(PushReaction.DESTROY).sound(SoundType.BONE_BLOCK)));

    //Sheep
    public static final RegistrySupplier<Block> SHEEP_FOSSIL = registerRareBlock("sheep_fossil", () -> new SheepFossilBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.SKELETON).strength(1.0F).pushReaction(PushReaction.DESTROY).sound(SoundType.BONE_BLOCK)));

    public static final RegistrySupplier<Block> SHEEP_FOSSIL_HEAD = registerRareBlock("sheep_fossil_head", () -> new SheepFossilHeadBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.SKELETON).strength(1.0F).pushReaction(PushReaction.DESTROY).sound(SoundType.BONE_BLOCK)));

    public static final RegistrySupplier<Block> SHEEP_FOSSIL_BODY = registerRareBlock("sheep_fossil_body", () -> new SheepFossilBodyBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.SKELETON).strength(1.0F).pushReaction(PushReaction.DESTROY).sound(SoundType.BONE_BLOCK)));

    //Sheep
    public static final RegistrySupplier<Block> CHICKEN_FOSSIL = registerRareBlock("chicken_fossil", () -> new ChickenFossilBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.SKELETON).strength(1.0F).pushReaction(PushReaction.DESTROY).sound(SoundType.BONE_BLOCK)));

    public static final RegistrySupplier<Block> CHICKEN_FOSSIL_HEAD = registerRareBlock("chicken_fossil_head", () -> new ChickenFossilHeadBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.SKELETON).strength(1.0F).pushReaction(PushReaction.DESTROY).sound(SoundType.BONE_BLOCK)));

    public static final RegistrySupplier<Block> CHICKEN_FOSSIL_BODY = registerRareBlock("chicken_fossil_body", () -> new ChickenFossilBodyBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.SKELETON).strength(1.0F).pushReaction(PushReaction.DESTROY).sound(SoundType.BONE_BLOCK)));

    //Creeper
    public static final RegistrySupplier<Block> CREEPER_FOSSIL = registerRareBlock("creeper_fossil", () -> new CreeperFossilBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.SKELETON).strength(1.0F).pushReaction(PushReaction.DESTROY).sound(SoundType.BONE_BLOCK)));

    public static final RegistrySupplier<Block> CREEPER_FOSSIL_HEAD = registerRareBlock("creeper_fossil_head", () -> new CreeperFossilHeadBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.SKELETON).strength(1.0F).pushReaction(PushReaction.DESTROY).sound(SoundType.BONE_BLOCK)));

    public static final RegistrySupplier<Block> CREEPER_FOSSIL_BODY = registerRareBlock("creeper_fossil_body", () -> new CreeperFossilBodyBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.SKELETON).strength(1.0F).pushReaction(PushReaction.DESTROY).sound(SoundType.BONE_BLOCK)));

    //Wolf
    public static final RegistrySupplier<Block> WOLF_FOSSIL = registerRareBlock("wolf_fossil", () -> new WolfFossilBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.SKELETON).strength(1.0F).pushReaction(PushReaction.DESTROY).sound(SoundType.BONE_BLOCK)));

    public static final RegistrySupplier<Block> WOLF_FOSSIL_HEAD = registerRareBlock("wolf_fossil_head", () -> new WolfFossilHeadBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.SKELETON).strength(1.0F).pushReaction(PushReaction.DESTROY).sound(SoundType.BONE_BLOCK)));

    public static final RegistrySupplier<Block> WOLF_FOSSIL_BODY = registerRareBlock("wolf_fossil_body", () -> new WolfFossilBodyBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.SKELETON).strength(1.0F).pushReaction(PushReaction.DESTROY).sound(SoundType.BONE_BLOCK)));

    //-----------ROTTEN WOOD-------------//
    public static final WoodType ROTTEN_WOOD_TYPE = registerWoodType("rotten_wood");
    public static final BlockSetType ROTTEN_WOOD_BLOCKSET = registerBlockSetType("rotten_wood");

    public static final RegistrySupplier<Block> ROTTEN_LOG = registerBlock("rotten_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).instrument(NoteBlockInstrument.BASS).strength(2.0F).ignitedByLava().sound(SoundType.STEM)));

    public static final RegistrySupplier<Block> ROTTEN_PLANKS = registerBlock("rotten_planks", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).instrument(NoteBlockInstrument.BASS).strength(2.0F).ignitedByLava().sound(SoundType.STEM)));

    public static final RegistrySupplier<Block> ROTTEN_SLAB = registerBlock("rotten_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).ignitedByLava().sound(SoundType.STEM)));

    public static final RegistrySupplier<Block> ROTTEN_STAIRS = registerBlock("rotten_stairs", () -> new StairBlock(ROTTEN_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).ignitedByLava().sound(SoundType.STEM)));

    public static final RegistrySupplier<Block> ROTTEN_FENCE = registerBlock("rotten_fence", () -> new FenceBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).forceSolidOn().instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).ignitedByLava().sound(SoundType.STEM)));

    public static final RegistrySupplier<Block> ROTTEN_FENCE_GATE = registerBlock("rotten_fence_gate", () -> new FenceGateBlock(ROTTEN_WOOD_TYPE, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).forceSolidOn().instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).ignitedByLava().sound(SoundType.STEM)));

    public static final RegistrySupplier<Block> ROTTEN_TRAPDOOR = registerBlock("rotten_trapdoor",
            () -> new TrapDoorBlock(ROTTEN_WOOD_BLOCKSET, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().isValidSpawn((state, getter, pos, entityType) -> false).ignitedByLava().sound(SoundType.STEM)));

    public static final RegistrySupplier<Block> ROTTEN_DOOR = registerBlock("rotten_door", () -> new DoorBlock(ROTTEN_WOOD_BLOCKSET, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().ignitedByLava().pushReaction(PushReaction.DESTROY).sound(SoundType.STEM)));

    public static final RegistrySupplier<Block> ROTTEN_PRESSURE_PLATE = registerBlock("rotten_pressure_plate", () -> new PressurePlateBlock(ROTTEN_WOOD_BLOCKSET, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(0.5F).ignitedByLava().pushReaction(PushReaction.DESTROY).sound(SoundType.STEM)));

    //-------------MUD Brick Stuff----------------//
    public static final RegistrySupplier<Block> INFESTED_MUD_BRICKS = registerBlock("infested_mud_bricks", () -> new InfestedBlock(Blocks.MUD_BRICKS, BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_LIGHT_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 3.0F).sound(SoundType.MUD_BRICKS)));

    public static final RegistrySupplier<Block> CRACKED_MUD_BRICKS = registerBlock("cracked_mud_bricks", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_LIGHT_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 3.0F).sound(SoundType.MUD_BRICKS)));

    public static final RegistrySupplier<Block> CRACKED_MUD_BRICK_SLAB = registerBlock("cracked_mud_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_LIGHT_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 3.0F).sound(SoundType.MUD_BRICKS)));

    public static final RegistrySupplier<Block> CRACKED_MUD_BRICK_STAIRS = registerBlock("cracked_mud_brick_stairs", () -> new StairBlock(CRACKED_MUD_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_LIGHT_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 3.0F).sound(SoundType.MUD_BRICKS)));

    //-----------------OTHER--------------------//
    public static final RegistrySupplier<Block> ARCHEOLOGY_TABLE = registerBlock("archeology_table", () -> new ArchelogyTable(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(2.5F).sound(SoundType.WOOD).ignitedByLava()));

    public static final RegistrySupplier<Block> LOOT_VASE = registerBlock("loot_vase", () -> new LootVaseBlock(BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY).sound(SoundType.DECORATED_POT)));

    public static final RegistrySupplier<Block> VASE = registerBlock("vase", () -> new VaseBlock(BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY).sound(SoundType.DECORATED_POT)));

    public static final RegistrySupplier<Block> LOOT_VASE_CREEPER = registerBlock("loot_vase_creeper", () -> new LootVaseBlock(BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY).sound(SoundType.DECORATED_POT)));

    public static final RegistrySupplier<Block> VASE_CREEPER = registerBlock("vase_creeper", () -> new VaseBlock(BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY).sound(SoundType.DECORATED_POT)));

    public static final RegistrySupplier<Block> LOOT_VASE_GREEN = registerBlock("loot_vase_green", () -> new LootVaseBlock(BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY).sound(SoundType.DECORATED_POT)));

    public static final RegistrySupplier<Block> VASE_GREEN = registerBlock("vase_green", () -> new VaseBlock(BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY).sound(SoundType.DECORATED_POT)));

    public static final RegistrySupplier<Block> EVOKER_TRAP = registerBlock("evoker_trap", () -> new EvokerTrapBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).pushReaction(PushReaction.BLOCK).strength(20f).requiresCorrectToolForDrops()));

    public static final RegistrySupplier<Block> GROWTH_TOTEM = registerRareBlock("growth_totem",
            () -> new GrowthTotemBlock(MobEffects.GLOWING, 15,
                    BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().pushReaction(PushReaction.DESTROY).sound(SoundType.AMETHYST).offsetType(BlockBehaviour.OffsetType.NONE).lightLevel((state) -> 15)));

    public static final RegistrySupplier<Block> RADIANCE_TOTEM = registerRareBlock("radiance_totem", () -> new RadianceTotemBlock(BlockBehaviour.Properties.of().mapColor(MapColor.GOLD).forceSolidOn().requiresCorrectToolForDrops().strength(3.5F).sound(SoundType.LANTERN).lightLevel((p_152677_) -> 15).noOcclusion().pushReaction(PushReaction.DESTROY)));

    public static boolean isFossil(Block block)
    {
        return block instanceof FossilBaseBodyBlock
                || block instanceof FossilBaseWithEntityBlock
                || block instanceof FossilBaseHeadBlock
                || block instanceof FossilBaseBlock;
    }

    // REGISTERING ─────────────────────────────────────────────────────────────────────
    private static <T extends Block> RegistrySupplier<T> registerBlock(String name, Supplier<T> block) {
        return registerBlock(name, block, null);
    }

    private static <T extends Block> RegistrySupplier<T> registerRareBlock(String name, Supplier<T> block) {
        return registerBlock(name, block, Rarity.UNCOMMON);
    }

    private static <T extends Block> RegistrySupplier<T> registerBlock(String name, Supplier<T> block, Rarity rarity) {
        RegistrySupplier<T> toReturn = BLOCKS.register(BACommon.createResource(name), block);
        registerBlockItem(name, toReturn, rarity);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistrySupplier<T> block, Rarity rarity)
    {
        ModItems.ITEMS.register(
                BACommon.createResource(name),
                () -> new BlockItem(block.get(), rarity != null ?
                        new Item.Properties().rarity(rarity) :
                        new Item.Properties())
        );
    }

    private static WoodType registerWoodType(String id)
    {
        return WoodType.register(new WoodType("betterarcheology." + id, new BlockSetType(id)));
    }

    private static BlockSetType registerBlockSetType(String id)
    {
        return BlockSetType.register(new BlockSetType(id));
    }

    // LOAD THIS REGISTRY ─────────────────────────────────────────────────────────────────────
    public static void register()
    {
        BACommon.logRegistryEvent(BLOCKS);
    }
}