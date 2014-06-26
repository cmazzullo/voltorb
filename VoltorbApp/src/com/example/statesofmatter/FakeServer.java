/**
 * A text-based server that runs locally, just to test game logic and 
 * the Server interface. 
 */

package com.example.statesofmatter;

import java.io.*;
import java.util.Scanner;

public class FakeServer implements ServerInterface {
    
    private final String HOST = "localhost";

    private BufferedReader reader;

    public PlayerAction sendAction() {
        try {
            reader = 
                new BufferedReader(new InputStreamReader(System.in));
            System.out.println("What action would you like to take?");
            String input = reader.readLine();
            System.out.println(input);
        } catch (IOException e) {
            System.err.println("Couldn't open reader from stdin!");
            e.printStackTrace();
            System.exit(0);
        }
        return null;
    }

    public Monster sendLead() {return null;}

    public Attack sendAttack(){return null;}

    public State sendState(){return null;}

    public PlayerAction getAction(){return null;}

    public Monster getLead(){return null;}

    public Attack getAttack(){return null;}

    public State getState(){return null;}

    public Player getPlayer(String ID) {
    	return null;
    }
    
    public String getPlayerName(String playerID) throws Exception, FileNotFoundException {
    	Scanner pn = null;
    	String name = null;
    	try {
    		pn = new Scanner(new BufferedReader(new FileReader("C:\\Users\\Jim\\Scripts\\voltorb\\VoltorbApp\\PlayerDatabase.txt")));
    		pn.useDelimiter(":|\n");
    		String id;
    		boolean found = false;
    		while (pn.hasNext() && !found) {
    			id = pn.next();
    			if (playerID.equals(id)) {
    				name = pn.next();
    				found = true;
    		}
    			else
    				pn.next();
    		}
    		if (name != null)
    			return name;
    		else 
    			throw new Exception("Player ID could not be found.");
    	} catch (FileNotFoundException fnfe) {
    		throw new FileNotFoundException("Input file could not be found");
    	} finally {
    		if (pn != null)
    			pn.close();
    	}
    }
    
    public Player getOpponent() {return null;}

    public boolean bothFainted(Player p1, Player p2) {
    	if (p1.allFainted() && p2.allFainted())
    		return true;
    	else
    		return false;
    }

    public Turn getTurn(Player player) {
    	return null;
    }
    
    public static void main (String[] args) throws Exception, FileNotFoundException {
    	FakeServer server = new FakeServer();
    	System.out.println(server.getPlayerName("3"));
    }
}