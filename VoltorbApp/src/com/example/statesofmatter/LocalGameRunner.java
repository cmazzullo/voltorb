/**
 * LocalGameRunner contains (will contain) the game logic for a client
 * playing States of Matter. It interfaces with the UserInterface on
 * the frontend and gets the other player's actions with Server on
 * the backend.
 */

package com.example.statesofmatter;

import java.io.*;

public class LocalGameRunner {
	
	public String attackFile;
	public String itemFile;
	public String monsterFile;
	private Database d;
	public static FakeServer server = new FakeServer(); //until connect function in SoMStart works
	private String playerID;
	private Player player;
	protected Monster[] team;
	protected Monster myLead;
	protected Attack[] attacks;
	private static Turn myTurn;
	private PlayerAction action = PlayerAction.PASS;
	private int argument;
	
	public void start() throws Exception, FileNotFoundException {
		playerID = this.fetchID();
		d = new Database(attackFile, itemFile, monsterFile);
		d.getData();
		player = new Player(server.getPlayerName(playerID), playerID);
	}
	
	public String fetchID() throws IOException, FileNotFoundException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("PlayerID.txt"));
			playerID = br.readLine();		
			return playerID;
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("Input file could not be found");
		} finally {
			if (br != null)
				br.close();
		}
	}
	
	public void setAttFile(String attackFile) {
		this.attackFile = attackFile;
	}
	
	public void setItemFile(String itemFile) {
		this.itemFile = itemFile;
	}
	
	public void setMonFile(String monsterFile) {
		this.monsterFile = monsterFile;
	}
	
	public Database getDbase() {
		return d;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void addToTeam(String monster, int fromIndex) throws Exception{
		int count = 0;
		for (Monster m : player.getTeam()) {
			if (m != null)
				count++;
		}
		if (count < player.MAX_TEAM_SIZE && fromIndex >= 0) {
			Monster newMonster = d.MonsterMap.get(monster);
			boolean added = false;
			int indexToAdd = 0;
			while (!added) {
				if (player.getTeam()[indexToAdd] == null) {
					player.addMonster(newMonster, indexToAdd);
					added = true;
				} else {
					indexToAdd++;
				}
			}
		}
	}
	
	public void removeFromTeam(String monster, int fromIndex) throws Exception {
		int count = 0;
		for (Monster m : player.getTeam()) {
			if (m != null)
				count++;
		} 
		if (count > 0 && fromIndex >= 0) {
			Monster[] updatedTeam = new Monster[6];
			Monster toRemove = d.MonsterMap.get(monster);
			int nextIndex = 0;
			player.removeMonster(toRemove, fromIndex);
			for (Monster m : player.getTeam()) {
				if (m != null) {
					updatedTeam[nextIndex] = m;
					nextIndex++;
				}
			}
			player.setTeam(updatedTeam);
		}
	}
	
    //All of these methods should go into a separate game logic class
    public static boolean isGameOver(Player player, FakeServer server) {
	
        return (player.allFainted() ||
		server.getOpponent().allFainted());
    }
    
    private static void doTurns(Player player, FakeServer server) {
    	//Turn myTurn = TextUserInterface.getTurn();
        Turn oppTurn = server.getTurn(server.getPlayer(""));
        Monster myLead = player.getLead();
        Monster oppLead = server.getOpponent().getLead();
        //calculate turn
        PlayerAction myAction = myTurn.getAction();
        PlayerAction oppAction = oppTurn.getAction();
        boolean myComplete = false;
        boolean oppComplete = false;
        
        //make speed comparison
        
        boolean meFirst = false;
        if(myLead.getSpeed() > oppLead.getSpeed()){
        	meFirst = true;
        }else if (myLead.getSpeed() == oppLead.getSpeed()) {
        	//TODO:  What to do during speed tie
        	//send isTied boolean to server, server does tie-breaker only,
        	//sends result back to clients. First client executes turn and sends
        	//results to server, to second client, second client goes.
        }
        
        if(myAction == PlayerAction.SWITCH || oppAction == PlayerAction.SWITCH){
        	if(meFirst && myAction == PlayerAction.SWITCH){
        		player.switchLead(myTurn.getArgument());
        		myLead = player.getLead();
        		myComplete = true;
        	}
        	if(oppAction == PlayerAction.SWITCH){
        		//switch
        		oppComplete = false;
        	}
        	if(!meFirst && myAction == PlayerAction.SWITCH){
        		//switch
        		myComplete = true;
        	}
        }
        
        if(!myComplete && meFirst){
        	if(myAction == PlayerAction.ATTACK){
        	}
        	else if(myAction == PlayerAction.ITEM){
        	}
        	else if(myAction == PlayerAction.STATE_SHIFT){
        	}
        	else if(myAction == PlayerAction.PASS){
        	}
        }
        
        if(!oppComplete){
        	if(oppAction == PlayerAction.ATTACK){
        	}
        	else if(oppAction == PlayerAction.ITEM){
        	}
        	else if(oppAction == PlayerAction.STATE_SHIFT){
        	}
        	else if(oppAction == PlayerAction.PASS){
        	}      	
        }
        
        if(!myComplete && !meFirst){
        	if(myAction == PlayerAction.ATTACK){
        	}
        	else if(myAction == PlayerAction.ITEM){
        	}
        	else if(myAction == PlayerAction.STATE_SHIFT){
        	}
        	else if(myAction == PlayerAction.PASS){
        	}
        }
    }

    private static void doPostGame() {
        
    }

    public static void main(String[] args) {
        //need to put playerdata in here
        //both players need to act at the same time
    	String ID1 = "";
    	String ID2 = "";
    	String myName = "";
        FakeServer server = new FakeServer();
        Player me = server.getPlayer(ID1);
        Player opponent = server.getPlayer(ID2);
        while (!isGameOver(me, server)) {
            doTurns(me, server);
            //opponentTurn.executeTurn(opponent);
        }
        doPostGame();
    }
}
