package com.example.statesofmatter;

import java.util.HashMap;
import java.io.*;
import java.util.Scanner;


public class Database {
    
    public HashMap<String, Monster> MonsterMap;
    public HashMap<String, Attack> AttackMap;
    public HashMap<String, Item> ItemMap;
    private String attackFile;
    private String itemFile;
    private String monsterFile;

    public Database () {
        // We need to read in all the Monster and Attack data from
        // text files or something here
    }
    
    public Database (String attackFile, String itemFile, String monsterFile) {
    	this.attackFile = attackFile;
    	this.itemFile = itemFile;
    	this.monsterFile = monsterFile;
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
        throw new Exception("AttackMap or input String is null/empty");
    }
    
    public Item getItem (String name) throws Exception {
    	if (ItemMap != null 
    		&& !ItemMap.isEmpty() 
    		&& !"".equals(name)) 
    			return ItemMap.get(name);
        throw new Exception("ItemMap or input String is null/empty");
    }
    
    // read data from txt and add to HashMaps
    public void getData () throws FileNotFoundException {
    	Scanner sa = null;
    	Scanner si = null;
    	Scanner sm = null;
    	
    	try {
    		sa = new Scanner(new BufferedReader(new FileReader("AttackList.txt")));
    		si = new Scanner(new BufferedReader(new FileReader("ItemList.txt")));
    		sm = new Scanner(new BufferedReader(new FileReader("MonsterList.txt")));
    		AttackMap = new HashMap<String, Attack>();
    		ItemMap = new HashMap<String, Item>();
    		MonsterMap = new HashMap<String, Monster>();
    		
    		while (sa.hasNextLine()) {
    			String[] tempAttack = sa.nextLine().split(";");
    			Attack newAttack = new Attack(tempAttack[0], 
    										  Element.valueOf(tempAttack[1]),
    										  Integer.parseInt(tempAttack[2]));
    			AttackMap.put(tempAttack[0], newAttack);
    		} while (si.hasNextLine()) {
    			String[] tempItem = si.nextLine().split(";");
    			Item newItem;
    			if (tempItem.length == 3 && tempItem[2].length() <= 5) {
    				newItem = new Item(tempItem[0], 
    								   Item.ItemType.valueOf(tempItem[1]),
    								   Integer.parseInt(tempItem[2]));
    			} else if (tempItem.length == 3 && tempItem[2].length() > 5) {
    				newItem = new Item(tempItem[0], 
							  		   Item.ItemType.valueOf(tempItem[1]),
							  		   Status.valueOf(tempItem[2]));
    			} else {
    				newItem = new Item(tempItem[0], 
							  		   Item.ItemType.valueOf(tempItem[1]));
    			}
    			ItemMap.put(tempItem[0], newItem);
    		} while (sm.hasNextLine()) {	
    			String[] tempMonster = sm.nextLine().split(";");
    			String[] elementString = tempMonster[1].split(",");
    			Element[] monElements = new Element[elementString.length];
    			
    			for (int i = 0; i < elementString.length; i++) {
    				Element tempElement = Element.valueOf(elementString[i]);
    				monElements[i] = tempElement;
    			}
    			
    			String[] attString = tempMonster[2].split(",");
    			Attack[] monAttacks = new Attack[4];
    			for (int i = 0; i < attString.length; i++) {
    				Attack tempAttack = AttackMap.get(attString[i]);
    				monAttacks[i] = tempAttack;
    			}
    			
    			String[] itemString = tempMonster[11].split(",");
    			Item[] monItems = new Item[2];
    			for (int i = 0; i < itemString.length; i++) {
    				Item tempItem = ItemMap.get(itemString[i]);
    				monItems[i] = tempItem;
    			}
    			
    			Monster newMonster = new Monster(tempMonster[0], monElements, monAttacks,
    			 								Integer.parseInt(tempMonster[3]), Integer.parseInt(tempMonster[4]), 
    			 								Integer.parseInt(tempMonster[5]), Integer.parseInt(tempMonster[6]),
    			 								Integer.parseInt(tempMonster[7]), Integer.parseInt(tempMonster[8]), 
    			 								Integer.parseInt(tempMonster[9]), Integer.parseInt(tempMonster[10]),
    			 								monItems);
    			MonsterMap.put(tempMonster[0], newMonster);
    		}
    	} catch (FileNotFoundException e) {
    		throw new FileNotFoundException("Input file could not be found");
    	} finally {
			if (sm != null) {
				sm.close();
			} if (si != null) {
				si.close();
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