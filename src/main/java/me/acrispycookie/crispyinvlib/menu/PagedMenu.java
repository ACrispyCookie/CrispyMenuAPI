package me.acrispycookie.crispyinvlib.menu;

import me.acrispycookie.crispyinvlib.exceptions.InitializeException;
import me.acrispycookie.crispyinvlib.items.ButtonItem;
import me.acrispycookie.crispyinvlib.items.Item;
import me.acrispycookie.crispyinvlib.items.SimpleItem;
import me.acrispycookie.crispyinvlib.utilities.itemstack.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class PagedMenu extends Menu {

    int totalPages;
    int currentPage;
    String title;
    int rows;
    Item previousDisplay;
    Item nextDisplay;
    Item previousAlt;
    Item nextAlt;
    ArrayList<SimpleMenu> invs = new ArrayList<>();
    public PagedMenu(Player p, String title, int rows, int totalPages) throws InitializeException {
        super(p, rows, InventoryType.CHEST);
        currentPage = 0;
        this.totalPages = totalPages;
        this.title = title;
        this.rows = rows;
        for(int i = 0; i < totalPages; i++){
            String replaced = title.replaceAll("%total%", String.valueOf(totalPages)).replaceAll("%page%", String.valueOf(i + 1));
            invs.add(new SimpleMenu(p, replaced, rows));
        }
    }
    public void changePage(int pageDiff){
        invs.get(currentPage).close();
        currentPage = currentPage + pageDiff;
        invs.get(currentPage).open();
    }
    public void addItem(Item item, int page) throws InitializeException {
        int current = currentPage;
        currentPage = page;
        if(getInv().getItem(item.getSlot()) != null){
            throw new InitializeException("There is already an item in this slot.");
        }
        else if(item.getSlot() >= getInv().getSize()){
            throw new InitializeException("The menu is too small to place the item in this slot.");
        }
        getItems().add(item);
        item.setMenu(this);
        getInv().setItem(item.getSlot(), item.getDisplay());
        currentPage = current;
    }

    public void addGlobalItem(Item item) throws InitializeException {
        for(int i = 0; i < totalPages; i++){
            addItem(item, i);
        }
    }

    @Override
    public void loadInventoryItems() {
        if(previousDisplay == null){
            previousDisplay = getDefaultPrevious();
        }
        if(nextDisplay == null){
            nextDisplay = getDefaultNext();
        }
        if(previousAlt == null){
            previousAlt = getPageAlt(3);
        }
        if(nextAlt == null){
            nextAlt = getPageAlt(5);
        }
        try {
            addItem(previousAlt, 0);
            addItem(nextAlt, totalPages - 1);
            if(totalPages > 1){
                addItem(nextDisplay, 0);
                addItem(previousDisplay, totalPages - 1);
                for(int i = 1; i < totalPages - 1; i++){
                    addItem(previousDisplay, i);
                    addItem(nextDisplay, i);
                }
            }
        } catch (InitializeException e) {
            e.printStackTrace();
        }
    }

    public void setPreviousDisplay(int row, int column, ItemStack prev, ItemStack alt){
        previousAlt = new SimpleItem(row, column, alt);
        previousDisplay = new ButtonItem(row, column, prev) {
            @Override
            protected void run() {
                changePage(-1);
            }
        };
    }

    public void setNextDisplay(int row, int column, ItemStack next, ItemStack alt){
        nextAlt = new SimpleItem(row, column, alt);
        nextDisplay = new ButtonItem(row, column, next) {
            @Override
            protected void run() {
                changePage(1);
            }
        };;
    }

    private Item getDefaultPrevious(){
        return new ButtonItem(rows - 1, 3, new ItemStackBuilder(Material.ARROW).name("&dPrevious Page").build()) {
            @Override
            protected void run() {
                changePage(-1);
            }
        };
    }

    private Item getDefaultNext(){
        return new ButtonItem(rows - 1, 5, new ItemStackBuilder(Material.ARROW).name("&dNext Page").build()) {
            @Override
            protected void run() {
                changePage(1);
            }
        };
    }

    private Item getPageAlt(int column){
        return new ButtonItem(rows - 1, column, new ItemStackBuilder(Material.AIR).build()) {
            @Override
            protected void run() {
                changePage(1);
            }
        };
    }

    public Item getItemBySlot(int slot, int page){
        for(Item i : getItems(page)){
            if(i.getSlot() == slot){
                return i;
            }
        }
        return null;
    }

    @Override
    public ArrayList<Item> getItems() {
        return invs.get(currentPage).getItems();
    }

    public ArrayList<Item> getItems(int page){
        return invs.get(page).getItems();
    }

    @Override
    public Inventory getInv() {
        return invs.get(currentPage).getInv();
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }
}
