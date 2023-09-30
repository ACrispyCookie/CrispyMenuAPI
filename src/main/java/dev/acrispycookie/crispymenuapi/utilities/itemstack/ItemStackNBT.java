package dev.acrispycookie.crispymenuapi.utilities.itemstack;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class ItemStackNBT {
	
	public static ItemStack addTag(ItemStack i, String identifier, NBTBase value) {
		net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(i);
		NBTTagCompound tag = nmsItem.hasTag() ? nmsItem.getTag() : new NBTTagCompound();
		tag.set(identifier, value);
		nmsItem.setTag(tag);
		return CraftItemStack.asBukkitCopy(nmsItem);
	}
	
	public static NBTBase getTag(ItemStack i, String identifier) {
		net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(i);
		if(nmsItem.hasTag() && nmsItem.getTag().hasKey(identifier)) {
			return nmsItem.getTag().get(identifier);
		}
		return null;
	}
	
	public static boolean hasTag(ItemStack i, String identifier) {
		net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(i);
		if(nmsItem.hasTag()) {
			return nmsItem.getTag().hasKey(identifier);
		}
		return false;
	}
	
	public static int getInt(ItemStack i, String identifier) {
		net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(i);
		if(nmsItem.hasTag() && nmsItem.getTag().hasKey(identifier) && nmsItem.getTag().get(identifier).getTypeId() == 3) {
			return ((NBTTagInt) nmsItem.getTag().get(identifier)).d();
		}
		return 0;
	}
	
	public static double getDouble(ItemStack i, String identifier) {
		net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(i);
		if(nmsItem.hasTag() && nmsItem.getTag().hasKey(identifier) && nmsItem.getTag().get(identifier).getTypeId() == 6) {
			return ((NBTTagDouble) nmsItem.getTag().get(identifier)).g();
		}
		return 0;
	}
	
	public static long getLong(ItemStack i, String identifier) {
		net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(i);
		if(nmsItem.hasTag() && nmsItem.getTag().hasKey(identifier) && nmsItem.getTag().get(identifier).getTypeId() == 4) {
			return ((NBTTagLong) nmsItem.getTag().get(identifier)).c();
		}
		return 0;
	}
	
	public static boolean getBoolean(ItemStack i, String identifier) {
		net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(i);
		if(nmsItem.hasTag() && nmsItem.getTag().hasKey(identifier) && nmsItem.getTag().get(identifier).getTypeId() == 3) {
			return ((NBTTagInt) nmsItem.getTag().get(identifier)).d() == 1;
		}
		return false;
	}
	
	public static String getString(ItemStack i, String identifier) {
		net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(i);
		if(nmsItem.hasTag() && nmsItem.getTag().hasKey(identifier)) {
			return nmsItem.getTag().getString(identifier);
		}
		return null;
	}
	
	public static int getInt(NBTTagList tag, int index) {
		if(index < tag.size()) {
			return (tag.g(index).getTypeId() == 3 ? ((NBTTagInt) tag.g(index)).d() : 0);
		}
		return 0;
	}
	
	public static double getDouble(NBTTagList tag, int index) {
		if(index < tag.size()) {
			return (tag.g(index).getTypeId() == 6 ? ((NBTTagDouble) tag.g(index)).g() : 0);
		}
		return 0;
	}	
	
	public static long getLong(NBTTagList tag, int index) {
		if(index < tag.size()) {
			return (tag.g(index).getTypeId() == 4 ? ((NBTTagLong) tag.g(index)).c() : 0);
		}
		return 0;
	}
	
	public static boolean getBoolean(NBTTagList tag, int index) {
		if(index < tag.size()) {
			return (tag.g(index).getTypeId() == 3 && (((NBTTagInt) tag.g(index)).d() == 1));
		}
		return false;
	}
	
	public static String getString(NBTTagList tag, int index) {
		if(index < tag.size()) {
			return (tag.g(index).getTypeId() == 8 ? ((NBTTagString) tag.g(index)).a_() : null);
		}
		return null;
	}

}
