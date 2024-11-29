package net.Pandarix.item;

import net.Pandarix.BACommon;
import net.Pandarix.entity.BombEntity;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class BombItem extends Item
{
    //gets id of advancement for having thrown a bomb which has the condition "impossible" because it needs to be triggered here
    ResourceLocation ADVANCEMENT_ID = BACommon.createResource( "used_bomb_item");

    public BombItem(Properties pProperties)
    {
        super(pProperties);
    }

    @Override
    @NotNull
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand)
    {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);

        //plays sound for throwing the bomb
        pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));

        BombEntity bombEntity = new BombEntity(pLevel, pPlayer);

        pPlayer.getCooldowns().addCooldown(this, 10);

        //on server, set velocity of thrown item and sets the item to the bomb
        //grants player the advancement
        if (!pLevel.isClientSide())
        {
            bombEntity.setItem(itemStack);
            bombEntity.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 0.75F, 1.0F);
            pLevel.addFreshEntity(bombEntity);

            AdvancementHolder advancement = Objects.requireNonNull(pLevel.getServer()).getAdvancements().get(ADVANCEMENT_ID);
            if (advancement != null)
            {
                ((ServerPlayer) pPlayer).getAdvancements().award(advancement, "criteria");
            }
        }

        //play fuse sound at bombEntity
        pLevel.playSound(null, bombEntity, SoundEvents.CREEPER_PRIMED, SoundSource.NEUTRAL, 1f, (float) pLevel.getRandom().nextDouble() * 0.5f + 0.5f);

        //decrease stack size
        itemStack.shrink(1);

        return InteractionResultHolder.consume(itemStack);
    }
}
