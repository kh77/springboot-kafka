package com.sm.entity;

public class MagicNumber {

	private int number;

	public MagicNumber() {

	}

	public MagicNumber(int number) {
		super();
		this.number = number;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "SimpleNumber [number=" + number + "]";
	}

}
