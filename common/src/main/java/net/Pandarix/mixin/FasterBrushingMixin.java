package net.Pandarix.mixin;

import net.Pandarix.BACommon;
import net.Pandarix.item.BetterBrushItem;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings("Unreachable code")
@Mixin(BrushableBlockEntity.class)
public abstract class FasterBrushingMixin
{
    @Inject(method = "brush", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/BrushableBlockEntity;unpackLootTable(Lnet/minecraft/world/entity/player/Player;)V"))
    private void injectMethod(long worldTime, Player player, Direction hitDirection, CallbackInfoReturnable<Boolean> cir)
    {
        try
        {
            if ((Object) this instanceof BrushableBlockEntity ba$brushableBlockEntity
                    && player.getUseItem().getItem() instanceof BetterBrushItem ba$brushItem)
            {
                ba$brushableBlockEntity.coolDownEndsAtTick += (long) (-10 + ba$brushItem.getBrushingSpeed());
            }
        } catch (Exception e)
        {
            BACommon.LOGGER.info("Could not apply faster brushing due to error: " + e);
        }
    }
}
