package net.Pandarix.mixin;

import net.minecraft.world.entity.animal.armadillo.Armadillo;
import net.minecraft.world.item.BrushItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Armadillo.class)
public abstract class ArmadilloMixin
{
    @Redirect(method = "mobInteract", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"))
    public boolean inject(ItemStack stack, Item item)
    {
        return item instanceof BrushItem;
    }
}
