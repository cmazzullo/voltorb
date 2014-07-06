/**Server connection listener
 * Listens for connections from client and passes them into UserThread before 
 * continuing to listen for more connections.
 */
package com.example.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.*;

import com.example.statesofmatter.Monster;

import android.annotation.TargetApi;

@TargetApi(19)
public class FakeServer extends Thread implements Runnable {
	
	private static ServerSocket serverSocket;
	private static final int PORT = 4444;
	private static Socket playerSocket;
	
	public static final FakeServerProtocol fsp = new FakeServerProtocol();
	
	private static boolean isRunning = false;
	
	private ObjectInputStream input;
	private ObjectOutputStream output;
	static UserThread[] users = new UserThread[2]; //TODO: Make array list of user arrays
	public static Object lock = new Object();
	private static boolean battleStarted;
	private static boolean gameOver;
	private static boolean turnsReady;
	private static boolean turnProcessed;
	private static Monster[] returnArray;
	
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
						users[i] = new UserThread(output, input, i);
						users[i].start();
						fsp.incConnection();
						fsp.manageStartup();
						System.out.println("Connection made");
						break;
					}
				}
			//TODO temp code until lobbies implemented
				if (users[1] != null)
					isRunning = false;
			}
		} catch (IOException e) {
			System.err.println("Could not use port " + PORT);
            System.exit(-1); //-1 convention for abnormal exit
		} finally {
			if (serverSocket != null)
				serverSocket.close();
		}
	}
	//TODO: figure out why we need method (minor)
	private void setupStreams(Socket s) throws IOException {
		output = new ObjectOutputStream(s.getOutputStream());
		input = new ObjectInputStream(s.getInputStream());
	}
	
	public static boolean getBattleStarted() {
		return battleStarted;
	}
	
	public static boolean getTurnProcessed() {
		return turnProcessed;
	}
	
	public static Monster[] getReturnArray() {
		return returnArray;
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		new FakeServer();
		
		while (!battleStarted) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {}
			boolean starting = FakeServer.fsp.manageStartup();
			if (starting == true) {
				battleStarted = true;
				synchronized (lock) {
					lock.notifyAll();
				}
			}
		}
		//TODO look into event queues so this can be synced with both UserThreads without messing up
		// ie receiving both notify messages before second lock
		while (!gameOver) {
			System.out.println("starting round");
			turnsReady = false;
			turnProcessed = false;
			do {
				Thread.sleep(200);
				returnArray = fsp.runBattle();
				if (fsp.getTurnsReady() == 2) {
					turnsReady = true;
				}
			} while (!turnsReady);
			
			returnArray = fsp.runBattle();
			fsp.resetTurns();
			turnProcessed = true;

			synchronized (lock) {
				lock.notifyAll();
			}
		}
	}
}