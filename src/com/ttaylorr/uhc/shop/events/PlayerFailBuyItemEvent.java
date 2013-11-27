package com.ttaylorr.uhc.shop.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerFailBuyItemEvent extends Event {
	private static final HandlerList handlers = new HandlerList();

	// TODO implementation

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
