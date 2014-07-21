package com.example.statesofmatter;

import java.io.Serializable;

public class TurnReturn implements Serializable {
	
	private Monster[] leads = new Monster[2];
	private int fainted = 0; //0 = no one fainted, 1 = player 1 fainted, 2 = player 2 fainted, 3 = both fainted
	private int turnFinished = 0; //0 = not started, 1 = mid-turn, 2 = finished
	
	
	public Monster[] getLeads() {
		return leads;
	}
	
	public void setLeads(Monster[] leads) {
		this.leads = leads;
	}
	
	public int getFainted() {
		return fainted;
	}
	
	public void setFainted(int fainted) {
		this.fainted = fainted;
	}
	
	public int getTurnFinished() {
		return turnFinished;
	}
	
	public void setTurnFinished(int turnFinished) {
		this.turnFinished = turnFinished;
	}
	
	public String toString() {
		return String.format("1st monster: %s, HP: %d%n" +
							 "2nd monster: %s, HP: %d%n" +
							 "Fainted: %d%n" +
							 "turnFinished: %d%n%n", leads[0].getName(), leads[0].getHP(), 
							 leads[1].getName(), leads[1].getHP(), fainted, turnFinished);
	}
	
}
