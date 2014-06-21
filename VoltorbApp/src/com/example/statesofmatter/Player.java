package com.example.statesofmatter;

public class Player implements PlayerInterface {

    private String name;
    private String ID;//get from Android phone
    private final int MAX_TEAM_SIZE = 6;
    
    private Monster[] team;
    //The lead is the Player's currently active Monster
    private Monster lead;
    
    public Player(String name, String ID){
    	this.name = name;
    	this.ID = ID;
    	team = new Monster[MAX_TEAM_SIZE];//only 6 monsters allowed
    }
    
    public String getName() {
    	return name;
    }

    public String getID() {
    	return ID;
    }

    public Monster[] getTeam() {
    	return team;
    }

    public Monster getLead() {
    	return lead;
    }

    public void setLead(Monster m) {
    	lead = m;
    }

    public Boolean allFainted() {

	for (int i = 0; i < MAX_TEAM_SIZE; i++) {
	    Monster m = team[i];
	    if (m.getHP() > 0) {
		return false;
	    }
	}
	return true;
    }
    
    public void addMonster(Monster m, int position) throws Exception {
    	if(position >= MAX_TEAM_SIZE) throw new Exception("Position is exceeds team size limit.  position = " + position);
    	if(m == null) throw new Exception("Paramater Monster is null.");
    	team[position] = m;
    }
    
    public void removeMonster(Monster m, int position) throws Exception {
    	if(position == 0) throw new Exception("There are no monster on your team.");
    	if(m == null) throw new Exception("Paramater Monster is null.");
    	team[position] = null;
    }
}
