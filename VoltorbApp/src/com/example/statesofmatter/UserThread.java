package com.example.statesofmatter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class UserThread extends Thread {
	
	private static Socket playerSocket;
	
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private int playerNum;
	private Player player;
	private Monster oppLead;
	private boolean playerReady = false;
	private boolean battleStarted = false;
	private boolean gameOver = false;
	
	public UserThread(ObjectOutputStream out, ObjectInputStream in, int playerNum) {
		this.output = out;
		this.input = in;
		this.playerNum = playerNum;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void run() {
		System.out.println(playerNum);
		try {
			FakeServer.fsp.incConnection();
			while (!playerReady) {
				Object o;
				FakeServer.fsp.manageStartup();
				Thread.sleep(1000);
				if ((o = input.readObject()) != null && (Boolean)o == true) {
					player = (Player)input.readObject();
					playerReady = true;
					FakeServer.fsp.incReady();
					System.out.println("I'm ready");	
				}
			}
			while (oppLead == null) {
				if (playerNum == 0) {
					if (FakeServer.users[1] != null &&
						FakeServer.users[1].getPlayer() != null)
						oppLead = FakeServer.users[1].getPlayer().getLead();
				} else {
					if (FakeServer.users[0] != null &&
						FakeServer.users[0].getPlayer() != null)
						oppLead = FakeServer.users[0].getPlayer().getLead();
				}
			}
			while (!battleStarted) {
				Thread.sleep(1000);
				boolean starting = FakeServer.fsp.manageStartup();
				if (starting == true) {
					output.writeObject((Boolean)starting);
					output.writeObject((Monster)oppLead);
					battleStarted = true;
				}
			}
			
			System.out.println("Print me when everyone's ready to battle!");
		
			while (!gameOver) {
			
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				FakeServer.fsp.decReady();
				FakeServer.fsp.decConnection();
				if (output != null)
					output.close();
				if (input != null)
					input.close();
				if (playerSocket != null)
					playerSocket.close();
				System.out.println("Player disconnected");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
