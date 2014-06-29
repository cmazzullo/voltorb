package com.example.statesofmatter;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.*;

import android.annotation.TargetApi;

@TargetApi(19)
public class FakeServer extends Thread{
	
	static ServerSocket serverSocket;
	private static final int PORT = 4444;
	private boolean isRunning = false;
	static Socket playerSocket;
	static ObjectInputStream in;
	static ObjectOutputStream out;
	static User[] users = new User[2];
	
	private FakeServer() {
		try {
			serverSocket = new ServerSocket(PORT);
			isRunning = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) throws IOException {
		new FakeServer().start();
		try {
			FakeServerLogic fsl = new FakeServerLogic();
			
			while (true) {
				playerSocket = serverSocket.accept();
				for (int i = 0; i < 2; i++) {
					if (users[i] == null) {
						out = new ObjectOutputStream(playerSocket.getOutputStream());
						in = new ObjectInputStream(playerSocket.getInputStream());
						users[i] = new User(in, out, users);
						Thread userThread = new Thread(users[i]);
						userThread.start();
						System.out.println("Connection made");
						break;
						/*
						try {
							
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}*/
					}
				}
			}
		} catch (IOException e) {
			System.err.println("Could not use port " + PORT);
            System.exit(-1);
		} finally {
			if (serverSocket != null)
				serverSocket.close();
		}
	}
	
	public void run() {
		while (isRunning) {
			
		}
	}
}	
class User implements Runnable {
	static ObjectInputStream in;
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
			try {
				//out.writeUTF("testing");
				String message = in.readUTF();
				for (int i = 0; i<2; i++) {
					if (users[i] != null) {
						users[i].out.writeUTF(message);
					}
				}
				/*String inputLine = in.readUTF();
				while ((inputLine) != null) {
					System.out.println(inputLine);
					System.out.println("blah");
				}*/
			} catch (IOException e) {
				// TODO Auto-generated catch block
				out = null;
				in = null;
				e.printStackTrace();
			}
			/*try {
				String message = in.readUTF();
				for (int i = 0; i < users.length; i++) {
					out.writeUTF(message);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}*/
		}
	}
}