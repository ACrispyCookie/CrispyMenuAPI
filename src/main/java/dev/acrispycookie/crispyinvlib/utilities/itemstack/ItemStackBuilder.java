package dev.acrispycookie.crispyinvlib.utilities.itemstack;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemStackBuilder extends ItemBuilder {

	boolean unbreakable = false;
	boolean enchantingGlint = false;
	
	
	public ItemStackBuilder(Material mat) {
		type = mat;
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
		net.minecraft.server.v1_8_R3.ItemStack nmsItem = null;
		i.setDurability(durability);
		ItemMeta meta = i.getItemMeta();
		if(meta != null){
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
			nmsItem = CraftItemStack.asNMSCopy(i);
			NBTTagCompound tag = nmsItem.hasTag() ? nmsItem.getTag() : new NBTTagCompound();
			for(String identifier : tags.keySet()) {
				tag.set(identifier, tags.get(identifier));
			}
			if(enchantingGlint && !tag.hasKey("ench")) {
				tag.set("ench", new NBTTagList());
			}
			nmsItem.setTag(tag);
		}
		if(nmsItem == null){
			nmsItem = CraftItemStack.asNMSCopy(i);
		}
		return CraftItemStack.asBukkitCopy(nmsItem);
	}

}
