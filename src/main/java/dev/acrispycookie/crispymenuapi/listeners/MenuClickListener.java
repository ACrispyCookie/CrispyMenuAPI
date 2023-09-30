package dev.acrispycookie.crispymenuapi.listeners;

import dev.acrispycookie.crispymenuapi.CrispyMenuAPI;
import dev.acrispycookie.crispymenuapi.managers.MenuManager;
import dev.acrispycookie.crispymenuapi.menu.Menu;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class MenuClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e){
        MenuManager manager = CrispyMenuAPI.getInstance().getMenuManager();
        Menu menu = manager.getMenuByID(manager.getMenuByInventory(e.getClickedInventory()));
        if(e.getClickedInventory() != null && e.getInventory() != null && menu != null){
            if(e.getClick().isLeftClick()) {
                menu.leftClick(e.getSlot(), e);
            } else if(e.getClick().isRightClick()) {
                menu.rightClick(e.getSlot(), e);
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e){
        MenuManager manager = CrispyMenuAPI.getInstance().getMenuManager();
        Menu menu = manager.getMenuByID(manager.getMenuByInventory(e.getInventory()));
        if(menu != null){
            manager.removeMenu(menu);
        }
    }
}
