package com.example.statesofmatter;

import java.io.*;
import java.net.*;

import android.annotation.TargetApi;

@TargetApi(19)
public class FakeServerThread extends Thread {
	
	private static Socket playerSocket;
	
	private ObjectOutputStream output;
	private ObjectInputStream input;
	
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
			FakeServerLogic fsl = new FakeServerLogic();
			while (true) {
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
