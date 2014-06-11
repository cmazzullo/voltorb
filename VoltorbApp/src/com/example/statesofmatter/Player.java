package com.example.statesofmatter;

public class Player {

    public String name;
    private String ID;//get from Android phone
    private final int MAX_TEAM_SIZE = 6;
    
    public Monster[] team;
    
    public Player(String name, String ID){
    	this.name = name;
    	this.ID = ID;
    	team = new Monster[MAX_TEAM_SIZE];//only 6 monsters allowed
    }
    
    public void addMonster(Monster m, int position) throws Exception{
    	if(position >= MAX_TEAM_SIZE) throw new Exception("Position is exceeds team size limit.  position = " + position);
    	if(m == null) throw new Exception("Paramater Monster is null.");
    	team[position] = m;
    }
    
}
