package me.acrispycookie.accmenuapi.menu;

import me.acrispycookie.accmenuapi.Main;
import me.acrispycookie.accmenuapi.exceptions.InitializeException;
import me.acrispycookie.accmenuapi.items.DataItem;
import me.acrispycookie.accmenuapi.items.Item;
import me.acrispycookie.accmenuapi.items.ButtonItem;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;


public abstract class Menu {

    Player player;

    public abstract Inventory getInv();
    public abstract ArrayList<Item> getItems();
    public abstract void loadInventoryItems();

    public Menu(Player p, int rows) throws InitializeException {
        if(rows > 6 || rows < 0) throw new InitializeException("Rows must be between 0 and 6.");
        player = p;
    }
    public void open(){
        Main.getInstance().getMenuManager().addMenu(this);
        player.closeInventory();
        loadInventoryItems();
        player.openInventory(getInv());
        startLoadingButtons();
    }
    public void close(){
        Main.getInstance().getMenuManager().removeMenu(this);
        player.closeInventory();
    }

    public void update(Item item){
        getInv().setItem(item.getSlot(), item.getDisplay());
    }

    public void click(int slot, InventoryClickEvent e){
        Item item = getItemBySlot(slot);
        e.setCancelled(true);
        if(item instanceof ButtonItem){
            ButtonItem button = (ButtonItem) item;
            button.click();
        }
    }
    private void startLoadingButtons(){
        for(Item item : getItems()){
            if(item instanceof DataItem){
                DataItem di = (DataItem) item;
                di.load();
            }
        }
    }

    public Player getPlayer() {
        return player;
    }

    private Item getItemBySlot(int slot){
        for(Item i : getItems()){
            if(i.getSlot() == slot){
                return i;
            }
        }
        return null;
    }
}
