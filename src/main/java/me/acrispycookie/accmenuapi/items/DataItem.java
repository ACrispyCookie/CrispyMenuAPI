package me.acrispycookie.accmenuapi.items;

import me.acrispycookie.accmenuapi.Main;
import me.acrispycookie.accmenuapi.utilities.itemstack.ItemStackBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public abstract class DataItem extends ButtonItem {

    public abstract ItemStack getLoaded();
    boolean isLoaded = false;

    public DataItem(int row, int column, ItemStack loading) {
        super(row, column, loading);
    }

    public DataItem(int row, int column) {
        super(row, column, null);
        display = getDefaultLoading();
    }

    public void click(){
        if(isLoaded){
            run();
        }
    }

    public void load(){
        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
            display = getLoaded();
            isLoaded = true;
            DataItem.this.update();
        });
    }

    private ItemStack getDefaultLoading(){
        return new ItemStackBuilder(Material.STAINED_GLASS_PANE).name(ChatColor.translateAlternateColorCodes('&', "&8Loading...")).durability((short) 15).build();
    }
}
