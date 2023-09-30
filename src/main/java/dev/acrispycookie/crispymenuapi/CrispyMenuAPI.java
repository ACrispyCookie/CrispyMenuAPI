package dev.acrispycookie.crispymenuapi;

import dev.acrispycookie.crispymenuapi.listeners.MenuClickListener;
import dev.acrispycookie.crispymenuapi.managers.MenuManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class CrispyMenuAPI extends JavaPlugin {

    static CrispyMenuAPI instance;
    MenuManager menuManager;
    @Override
    public void onEnable() {
        getLogger().info("Loading CrispyInvLib...");
        instance = this;
        menuManager = new MenuManager();
        Bukkit.getPluginManager().registerEvents(new MenuClickListener(), this);
    }

    @Override
    public void onDisable(){
        getLogger().info("Disabling CrispyInvLib...");
    }

    public MenuManager getMenuManager() {
        return menuManager;
    }

    public static CrispyMenuAPI getInstance(){
        return instance;
    }
}
