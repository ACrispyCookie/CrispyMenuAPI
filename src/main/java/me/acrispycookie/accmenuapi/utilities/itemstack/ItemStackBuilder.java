package me.acrispycookie.accmenuapi.utilities.itemstack;

import net.minecraft.server.v1_8_R3.NBTBase;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemStackBuilder {
	
	Material type;
	int amount = 1;
	short durability = 0;
	String name;
	boolean unbreakable = false;
	boolean enchantingGlint = false;
	final List<String> lore = new ArrayList<>();
	final ArrayList<ItemFlag> flags = new ArrayList<>();
	final HashMap<Enchantment, Integer> enchants = new HashMap<>();
	final HashMap<String, NBTBase> tags = new HashMap<>();
	
	
	public ItemStackBuilder(Material mat) {
		type = mat;
	}
	
	public ItemStackBuilder material(Material mat) {
		type = mat;
		return this;
	}
	
	public ItemStackBuilder name(String s) {
		name = ChatColor.translateAlternateColorCodes('&', s);
		return this;
	}
	
	public ItemStackBuilder amount(int amount) {
		this.amount = amount;
		return this;
	}
	
	public ItemStackBuilder durability(short dur) {
		durability = dur;
		return this;
	}
	
	public ItemStackBuilder lore(String s) {
		String[] list = s.split("\n");
		for(String ss : list) {
			lore.add(ChatColor.translateAlternateColorCodes('&', ss));
		}
		return this;
	}
	
	public ItemStackBuilder addEnchant(Enchantment ench, int level) {
		enchants.put(ench, level);
		return this;
	}
	
	public ItemStackBuilder addTag(String identifier, NBTBase value) {
		tags.put(identifier, value);
		return this;
	}
	
	public ItemStackBuilder addFlag(ItemFlag flag) {
		flags.add(flag);
		return this;
	}
	
	public ItemStackBuilder unbreakable(boolean unb) {
		unbreakable = unb;
		return this;
	}
	
	public ItemStackBuilder glint(boolean gl) {
		enchantingGlint = gl;
		return this;
	}
	
	public ItemStack build() {
		ItemStack i = new ItemStack(type, amount);
		i.setDurability(durability);
		ItemMeta meta = i.getItemMeta();
		meta.setDisplayName(name);
		meta.spigot().setUnbreakable(unbreakable);
		meta.setLore(lore);
		for(ItemFlag f : flags) {
			meta.addItemFlags(f);
		}
		for(Enchantment ench : enchants.keySet()) {
			meta.addEnchant(ench, enchants.get(ench), true);
		}
		i.setItemMeta(meta);
		net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(i);
		NBTTagCompound tag = nmsItem.hasTag() ? nmsItem.getTag() : new NBTTagCompound();
		for(String identifier : tags.keySet()) {
			tag.set(identifier, tags.get(identifier));
		}
		if(enchantingGlint && !tag.hasKey("ench")) {
			tag.set("ench", new NBTTagList());
		}
		nmsItem.setTag(tag);
		return CraftItemStack.asBukkitCopy(nmsItem);
	}

}
