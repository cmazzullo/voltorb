/**Game logic to manage progression through game
 * and execute player turns
 */
package com.example.server;

import com.example.statesofmatter.Attack;
import com.example.statesofmatter.BuffDebuff;
import com.example.statesofmatter.Item;
import com.example.statesofmatter.Monster;
import com.example.statesofmatter.PlayerAction;
import com.example.statesofmatter.Status;
import com.example.statesofmatter.Turn;
import com.example.statesofmatter.TurnReturn;

public class FakeServerProtocol {
	private final int NOT_FULL = 0;
	private final int LOBBY_FULL = 1;
	private final int LOBBY_READY = 2;
	private final int TURN_WAITING = 3;
	private final int TURNS_READY = 4;
	private final int PLAYER_FAINTED = 5;
	
	private int lobbyNum = -1;
	
	private int gameState = 0;
	private int connections = 0;
	private int playersReady = 0;
	private int turnsReady = 0;

	public FakeServerProtocol(int lobbyNum) {
		this.lobbyNum = lobbyNum;
	}
	
	public int getLobbyNum() {
		return lobbyNum;
	}
	
	public void incConnection() {
		connections++;
	}
	
	public void decConnection() {
		connections--;
	}
	
	public void incReady() {
		playersReady++;
	}
	
	public void decReady() {
		playersReady--;
	}
	
	public void incTurns() {
		turnsReady++;
	}
	
	public void resetTurns() {
		turnsReady = 0;
	}
	
	public int getTurnsReady() {
		return turnsReady;
	}
	
	public boolean manageStartup() {
		if (gameState == NOT_FULL) {
			if (connections == 2 && playersReady < 2)
				gameState = 1;
			else if (connections == 2 && playersReady == 2)
				gameState = 2;
		} else if (gameState == LOBBY_FULL) {
			if (connections < 2)
				gameState = 0;
			else if (playersReady == 2)
				gameState = 2;
		} else if (gameState == LOBBY_READY) {
			if (connections < 2)
				gameState = 0;
			else if (playersReady < 2)
				gameState = 1;
			else {
				gameState = 3;
				return true;
			}
		}
		return false;
	}
	
	public TurnReturn runBattle() {
		
		if (gameState == TURN_WAITING) {
			if (turnsReady == 2)
				gameState = 4;
		} else if (gameState == TURNS_READY) {
			TurnReturn returnData = new TurnReturn();
			boolean p1First = battleOrder(FakeServer.getLobby(lobbyNum).getUserThread()[0].getTurn(), 
					FakeServer.getLobby(lobbyNum).getUserThread()[0].getPlayer().getLead(),
					FakeServer.getLobby(lobbyNum).getUserThread()[1].getTurn(), 
					FakeServer.getLobby(lobbyNum).getUserThread()[1].getPlayer().getLead());
			returnData = executeTurns(p1First, FakeServer.getLobby(lobbyNum).getUserThread()[0].getTurn(), 
					FakeServer.getLobby(lobbyNum).getUserThread()[0].getPlayer().getLead(),
					FakeServer.getLobby(lobbyNum).getUserThread()[1].getTurn(), 
					FakeServer.getLobby(lobbyNum).getUserThread()[1].getPlayer().getLead(),
					returnData);
			if (returnData.getTurnFinished() == 2)
				gameState = 3;
			else
				gameState = 5;
			return returnData;
		} else if (gameState == PLAYER_FAINTED) {
			TurnReturn returnData = new TurnReturn();
			
		}
		return null;
	}	
	
	private TurnReturn executeTurns(boolean p1First, Turn p1Turn, Monster p1Lead, Turn p2Turn, Monster p2Lead, TurnReturn returnData) {

		if (p1First) {
			returnData = doTurn(0, 1, p1Turn, p1Lead, p2Lead, returnData);
			if (returnData.getFainted() == 0) {
				returnData = doTurn(1, 0, p2Turn, returnData.getLeads()[1], returnData.getLeads()[0], returnData);
				if (returnData.getFainted() == 0)
					returnData.setTurnFinished(2);
				else
					returnData.setTurnFinished(1);
			}
			returnData.setLeads(new Monster[] { returnData.getLeads()[1], returnData.getLeads()[0] });
			return returnData; 
		} else {
			returnData = doTurn(1, 0, p2Turn, p2Lead, p1Lead, returnData);
			if (returnData.getFainted() == 0) {
				returnData = doTurn(0, 1, p1Turn, returnData.getLeads()[1], returnData.getLeads()[0], returnData);
				if (returnData.getFainted() == 0)
					returnData.setTurnFinished(2);
				else
					returnData.setTurnFinished(1);
			}
			return returnData;
		}
	}
	
