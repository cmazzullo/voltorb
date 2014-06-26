/**
 * A text-based server that runs locally, just to test game logic and 
 * the Server interface. 
 */

package com.example.statesofmatter;

import java.io.*;

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
    	return new Player(ID, name);
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
}