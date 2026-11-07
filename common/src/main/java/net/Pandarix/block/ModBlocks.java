package net.Pandarix.block;

import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.Pandarix.BACommon;
import net.Pandarix.block.custom.*;
import net.Pandarix.item.HovertextBlockItem;
import net.Pandarix.item.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.lighting.LightEngine;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class ModBlocks
{
    // REGISTRY ────────────────────────────────────────────────────────────────────────
    public static final Registrar<Block> BLOCKS = BACommon.REGISTRIES.get().get(Registries.BLOCK);

    // BLOCKS ──────────────────────────────────────────────────────────────────────────
    // ─── SUSPICIOUS ──────────────────────────────────────────────────────────────────
    public static final RegistrySupplier<Block> SUSPICIOUS_RED_SAND = registerBlock("suspicious_red_sand", () -> new SusBlock(
            Blocks.RED_SAND, SoundEvents.BRUSH_SAND, SoundEvents.BRUSH_SAND_COMPLETED,
            propsWithId("suspicious_red_sand")
                    .mapColor(MapColor.SAND)
                    .instrument(NoteBlockInstrument.SNARE)
                    .strength(0.25F)
                    .sound(SoundType.SUSPICIOUS_SAND)
                    .pushReaction(PushReaction.POPPED)));

    public static final RegistrySupplier<Block> SUSPICIOUS_DIRT = registerBlock("suspicious_dirt", () -> new SusBlock(
            Blocks.DIRT, SoundEvents.BRUSH_GRAVEL, SoundEvents.BRUSH_GRAVEL_COMPLETED,
            propsWithId("suspicious_dirt")
                    .mapColor(MapColor.STONE)
                    .instrument(NoteBlockInstrument.SNARE)
                    .strength(0.25F)
                    .sound(SoundType.SUSPICIOUS_GRAVEL)
                    .pushReaction(PushReaction.POPPED)));

    // ─── FOSSILIFEROUS ────────────────────────────────────────────────────────────────
    public static final RegistrySupplier<Block> FOSSILIFEROUS_DIRT = registerBlock("fossiliferous_dirt", () -> new SusBlock(
            Blocks.DIRT, SoundEvents.BRUSH_GRAVEL, SoundEvents.BRUSH_GRAVEL_COMPLETED,
            propsWithId("fossiliferous_dirt")
                    .mapColor(MapColor.STONE)
                    .instrument(NoteBlockInstrument.SNARE)
                    .strength(0.25F)
                    .sound(SoundType.SUSPICIOUS_GRAVEL)
                    .pushReaction(PushReaction.POPPED)));

    public static final RegistrySupplier<Block> CHISELED_BONE_BLOCK = registerBlock("chiseled_bone_block",
            () -> new RotatedPillarBlock(propsWithId("chiseled_bone_block").mapColor(MapColor.STONE).strength(0.3F).instrument(NoteBlockInstrument.XYLOPHONE).sound(SoundType.BONE_BLOCK)));

    // ─── FOSSILS ─────────────────────────────────────────────────────────────────────
    // Declared in creative tab order; fossilSets() keeps it.
    private static final List<FossilSet> FOSSIL_SETS = new ArrayList<>();

    public static final FossilSet CREEPER_FOSSIL = registerFossilSet("creeper",
            props -> new CreeperFossilBlock(boneProps(props)), FossilShapes.CREEPER_HEAD, FossilShapes.CREEPER_BODY);

    public static final FossilSet VILLAGER_FOSSIL = registerFossilSet("villager",
            props -> new VillagerFossilBlock(props.mapColor(MapColor.SAND).instrument(NoteBlockInstrument.XYLOPHONE).requiresCorrectToolForDrops().strength(2.0F).sound(SoundType.BONE_BLOCK).lightLevel(state -> state.getValue(VillagerFossilBlock.INVENTORY_LUMINANCE))),
            FossilShapes.VILLAGER_HEAD, FossilShapes.VILLAGER_BODY);

    public static final FossilSet CHICKEN_FOSSIL = registerFossilSet("chicken",
            props -> new ChickenFossilBlock(boneProps(props)), FossilShapes.CHICKEN_HEAD, FossilShapes.CHICKEN_BODY);

    public static final FossilSet OCELOT_FOSSIL = registerFossilSet("ocelot",
            props -> new OcelotFossilBlock(boneProps(props)), FossilShapes.OCELOT_HEAD, FossilShapes.OCELOT_BODY);

    public static final FossilSet WOLF_FOSSIL = registerFossilSet("wolf",
            props -> new WolfFossilBlock(boneProps(props)), FossilShapes.WOLF_HEAD, FossilShapes.WOLF_BODY);

    public static final FossilSet SHEEP_FOSSIL = registerFossilSet("sheep",
            props -> new SheepFossilBlock(boneProps(props)), FossilShapes.SHEEP_HEAD, FossilShapes.SHEEP_BODY);

    public static final FossilSet GUARDIAN_FOSSIL = registerFossilSet("guardian",
            props -> new GuardianFossilBlock(boneProps(props)), FossilShapes.GUARDIAN_HEAD, FossilShapes.GUARDIAN_BODY);

    // ─── Rotten Wood ──────────────────────────────────────────────────────────────────
    public static final WoodType ROTTEN_WOOD_TYPE = registerWoodType("rotten_wood");
    public static final BlockSetType ROTTEN_WOOD_BLOCKSET = registerBlockSetType("rotten_wood");

    public static final RegistrySupplier<Block> ROTTEN_LOG = registerBlock("rotten_log", () -> new RotatedPillarBlock(propsWithId("rotten_log").mapColor(MapColor.COLOR_BROWN).instrument(NoteBlockInstrument.BASS).strength(2.0F).ignitedByLava().sound(SoundType.STEM)));

    public static final RegistrySupplier<Block> ROTTEN_PLANKS = registerBlock("rotten_planks", () -> new Block(propsWithId("rotten_planks").mapColor(MapColor.COLOR_BROWN).instrument(NoteBlockInstrument.BASS).strength(2.0F).ignitedByLava().sound(SoundType.STEM)));

    public static final RegistrySupplier<Block> ROTTEN_SLAB = registerBlock("rotten_slab", () -> new SlabBlock(propsWithId("rotten_slab").mapColor(MapColor.COLOR_BROWN).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).ignitedByLava().sound(SoundType.STEM)));

    public static final RegistrySupplier<Block> ROTTEN_STAIRS = registerBlock("rotten_stairs", () -> new StairBlock(ROTTEN_PLANKS.get().defaultBlockState(), propsWithId("rotten_stairs").mapColor(MapColor.COLOR_BROWN).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).ignitedByLava().sound(SoundType.STEM)));

    public static final RegistrySupplier<Block> ROTTEN_FENCE = registerBlock("rotten_fence", () -> new FenceBlock(propsWithId("rotten_fence").mapColor(MapColor.COLOR_BROWN).forceSolidOn().instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).ignitedByLava().sound(SoundType.STEM)));

    public static final RegistrySupplier<Block> ROTTEN_FENCE_GATE = registerBlock("rotten_fence_gate", () -> new FenceGateBlock(ROTTEN_WOOD_TYPE, propsWithId("rotten_fence_gate").mapColor(MapColor.COLOR_BROWN).forceSolidOn().instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).ignitedByLava().sound(SoundType.STEM)));

    public static final RegistrySupplier<Block> ROTTEN_TRAPDOOR = registerBlock("rotten_trapdoor",
            () -> new TrapDoorBlock(ROTTEN_WOOD_BLOCKSET, propsWithId("rotten_trapdoor").mapColor(MapColor.COLOR_BROWN).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().isValidSpawn((state, getter, pos, entityType) -> false).ignitedByLava().sound(SoundType.STEM)));

    public static final RegistrySupplier<Block> ROTTEN_DOOR = registerBlock("rotten_door", () -> new DoorBlock(ROTTEN_WOOD_BLOCKSET, propsWithId("rotten_door").mapColor(MapColor.COLOR_BROWN).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().ignitedByLava().pushReaction(PushReaction.POPPED).sound(SoundType.STEM)));

    public static final RegistrySupplier<Block> ROTTEN_PRESSURE_PLATE = registerBlock("rotten_pressure_plate", () -> new PressurePlateBlock(ROTTEN_WOOD_BLOCKSET, propsWithId("rotten_pressure_plate").mapColor(MapColor.COLOR_BROWN).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollision().strength(0.5F).ignitedByLava().pushReaction(PushReaction.POPPED).sound(SoundType.STEM)));

    // ─── Mud Bricks ─────────────────────────────────────────────────────────────────
    public static final RegistrySupplier<Block> INFESTED_MUD_BRICKS = registerBlock("infested_mud_bricks", () -> new InfestedBlock(Blocks.MUD_BRICKS, propsWithId("infested_mud_bricks").mapColor(MapColor.TERRACOTTA_LIGHT_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 3.0F).sound(SoundType.MUD_BRICKS)));

    public static final RegistrySupplier<Block> CRACKED_MUD_BRICKS = registerBlock("cracked_mud_bricks", () -> new Block(propsWithId("cracked_mud_bricks").mapColor(MapColor.TERRACOTTA_LIGHT_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 3.0F).sound(SoundType.MUD_BRICKS)));

    public static final RegistrySupplier<Block> CRACKED_MUD_BRICK_SLAB = registerBlock("cracked_mud_brick_slab", () -> new SlabBlock(propsWithId("cracked_mud_brick_slab").mapColor(MapColor.TERRACOTTA_LIGHT_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 3.0F).sound(SoundType.MUD_BRICKS)));

    public static final RegistrySupplier<Block> CRACKED_MUD_BRICK_STAIRS = registerBlock("cracked_mud_brick_stairs", () -> new StairBlock(CRACKED_MUD_BRICKS.get().defaultBlockState(), propsWithId("cracked_mud_brick_stairs").mapColor(MapColor.TERRACOTTA_LIGHT_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 3.0F).sound(SoundType.MUD_BRICKS)));

    // ─── Misc ──────────────────────────────────────────────────────────────────────
    public static final RegistrySupplier<Block> ARCHEOLOGY_TABLE = registerBlock("archeology_table", () -> new ArchelogyTable(propsWithId("archeology_table").mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(2.5F).sound(SoundType.WOOD).ignitedByLava()));

    public static final RegistrySupplier<Block> LOOT_VASE = registerBlock("loot_vase", () -> new LootVaseBlock(propsWithId("loot_vase").instabreak().noOcclusion().pushReaction(PushReaction.POPPED).sound(SoundType.DECORATED_POT)));

    public static final RegistrySupplier<Block> VASE = registerBlock("vase", () -> new VaseBlock(propsWithId("vase").instabreak().noOcclusion().pushReaction(PushReaction.POPPED).sound(SoundType.DECORATED_POT)));

    public static final RegistrySupplier<Block> LOOT_VASE_CREEPER = registerBlock("loot_vase_creeper", () -> new LootVaseBlock(propsWithId("loot_vase_creeper").instabreak().noOcclusion().pushReaction(PushReaction.POPPED).sound(SoundType.DECORATED_POT)));

    public static final RegistrySupplier<Block> VASE_CREEPER = registerBlock("vase_creeper", () -> new VaseBlock(propsWithId("vase_creeper").instabreak().noOcclusion().pushReaction(PushReaction.POPPED).sound(SoundType.DECORATED_POT)));

    public static final RegistrySupplier<Block> LOOT_VASE_GREEN = registerBlock("loot_vase_green", () -> new LootVaseBlock(propsWithId("loot_vase_green").instabreak().noOcclusion().pushReaction(PushReaction.POPPED).sound(SoundType.DECORATED_POT)));

    public static final RegistrySupplier<Block> VASE_GREEN = registerBlock("vase_green", () -> new VaseBlock(propsWithId("vase_green").instabreak().noOcclusion().pushReaction(PushReaction.POPPED).sound(SoundType.DECORATED_POT)));

    public static final RegistrySupplier<Block> EVOKER_TRAP = registerBlock("evoker_trap", () -> new EvokerTrapBlock(propsWithId("evoker_trap").mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).pushReaction(PushReaction.IMMOVEABLE).strength(20f).requiresCorrectToolForDrops()));

    // Glowing effect length of suspicious stew made with the growth totem
    private static final int GROWTH_TOTEM_STEW_EFFECT_SECONDS = 15;

    public static final RegistrySupplier<Block> GROWTH_TOTEM = registerRareBlock("growth_totem",
            () -> new GrowthTotemBlock(
                    MobEffects.GLOWING, GROWTH_TOTEM_STEW_EFFECT_SECONDS,
                    propsWithId("growth_totem").mapColor(MapColor.PLANT).noCollision().instabreak().pushReaction(PushReaction.POPPED).sound(SoundType.AMETHYST).offsetType(BlockBehaviour.OffsetType.NONE).lightLevel(state -> LightEngine.MAX_LEVEL)), ChatFormatting.DARK_GREEN);

    public static final RegistrySupplier<Block> RADIANCE_TOTEM = registerRareBlock("radiance_totem",
            () -> new RadianceTotemBlock(
                    propsWithId("radiance_totem").mapColor(MapColor.GOLD).forceSolidOn().requiresCorrectToolForDrops().strength(3.5F).sound(SoundType.LANTERN).lightLevel(state -> LightEngine.MAX_LEVEL).noOcclusion().pushReaction(PushReaction.POPPED)), ChatFormatting.DARK_GREEN);

    // FOSSIL SETS ──────────────────────────────────────────────────────────────────────
    /** The three blocks of one fossil animal: the assembled fossil and its two wearable parts. */
    public record FossilSet(RegistrySupplier<Block> full, RegistrySupplier<Block> head, RegistrySupplier<Block> body)
    {
        /** All three blocks, in creative tab order; the tab and the pickaxe tag iterate this. */
        public Stream<RegistrySupplier<Block>> blocks()
        {
            return Stream.of(full, body, head);
        }
    }

    public static List<FossilSet> fossilSets()
    {
        return Collections.unmodifiableList(FOSSIL_SETS);
    }

    private static FossilSet registerFossilSet(String animal, Function<BlockBehaviour.Properties, Block> fullFossil,
                                               Map<Direction, VoxelShape> headShapes, Map<Direction, VoxelShape> bodyShapes)
    {
        String fullId = animal + "_fossil";
        String headId = fullId + "_head";
        String bodyId = fullId + "_body";

        FossilSet set = new FossilSet(
                registerRareBlock(fullId, () -> fullFossil.apply(propsWithId(fullId))),
                registerSkullBlock(headId, () -> new FossilBlock(boneProps(propsWithId(headId)), headShapes, false)),
                registerBodyBlock(bodyId, () -> new FossilBlock(boneProps(propsWithId(bodyId)), bodyShapes, true)));
        FOSSIL_SETS.add(set);
        return set;
    }

    /** Properties shared by fossil parts and most assembled fossils. */
    private static BlockBehaviour.Properties boneProps(BlockBehaviour.Properties props)
    {
        return props.instrument(NoteBlockInstrument.SKELETON).strength(1.0F).pushReaction(PushReaction.POPPED).sound(SoundType.BONE_BLOCK);
    }

    // UTIL ─────────────────────────────────────────────────────────────────────────────
    public static boolean isFossil(Block block)
    {
        return block instanceof FossilBlock;
    }

    private static BlockBehaviour.Properties propsWithId(String id)
    {
        return BlockBehaviour.Properties.of().setId(BACommon.createRKey(Registries.BLOCK, id));
    }

    private static <T extends Block> MutableComponent tooltipComponent(String name)
    {
        return Component.translatable(String.format("block.%s.%s_tooltip", BACommon.MOD_ID, name));
    }

    // REGISTERING ─────────────────────────────────────────────────────────────────────
    private static <T extends Block> RegistrySupplier<T> registerBlock(String name, Supplier<T> block)
    {
        return registerBlock(name, block, null, null, false);
    }

    private static <T extends Block> RegistrySupplier<T> registerRareBlock(String name, Supplier<T> block)
    {
        MutableComponent hoverText = tooltipComponent(name).withStyle(ChatFormatting.GRAY);
        return registerBlock(name, block, Rarity.COMMON, hoverText, false);
    }

    private static <T extends Block> RegistrySupplier<T> registerRareBlock(String name, Supplier<T> block, ChatFormatting tooltipColor)
    {
        MutableComponent hoverText = tooltipComponent(name).withStyle(tooltipColor);
        return registerBlock(name, block, Rarity.COMMON, hoverText, false);
    }

    private static <T extends Block> RegistrySupplier<T> registerSkullBlock(String name, Supplier<T> block)
    {
        MutableComponent hoverText = tooltipComponent(name).withStyle(ChatFormatting.GRAY)
                .append(Component.translatable("block.betterarcheology.fossil_head_set").withStyle(ChatFormatting.BLUE));
        return registerBlock(name, block, Rarity.COMMON, hoverText, true);
    }

    private static <T extends Block> RegistrySupplier<T> registerBodyBlock(String name, Supplier<T> block)
    {
        MutableComponent hoverText = tooltipComponent(name).withStyle(ChatFormatting.GRAY)
                .append(Component.translatable("block.betterarcheology.fossil_body_set").withStyle(ChatFormatting.BLUE));
        return registerBlock(name, block, Rarity.COMMON, hoverText, true);
    }

    private static <T extends Block> RegistrySupplier<T> registerBlock(String name, Supplier<T> block, @Nullable Rarity rarity, @Nullable MutableComponent component, boolean isSkull)
    {
        Identifier loc = BACommon.createRLoc(name);
        RegistrySupplier<T> registeredBlock = BLOCKS.register(loc, block);
        registerBlockItem(name, loc, registeredBlock, rarity, component, isSkull);
        return registeredBlock;
    }

    private static <T extends Block> void registerBlockItem(String name, Identifier loc, RegistrySupplier<T> block, @Nullable Rarity rarity, @Nullable MutableComponent component, boolean isSkull)
    {
        Item.Properties properties = ModItems.propsWithId(name).useBlockDescriptionPrefix();

        if (rarity != null)
            properties.rarity(rarity);

        if (isSkull)
            properties.component(DataComponents.EQUIPPABLE, Equippable.builder(EquipmentSlot.HEAD).setEquipSound(SoundEvents.ARMOR_EQUIP_TURTLE).setDamageOnHurt(false).build());

        ModItems.ITEMS.register(loc, () ->
        {
            if (component != null)
                return new HovertextBlockItem(block.get(), properties, component);

            return new BlockItem(block.get(), properties);
        });
    }

    private static WoodType registerWoodType(String id)
    {
        return WoodType.register(new WoodType("betterarcheology." + id, new BlockSetType(id)));
    }

    private static BlockSetType registerBlockSetType(String id)
    {
        return BlockSetType.register(new BlockSetType(id));
    }

    // LOAD ────────────────────────────────────────────────────────────────────────────
    public static void register()
    {
        BACommon.logRegistryEvent(BLOCKS);
    }
}