package com.example.statesofmatter;

public enum State {
	SOLID(0), LIQUID(1), GAS(2), PLASMA(3);
	
	private final int value;
	
	private State (int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}
