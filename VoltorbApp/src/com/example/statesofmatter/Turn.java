/**
 * Implements Turn, allows player to select a PlayerAction and an
 * index. When the player is switching attack, index selects the
 * attack to switch to from the Player.attacks array, when switching
 * Monsters it selects the Monster, etc.
 */
package com.example.statesofmatter;

import java.io.Serializable;

public class Turn implements TurnInterface, Serializable {

    private PlayerAction action;
    private int argument;
    private boolean isDone;
    
    public Turn (PlayerAction action, int argument) {
    	this.action = action;
    	this.argument = argument;
    	this.isDone = false;
    }
    
    public void executeTurn(Player player) {
    	switch (action) {
			case SWITCH:
				Monster[] team = player.getTeam();
				player.setLead(team[argument]);
				setIsDone(true);
				break;
			case ATTACK:
				
			case STATE_SHIFT:
				Monster toShift = player.getTeam()[argument];
				
			case ITEM:
    	}
    }
    
    public PlayerAction getAction(){
    	return action;
    }
    
    public int getArgument(){
    	return argument;
    }
    
    public boolean getIsDone() {
    	return isDone;
    }
    
    public void setIsDone(boolean isDone) {
    	this.isDone = isDone;
    }
}