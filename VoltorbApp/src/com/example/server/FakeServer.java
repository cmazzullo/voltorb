/**Server connection listener
 * Listens for connections from client and passes them into UserThread before 
 * continuing to listen for more connections.
 */
package com.example.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.example.statesofmatter.Monster;

import android.annotation.TargetApi;

@TargetApi(19)
public class FakeServer extends Thread implements Runnable {
	
	private static ServerSocket serverSocket;
	private static final int PORT = 4444;
	private static Socket playerSocket;
	
	private static boolean isRunning = false;
	
	private ObjectInputStream input;
	private ObjectOutputStream output;
	static List<UserThread> connUsers = Collections.synchronizedList(new ArrayList<UserThread>());
	static List<FakeLobby> lobbies = new ArrayList<FakeLobby>();
	static HashMap<Integer, FakeLobby> lobbyMap = new HashMap<Integer, FakeLobby>();
	private int nextConn;
	private int nextLobby;
	
	
	private FakeServer() throws IOException {
		try {
			System.out.println("Attempting to start...");
			serverSocket = new ServerSocket(PORT);
			System.out.println("Successfully started on port " + PORT);
			isRunning = true;
			UserThread newUser;
			FakeLobby newLobby;
			
			while (isRunning) {
				playerSocket = serverSocket.accept();
				setupStreams(playerSocket);
				newUser = new UserThread(output, input);
				connUsers.add(newUser);
				connUsers.get(nextConn).start();
				System.out.println("Connection made");
				
				if (nextConn % 2 == 0) {
					newLobby = new FakeLobby(nextLobby);
					System.out.println("created new lobby");
					newUser.setInLobby(true);
					newUser.setLobbyNum(nextLobby);
					newLobby.addPlayer(newUser);
					newLobby.start();
					System.out.println("added user to lobby");
					lobbyMap.put(nextLobby, newLobby);
					System.out.println("put lobby in map");
				} else {
					newUser.setInLobby(true);
					newUser.setLobbyNum(nextLobby);
					lobbyMap.get(nextLobby).addPlayer(newUser);
					System.out.println("added user to map");
					nextLobby++; //TODO should this change so if a lobby closes, the key can be re-used? Otherwise key will keep increasing, new values might require new buckets in HashMap?
				}
				nextConn++;
				newUser = null;
				newLobby = null;
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
	
	public static FakeLobby getLobby(int key) {
		return lobbyMap.get(key);
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		new FakeServer();
	}
}