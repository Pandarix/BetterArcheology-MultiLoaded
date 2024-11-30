package net.Pandarix.util;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;

import java.util.Optional;

public class ServerPlayerHelper
{
    public static Optional<ServerPlayer> getServerPlayer(Player player)
    {
        if (player instanceof ServerPlayer serverPlayer)
        {
            return Optional.of(serverPlayer);
        }
        return Optional.empty();
    }

    public static void tryOpenScreen(Player pPlayer, MenuProvider pMenuProvider)
    {
        getServerPlayer(pPlayer)
                .ifPresent(serverPlayer -> serverPlayer.openMenu(pMenuProvider));
    }
}
