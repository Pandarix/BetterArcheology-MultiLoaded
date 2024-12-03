package net.Pandarix.util;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;

public class MenuHelper
{
    /**
     * Adds {@link Slot}s for the {@link AbstractContainerMenu} given that represent a Player's inventory (NOT hotbar).
     * Used for the screens of the {@link net.Pandarix.screen.FossilInventoryMenu} and {@link net.Pandarix.screen.IdentifyingMenu}.
     * <br>
     * Adds 9 columns of slots with a dynamic amount of rows, because they could be modified by other mods.
     *
     * @param menu      Menu in which the slots should be added
     * @param playerInv Inventory which slots are represented
     */
    public static <T extends AbstractContainerMenu> void createPlayerInventory(T menu, Inventory playerInv)
    {
        // Inventory width is 9, number of rows could be modified by mods.
        // Calc the no of rows & subtract the hotbar row
        int columnNo = 9;
        int rowNoWithoutHotbar = (Inventory.INVENTORY_SIZE / columnNo) - 1;

        for (int row = 0; row < rowNoWithoutHotbar; row++)
        {
            for (int column = 0; column < columnNo; column++)
            {
                menu.addSlot(new Slot(playerInv,
                        9 + column + (row * 9),
                        8 + (column * 18),
                        86 + (row * 18)));
            }
        }
    }

    /**
     * Adds {@link Slot}s for the {@link AbstractContainerMenu} given that represent a Player's hotbar (NOT inventory).
     * Used for the screens of the {@link net.Pandarix.screen.FossilInventoryMenu} and {@link net.Pandarix.screen.IdentifyingMenu}.
     * <br>
     * Adds 9 columns of slots with one row.
     *
     * @param menu      Menu in which the slots should be added
     * @param playerInv Inventory which slots are represented
     */
    public static <T extends AbstractContainerMenu> void createPlayerHotbar(T menu, Inventory playerInv)
    {
        for (int column = 0; column < 9; column++)
        {
            menu.addSlot(new Slot(playerInv,
                    column,
                    8 + (column * 18),
                    144));
        }
    }
}
