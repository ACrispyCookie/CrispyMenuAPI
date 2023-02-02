package me.acrispycookie.crispyinvlib.items;

import org.bukkit.inventory.ItemStack;

public abstract class ButtonItem extends Item {

    protected abstract void run();

    public ButtonItem(int row, int column, ItemStack display) {
        super(row, column, display);
    }

    public ButtonItem(int slot, ItemStack display) {
        super(slot, display);
    }

    public void click(){
        run();
    }
}
