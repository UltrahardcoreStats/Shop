package com.ttaylorr.uhc.shop.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Score;

import com.ttaylorr.uhc.shop.Shop;
import com.ttaylorr.uhc.shop.util.ShopUtil;

public class PlayerScoreboardListener implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Score score = Shop.getObjective().getScore(event.getPlayer());
		int amount = ShopUtil.getCurrencyFor(event.getPlayer());
		
		if (amount != 0) {
			score.setScore(amount);
		}

		event.getPlayer().setScoreboard(Shop.getScoreboard());
	}

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		Shop.getScoreboard().resetScores(event.getPlayer());
	}
	
	@EventHandler
	public void onPlayerKick(PlayerKickEvent event) {
		Shop.getScoreboard().resetScores(event.getPlayer());
	}

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked()) instanceof Player) {
            return;
        }

        Player player = (Player) event.getWhoClicked();

        Score score = Shop.getObjective().getScore(player);
        int amount = ShopUtil.getCurrencyFor(player);

        if (amount != 0) {
            score.setScore(amount);
        }

        event.getPlayer().setScoreboard(Shop.getScoreboard());
    }
}
