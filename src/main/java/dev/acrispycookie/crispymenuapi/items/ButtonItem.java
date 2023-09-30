package dev.acrispycookie.crispymenuapi.items;

import org.bukkit.inventory.ItemStack;

public abstract class ButtonItem extends Item {

    protected abstract void onLeftClick();

    public ButtonItem(int row, int column, ItemStack display) {
        super(row, column, display);
    }

    public ButtonItem(int slot, ItemStack display) {
        super(slot, display);
    }

    public void leftClick(){
        onLeftClick();
    }

    public void rightClick(){
        onRightClick();
    }

    protected void onRightClick(){
        onLeftClick();
    }
}
