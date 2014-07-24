/**
 * LocalGameRunner contains (will contain) the game logic for a client
 * playing States of Matter. It interfaces with the UserInterface on
 * the frontend and gets the other player's actions with Server on
 * the backend.
 */

package com.example.statesofmatter;

import java.io.*;
import java.net.*;
import java.util.Scanner;

import android.annotation.TargetApi;

@TargetApi(19)
public class LocalGameRunner implements Runnable {
	
	private boolean isReady = false;
	private boolean battleStarted = false;
	private boolean turnReady = false; //should be set to false once move selected and set back to true once turn is resolved
	private boolean gameOver = false;
	
	public Object lock = new Object();
	
	private Socket playerSocket;
	public static ObjectInputStream input;
	public static ObjectOutputStream output;
	
	private Database d;
	private String playerID;
	private String playerName;
	private int playerNum = -1;
	private Player player;
	private Monster oppLead;
	private PlayerAction action = PlayerAction.PASS;
	private int argument;
	private State turnState;
	private TurnReturn returnData = new TurnReturn();
	
	
	public Database getDbase() {
		return d;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public int getPlayerNum() {
		return playerNum;
	}
	
	public Monster getOppLead() {
		return oppLead;
	}
	
	public PlayerAction getAction() {
		return action;
	}
	
	public void setAction(PlayerAction action) {
		this.action = action;
	}
	
	public int getArgument() {
		return argument;
	}
	
	public void setArgument(int argument) {
		this.argument = argument;
	}
	
	public State getTurnState() {
		return turnState;
	}
	
	public void setTurnState(State turnState) {
		this.turnState = turnState;
	}
	
	public boolean getIsReady() {
		return isReady;
	}
	
	public void setIsReady(boolean ready) {
		this.isReady = ready;
	}
	
	public boolean getBattleStarted() {
		return battleStarted;
	}
	
	public boolean getTurnReady() {
		return turnReady;
	}
	
	public void setTurnReady(boolean ready) {
		this.turnReady = ready;
	}
	
	public void addToTeam(String monster, int fromIndex) throws Exception{
		int count = 0;
		for (Monster m : player.getTeam()) {
			if (m != null)
				count++;
		}
		if (count < player.MAX_TEAM_SIZE && fromIndex >= 0) {
			Monster newMonster = d.MonsterMap.get(monster);
			boolean added = false;
			int indexToAdd = 0;
			while (!added) {
				if (player.getTeam()[indexToAdd] == null) {
					player.addMonster(newMonster, indexToAdd);
					added = true;
				} else {
					indexToAdd++;
				}
			}
		}
	}
	
	public void removeFromTeam(String monster, int fromIndex) throws Exception {
		int count = 0;
		for (Monster m : player.getTeam()) {
			if (m != null)
				count++;
		} 
		if (count > 0 && fromIndex >= 0) {
			Monster[] updatedTeam = new Monster[6];
			Monster toRemove = d.MonsterMap.get(monster);
			int nextIndex = 0;
			player.removeMonster(toRemove, fromIndex);
			for (Monster m : player.getTeam()) {
				if (m != null) {
					updatedTeam[nextIndex] = m;
					nextIndex++;
				}
			}
			player.setTeam(updatedTeam);
		}
	}
    
    public void startClient() {
    	try {
			fetchPlayerInfo();
			d = new Database();
			d.getData();
			player = new Player(playerName, playerID);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Thread thread = new Thread(this);
		thread.start();
	}
	
	private void fetchPlayerInfo() throws IOException, FileNotFoundException {
		try (Scanner sc = new Scanner(new BufferedReader
									 (new FileReader("PlayerID.txt")));) {
			sc.useDelimiter(":");
			playerID = sc.next();		
			playerName = sc.next();
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("Input file could not be found");
		}
	}
	
	public void connect(String host, int port) {
		System.out.println("Trying to connect...");
		try {
			playerSocket = new Socket(host, port);
			System.out.println("Connected");
			setupStreams(playerSocket);
			System.out.println("Streams established");
			playerNum = (Integer)input.readUnshared();
			System.out.println(playerNum);
    	} catch (UnknownHostException e) {
            System.err.println("Unknown host " + host);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for connection to " + host);
            System.exit(1);
        } catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void setupStreams(Socket s) throws IOException {
		output = new ObjectOutputStream(s.getOutputStream());
		input = new ObjectInputStream(s.getInputStream());
	}
    
	public void run() {
		connect("localhost", 4444);
		try {
			while (!isReady) {
				synchronized (lock) {
					try {
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			System.out.println("moving on");
			output.writeUnshared(isReady);
			output.writeUnshared(player);
			Object o;
			while (!battleStarted) {
				if ((o = input.readUnshared()) != null && (Boolean)o == true) {
					oppLead = (Monster)input.readUnshared();
					battleStarted = true;
					synchronized (lock) {
						lock.notifyAll();
					}
				}
			}
			System.out.println("Battle started!");
		
			while (!gameOver) {
				System.out.println(player.getLead().getHP() + " : " + oppLead.getHP());
				if (returnData.getTurnFinished() == 0 || returnData.getTurnFinished() == 2 ||
						(returnData.getTurnFinished() == 1 && 
						(returnData.getFainted() == (playerNum + 1) || 
						returnData.getFainted() == 3))) {
					do {
						synchronized (lock) {
							try {
								System.out.println("i'm in the lock");
								lock.wait();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						Thread.sleep(100);
					} while (!turnReady);
					
					System.out.println("turn set to ready");
					returnData = new TurnReturn();
					Turn turn = new Turn(action, argument, turnState, false);
					output.writeUnshared(turn);
				} else {
					Turn turn = null;
					returnData = new TurnReturn();
					output.writeUnshared(turn);
				}
				System.out.println("wrote the turn");
				returnData = (TurnReturn)input.readUnshared();
				if (playerNum == 0) {
					player.setLead(returnData.getLeads()[0]);
					oppLead = returnData.getLeads()[1];
				} else {
					player.setLead(returnData.getLeads()[1]);
					oppLead = returnData.getLeads()[0];
				}
				//System.out.println(player.getLead().getHP());
				//System.out.println(oppLead.getHP());
				//System.out.println(returnData.getTurnFinished());
				System.out.println(returnData);
				turnReady = false;
			}
			
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			try {
				if (input != null)
					input.close();
				if (output != null);
					output.close();
				if (playerSocket != null)
					playerSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}