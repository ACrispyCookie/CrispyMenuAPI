package dev.acrispycookie.crispymenuapi.items;

import dev.acrispycookie.crispymenuapi.utilities.itemstack.ItemStackBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class DataItem extends ButtonItem {

    boolean isLoaded = false;
    Runnable leftClick;
    Runnable rightClick;

    public DataItem(int row, int column, ItemStack loading) {
        super(row, column, loading);
    }

    public DataItem(int slot, ItemStack display) {
        super(slot, display);
    }

    public DataItem(int row, int column) {
        super(row, column, null);
        display = getDefaultLoading();
    }

    public DataItem(int slot) {
        super(slot, null);
        display = getDefaultLoading();
    }

    @Override
    protected void onLeftClick() {
        leftClick.run();
    }

    @Override
    protected void onRightClick() {
        rightClick.run();
    }

    public void load(ItemStack loaded, Runnable leftClick){
        display = loaded;
        this.leftClick = leftClick;
        this.rightClick = leftClick;
        isLoaded = true;
        DataItem.this.update();
    }

    public void load(ItemStack loaded, Runnable leftClick, Runnable rightClick){
        display = loaded;
        this.leftClick = leftClick;
        this.rightClick = rightClick;
        isLoaded = true;
        DataItem.this.update();
    }

    private ItemStack getDefaultLoading(){
        return new ItemStackBuilder(Material.STAINED_GLASS_PANE).name(ChatColor.translateAlternateColorCodes('&', "&8Loading...")).durability((short) 15).build();
    }
}
