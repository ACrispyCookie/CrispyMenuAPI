package me.acrispycookie.crispyinvlib.utilities.itemstack;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

public class SkullItemBuilder extends ItemBuilder {

	UUID uuid;
	String url;
	
	
	public SkullItemBuilder() {
	}
	
	public SkullItemBuilder url(String url) {
		this.url = url;
		return this;
	}
	
	public SkullItemBuilder playerUuid(UUID uuid) {
		this.uuid = uuid;
		return this;
	}

	public ItemStack build() {
		ItemStack i;
		SkullMeta meta = null;
		if(url != null) {
			i = getSkull(url);
			meta = (SkullMeta) i.getItemMeta();
		}
		else {
			i = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
			meta = (SkullMeta) i.getItemMeta();
			if(Bukkit.getPlayer(uuid) != null) {
				meta.setOwner(Bukkit.getPlayer(uuid).getName());
			}
			else {
		        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
		        profile.getProperties().put("textures", new Property("textures", new String(getBase64(uuid))));
		        Field profileField = null;
		        try {
		            profileField = meta.getClass().getDeclaredField("profile");
		        } catch (NoSuchFieldException | SecurityException e) {
		            e.printStackTrace();
		        }
		        profileField.setAccessible(true);
		        try {
		            profileField.set(meta, profile);
		        } catch (IllegalArgumentException | IllegalAccessException e) {
		            e.printStackTrace();
		        }
			}
		}
		meta.setDisplayName(name);
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
		nmsItem.setTag(tag);
		ItemStack finalItem = CraftItemStack.asBukkitCopy(nmsItem);
		finalItem.setAmount(amount);
		return finalItem;
	}
	
	private String getBase64(UUID uuid) {
		Gson g = new Gson();
		String signature = getURLContent("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid.toString());
		JsonObject obj = g.fromJson(signature, JsonObject.class);
		return obj.getAsJsonArray("properties").get(0).getAsJsonObject().get("value").getAsString();
	}
	
	private String getURLContent(String urlStr) {
        URL url;
        BufferedReader in = null;
        StringBuilder sb = new StringBuilder();
        try{
            url = new URL(urlStr);
            in = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8) );
            String str;
            while((str = in.readLine()) != null) {
                sb.append( str );
            }
        } catch (Exception ignored) { }
        finally{
            try{
                if(in!=null) {
                    in.close();
                }
            }catch(IOException ignored) { }
        }
        return sb.toString();
    }

	private static ItemStack getSkull(String url) {
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		if (url == null || url.isEmpty())
			return skull;
		SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
		GameProfile profile = new GameProfile(UUID.randomUUID(), null);
		byte[] encodedData = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
		profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
		Field profileField = null;
		try {
			profileField = skullMeta.getClass().getDeclaredField("profile");
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		profileField.setAccessible(true);
		try {
			profileField.set(skullMeta, profile);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		skull.setItemMeta(skullMeta);
		return skull;
	}

}
