package com.ttaylorr.uhc.shop.util;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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

}
