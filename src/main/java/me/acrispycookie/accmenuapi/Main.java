package me.acrispycookie.accmenuapi;

import me.acrispycookie.accmenuapi.listeners.MenuClickListener;
import me.acrispycookie.accmenuapi.managers.MenuManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    static Main instance;
    MenuManager menuManager;
    @Override
    public void onEnable() {
        getLogger().info("Loading ACCMenuAPI...");
        instance = this;
        menuManager = new MenuManager();
        Bukkit.getPluginManager().registerEvents(new MenuClickListener(), this);
        getCommand("menu").setExecutor(new MenuCommand());
    }

    @Override
    public void onDisable(){
        getLogger().info("Disabling ACCMenuAPI...");
    }

    public MenuManager getMenuManager() {
        return menuManager;
    }

    public static Main getInstance(){
        return instance;
    }
}
