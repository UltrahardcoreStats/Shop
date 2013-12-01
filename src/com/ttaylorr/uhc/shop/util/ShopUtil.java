package com.ttaylorr.uhc.shop.util;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Score;

import com.ttaylorr.uhc.shop.Shop;

public class ShopUtil {

	public static int getCurrencyFor(Player player) {
		Material currency = Shop.getCurrency();

		int amount = 0;

		for (ItemStack is : player.getInventory().getContents()) {
			if (is == null) continue;

			if (is.getType() == currency) {
				amount += is.getAmount();
			}
		}

		return amount;
	}

	public static void updateAllScores(List<Player> players) {
		updateAllScores((Player[]) players.toArray());
	}

	public static void updateAllScores(Player[] players) {
		for (Player p : players) {
			Score score = Shop.getObjective().getScore(p);

			int amount = getCurrencyFor(p);

			if (amount != 0) {
				score.setScore(amount);
			} else {
				Shop.getScoreboard().resetScores(p);
			}
		}
	}
}
