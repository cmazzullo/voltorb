package com.example.statesofmatter;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.*;

import android.annotation.TargetApi;

@TargetApi(19)
public class FakeServer extends Thread implements Runnable {
	
	private static ServerSocket serverSocket;
	private static final int PORT = 4444;
	private static Socket playerSocket;
	
	private static boolean isRunning = false;
	
	private ObjectInputStream input;
	private ObjectOutputStream output;
	static User[] users = new User[2];
	
	private FakeServer() throws IOException {
		try {
			System.out.println("Attempting to start...");
			serverSocket = new ServerSocket(PORT);
			System.out.println("Successfully started on port " + PORT);
			isRunning = true;
			
			while (isRunning) {
				playerSocket = serverSocket.accept();
				for (int i = 0; i< users.length; i++) {
					if (users[i] == null) {
						setupStreams(playerSocket);
						users[i] = new User(output, input, users);
						users[i].start();
						System.out.println("Connection made");
						break;
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
	
	private void setupStreams(Socket s) throws IOException {
		output = new ObjectOutputStream(s.getOutputStream());
		input = new ObjectInputStream(s.getInputStream());
	}
	
	public static void main(String[] args) throws IOException {
		new FakeServer();
	}
}