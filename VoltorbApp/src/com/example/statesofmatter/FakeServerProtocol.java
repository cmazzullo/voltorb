package com.example.statesofmatter;

public class FakeServerProtocol {
	
	private final int NOT_FULL = 0;
	private final int LOBBY_FULL = 1;
	private final int LOBBY_READY = 2;
	private final int ONE_BATTLING = 3;
	private final int BATTLE_STARTED = 4;
	
	private int gameState = 0;
	private int connections = 0;
	private int playersReady = 0;
	
	public void incConnection() {
		connections++;
	}
	
	public void decConnection() {
		connections--;
	}
	
	public void incReady() {
		playersReady++;
	}
	
	public void decReady() {
		playersReady--;
	}
	
	public boolean manageStartup() {
		if (gameState == NOT_FULL) {
			if (connections == 2 && playersReady < 2)
				gameState = 1;
			else if (connections == 2 && playersReady == 2)
				gameState = 2;
		} else if (gameState == LOBBY_FULL) {
			if (connections < 2)
				gameState = 0;
			else if (playersReady == 2)
				gameState = 2;
		} else if (gameState == LOBBY_READY) {
			if (connections < 2)
				gameState = 0;
			else if (playersReady < 2)
				gameState = 1;
			else {
				gameState = 3;
				return true;
			}
		} else if (gameState == ONE_BATTLING) {
			gameState = 4;
			return true;
		}
		return false;
	}
	
	public boolean processTie(String s) {
		if (s.equals("tied"))
			return true;
		else
			return false;
	}	
}
