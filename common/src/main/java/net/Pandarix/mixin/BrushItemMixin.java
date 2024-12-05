package net.Pandarix.mixin;

import net.Pandarix.item.BetterBrushItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BrushItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BrushItem.class)
public class BrushItemMixin
{
    @Redirect(method = "onUseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/BrushItem;getUseDuration(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/LivingEntity;)I"))
    public int inject(BrushItem instance, ItemStack itemStack, LivingEntity livingEntity, Level pLevel, LivingEntity pLivingEntity, ItemStack pItemStack, int pRemainingUseDuration)
    {
        if((Object) this instanceof BetterBrushItem ba$betterBrushItem)
        {
            if(pRemainingUseDuration % ba$betterBrushItem.getBrushingSpeed() == 0)
            {
                return 4 + pRemainingUseDuration;
            }
            return pRemainingUseDuration;
        }
        return instance.getUseDuration(itemStack, livingEntity);
    }
}
