/**A server thread
 * Talks with client over socket using object streams
 * Implements FakeServerProtocol for game logic and managing game state flow
 */
package com.example.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.example.statesofmatter.Monster;
import com.example.statesofmatter.Player;
import com.example.statesofmatter.Turn;

public class UserThread extends Thread {
	
	private static Socket playerSocket;
	
	private ObjectOutputStream output;
	private ObjectInputStream input;
	
	public int playerNum;
	private Player player;
	private Monster oppLead;
	private Turn currentTurn;
	
	private boolean playerReady = false;
	private boolean gameOver = false;
	
	public UserThread(ObjectOutputStream out, ObjectInputStream in, 
					  int playerNum) {
		this.output = out;
		this.input = in;
		this.playerNum = playerNum;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Turn getTurn() {
		return currentTurn;
	}
	
	public void run() {
		Object o;
		System.out.println(playerNum);
		try {
			while (!playerReady) {
				if ((o = input.readUnshared()) != null && (Boolean)o == true) {
					player = (Player)input.readUnshared();
					playerReady = true;
					FakeServer.fsp.incReady();
					System.out.println("I'm ready");	
				}
			}

			while (!FakeServer.getBattleStarted()) {
				synchronized (FakeServer.lock) {
					try {
						FakeServer.lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			
			if (playerNum == 0) {
				if (FakeServer.users[1] != null &&
					FakeServer.users[1].getPlayer() != null)
					oppLead = FakeServer.users[1].getPlayer().getLead();
			} else {
				if (FakeServer.users[0] != null &&
					FakeServer.users[0].getPlayer() != null)
					oppLead = FakeServer.users[0].getPlayer().getLead();
			}
			
			output.writeUnshared((Boolean)FakeServer.getBattleStarted());
			output.writeUnshared((Monster)oppLead);
			System.out.println("Print me when everyone's ready to battle!");
			Monster[] returnArray;
			
			while (!gameOver) {
				o = input.readUnshared();
				if ((Turn)o != null) {
					currentTurn = (Turn)o;
					FakeServer.fsp.incTurns();

					while (!FakeServer.getTurnProcessed()) {
						synchronized (FakeServer.lock) {
							try {
								FakeServer.lock.wait();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
					returnArray = FakeServer.getReturnArray();
					currentTurn = null;
					if (playerNum == 1) {
						player.setLead(returnArray[0]);
						output.writeUnshared(player.getLead());
						oppLead = returnArray[1];
						output.writeUnshared(oppLead);
					} else if (playerNum == 0) {
						player.setLead(returnArray[0]);
						output.writeUnshared(player.getLead());
						oppLead = returnArray[1];
						output.writeUnshared(oppLead);
					}	
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (playerReady)
					FakeServer.fsp.decReady();
				FakeServer.fsp.decConnection();
				FakeServer.fsp.manageStartup();
				if (output != null)
					output.close();
				if (input != null)
					input.close();
				FakeServer.users[playerNum] = null;
				if (playerSocket != null)
					playerSocket.close();
				System.out.println("Player disconnected");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
