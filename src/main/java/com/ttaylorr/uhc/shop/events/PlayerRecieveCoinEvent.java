package com.ttaylorr.uhc.shop.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.ttaylorr.uhc.shop.Shop;

public class PlayerRecieveCoinEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	
	private Player recipient;
	private Player death;
	private Material currency;
	private int amount;
	
	public PlayerRecieveCoinEvent(Player recipient, Player death, Material currency, int amount) {
		this.recipient = recipient;
		this.death = death;
		this.currency = currency;
		this.amount = amount;
	}
	
	public PlayerRecieveCoinEvent(Player recipient, Player death) {
		this.recipient = recipient; 
		this.death = death;
		this.currency = Shop.getCurrency();
		this.amount = Shop.getAmountPerDeath();
	}
	
	public Player getRecipient() {
		return recipient;
	}

	public Player getDeath() {
		return death;
	}

	public Material getCurrency() {
		return currency;
	}

	public int getAmount() {
		return amount;
	}

	public HandlerList getHandlers() {
	    return handlers;
	}
	 
	public static HandlerList getHandlerList() {
	    return handlers;
	}
	
}
