package me.acrispycookie.accmenuapi;

import me.acrispycookie.accmenuapi.exceptions.InitializeException;
import me.acrispycookie.accmenuapi.items.ButtonItem;
import me.acrispycookie.accmenuapi.menu.Menu;
import me.acrispycookie.accmenuapi.menu.PagedMenu;
import me.acrispycookie.accmenuapi.menu.SimpleMenu;
import me.acrispycookie.accmenuapi.utilities.itemstack.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MenuCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("Opening test menu...");
        Player p = (Player) sender;
        try {
            PagedMenu menu = new PagedMenu(p, "ise xontros", 6, 2);
            menu.addItem(new ButtonItem(3, 4, new ItemStackBuilder(Material.BEDROCK).name("&akalhmera").glint(true).build()) {
                @Override
                protected void run() {
                    menu.close();
                    menu.getPlayer().damage(200);
                }
            }, 0);
            menu.addItem(new ButtonItem(3, 4, new ItemStackBuilder(Material.BEDROCK).name("&1kalhmera").glint(true).build()) {
                @Override
                protected void run() {
                    menu.close();
                    menu.getPlayer().setVelocity(menu.getPlayer().getVelocity().multiply(3));
                }
            }, 1);
            menu.open();
        } catch (InitializeException e) {
            e.printStackTrace();
        }
        return false;
    }
    
}
