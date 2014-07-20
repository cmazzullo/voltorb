/**
 * This defines an interface for a Turn. When the game starts, each 
 * Player takes a Turn simultaneously. The Turn is then processed and
 * the objects involved in the game are modified according to the 
 * Player's action. 
 */

package com.example.statesofmatter;

public interface TurnInterface {
    
	 public PlayerAction getAction();
	 
	 public int getArgument();
	
    //TODO public void executeTurn(Player player); here or fakeserverprotocol?
}