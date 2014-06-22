/**
 * Implements Turn, allows player to select a PlayerAction and an
 * index. When the player is switching attack, index selects the
 * attack to switch to from the Player.attacks array, when switching
 * Monsters it selects the Monster, etc.
 */
package com.example.statesofmatter;

public class Turn implements TurnInterface {

    private PlayerAction action;
    private int argument;
    
    public Turn (PlayerAction action, int argument) {
    	this.action = action;
    	this.argument = argument;
    }

    public void executeTurn(Player player) {
	switch (action) {
	case SWITCH:
	    Monster[] team = player.getTeam();
	    player.setLead(team[argument]);
	    break;
	case ATTACK:
	case STATESHIFT:
	case ITEM:
	}
    }
    
    public PlayerAction getAction(){
    	return action;
    }
    
    public int getArgument(){
    	return argument;
    }
    
}
