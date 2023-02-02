package me.acrispycookie.crispyinvlib.items;

import me.acrispycookie.crispyinvlib.utilities.itemstack.ItemStackBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class DataItem extends ButtonItem {

    boolean isLoaded = false;
    Runnable runnable;

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
    protected void run() {
        runnable.run();
    }

    public void click(){
        if(isLoaded){
            run();
        }
    }

    public void load(ItemStack loaded, Runnable run){
        display = loaded;
        runnable = run;
        isLoaded = true;
        DataItem.this.update();
    }

    private ItemStack getDefaultLoading(){
        return new ItemStackBuilder(Material.STAINED_GLASS_PANE).name(ChatColor.translateAlternateColorCodes('&', "&8Loading...")).durability((short) 15).build();
    }
}
