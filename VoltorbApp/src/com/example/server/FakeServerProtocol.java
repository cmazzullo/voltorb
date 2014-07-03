/**Game logic to manage progression through game
 * and execute player turns
 */
package com.example.server;

import com.example.statesofmatter.Attack;
import com.example.statesofmatter.Item;
import com.example.statesofmatter.Monster;
import com.example.statesofmatter.PlayerAction;
import com.example.statesofmatter.Turn;

public class FakeServerProtocol {
	
	private final int NOT_FULL = 0;
	private final int LOBBY_FULL = 1;
	private final int LOBBY_READY = 2;
	private final int ONE_BATTLING = 3;
	private final int TURN_WAITING = 4;
	private final int TURNS_READY = 5;
	
	private int gameState = 0;
	private int connections = 0;
	private int playersReady = 0;
	private int turnsReady = 0;
	
	private Monster p1Lead;
	private Monster p2Lead;
	
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
		} else if (gameState == ONE_BATTLING) {
			gameState = 4;
			return true;
		}
		return false;
	}
	
	public boolean processTie(String s) {
		if (s.equals("tied"))
			return true;
		else
			return false;
	}

	public Monster[] runBattle(int playerNum) {
		Monster[] returnArray;
		if (gameState == TURN_WAITING) {
			if (turnsReady == 2)
				gameState = 5;
		} else if (gameState == TURNS_READY) {
			System.out.println("TURNS_READY");
			executeTurns(FakeServer.users[0].getTurn(), 
						 FakeServer.users[0].getPlayer().getLead(),
						 FakeServer.users[1].getTurn(),
						 FakeServer.users[1].getPlayer().getLead());
			if (playerNum == 0) {
				System.out.println("returning monster[] #1");
				returnArray = new Monster[] {this.p1Lead, this.p2Lead};
				turnsReady--;
				if (turnsReady == 0) {
					this.p1Lead = this.p2Lead = null;
					gameState = 4;
				}
				return returnArray;
			} else {
				System.out.println("returning monster[] #2");
				returnArray = new Monster[] {this.p2Lead, this.p1Lead};
				turnsReady--;
				if (turnsReady == 0) {
					this.p1Lead = this.p2Lead = null;
					gameState = 4;
				}
				return returnArray;
			}
		}
		return null;
	}	

	//TODO Should this method go in Turn class?
	private void executeTurns(Turn p1, Monster p1Lead, Turn p2, Monster p2Lead) {
		int p1First;
		
		if (p1.getAction() == PlayerAction.SWITCH || 
			p2.getAction() == PlayerAction.SWITCH) {
			if (p1.getAction() == PlayerAction.SWITCH &&
				p2.getAction() != PlayerAction.SWITCH)
				p1First = 1;
			else if (p1.getAction() != PlayerAction.SWITCH &&
					 p2.getAction() == PlayerAction.SWITCH)
				p1First = 2;
			else {
				if (p1Lead.getSpeed() > p2Lead.getSpeed())
					p1First = 1;
				else if (p1Lead.getSpeed() < p2Lead.getSpeed())
					p1First = 2;
				else {
					double tieBreaker = Math.random();
					System.out.println(tieBreaker);
					if (tieBreaker < 0.5) {
						p1First = 1;
					} else {
						p1First = 2;
					}
				}
			}
		} else {
			if (p1Lead.getSpeed() > p2Lead.getSpeed())
				p1First = 1;
			else if (p1Lead.getSpeed() < p2Lead.getSpeed())
				p1First = 2;
			else {
				double tieBreaker = Math.random();
				if (tieBreaker < 0.5)
					p1First = 1;
				else
					p1First = 2;
			}	
		}
		
		if (p1First == 1) {
			doTurn(0, p1, p1Lead, p2Lead);
			doTurn(1, p2, p2Lead, p1Lead);
		} else {
			doTurn(1, p2, p2Lead, p1Lead);
			doTurn(0, p1, p1Lead, p2Lead);
		}
	}

	private void doTurn(int user, Turn player, Monster actingLead, Monster receivingLead) {
		if (player.getAction() == PlayerAction.SWITCH) {
			if (user == 0)
				this.p1Lead = FakeServer.users[user].getPlayer().getTeam()[player.getArgument()];
			else if (user == 1)
				this.p2Lead = FakeServer.users[user].getPlayer().getTeam()[player.getArgument()];
		}
		// TODO STATE_SHIFT, ATTACK, ITEM, PASS
	}
}

//TODO pre-turn STATUS resolution
// TODO postGame