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

    private static void doTurns(Player player, Server server) {
    	Turn myTurn = TextUserInterface.getTurn();
        Turn oppTurn = server.getOpponentTurn();
        Monster myLead = player.getLead();
        Monster oppLead = server.getOpponent().getLead();
        //calculate turn
        PlayerAction myAction = myTurn.getAction();
        PlayerAction oppAction = oppTurn.getAction();
        boolean myComplete = false;
        boolean oppComplete = false;
        
        //make speed comparison
        
        boolean meFirst = false;
        if(myLead.getSpeed() > oppLead.getSpeed()){
        	meFirst = true;
        }else if(myLead.getSpeed() == oppLead.getSpeed()){
        	//TODO:  What to do during speed tie
        }
        
        if(myAction == PlayerAction.SWITCH || oppAction == PlayerAction.SWITCH){
        	if(meFirst && myAction == PlayerAction.SWITCH){
        		player.switchLead(myTurn.getArgument());
        		myLead = player.getLead();
        		myComplete = true;
        	}
        	if(oppAction == PlayerAction.SWITCH){
        		//switch
        		oppComplete = false;
        	}
        	if(!meFirst && myAction == PlayerAction.SWITCH){
        		//switch
        		myComplete = true;
        	}
        }
        
        if(!myComplete && meFirst){
        	if(myAction == PlayerAction.ATTACK){
        	}
        	else if(myAction == PlayerAction.ITEM){
        	}
        	else if(myAction == PlayerAction.STATE_SHIFT){
        	}
        	else if(myAction == PlayerAction.PASS){
        	}
        }
        
        if(!oppComplete){
        	if(oppAction == PlayerAction.ATTACK){
        	}
        	else if(oppAction == PlayerAction.ITEM){
        	}
        	else if(oppAction == PlayerAction.STATE_SHIFT){
        	}
        	else if(oppAction == PlayerAction.PASS){
        	}      	
        }
        
        if(!myComplete && !meFirst){
        	if(myAction == PlayerAction.ATTACK){
        	}
        	else if(myAction == PlayerAction.ITEM){
        	}
        	else if(myAction == PlayerAction.STATE_SHIFT){
        	}
        	else if(myAction == PlayerAction.PASS){
        	}
        }
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
            doTurns(player, server);
            //opponentTurn.executeTurn(opponent);
        }
        doPostGame();
    }
}
