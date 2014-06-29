/**
 * LocalGameRunner contains (will contain) the game logic for a client
 * playing States of Matter. It interfaces with the UserInterface on
 * the frontend and gets the other player's actions with Server on
 * the backend.
 */

package com.example.statesofmatter;

import java.io.*;
import java.net.*;
import java.util.Scanner;

import android.annotation.TargetApi;

@TargetApi(19)
public class LocalGameRunner implements Runnable {
	
	private boolean running = false;
	private Thread lgrThread;
	
	static Socket socket;
	static ObjectInputStream in;
	static ObjectOutputStream out;
	
	private String attackFile;
	private String itemFile;
	private String monsterFile;
	private Database d;
	private static String playerID;
	private static String playerName;
	private String oppID;
	private static Player player;
	//private Monster[] team;
	private Monster myLead;
	private Monster oppLead;
	//protected Attack[] attacks;
	//private Turn myTurn;
	private PlayerAction action = PlayerAction.PASS;
	private int argument;
	private Turn oppTurn;
	
	
	
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
	
	public PlayerAction getAction() {
		return action;
	}
	
	public void setAction(PlayerAction action) {
		this.action = action;
	}
	
	public int getArgument() {
		return argument;
	}
	
	public void setArgument(int argument) {
		this.argument = argument;
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
    private void doTurns(Player player) {
    	Turn myTurn = new Turn(action, argument);
        //Turn oppTurn = FakeServerLogic.getTurn(FakeServerLogic.getPlayer(oppID));
        myLead = player.getLead();
        //oppLead = FakeServerLogic.getPlayer(oppID).getLead();
        //calculate turn
        PlayerAction myAction = action;
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
        	//FakeServerLogic.isTie(player);
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
    
    public void start() throws Exception, FileNotFoundException {
    	running = true;
    	lgrThread = new Thread(this);
    	lgrThread.start();
		fetchPlayerInfo();
		d = new Database(attackFile, itemFile, monsterFile);
		d.getData();
		player = new Player(playerName, playerID);
	}
	
	public void fetchPlayerInfo() throws IOException, FileNotFoundException {
		try (Scanner sc = new Scanner(new BufferedReader(new FileReader("PlayerID.txt")));) {
			sc.useDelimiter(":");
			playerID = sc.next();		
			playerName = sc.next();
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("Input file could not be found");
		}
	}
	
	public static void connect(String host, int port) throws IOException {
		System.out.println("Trying to connect...");
		try {
			socket = new Socket(host, port);
			System.out.println("Connected");
			out = new ObjectOutputStream(socket.getOutputStream());
			System.out.println("vegetables");
			in = new ObjectInputStream(socket.getInputStream());
			Input input = new Input(in);
			Thread thread = new Thread(input);
			thread.start();
			System.out.println("bananas");
    	} catch (UnknownHostException e) {
            System.err.println("Unknown host " + host);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + host);
            System.exit(1);
        }
	}
    
	public void run() {
		while (true) {
			try {
				System.out.println("hmm");
				out.writeUTF(playerID);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
    public static void main(String[] args) throws IOException, Exception {
    	LocalGameRunner runner = new LocalGameRunner();
        runner.start();
        System.out.println(playerID + ":" + playerName);
        
    	String host = "localhost";
    	int port = 4444;
    	connect(host, port);
    	
        while (in.read() != -1) {
            //runner.doTurns(player);
            //opponentTurn.executeTurn(opponent);
        }
        doPostGame();
    }
}

class Input implements Runnable {

	ObjectInputStream in;
	
	public Input(ObjectInputStream in) {
		this.in = in;
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				System.out.println(in.readUTF());
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
