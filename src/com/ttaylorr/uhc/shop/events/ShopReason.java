package com.ttaylorr.uhc.shop.events;

public enum ShopReason {

	OUT_OF_MONEY("Player does not have enough money..."), INVALID_ITEM("Player may not purchase this item..."), ALREADY_HAS("Player already has this item...");

	private String desc;

	private ShopReason(String desc) {
		this.desc = desc;
	}

	public String getDescription() {
		return this.desc;
	}

}
