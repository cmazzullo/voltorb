/**
 * Implements Turn, allows player to select a PlayerAction and an
 * index. When the player is switching attack, index selects the
 * attack to switch to from the Player.attacks array, when switching
 * Monsters it selects the Monster, etc.
 */
package com.example.statesofmatter;

import java.io.Serializable;

public class Turn implements Serializable {

    private PlayerAction action;
    private int argument;
    private State state;
    private boolean completed;
    
    public Turn() {}
    
    public Turn (PlayerAction action, int argument, State state, boolean completed) {
    	this.action = action;
    	this.argument = argument;
    	this.state = state;
    	this.completed = completed;
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
    
    public boolean isCompleted() {
    	return completed;
    }
    
    public void setCompleted(boolean completed) {
    	this.completed = completed;
    }
    
    @Override
    public String toString() {
    	return String.format("%s : %d : %s", action, argument, state);
    }
}