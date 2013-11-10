package com.ttaylorr.uhc.shop;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.ttaylorr.uhc.shop.listeners.ShopBuyListener;

public class Shop extends JavaPlugin {

	@Override
	public void onDisable() {
		
	}
	
	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(new ShopBuyListener(), this);
	}
	
}
