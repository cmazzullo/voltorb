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
    
    private enum PlayerAction {
        SWITCH, ATTACK, STATESHIFT, ITEM
    }

    public Turn () {
	//Something like this from the user interface would work:
	//action = UserInterface.getPlayerAction();
	//argument = UserInterface.getActionArgument();
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
}
