package com.ttaylorr.uhc.shop;

import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ShopCommand implements CommandExecutor {

	private Shop plugin;

	public ShopCommand(Shop plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) return false;

		if (args[0].equalsIgnoreCase("reload")) {
			plugin.reloadConfig();
			
			boolean state = plugin.reloadConfigCommand();
			String stateMessage = state ? ChatColor.GREEN + "succesfully" : ChatColor.RED + "unsuccesfully";
			
			sender.sendMessage(ChatColor.AQUA + "[Shop] - " + stateMessage + " reloaded the config file!");
			
			return true;
		} else if(args[0].equalsIgnoreCase("check") || args[0].equalsIgnoreCase("status")) {
			Material m = Material.getMaterial(args[1].toUpperCase());
			
			if(Shop.getValidItems().containsKey(m)) {
				sender.sendMessage(ChatColor.AQUA + "[Shop] - " + ChatColor.GREEN + "does " + ChatColor.RED + "contain " + ChatColor.GREEN + m.name());
				sender.sendMessage(ChatColor.AQUA + "[Shop] - " + ChatColor.RED + "Cost: " + ChatColor.GREEN + Shop.getValidItems().get(m).get("cost"));
				sender.sendMessage(ChatColor.AQUA + "[Shop] - " + ChatColor.RED + "Quantity: " + ChatColor.GREEN + Shop.getValidItems().get(m).get("quantity"));
				sender.sendMessage(ChatColor.AQUA + "[Shop] - " + ChatColor.RED + "Multiple: " + ChatColor.GREEN + (Shop.getValidItems().get(m).get("multipleAllowed") == 1));
			} else {
				sender.sendMessage(ChatColor.AQUA + "[Shop] - " + ChatColor.RED + "does not " + ChatColor.RED + "contain " + ChatColor.GREEN +
						WordUtils.capitalizeFully(m.name().replace('_', ' ').toLowerCase()));
			}
			
			return true;
			
		}
		return false;
	}

}
