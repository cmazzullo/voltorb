package com.example.statesofmatter;

import java.io.*;
import java.net.*;

import android.annotation.TargetApi;

@TargetApi(19)
public class FakeServerThread extends Thread {
	
	private static Socket playerSocket;
	
	private ObjectOutputStream output;
	private ObjectInputStream input;
	
	private boolean gameOver = false;
	
	public FakeServerThread(Socket s) {
		super("FakeServerThread");
		playerSocket = s;
	}
	
	private void setupStreams(Socket s) throws IOException {
		output = new ObjectOutputStream(s.getOutputStream());
		input = new ObjectInputStream(s.getInputStream());
	}
	
	public void run() {
		try {
			setupStreams(playerSocket);
			FakeServerProtocol fsp = new FakeServerProtocol();
			System.out.println("stuff");
			while (!gameOver) {
				Object o;
				o = input.readObject();
				if (o == "Tied")
					output.flush();
				output.writeObject((Object)fsp.processTie((String)o));
				//else if ((o == "Bleargh"))
					//output.writeObject((Object)fsp.processTie((String)o));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
