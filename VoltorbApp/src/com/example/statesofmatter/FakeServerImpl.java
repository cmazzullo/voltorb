/**
 * A text-based server that runs locally, just to test game logic and 
 * the Server interface. 
 */

package com.example.statesofmatter;

import java.io.*;

public class FakeServerImpl implements Server {
    
    private final String HOST = "localhost";

    private BufferedReader reader;

    public Server.PlayerAction sendMyAction() {
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

    public Monster sendMyLead() {return null;}

    public Attack sendMyAttack(){return null;}

    public Monster.State sendMyState(){return null;}

    public PlayerAction getOpponentAction(){return null;}

    public Monster getOpponentLead(){return null;}

    public Attack getOpponentAttack(){return null;}

    public Monster.State getOpponentState(){return null;}

}
