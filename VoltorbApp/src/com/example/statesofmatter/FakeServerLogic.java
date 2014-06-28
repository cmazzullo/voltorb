/**
 * A text-based server that runs locally, just to test game logic and 
 * the Server interface. 
 */

package com.example.statesofmatter;

import java.io.*;
import java.util.Scanner;
import java.net.*;

import android.annotation.TargetApi;

public class FakeServerLogic{
    
    private final String HOST = "localhost";
    static String p1ID, p2ID = null;
    static Player p1, p2 = null;
    static int connected = 0;

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

    public static Player getPlayer(String ID) throws IOException {
    	if (ID == p1ID)
    		return p1;
    	else if (ID == p2ID)
    		return p2;
    	else
    		throw new IOException("Couldn't find player ID.");
    }
    
    public void setPlayerID(String ID) {
    	if (connected == 0) {
    		p1ID = ID;
    		connected++;
    	} else {
    		p2ID = ID;
    		connected++;
    	}
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

    public Turn getTurn(Player player) {
    	return null;
    }
    
    public boolean isTie(Player player) {
    	return true;
    }
    
    public boolean isGameOver() throws IOException {
        return (this.getPlayer(p1ID).allFainted() ||
		this.getPlayer(p2ID).allFainted());
    }
}