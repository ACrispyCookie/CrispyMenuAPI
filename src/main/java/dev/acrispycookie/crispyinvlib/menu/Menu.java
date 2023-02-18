package dev.acrispycookie.crispyinvlib.menu;

import dev.acrispycookie.crispyinvlib.CrispyInvLib;
import dev.acrispycookie.crispyinvlib.exceptions.InitializeException;
import dev.acrispycookie.crispyinvlib.items.Item;
import dev.acrispycookie.crispyinvlib.items.ButtonItem;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;


public abstract class Menu {

    Player player;
    InventoryType type;
    public abstract Inventory getInv();
    public abstract ArrayList<Item> getItems();
    protected abstract void loadInventoryItems();

    public Menu(Player p, int rows, InventoryType type) throws InitializeException {
        this.type = type;
        if(type == InventoryType.CHEST && (rows > 6 || rows < 0)) throw new InitializeException("Rows must be between 0 and 6.");
        player = p;
    }
    public void open(){
        CrispyInvLib.getInstance().getMenuManager().addMenu(this);
        player.closeInventory();
        loadInventoryItems();
        player.openInventory(getInv());
    }
    public void close(){
        CrispyInvLib.getInstance().getMenuManager().removeMenu(this);
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

    public Player getPlayer() {
        return player;
    }

    public Item getItemBySlot(int slot){
        for(Item i : getItems()){
            if(i.getSlot() == slot){
                return i;
            }
        }
        return null;
    }

    public Item getItemByPos(int row, int col){
        int slot = row * 9 + col;
        return getItemBySlot(slot);
    }
}
