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
			//setupStreams(playerSocket);
			FakeServerProtocol fsp = new FakeServerProtocol();
			while (!gameOver) {
				Object o;
				if ((o = input.readObject()) != null) {
					if (((String)o).equals("tied"))
						//output.flush();
						output.writeObject((Object)fsp.processTie((String)o));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
