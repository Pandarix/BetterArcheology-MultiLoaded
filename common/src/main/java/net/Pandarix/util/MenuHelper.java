package net.Pandarix.util;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;

public class MenuHelper
{
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
