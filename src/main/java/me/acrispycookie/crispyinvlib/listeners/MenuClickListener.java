package me.acrispycookie.crispyinvlib.listeners;

import me.acrispycookie.crispyinvlib.CrispyInvLib;
import me.acrispycookie.crispyinvlib.menu.Menu;
import me.acrispycookie.crispyinvlib.managers.MenuManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class MenuClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e){
        MenuManager manager = CrispyInvLib.getInstance().getMenuManager();
        Menu menu = manager.getMenuByID(manager.getMenuByInventory(e.getClickedInventory()));
        if(e.getClickedInventory() != null && e.getInventory() != null && menu != null){
            menu.click(e.getSlot(), e);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e){
        MenuManager manager = CrispyInvLib.getInstance().getMenuManager();
        Menu menu = manager.getMenuByID(manager.getMenuByInventory(e.getInventory()));
        if(menu != null){
            manager.removeMenu(menu);
        }
    }
}
