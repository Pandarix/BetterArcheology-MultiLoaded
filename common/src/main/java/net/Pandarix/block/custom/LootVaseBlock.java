package net.Pandarix.block.custom;

import net.Pandarix.BACommon;
import net.Pandarix.util.ServerPlayerHelper;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class LootVaseBlock extends Block
{
    private static final VoxelShape SHAPE = Block.box(3, 0, 3, 13, 14, 13);
    //advancement id for granting the advancement in onBreak, condition of advancement is "impossible" and needs to be executed here
    ResourceLocation ADVANCEMENT_ID = BACommon.createRLoc("loot_vase_broken");

    public LootVaseBlock(Properties settings)
    {
        super(settings);
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos blockPos, BlockState blockState, @Nullable BlockEntity blockEntity, ItemStack itemStack)
    {
        try
        {
            Optional<Holder.Reference<Enchantment>> SILK_TOUCH = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).get(Enchantments.SILK_TOUCH);
            boolean hasSilkTouch = SILK_TOUCH.isPresent() && EnchantmentHelper.getItemEnchantmentLevel(SILK_TOUCH.get(), player.getMainHandItem()) > 0;

            if (!level.isClientSide())
            {
                //if the players is not in creative and doesn't silk-touch the vase
                if (!player.isCreative() && !hasSilkTouch)
                {
                    //spawn xpOrbs
                    Entity xpOrb = new ExperienceOrb(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), 4);
                    level.addFreshEntity(xpOrb);
                }

                //gets the AdvancementLoader of the ServerPlayer and grants him the
                // custom criteria called "criteria"
                // will not get executed when advancement is already granted
                if (level.getServer() != null)
                {
                    AdvancementHolder advancement = level.getServer().getAdvancements().get(ADVANCEMENT_ID);
                    if (advancement != null)
                    {
                        ServerPlayerHelper.tryGetServerPlayer(player)
                                .ifPresent(sp -> sp.getAdvancements().award(advancement, "criteria"));
                    }
                }
            }
            if (level.getServer() != null && level.getServer().getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && !hasSilkTouch)
            {
                //4% chance of spawning a silverfish when breaking a loot vase
                if (level.getRandom().nextInt(25) == 1)
                {
                    spawnSilverFish(level, blockPos);
                }
            }
        } catch (Exception e)
        {
            BACommon.LOGGER.error("Error in breaking LootVase Block! : ", e);
        }
        super.playerDestroy(level, player, blockPos, blockState, blockEntity, itemStack);
    }

    //Similar code that also gets executed when InfestedBlock is brocke to spawn a SilverFish
    private static void spawnSilverFish(Level level, BlockPos pos)
    {
        Silverfish silverfishEntity = EntityType.SILVERFISH.create(level, EntitySpawnReason.TRIGGERED);
        if (silverfishEntity != null)
        {
            silverfishEntity.moveTo((double) pos.getX() + 0.5, pos.getY(), (double) pos.getZ() + 0.5, 0.0F, 0.0F);
            level.addFreshEntity(silverfishEntity);
            silverfishEntity.spawnAnim();
        }
    }

    @Override
    @NotNull
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext)
    {
        return SHAPE;
    }

    @Override
    protected boolean isPathfindable(BlockState blockState, PathComputationType pathComputationType)
    {
        return false;
    }
}
