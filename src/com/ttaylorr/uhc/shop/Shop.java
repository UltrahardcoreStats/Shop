package com.ttaylorr.uhc.shop;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import com.ttaylorr.uhc.shop.commands.ShopCommand;
import com.ttaylorr.uhc.shop.listeners.PlayerDeathListener;
import com.ttaylorr.uhc.shop.listeners.PlayerScoreboardListener;
import com.ttaylorr.uhc.shop.listeners.ShopBuyListener;
import com.ttaylorr.uhc.shop.util.ShopUtil;

public class Shop extends JavaPlugin {

	private static HashMap<Material, HashMap<String, Integer>> items;
	private static Material currency;
	private static int amountPerDeath;
	private static Scoreboard board;
	private static Objective objective;
	private static boolean sidebarEnabled;

	@Override
	public void onDisable() {

	}

	@Override
	public void onEnable() {
		items = new HashMap<Material, HashMap<String, Integer>>();

		saveDefaultConfig();

		reloadConfigCommand();

		Bukkit.getPluginManager().registerEvents(new ShopBuyListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerScoreboardListener(), this);

		getCommand("shop").setExecutor(new ShopCommand(this));
	}

	public boolean reloadConfigCommand() {
		try {
			loadItemValues();
			currency = Material.getMaterial(getConfig().getString("currency"));
			sidebarEnabled = getConfig().getBoolean("sidebar");
	
			setupScoreboard();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	private void setupScoreboard() {
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		board = manager.getNewScoreboard();

		objective = board.registerNewObjective("Coins", "dummy");
		objective.setDisplayName(ChatColor.BLUE + "" + ChatColor.BOLD + "Nuggets");
		
		if(sidebarEnabled) {			
			objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		} else {
			objective.setDisplaySlot(null);
		}

		for (Player p : Bukkit.getOnlinePlayers()) {
			Score score = objective.getScore(p);
			score.setScore(ShopUtil.getCurrencyFor(p));
			p.setScoreboard(board);
		}
	}

	private void loadItemValues() {
		FileConfiguration config = getConfig();

		for (Material m : Material.values()) {
			if (config.contains("items." + m.name())) {
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

	public static int getAmountPerDeath() {
		return amountPerDeath;
	}

	public static Scoreboard getScoreboard() {
		return board;
	}

	public static Objective getObjective() {
		return objective;
	}

	public static boolean getSidebarEnabled() {
		return sidebarEnabled;
	}
}
