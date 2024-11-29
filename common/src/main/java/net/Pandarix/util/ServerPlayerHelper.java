package net.Pandarix.util;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;

public class ServerPlayerHelper
{
    public static void tryOpenScreen(Player pPlayer, MenuProvider pMenuProvider, BlockPos pPos)
    {
        ServerPlayer serverPlayer = getServerPlayer(pPlayer);
        if (serverPlayer != null)
        {
            serverPlayer.openMenu(pMenuProvider, pPos);
        }
    }
}
