package com.example.statesofmatter;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.*;

import android.annotation.TargetApi;

@TargetApi(19)
public class FakeServer {
	
	static ServerSocket serverSocket;
	static Socket socket;
	static ObjectInputStream in;
	static ObjectOutputStream out;
	static User[] users = new User[2];
	
	public static void main(String[] args) throws IOException {

		if (args.length != 1) {
			System.err.println("Usage: java FakeServer <port number>");
			System.exit(1);
		}
		
		int port = Integer.parseInt(args[0]);	

		try {
			FakeServerLogic fsl = new FakeServerLogic();
			serverSocket = new ServerSocket(port);
			while (true) {
				socket = serverSocket.accept();
				for (int i = 0; i < 2; i++) {
					if (users[i] == null) {
						in = new ObjectInputStream(socket.getInputStream());
						out = new ObjectOutputStream(socket.getOutputStream());
						out.flush();
						users[i] = new User(in, out, users);
						Thread userThread = new Thread(users[i]);
						userThread.start();
						System.out.println("Connection made");
						break;
						/*String inputLine;
						try {
							while ((inputLine = in.readUTF()) != null) {
								System.out.println(in.readUTF());
								System.out.println("blah");
							}
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}*/
					}
				}
			}
		} catch (IOException e) {
			System.err.println("Could not use port " + port);
            System.exit(-1);
		} finally {
			if (serverSocket != null)
				serverSocket.close();
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

		/*while (true) {
			try {
				String message = in.readUTF();
				for (int i = 0; i < users.length; i++) {
					out.writeUTF(message);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}*/
	}
}