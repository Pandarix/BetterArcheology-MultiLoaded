package net.Pandarix.mixin;

import net.Pandarix.BACommon;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BrushableBlockEntity.class)
public abstract class FasterBrushingMixin
{
    @Inject(method = "brush", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/BrushableBlockEntity;unpackLootTable(Lnet/minecraft/world/entity/player/Player;)V"))
    private void injectMethod(long worldTime, Player player, Direction hitDirection, CallbackInfoReturnable<Boolean> cir)
    {
        // TODO: make work with brush tiers
        try
        {
            /*BrushableBlockEntity ba$brushableBlockEntity = ((BrushableBlockEntity) (Object) this);
            if (player.getUseItem().getItem() instanceof BetterBrushItem ba$brushItem)
            {
                ba$brushableBlockEntity.coolDownEndsAtTick -= (long) (10L - ba$brushItem.getBrushingSpeed());
            }*/
        } catch (Exception e)
        {
            BACommon.LOGGER.info("Could not apply faster brushing due to error: " + e);
        }
    }
}
