package com.example.statesofmatter;

import java.io.*;
import java.net.*;

import android.annotation.TargetApi;

@TargetApi(19)
public class FakeServerThread extends Thread {
	
	private Socket socket = null;
	
	public FakeServerThread(Socket socket) {
		super("FakeServerThread");
		this.socket = socket;
	}
	
	public void run() {
		try (OutputStream out = socket.getOutputStream();
			 InputStream in = socket.getInputStream();) {
			
			FakeServerLogic fsl = new FakeServerLogic();
			
			while (!fsl.isGameOver()) {
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
