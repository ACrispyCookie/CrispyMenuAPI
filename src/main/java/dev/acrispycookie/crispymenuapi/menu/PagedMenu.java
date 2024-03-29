package dev.acrispycookie.crispymenuapi.menu;

import dev.acrispycookie.crispymenuapi.exceptions.InitializeException;
import dev.acrispycookie.crispymenuapi.items.ButtonItem;
import dev.acrispycookie.crispymenuapi.items.Item;
import dev.acrispycookie.crispymenuapi.items.SimpleItem;
import dev.acrispycookie.crispymenuapi.utilities.itemstack.ItemStackBuilder;
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
        SimpleMenu menu = invs.get(page);
        if(menu.getInv().getItem(item.getSlot()) != null){
            throw new InitializeException("There is already an item in this slot.");
        }
        else if(item.getSlot() >= menu.getInv().getSize()){
            throw new InitializeException("The menu is too small to place the item in this slot.");
        }
        menu.getItems().add(item);
        item.setMenu(menu);
        menu.getInv().setItem(item.getSlot(), item.getDisplay());
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
            protected void onLeftClick() {
                changePage(-1);
            }
        };
    }

    public void setNextDisplay(int row, int column, ItemStack next, ItemStack alt){
        nextAlt = new SimpleItem(row, column, alt);
        nextDisplay = new ButtonItem(row, column, next) {
            @Override
            protected void onLeftClick() {
                changePage(1);
            }
        };;
    }

    private Item getDefaultPrevious(){
        return new ButtonItem(rows - 1, 3, new ItemStackBuilder(Material.ARROW).name("&dPrevious Page").build()) {
            @Override
            protected void onLeftClick() {
                changePage(-1);
            }
        };
    }

    private Item getDefaultNext(){
        return new ButtonItem(rows - 1, 5, new ItemStackBuilder(Material.ARROW).name("&dNext Page").build()) {
            @Override
            protected void onLeftClick() {
                changePage(1);
            }
        };
    }

    private Item getPageAlt(int column){
        return new ButtonItem(rows - 1, column, new ItemStackBuilder(Material.AIR).build()) {
            @Override
            protected void onLeftClick() {
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
