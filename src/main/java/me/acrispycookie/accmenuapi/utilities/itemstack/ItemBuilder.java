package me.acrispycookie.accmenuapi.utilities.itemstack;

import net.minecraft.server.v1_8_R3.NBTBase;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class ItemBuilder {

    Material type;
    int amount = 1;
    short durability = 0;
    String name;
    final List<String> lore = new ArrayList<>();
    final ArrayList<ItemFlag> flags = new ArrayList<>();
    final HashMap<Enchantment, Integer> enchants = new HashMap<>();
    final HashMap<String, NBTBase> tags = new HashMap<>();

    public abstract ItemStack build();

    public ItemBuilder material(Material mat) {
        type = mat;
        return this;
    }

    public ItemBuilder name(String s) {
        name = ChatColor.translateAlternateColorCodes('&', s);
        return this;
    }

    public ItemBuilder amount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemBuilder durability(short dur) {
        durability = dur;
        return this;
    }

    public ItemBuilder lore(String s) {
        lore.clear();
        String[] list = s.split("\n");
        for(String ss : list) {
            lore.add(ChatColor.translateAlternateColorCodes('&', ss));
        }
        return this;
    }

    public ItemBuilder addEnchant(Enchantment ench, int level) {
        enchants.put(ench, level);
        return this;
    }

    public ItemBuilder addTag(String identifier, NBTBase value) {
        tags.put(identifier, value);
        return this;
    }

    public ItemBuilder addFlag(ItemFlag flag) {
        flags.add(flag);
        return this;
    }

}
