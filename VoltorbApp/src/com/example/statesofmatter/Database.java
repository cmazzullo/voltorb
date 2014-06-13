package com.example.statesofmatter;

import java.util.HashMap;
import java.io.*;
import java.util.Scanner;
import java.util.Arrays;


public class Database {
    
    private HashMap<String, Monster> MonsterMap;
    private HashMap<String, Attack> AttackMap;

    public Database () {
        // We need to read in all the Monster and Attack data from
        // text files or something here
    }
 
    public Monster getMonster (String name) throws Exception {
    	if (MonsterMap != null 
    		&& !MonsterMap.isEmpty() 
    		&& !"".equals(name)) 
    			return MonsterMap.get(name);
        throw new Exception("MonsterMap or input String is null/empty");
    }
    
    public Attack getAttack (String name) throws Exception {
    	if (AttackMap != null 
    		&& !AttackMap.isEmpty() 
    		&& !"".equals(name)) 
    			return AttackMap.get(name);
        throw new Exception("MonsterMap or input String is null/empty");
    }
    
    // read data from txt and add to HashMaps
    public void getData () throws FileNotFoundException {
    	Scanner sa = null;
    	Scanner sm = null;
    	
    	try {
    		sa = new Scanner(new BufferedReader(new FileReader("AttackList.txt")));
    		sm = new Scanner(new BufferedReader(new FileReader("MonsterList.txt")));
    		AttackMap = new HashMap<String, Attack>();
    		MonsterMap = new HashMap<String, Monster>();
    		
    		while (sa.hasNextLine()) {
    			String[] tempAttack = sa.nextLine().split(";");
    			Attack newAttack = new Attack(tempAttack[0], 
    										  Attack.Element.valueOf(tempAttack[1]),
    										  Integer.parseInt(tempAttack[2]));
    			AttackMap.put(tempAttack[0], newAttack);
    		} while (sm.hasNextLine()) {	
    			String[] tempMonster = sm.nextLine().split(";");
    			String[] elementString = tempMonster[1].split(",");
    			Monster.Element[] monElements = new Monster.Element[elementString.length];
    			
    			for (int i = 0; i < elementString.length; i++) {
    				Monster.Element tempElement = Monster.Element.valueOf(elementString[i]);
    				monElements[i] = tempElement;
    			}
    			
    			String[] attString = tempMonster[2].split(",");
    			Attack[] monAttacks = new Attack[4];
    			for (int i = 0; i < attString.length; i++) {
    				Attack tempAttack = AttackMap.get(attString[i]);
    				monAttacks[i] = tempAttack;
    			}
    			
    			Monster newMonster = new Monster(tempMonster[0], monElements, monAttacks,
    			 								Integer.parseInt(tempMonster[3]), Integer.parseInt(tempMonster[4]), 
    			 								Integer.parseInt(tempMonster[5]), Integer.parseInt(tempMonster[6]),
    			 								Integer.parseInt(tempMonster[7]), Integer.parseInt(tempMonster[8]), 
    			 								Integer.parseInt(tempMonster[9]), Integer.parseInt(tempMonster[10]));
    			MonsterMap.put(tempMonster[0], newMonster);
    			newMonster.printStatus();
    		}
    	} catch (FileNotFoundException e) {
    		throw new FileNotFoundException("Input file could not be found");
    	} finally {
    		System.out.println(MonsterMap.size());
			if (sm != null) {
				sm.close();
			} if (sa != null) {
				sa.close();
			}
    	}
    }
    
    public static void main(String[] Args) throws FileNotFoundException {
    	Database test = new Database();
    	test.getData();
    }
}