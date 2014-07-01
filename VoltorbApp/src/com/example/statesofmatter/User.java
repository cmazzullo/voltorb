package com.example.statesofmatter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class User extends Thread {
	
	private static Socket playerSocket;
	
	private ObjectOutputStream output;
	private ObjectInputStream input;
	static User[] users = new User[2];
	private Player player;
	private boolean playerReady = false;
	private boolean battleStarted = false;
	private boolean gameOver = false;
	
	public User(ObjectOutputStream out, ObjectInputStream in, User[] users) {
		this.output = out;
		this.input = in;
		this.users = users;
	}
	
	private void setupStreams(Socket s) throws IOException {
		output = new ObjectOutputStream(s.getOutputStream());
		input = new ObjectInputStream(s.getInputStream());
	}
	
	public void run() {
		try {
			FakeServer.fsp.incConnection();
			while (!playerReady) {
				Object o;
				FakeServer.fsp.manageStartup();
				Thread.sleep(1000);
				if ((o = input.readObject()) != null && (Boolean)o == true) {
					playerReady = true;
					FakeServer.fsp.incReady();
					System.out.println("I'm ready");	
				}
			}

			while (!battleStarted) {
				Thread.sleep(1000);
				boolean starting = FakeServer.fsp.manageStartup();
				if (starting == true) {
					output.writeObject((Boolean)starting);
					battleStarted = true;
					//FakeServer.fsp.incBattling();
				}
			}
			
			System.out.println("Print me when everyone's ready to battle!");
		
			while (true) {
			
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
	
/*	static ObjectInputStream in;
	static ObjectOutputStream out;
	static User[] users = new User[2];
	static int pid;
	
	public User(ObjectInputStream in, ObjectOutputStream out, User[] users) {
		this.in = in;
		this.out = out;
		this.users = users;
	}
	
	public void run() {
		while (true) {
			
		}
	}*/
}
