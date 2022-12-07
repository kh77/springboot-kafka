package com.sm.entity;

public class Order {

	private int amount;
	private String itemName;

	public Order() {
	}

	public Order(int amount, String itemName) {
		super();
		this.amount = amount;
		this.itemName = itemName;
	}

	public int getAmount() {
		return amount;
	}

	public String getItemName() {
		return itemName;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Override
	public String toString() {
		return "FoodOrder [amount=" + amount + ", item=" + itemName + "]";
	}

}
