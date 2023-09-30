package dev.acrispycookie.crispymenuapi.items;

import org.bukkit.inventory.ItemStack;

public class SimpleItem extends Item {
    public SimpleItem(int row, int column, ItemStack display) {
        super(row, column, display);
    }
    public SimpleItem(int slot, ItemStack display) {
        super(slot, display);
    }
}
