package com.ttaylorr.uhc.shop.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Score;

import com.ttaylorr.uhc.shop.Shop;
import com.ttaylorr.uhc.shop.events.PlayerRecieveCoinEvent;

public class PlayerDeathListener implements Listener {

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		Player killer = event.getEntity().getKiller();
		
		player.getInventory().addItem(new ItemStack(Shop.getCurrency(), Shop.getAmountPerDeath()));
		
		Score score = Shop.getObjective().getScore(player);
		score.setScore(score.getScore() + 1);
		
		Bukkit.getServer().getPluginManager().callEvent(new PlayerRecieveCoinEvent(player, killer));
		
		
	}

}
