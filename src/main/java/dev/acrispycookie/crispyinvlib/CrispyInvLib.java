package dev.acrispycookie.crispyinvlib;

import dev.acrispycookie.crispyinvlib.listeners.MenuClickListener;
import dev.acrispycookie.crispyinvlib.managers.MenuManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class CrispyInvLib extends JavaPlugin {

    static CrispyInvLib instance;
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

    public static CrispyInvLib getInstance(){
        return instance;
    }
}
