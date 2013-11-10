package com.ttaylorr.uhc.shop;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.ttaylorr.uhc.shop.listeners.ShopBuyListener;

public class Shop extends JavaPlugin {
	
	private static HashMap<Material, HashMap<String, Integer>> items;
	private static Material currency;
	
	@Override
	public void onDisable() {
		
	}
	
	@Override
	public void onEnable() {
		items = new HashMap<Material, HashMap<String, Integer>>();
		
		saveDefaultConfig();
		
		loadItemValues();
		
		currency = Material.getMaterial(getConfig().getString("currency"));
		System.out.println("Currency: " + currency.name());
		
		Bukkit.getPluginManager().registerEvents(new ShopBuyListener(), this);
	}
	
	private void loadItemValues() {
		FileConfiguration config = getConfig(); 
		
		for(Material m : Material.values()) {
			if(config.contains("items." + m.name())) {
				HashMap<String, Integer> map = new HashMap<String, Integer>();

				map.put("cost", config.getInt("items." + m.name() + ".cost"));
				map.put("quantity", config.getInt("items." + m.name() + ".quantity"));
				map.put("multipleAllowed", config.getBoolean("items." + m.name() + ".multipleAllowed") == true ? 1 : 0);
				
				items.put(m, map);
			}
		}
	}
	
	public static HashMap<Material, HashMap<String, Integer>> getValidItems() {
		return items;
	}
	
	public static Material getCurrency() {
		return currency;
	}
	
}
