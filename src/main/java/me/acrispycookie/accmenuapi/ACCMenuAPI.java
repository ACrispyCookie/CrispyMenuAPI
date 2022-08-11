package me.acrispycookie.accmenuapi;

import me.acrispycookie.accmenuapi.listeners.MenuClickListener;
import me.acrispycookie.accmenuapi.managers.MenuManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class ACCMenuAPI extends JavaPlugin {

    static ACCMenuAPI instance;
    MenuManager menuManager;
    @Override
    public void onEnable() {
        getLogger().info("Loading ACCMenuAPI...");
        instance = this;
        menuManager = new MenuManager();
        Bukkit.getPluginManager().registerEvents(new MenuClickListener(), this);
    }

    @Override
    public void onDisable(){
        getLogger().info("Disabling ACCMenuAPI...");
    }

    public MenuManager getMenuManager() {
        return menuManager;
    }

    public static ACCMenuAPI getInstance(){
        return instance;
    }
}
