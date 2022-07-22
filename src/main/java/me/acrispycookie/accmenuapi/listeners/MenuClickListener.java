package me.acrispycookie.accmenuapi.listeners;

import me.acrispycookie.accmenuapi.Main;
import me.acrispycookie.accmenuapi.menu.Menu;
import me.acrispycookie.accmenuapi.managers.MenuManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MenuClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e){
        MenuManager manager = Main.getInstance().getMenuManager();
        Menu menu = manager.getMenuByID(manager.getMenuByInventory(e.getClickedInventory()));
        if(e.getClickedInventory() != null && e.getInventory() != null && menu != null){
            menu.click(e.getSlot(), e);
        }
    }
}
