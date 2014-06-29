package com.example.statesofmatter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class User implements Runnable {
	
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

				String message = in.readUTF();
				for (int i = 0; i<2; i++) {
					if (users[i] != null) {
						users[i].out.writeUTF(message);
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				out = null;
				in = null;
				e.printStackTrace();
			}
		}
	}
}
