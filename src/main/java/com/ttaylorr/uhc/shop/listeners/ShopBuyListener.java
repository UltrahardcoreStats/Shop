package com.ttaylorr.uhc.shop.listeners;

import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.Potion;
import org.bukkit.scoreboard.Score;

import com.ttaylorr.uhc.shop.Shop;
import com.ttaylorr.uhc.shop.util.ShopUtil;

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

		if(frame.getItem().getType() == Material.AIR) {
			event.setCancelled(false);
			return;
		}
		
		// Make sure that the player has the permission to buy it
		if (!(event.getPlayer().hasPermission("shop.buy"))) return;

		// Make sure that it has an item in it
		if (frame.getItem() == null) return;

		// Define variables
		Player player = event.getPlayer();
		ItemStack item = frame.getItem();
		ItemStack toGive = frame.getItem();
		
		//hacky
		ItemMeta m = toGive.getItemMeta();
		m.setDisplayName(frame.getItem().getItemMeta().getDisplayName());
		m.setLore(frame.getItem().getItemMeta().getLore());
		
		for(Enchantment e: frame.getItem().getItemMeta().getEnchants().keySet()) {
			m.addEnchant(e, item.getEnchantmentLevel(e), true);
		}
				
		// Make sure that the user can buy the item
		if(!(Shop.getValidItems().containsKey(item.getType()))) {
			player.sendMessage(ChatColor.AQUA + "[Shop] - " + ChatColor.RED + "you may not buy this item!");
			return;
		}
		
		int cost = Shop.getValidItems().get(item.getType()).get("cost");
		int quantity = Shop.getValidItems().get(item.getType()).get("quantity");
		boolean multipleAllowed = Shop.getValidItems().get(item.getType()).get("multipleAllowed") == 1;
		String shortCurrency = Shop.getCurrency().name().replace('_', ' ');
		shortCurrency = shortCurrency.substring(shortCurrency.indexOf(" ") + 1).toLowerCase();
		
		// Make sure that the player has golden nuggets
		if (!(player.getInventory().containsAtLeast(new ItemStack(Shop.getCurrency()),cost))) {
			player.sendMessage(ChatColor.AQUA + "[Shop] - " + ChatColor.RED + "you need at least " + ChatColor.GREEN + cost + 
					(cost == 1 ? " " + shortCurrency : " " + shortCurrency + "s") + ChatColor.RED + " to purchase this!");
			return;
		}
	
		// Make sure that the player doesn't already have the item they are
		// trying to buy
		
		if(!multipleAllowed) {
			item.setItemMeta(m);
			
			if (player.getInventory().contains(item)) {
				player.sendMessage(ChatColor.AQUA + "[Shop] - " + ChatColor.RED + "you already have this item!");
				return;

			}			
		}

		// Make the transaction
		if(item.getType() != Material.POTION) {
			ItemStack i = new ItemStack(item.getType(), quantity);
			i.setItemMeta(m);
			
			player.getInventory().addItem(i);
		} else {
            for (int i = 0; i < quantity; i++) {
                player.getInventory().addItem(item);
            }
		}
		player.getInventory().removeItem(new ItemStack(Shop.getCurrency(), cost));

		String name = WordUtils.capitalize(item.getType().name().replace('_', ' ').toLowerCase());
		
		player.sendMessage(ChatColor.AQUA + "[Shop] - " + ChatColor.GREEN + "you bought " + ChatColor.RED + quantity + " " +
				(quantity == 1 ? name : name + "s") + ChatColor.GREEN + " for " + ChatColor.RED + cost +
				(cost == 1 ? " " + shortCurrency : " " + shortCurrency + "s"));

		Score score = Shop.getObjective().getScore(player);
        int amount = ShopUtil.getCurrencyFor(player);

        if (amount != 0) {
            score.setScore(amount);
        } else {
            Shop.getScoreboard().resetScores(player);
        }

		player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0f, 1.0f);
		
	}

}
