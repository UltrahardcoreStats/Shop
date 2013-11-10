package com.ttaylorr.uhc.shop.listeners;

import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class ShopBuyListener implements Listener {

	@EventHandler
	public void onPlayerBuy(PlayerInteractEntityEvent event) {

		// Make sure it's an ItemFrame
		if (event.getRightClicked() instanceof ItemFrame) {
			event.setCancelled(true);
		} else {
			return;
		}

		// Define the Item Frame
		ItemFrame frame = (ItemFrame) event.getRightClicked();

		// Make sure that the player has the permission to buy it
		if (!(event.getPlayer().hasPermission("shop.buy"))) return;

		// Make sure that it has an item in it
		if (frame.getItem() == null) return;

		// Define variables
		Player player = event.getPlayer();
		ItemStack item = frame.getItem();

		// Make sure that the player doesn't already have the item they are
		// trying to buy
		if (player.getInventory().contains(item)) {
			player.sendMessage(ChatColor.AQUA + "[Shop] - " + ChatColor.RED + "you already have this item!");
			return;

		}
		// Make sure that the player has golden nuggets
		if (!(player.getInventory().contains(Material.GOLD_NUGGET))) {
			player.sendMessage(ChatColor.AQUA + "[Shop] - " + ChatColor.RED + "you don't have enough nuggets to do this!");
			return;
		}

		// Make the transaction
		player.getInventory().addItem(new ItemStack(item.getType(), 1));
		player.getInventory().removeItem(new ItemStack(Material.GOLD_NUGGET, 1));

		player.sendMessage(ChatColor.AQUA + "[Shop] - " + ChatColor.GREEN + "you bought an " + ChatColor.RED +
				WordUtils.capitalize(item.getType().name().replace('_', ' ').toLowerCase()) + ChatColor.GREEN + " for one nugget!");

	}

}
