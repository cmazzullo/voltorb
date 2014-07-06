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
    private State state;
    
    public Turn() {}
    
    public Turn (PlayerAction action, int argument, State state) { //TODO: include state as arg since state can be set in same turn as a move
    	this.action = action;
    	this.argument = argument;
    	this.state = state;
    }
    
    public PlayerAction getAction() {
    	return action;
    }
    
    public int getArgument() {
    	return argument;
    }
    
    public State getState() {
    	return state;
    }
    
    @Override
    public String toString() {
    	return String.format("%s : %d : %s", action, argument, state);
    }
}