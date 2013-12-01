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
import com.ttaylorr.uhc.shop.util.ShopUtil;

public class PlayerDeathListener implements Listener {

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		if (!(event.getEntity().getKiller() instanceof Player)) return;

		Player player = event.getEntity();
		Player killer = event.getEntity().getKiller();

		if (player == null && killer == null) return;

		if (Shop.getAddToInventory()) {
			killer.getInventory().addItem(new ItemStack(Shop.getCurrency(), Shop.getAmountPerDeath()));
		} else {
			event.getEntity().getKiller().getWorld().dropItem(event.getEntity().getLocation(), new ItemStack(Shop.getCurrency(), Shop.getAmountPerDeath()));
		}

		Score score = Shop.getObjective().getScore(killer);
		
		int amount = ShopUtil.getCurrencyFor(killer);
		
		if(amount != 0) {
			score.setScore(amount);			
		} else {
			Shop.getScoreboard().resetScores(killer);
		}

		Bukkit.getServer().getPluginManager().callEvent(new PlayerRecieveCoinEvent(player, killer));

	}

}
