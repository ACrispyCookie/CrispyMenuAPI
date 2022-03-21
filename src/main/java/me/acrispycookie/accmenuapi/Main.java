package me.acrispycookie.accmenuapi;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Loading ACCMenuAPI...");
        getCommand("menu").setExecutor(new MenuCommand());
    }

    @Override
    public void onDisable(){
        getLogger().info("Disabling ACCMenuAPI...");
    }
    
}
