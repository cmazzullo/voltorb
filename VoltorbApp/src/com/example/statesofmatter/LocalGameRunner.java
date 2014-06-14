/**
 * LocalGameRunner contains (will contain) the game logic for a client
 * playing States of Matter. It interfaces with the UserInterface on
 * the frontend and gets the other player's actions with Server on
 * the backend.
 */

package com.example.statesofmatter;

public class LocalGameRunner {

    //All of these methods should go into a separate game logic class
    private static Boolean isGameOver(Player player, Server server) {
	
        return (player.allFainted() ||
		server.opponentAllFainted());
    }

    private static void doMyTurn(Player player, Server server) {
	//Turn turn = UserInterface.getMyTurn();
    }

    private static void doPostGame() {
        
    }

    public static void main(String[] args) {
        //need to put playerdata in here
        //both players need to act at the same time
	String ID = "";
	String playername = "";
        Server server = new FakeServerImpl();
        Player player = server.getPlayer(ID);
	Player opponent = server.getOpponent();
        while (!isGameOver(player, server)) {
            doMyTurn(player, server);
            Turn opponentTurn = server.getOpponentTurn();
	    opponentTurn.executeTurn(opponent);
        }
        doPostGame();
    }
}
