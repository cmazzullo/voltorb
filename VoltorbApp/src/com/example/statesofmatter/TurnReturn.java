package com.example.statesofmatter;

public class TurnReturn {
	
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
	

	
}