	private boolean battleOrder(Turn p1Turn, Monster p1Lead, Turn p2Turn, Monster p2Lead) {
		if (p1Turn.getAction() == PlayerAction.SWITCH || 
			p2Turn.getAction() == PlayerAction.SWITCH) {
			if (p1Turn.getAction() == PlayerAction.SWITCH &&
				p2Turn.getAction() != PlayerAction.SWITCH)
				return true;
			else if (p1Turn.getAction() != PlayerAction.SWITCH &&
					 p2Turn.getAction() == PlayerAction.SWITCH)
				return false;
			else {
				if (p1Lead.getSpeed() > p2Lead.getSpeed())
					return true;
				else if (p1Lead.getSpeed() < p2Lead.getSpeed())
					return false;
				else {
					double tieBreaker = Math.random();
					if (tieBreaker < 0.5) {
						return true;
					} else {
						return false;
					}
				}
			}
		} else {
			if (p1Lead.getSpeed() > p2Lead.getSpeed())
				return true;
			else if (p1Lead.getSpeed() < p2Lead.getSpeed())
				return false;
			else {
				double tieBreaker = Math.random();
				if (tieBreaker < 0.5)
					return true;
				else
					return false;
			}	
		}
	}
	
	// TODO may or may not need p2Num
	private TurnReturn doTurn(int actPlayer, int recPlayer, Turn playerTurn, Monster actLead, Monster recLead, TurnReturn returnData) {
		if (playerTurn.getAction() == PlayerAction.SWITCH) {
			actLead = FakeServer.getLobby(lobbyNum).getUserThread()[actPlayer].getPlayer().getTeam()[playerTurn.getArgument()];
			returnData.setLeads(new Monster[] {actLead, recLead});
			return returnData;
		} else {
			if (actLead.getStatus() != Status.NORMAL || 
				actLead.getBuffDebuff() != BuffDebuff.NONE) {
				
				actLead.resolveStatus();//TODO advance status and buffdebuff timers/item cure
				
				switch (actLead.getStatus()) { //apply status effect if applicable
					case NORMAL:
						break;
					case POISON:
						break;
					case SLEEP:
						break;
					case PARALYSIS:
						break;
					}//TODO communicate (through TurnReturn) whether a status effect did something to player
			}	
			if (playerTurn.getState() != FakeServer.getLobby(lobbyNum).getUserThread()[actPlayer].getPlayer().getLead().getState()) {
				FakeServer.getLobby(lobbyNum).getUserThread()[actPlayer].getPlayer().getLead().setState(playerTurn.getState());
			}
			if (playerTurn.getAction() == PlayerAction.ATTACK) {
				//do damage to receivingLead
				Attack attack = actLead.getAttacks()[playerTurn.getArgument()];
				attack.applyAttack(actLead, recLead);
				
				if (!recLead.isFainted()) {
					//apply status/debuffs from attack
				//apply buffs from attack to self
				}
				//apply post-turn damage status
				if (actLead.isFainted() && recLead.isFainted())
					returnData.setFainted(3);
				else if (actLead.isFainted()) {
					if (actPlayer == 0)
						returnData.setFainted(1);
					else
						returnData.setFainted(2);
				} else if (recLead.isFainted()) {
					if (actPlayer == 0)
						returnData.setFainted(1);
					else
						returnData.setFainted(2);
				}
				
				returnData.setLeads(new Monster[] {actLead, recLead});
				return returnData;
			} else {//if (player.getAction() == PlayerAction.ITEM) {
				//apply item effect
				
				//apply post-turn damage status
				if (actLead.isFainted() && recLead.isFainted())
					returnData.setFainted(3);
				else if (actLead.isFainted()) {
					if (actPlayer == 0)
						returnData.setFainted(1);
					else
						returnData.setFainted(2);
				} else if (recLead.isFainted()) {
					if (actPlayer == 0)
						returnData.setFainted(1);
					else
						returnData.setFainted(2);
				}
				
				returnData.setLeads(new Monster[] {actLead, recLead});
				return returnData;
			}
		}
		// TODO STATE_SHIFT, ATTACK, ITEM, PASS
		// TODO Doesn't need STATE_SHIFT; PASS either if game async
	}
}

//TODO pre-turn STATUS resolution
// TODO postGame