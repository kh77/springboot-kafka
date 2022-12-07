package com.sm.entity;

public class Invoice {

	private String invoiceNumber;
	private int amount;
	private String currency;

	public Invoice() {

	}

	public Invoice(String invoiceNumber, int amount, String currency) {
		super();
		this.invoiceNumber = invoiceNumber;
		this.amount = amount;
		this.currency = currency;
	}

	public int getAmount() {
		return amount;
	}

	public String getCurrency() {
		return currency;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	@Override
	public String toString() {
		return "Invoice [invoiceNumber=" + invoiceNumber + ", amount=" + amount + ", currency=" + currency + "]";
	}

}
