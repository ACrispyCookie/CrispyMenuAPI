package me.acrispycookie.crispyinvlib.items;

import me.acrispycookie.crispyinvlib.menu.Menu;
import org.bukkit.inventory.ItemStack;

public abstract class Item {

    int slot;
    ItemStack display;
    Menu menu = null;

    public Item(int row, int column, ItemStack display){
        this.slot = row * 9 + column;
        this.display = display;
    }

    public Item(int slot, ItemStack display){
        this.slot = slot;
        this.display = display;
    }

    public void setMenu(Menu menu){
        this.menu = menu;
    }

    public void update(){
        menu.update(this);
    }

    public void setDisplay(ItemStack display){
        this.display = display;
        update();
    }

    public ItemStack getDisplay() {
        return display;
    }

    public int getSlot() {
        return slot;
    }
}
