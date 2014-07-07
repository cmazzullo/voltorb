// TODO handle in-lobby disconnection
package com.example.server;

import com.example.statesofmatter.Monster;

public class FakeLobby extends Thread {
	
	private FakeServerProtocol fsp;
	
	private int lobbyNum;
	
	private UserThread[] players = new UserThread[2];

	private boolean battleStarted;
	private boolean gameOver;
	private boolean turnsReady;
	private boolean turnProcessed;
	private Monster[] returnArray;
	
	private Object lock = new Object();
	
	public FakeLobby(int lobbyNum) {
		this.lobbyNum = lobbyNum;
		fsp = new FakeServerProtocol(lobbyNum);
	}
	
	public void addPlayer(UserThread player) {
		for (int i = 0; i < 2; i++) {
			if (players[i] == null) {
				player.setNum(i);
				players[i] = player;
				System.out.println(i);
				break;
			}
		}
		fsp.incConnection();
		fsp.manageStartup();
	}

	public int getLobbyNum() {
		return lobbyNum;
	}
	
	public UserThread[] getUserThread() {
		return players;
	}
	
	public FakeServerProtocol getProtocol() {
		return fsp;
	}
	
	public Object getLock() {
		return lock;
	}
	
	public boolean getBattleStarted() {
		return battleStarted;
	}
	
	public boolean getTurnProcessed() {
		return turnProcessed;
	}
	
	public Monster[] getReturnArray() {
		return returnArray;
	}
	
	@Override
	public void run() {
		while (!battleStarted) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {}
			if (fsp.manageStartup() == true) {
				battleStarted = true;
				synchronized (lock) {
					lock.notifyAll();
				}
			}
		}
		//TODO look into event queues so this can be synced with both UserThreads without messing up
		// ie receiving both notify messages before second lock
		while (!gameOver) {
			System.out.println("starting round");
			turnsReady = false;
			turnProcessed = false;
			do {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				returnArray = fsp.runBattle();
				if (fsp.getTurnsReady() == 2) {
					turnsReady = true;
				}
			} while (!turnsReady);
			
			returnArray = fsp.runBattle();
			fsp.resetTurns();
			turnProcessed = true;

			synchronized (lock) {
				lock.notifyAll();
			}
		}
	}

}
