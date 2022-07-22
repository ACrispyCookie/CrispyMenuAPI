package me.acrispycookie.accmenuapi.menu;

import me.acrispycookie.accmenuapi.exceptions.InitializeException;
import me.acrispycookie.accmenuapi.items.ButtonItem;
import me.acrispycookie.accmenuapi.items.Item;
import me.acrispycookie.accmenuapi.utilities.itemstack.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
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
    ArrayList<SimpleMenu> invs = new ArrayList<>();
    public PagedMenu(Player p, String title, int rows, int totalPages) throws InitializeException {
        super(p, rows);
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
        try {
            addItem(nextDisplay, 0);
            for(int i = 1; i < totalPages - 1; i++){
                addItem(previousDisplay, i);
            }
            addItem(previousDisplay, totalPages - 1);
        } catch (InitializeException e) {
            e.printStackTrace();
        }
    }

    public void setPreviousDisplay(int row, int column, ItemStack prev){
        previousDisplay = new ButtonItem(row, column, prev) {
            @Override
            protected void run() {
                changePage(-1);
            }
        };
    }

    public void setNextDisplay(int row, int column, ItemStack next){
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

    @Override
    public ArrayList<Item> getItems() {
        return invs.get(currentPage).getItems();
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
