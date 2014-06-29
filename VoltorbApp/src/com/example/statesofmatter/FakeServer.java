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
	static Socket playerSocket;
	
	private boolean isRunning = false;
	
	private ObjectInputStream input;
	private ObjectOutputStream output;
	static User[] users = new User[2];
	
	private FakeServer() throws IOException, ClassNotFoundException {
		try {
			System.out.println("Attempting to start...");
			serverSocket = new ServerSocket(PORT);
			System.out.println("Successfully started on port " + PORT);
			isRunning = true;
			
			while (isRunning) {
				playerSocket = serverSocket.accept();
				for (int i = 0; i < 2; i++) {
					if (users[i] == null) {
						setupStreams(playerSocket);
						users[i] = new User(input, output, users);
						Thread userThread = new Thread(users[i]);
						userThread.start();
						System.out.println("Connection made");
						break;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void setupStreams(Socket s) throws IOException {
		output = new ObjectOutputStream(s.getOutputStream());
		input = new ObjectInputStream(s.getInputStream());
	}
	
	public static void main(String[] args) throws IOException {
		try {
			new FakeServer();
			FakeServerLogic fsl = new FakeServerLogic();
		} catch (IOException e) {
			System.err.println("Could not use port " + PORT);
            System.exit(-1);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (playerSocket != null)
				playerSocket.close();
			if (serverSocket != null)
				serverSocket.close();
		}
	}
	
	public void run() {
		while (isRunning) {
			
		}
	}
}