package dev.acrispycookie.crispyinvlib.managers;

import dev.acrispycookie.crispyinvlib.menu.Menu;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.UUID;

public class MenuManager {

    HashMap<UUID, Menu> menus = new HashMap<>();

    public MenuManager(){}

    public void addMenu(Menu menu){
        UUID uuid = UUID.randomUUID();
        while(menus.containsKey(uuid)){
            uuid = UUID.randomUUID();
        }
        menus.put(uuid, menu);
    }

    public void removeMenu(Menu menu){
        menus.remove(getIdByMenu(menu));
    }

    public void removeMenu(UUID uuid){
        menus.remove(uuid);
    }

    public UUID getIdByMenu(Menu menu){
        for(UUID uuid : menus.keySet()){
            if(menus.get(uuid).equals(menu)){
                return uuid;
            }
        }
        return null;
    }

    public Menu getMenuByID(UUID uuid){
        if(menus.containsKey(uuid)){
            return menus.get(uuid);
        }
        return null;
    }

    public UUID getMenuByPlayer(Player p){
        for(UUID uuid : menus.keySet()){
            Menu menu = menus.get(uuid);
            if(menu.getPlayer().equals(p)){
                return uuid;
            }
        }
        return null;
    }

    public UUID getMenuByInventory(Inventory inv){
        for(UUID uuid : menus.keySet()){
            Menu menu = menus.get(uuid);
            if(menu.getInv().equals(inv)){
                return uuid;
            }
        }
        return null;
    }
}
