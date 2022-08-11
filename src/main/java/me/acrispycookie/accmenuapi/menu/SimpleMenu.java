package me.acrispycookie.accmenuapi.menu;

import me.acrispycookie.accmenuapi.exceptions.InitializeException;
import me.acrispycookie.accmenuapi.items.Item;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.ArrayList;

public class SimpleMenu extends Menu {

    Inventory menu;
    InventoryHolder holder;
    ArrayList<Item> items;
    public SimpleMenu(Player p, String title, int rows) throws InitializeException {
        super(p, rows, InventoryType.CHEST);
        holder = () -> menu;
        menu =  Bukkit.createInventory(holder, rows * 9, ChatColor.translateAlternateColorCodes('&', title));
        this.items = new ArrayList<>();
    }

    public void addItem(Item item) throws InitializeException {
        if(menu.getItem(item.getSlot()) != null){
            throw new InitializeException("There is already an item in this slot.");
        }
        else if(item.getSlot() >= getInv().getSize()){
            throw new InitializeException("The menu is too small to place the item in this slot.");
        }
        items.add(item);
        item.setMenu(this);
        menu.setItem(item.getSlot(), item.getDisplay());
    }

    @Override
    public Inventory getInv() {
        return menu;
    }

    @Override
    public ArrayList<Item> getItems() {
        return items;
    }

    @Override
    public void loadInventoryItems() {

    }
}
