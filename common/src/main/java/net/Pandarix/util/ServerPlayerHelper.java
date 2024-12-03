package net.Pandarix.util;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;

import java.util.Optional;

public class ServerPlayerHelper
{
    /**
     * Used to try to get the {@link ServerPlayer} when given a {@link Player}.
     *
     * @param player The player which is attempted to be cast to a {@link ServerPlayer}
     * @return An {@link Optional} of the gotten {@link ServerPlayer} or an <b>empty</b> {@link Optional} if unsuccessful.
     */
    public static Optional<ServerPlayer> tryGetServerPlayer(Player player)
    {
        if (player instanceof ServerPlayer serverPlayer)
        {
            return Optional.of(serverPlayer);
        }
        return Optional.empty();
    }

    /**
     * Used to attempt to open a screen for a player. Does nothing if {@link Player} is not a {@link ServerPlayer}.
     *
     * @param pPlayer       The player to open the {@link MenuProvider} for
     * @param pMenuProvider The Menu that should be opened
     */
    public static void tryOpenScreen(Player pPlayer, MenuProvider pMenuProvider)
    {
        tryGetServerPlayer(pPlayer)
                .ifPresent(serverPlayer -> serverPlayer.openMenu(pMenuProvider));
    }
}
