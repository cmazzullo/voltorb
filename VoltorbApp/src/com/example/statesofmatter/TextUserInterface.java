package com.example.statesofmatter;

import java.io.*;


public class TextUserInterface {

	private static String input(String prompt) {
		System.out.println(prompt);
		BufferedReader reader = null;
		String line = null;
		try { 
			reader = new BufferedReader(new InputStreamReader(System.in));
			line = reader.readLine(); 
		} catch (IOException e) { 
			System.out.println("TextUserInterface: Couldn't make the BufferedReader" + e); 
		} finally { 
			try{
				if (reader != null) 
					reader.close(); 		
			}catch(IOException e){
				System.out.println("TextUserInterface: Couldn't close the BufferedReader" + e); 
			}
		}
		return line;
	}
	
	/*public static Turn getTurn() {
		String prompt = "What would you like to do?\n1. Attack\n2. Switch\n3. Change State\n4. Use Item\n";
		Integer userInput = Integer.parseInt(input(prompt));
		PlayerAction action = PlayerAction.PASS;
		switch (userInput) {
		case 1:
			System.out.println("Attacking!");
			action = PlayerAction.ATTACK;
			break;
		case 2:
			System.out.println("Switching!");
			action = PlayerAction.SWITCH;
			break;
		case 3: 
			System.out.println("Changing State!");
			action = PlayerAction.STATE_SHIFT;
			break;
		case 4:
			System.out.println("Using Item!");
			action = PlayerAction.ITEM;
			break; // TODO: Does this break need to break here?
		}
		prompt = "Which thing would you like to select";
		userInput = Integer.parseInt(input(prompt));
		int argument = userInput;
		return new Turn(action, argument);
	}*/
}
