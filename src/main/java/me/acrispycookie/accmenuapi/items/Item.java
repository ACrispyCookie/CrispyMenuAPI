package me.acrispycookie.accmenuapi.items;

import me.acrispycookie.accmenuapi.menu.Menu;
import org.bukkit.inventory.ItemStack;

public abstract class Item {

    int slot;
    ItemStack display;
    Menu menu = null;

    public Item(int row, int column, ItemStack display){
        this.slot = row * 9 + column;
        this.display = display;
    }

    public void setMenu(Menu menu){
        this.menu = menu;
    }

    public void update(){
        menu.update(this);
    }

    public ItemStack getDisplay() {
        return display;
    }

    public int getSlot() {
        return slot;
    }
}