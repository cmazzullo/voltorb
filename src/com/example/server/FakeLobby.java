// TODO handle in-lobby disconnection
package com.example.server;

import com.example.statesofmatter.Monster;
import com.example.statesofmatter.TurnReturn;

public class FakeLobby extends Thread {
	
	private FakeServerProtocol fsp;
	
	private int lobbyNum;
	
	private UserThread[] players = new UserThread[2];

	private boolean battleStarted;
	private boolean gameOver;
	private boolean turnsReady;
	private boolean turnProcessed;
	private TurnReturn returnData;
	
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
	
	public TurnReturn getTurnReturn() {
		return returnData;
	}
	
	@Override
	public void run() {
		try {
			while (!battleStarted) {
				if (isInterrupted())
					throw new InterruptedException();
				Thread.sleep(200); // TODO try lock here, but see below todo
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
				if (isInterrupted())
					throw new InterruptedException();
				System.out.println("starting round");
				turnsReady = false;
				turnProcessed = false;
				do {
					Thread.sleep(200); //TODO try another lock here, but see above todo
					fsp.runBattle();
					if (fsp.getTurnsReady() == 2) {
						turnsReady = true;
					}
				} while (!turnsReady);
			
				returnData = fsp.runBattle();
				fsp.resetTurns();
				turnProcessed = true;
				System.out.println(returnData);
				synchronized (lock) {
					lock.notifyAll();
				}
			}
		} catch (InterruptedException e) {
		} catch (Exception e) {
			// TODO super-secret exception for invalid move
			e.printStackTrace();
		} finally { // TODO figure out how to make sure this code only occurs when external interruption occurs, not from Thread.sleep()
			for (UserThread ut : players) {
				if (ut != null) {
					ut.setLobbyNum(-1);
					ut.setInLobby(false);
				}
			}
		}
	}
}