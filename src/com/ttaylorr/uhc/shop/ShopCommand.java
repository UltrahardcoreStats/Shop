package com.ttaylorr.uhc.shop;

import org.bukkit.ChatColor;
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
		if (args.length != 1) return false;

		if (args[0].equalsIgnoreCase("reload")) {
			plugin.reloadConfig();
			
			boolean state = plugin.reloadConfigCommand();
			String stateMessage = state ? ChatColor.GREEN + "succesfully" : ChatColor.RED + "unsuccesfully";
			
			sender.sendMessage(ChatColor.AQUA + "[Shop] - " + stateMessage + " reloaded the config file!");
			
			return true;
		}
		return false;
	}

}
