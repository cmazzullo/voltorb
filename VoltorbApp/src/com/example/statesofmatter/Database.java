package com.example.statesofmatter;

import java.util.HashMap;
import java.io.*;
import java.util.Scanner;


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
    
    public void getData () throws FileNotFoundException{
    	Scanner s = null;
    	
    	try {
    		s = new Scanner(new BufferedReader(new FileReader("MonsterList.txt")));
    		s.useDelimiter(",");
    		MonsterMap = new HashMap<String, Monster>();
    		//Monster newMonster = new Monster(s.next(),  (s.next()));
    		
    		while (s.hasNextLine()) {
    			String monster = s.nextLine();
    			String[] temp = monster.split(";");
    			String[] typeString = temp[1].split(",");
    			Monster.Element[] types = new Monster.Element[typeString.length];
    			for (int i = 0; i < typeString.length; i++) {
    				Monster.Element tempEnum = Monster.Element.valueOf(typeString[i]);
    				types[i] = tempEnum;
    			}
    			Monster newMonster = new Monster(temp[0], types, temp[2].split(","),
    			 								Integer.parseInt(temp[3]), Integer.parseInt(temp[4]), 
    			 								Integer.parseInt(temp[5]), Integer.parseInt(temp[6]),
    			 								Integer.parseInt(temp[7]), Integer.parseInt(temp[8]), 
    			 								Integer.parseInt(temp[9]), Integer.parseInt(temp[10]));
    			MonsterMap.put(temp[0], newMonster);
    			newMonster.printStatus();
    		}
    	} catch (FileNotFoundException e) {
    		System.err.println("File does not exist");
    		throw new FileNotFoundException("Input file could not be found");
    	} finally {
			if (s != null) {
				s.close();
			}
    	}
        // read data from txt and add to HashMaps
    }
    public static void main(String[] Args) throws FileNotFoundException {
    	Database test = new Database();
    	test.getData();
    }
}
