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
	
	private int playerNum;
	private Player player;
	private Monster oppLead;
	private Turn currentTurn;
	
	private boolean playerReady = false;
	private boolean battleStarted = false;
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
		try {
			FakeServer.fsp.incConnection();
			FakeServer.fsp.manageStartup();
			while (!playerReady) {
				if ((o = input.readObject()) != null && (Boolean)o == true) {
					player = (Player)input.readObject();
					playerReady = true;
					FakeServer.fsp.incReady();
					System.out.println("I'm ready");	
				}
			}

			while (!battleStarted) {
				Thread.sleep(1000);
				boolean starting = FakeServer.fsp.manageStartup();
				if (starting == true) {
					if (playerNum == 0) {
						if (FakeServer.users[1] != null &&
							FakeServer.users[1].getPlayer() != null)
							oppLead = FakeServer.users[1].getPlayer().getLead();
					} else {
						if (FakeServer.users[0] != null &&
							FakeServer.users[0].getPlayer() != null)
							oppLead = FakeServer.users[0].getPlayer().getLead();
					}
					output.writeObject((Boolean)starting);
					output.writeObject((Monster)oppLead);
					battleStarted = true;
				}
			}
			
			System.out.println("Print me when everyone's ready to battle!");
			boolean turnProcessing;
			Monster[] returnArray;
			
			while (!gameOver) {
				if ((Turn)(o = input.readObject()) != null) {
					System.out.println("got a turn");
					currentTurn = (Turn)o;
					turnProcessing = true;
					FakeServer.fsp.incTurns();
					 do {
						returnArray = FakeServer.fsp.runBattle(playerNum);
						if (returnArray != null) {
							player.setLead(returnArray[0]);
							oppLead = returnArray[1];
							System.out.println(returnArray[0].getName() + " : " + returnArray[1].getName());
							output.writeObject(returnArray);
							turnProcessing = false;
						}
					} while (turnProcessing);
				}	
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
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
