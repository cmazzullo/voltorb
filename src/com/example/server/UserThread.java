/**A server thread
 * Talks with client over socket using object streams
 * Implements FakeLobbyProtocol for game logic and managing game state flow
 */
package com.example.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.example.statesofmatter.Monster;
import com.example.statesofmatter.Player;
import com.example.statesofmatter.Turn;
import com.example.statesofmatter.TurnReturn;

public class UserThread extends Thread {
	private static Socket playerSocket;
	
	private ObjectOutputStream output;
	private ObjectInputStream input;
	
	private int playerNum = -1;
	private boolean inLobby;
	private int lobbyNum = -1;
	private Player player;
	private Monster oppLead;
	private Turn currentTurn;
	
	private boolean playerReady = false;
	private boolean gameOver = false;

	private Object userLock = new Object();
	
	public UserThread(ObjectOutputStream out, ObjectInputStream in) {
		this.output = out;
		this.input = in;
		this.inLobby = false;
	}
	
	public int getNum() {
		return playerNum;
	}
	
	public void setNum(int newNum) {
		playerNum = newNum;
	}
	
	public boolean getInLobby() {
		return inLobby;
	}
	
	public void setInLobby(boolean inLobby) {
		this.inLobby = inLobby;
	}
	
	public int getLobbyNum() {
		return lobbyNum;
	}
	
	public void setLobbyNum(int lobbyNum) {
		this.lobbyNum = lobbyNum;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Turn getTurn() {
		return currentTurn;
	}
	
	public Object getUserLock() {
		return userLock;
	}
	
	public void run() {
		Object o;
		try {
			do {
				synchronized (userLock) {
					userLock.wait();
				}
			} while (!inLobby);
			
			System.out.println("num is " + playerNum + " lobby num is " + lobbyNum + " inLobby is " + inLobby);
			output.writeUnshared(playerNum);
			while (!playerReady) {
				if ((o = input.readUnshared()) != null && (Boolean)o == true) {
					player = (Player)input.readUnshared();
					playerReady = true;
					FakeServer.getLobby(lobbyNum).getProtocol().incReady();
					System.out.println("I'm ready");	
				}
			}

			while (!FakeServer.getLobby(lobbyNum).getBattleStarted()) {
				synchronized (FakeServer.getLobby(lobbyNum).getLock()) {
					try {
						FakeServer.getLobby(lobbyNum).getLock().wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

			if (playerNum == 0) {
				if (FakeServer.getLobby(lobbyNum).getUserThread()[1] != null &&
					FakeServer.getLobby(lobbyNum).getUserThread()[1].getPlayer() != null)
					oppLead = FakeServer.getLobby(lobbyNum).getUserThread()[1].getPlayer().getLead();
			} else {
				if (FakeServer.getLobby(lobbyNum).getUserThread()[0] != null &&
					FakeServer.getLobby(lobbyNum).getUserThread()[0].getPlayer() != null)
					oppLead = FakeServer.getLobby(lobbyNum).getUserThread()[0].getPlayer().getLead();
			}
			
			output.writeUnshared((Boolean)FakeServer.getLobby(lobbyNum).getBattleStarted());
			output.writeUnshared((Monster)oppLead);
			System.out.println("Print me when everyone's ready to battle!");
			currentTurn = new Turn();
			TurnReturn returnData;
			
			while (!gameOver) {
				/*o = input.readUnshared(); //non-fainted thread will block on this in case of monster fainting because it will get no input
				if ((Turn)o != null) {
					currentTurn = (Turn)o;
					FakeServer.getLobby(lobbyNum).getProtocol().incTurns();

					while (!FakeServer.getLobby(lobbyNum).getTurnProcessed()) {
						synchronized (FakeServer.getLobby(lobbyNum).getLock()) {
							try {
								//TODO condition for if lobby returns mid-turn TurnReturn
								if (FakeServer.getLobby(lobbyNum).getTurnReturn() != null &&
									FakeServer.getLobby(lobbyNum).getTurnReturn().getTurnFinished() == 1) {
									returnData = FakeServer.getLobby(lobbyNum).getTurnReturn();
									if (playerNum == 0) {
										player.setLead(returnData.getLeads()[0]);
										oppLead = returnData.getLeads()[1];
										output.writeUnshared(returnData);
									} else if (playerNum == 1) {
										player.setLead(returnData.getLeads()[1]);
										oppLead = returnData.getLeads()[0];
										output.writeUnshared(returnData);
									}
									o = input.readUnshared();
									if ((Turn)o != null) {
										currentTurn = (Turn)o;
										FakeServer.getLobby(lobbyNum).getProtocol().incTurns();
									}
								}
								FakeServer.getLobby(lobbyNum).getLock().wait();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
					returnData = FakeServer.getLobby(lobbyNum).getTurnReturn();
					currentTurn = null;
					if (playerNum == 0) {
						player.setLead(returnData.getLeads()[0]);
						oppLead = returnData.getLeads()[1];
						output.writeUnshared(returnData);
					} else if (playerNum == 1) {
						returnData.setLeads(new Monster[] { returnData.getLeads()[1], returnData.getLeads()[0] });
						player.setLead(returnData.getLeads()[0]);
						oppLead = returnData.getLeads()[1];
						output.writeUnshared(returnData);
					}
				}*/
				if ((Turn)(o = input.readUnshared()) != null)
					currentTurn = (Turn)o;
				FakeServer.getLobby(lobbyNum).getProtocol().incTurns();
				System.out.println("Player " + (playerNum + 1) + " has made their move.");
				do {
					synchronized (FakeServer.getLobby(lobbyNum).getLock()) {
						try {
						FakeServer.getLobby(lobbyNum).getLock().wait();
						} catch (InterruptedException e) {	}
					}
				} while (!FakeServer.getLobby(lobbyNum).getTurnProcessed());
				
				returnData = FakeServer.getLobby(lobbyNum).getTurnReturn();
				
				if (playerNum == 0) {
					player.setLead(returnData.getLeads()[0]);
					oppLead = returnData.getLeads()[1];
				} else {
					player.setLead(returnData.getLeads()[1]);
					oppLead = returnData.getLeads()[0];
				}
				output.writeUnshared(returnData);
				System.out.println(player.getLead().getHP() + " : " + oppLead.getHP());
				output.reset();
					//System.out.println("returning for player 1");
				/*} else if (playerNum == 1) {
					returnData.setLeads(new Monster[] { returnData.getLeads()[1], returnData.getLeads()[0] });
					player.setLead(returnData.getLeads()[0]);
					oppLead = returnData.getLeads()[1];
					output.writeUnshared(returnData);
					System.out.println("returning for player 2");
					for (int i = 0; i < 2; i++) {
						System.out.println(returnData.getLeads()[i].getHP());
					}
				}*/
			}
		} catch (IOException e) {
			System.err.println("Player disconnected");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			try {
				//if (playerReady && lobbyNum != -1);
					//FakeServer.getLobby(lobbyNum).getProtocol().decReady();
				//FakeServer.getLobby(lobbyNum).getProtocol().decConnection();
				//FakeServer.getLobby(lobbyNum).getProtocol().manageStartup();
				if (output != null)
					output.close();
				if (input != null)
					input.close();
				if (playerSocket != null)
					playerSocket.close();
				if (lobbyNum != -1) {
					FakeServer.getLobby(lobbyNum).getUserThread()[playerNum] = null;
					FakeServer.getLobby(lobbyNum).interrupt();
				}
				FakeServer.decConnNum();
				FakeServer.connUsers.remove(this);
			} catch (IOException e) { }
		}
	}
}
