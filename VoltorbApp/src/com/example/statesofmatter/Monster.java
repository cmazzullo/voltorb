/**
 * Implements a monster object which contains stats (immutable)
 * as well as HP, state, status, and fainted values (mutable).
 */
package com.example.statesofmatter;

import java.util.Arrays;

public class Monster implements MonsterInterface {

    // Stats that change
    private int currentHP;
    private State state;
    private Status status;
    private BuffDebuff buffdebuff;

    // Permanent stats
    private String name;
    private Element[] elements;
    private Attack[] attacks;
    private int maxHP;
    private int speed;
    private int physStr;
    private int spiritStr;
    private int intStr;
    private int physEndur;
    private int spiritEndur;
    private int intEndur;
    
    // Equipables
    private Item[] equipment;

    public Monster (String name, Element[] elements, Attack[] attacks, 
                    int maxHP, int speed, int physStr, 
                    int spiritStr, int intStr, int physEndur, 
                    int spiritEndur, int intEndur, Item[] equipment) {
        this.name = name;
        this.elements = elements;
        this.attacks = attacks;
        this.maxHP = maxHP;
        this.speed = speed;
        this.physStr = physStr;
        this.spiritStr = spiritStr;
        this.intStr = intStr;
        this.physEndur = physEndur;
        this.spiritEndur = spiritEndur;
        this.intEndur = intEndur;

        this.currentHP = maxHP;
        state = State.SOLID;
        status = Status.NORMAL;
        buffdebuff = BuffDebuff.NONE;
        
        this.equipment = equipment;
        equipment = new Item[2];
    }

    /* Boilerplate accessor/modifier methods */
    public int getHP() {
        return currentHP;
    }

    public void setHP(int newHP) {
        currentHP = newHP;
    }

    public State getState() {
        return state;
    }

    public void setState(State newState) {
        state = newState;
    }
    
    public Status getStatus() {
    	return status;
    }
    
    public void setStatus(Status newStatus) {
    	status = newStatus;
    }
    
    public BuffDebuff getBuffDebuff() {
    	return buffdebuff;
    }
    
    public void setBuffDebuff(BuffDebuff newBuffDebuff) {
    	buffdebuff = newBuffDebuff;
    }
    
    public boolean isFainted() {
        return (currentHP <= 0);
    }

    public String getName() {
        return name;
    }    

    public Attack[] getAttacks() {
        return attacks;
    }

    public Element[] getElements() {
        return elements;
    }

    public int getmaxHP() {
        return maxHP;
    }

    public int getSpeed() {
        return speed;
    }

    public int getPhysStr() {
        return physStr;
    }

    public int getSpiritStr() {
        return spiritStr;
    }

    public int getIntStr() {
        return intStr;
    }

    public int getPhysEndur() {
        return physEndur;
    }

    public int getSpiritEndur() {
        return spiritEndur;
    }

    public int getIntEndur() {
        return intEndur;
    }
    
    public Item[] getEquip() {
    	return equipment;
    }

    @Override
    public String toString() {
    	String[] attackNames = new String[4];
    	String[] itemNames = new String[2];
		for (int i = 0; i < attacks.length; i++) {
			if (attacks[i] != null) {
				attackNames[i] = attacks[i].getName();
			}
		}
		for (int i = 0; i < equipment.length; i++) {
			if (equipment[i] != null) {
				itemNames[i] = equipment[i].getName();
			}
		}
    	return String.format("name = %s%n" +
                	  		 "element = %s%n" +
                	  		 "state = %s%n" +
                	  		 "attacks = %s%n" +
                	  		 "HP = %d%n" +
                	  		 "maxHP = %d%n" +
                	  		 "speed = %d%n" +
                	  		 "physStr = %d%n" +
                	  		 "spiritStr = %d%n" +
                	  		 "intStr = %d%n" +
                	  		 "physEndur = %d%n" +
                	  		 "spiritEndur = %d%n" +
                	  		 "intEndur = %d%n" +
                	  		 "equipped items = %s%n%n",
                	  		 name, Arrays.toString(elements), state, Arrays.toString(attackNames), 
                	  		 currentHP, maxHP, speed, physStr, spiritStr, intStr, 
                	  		 physEndur, spiritEndur, intEndur, Arrays.toString(itemNames));
    }

    public void printStatus() {
    	String[] attackNames = new String[4];
    	String[] itemNames = new String[2];
		for (int i = 0; i < attacks.length; i++) {
			if (attacks[i] != null) {
				attackNames[i] = attacks[i].getName();
			}
		}
		for (int i = 0; i < equipment.length; i++) {
			if (equipment[i] != null) {
				itemNames[i] = equipment[i].getName();
			}
		}
        System.out.printf("name = %s%n" +
                          "element = %s%n" +
                          "state = %s%n" +
                          "attacks = %s%n" +
        				  "HP = %d%n" +
                          "maxHP = %d%n" +
                          "speed = %d%n" +
                          "physStr = %d%n" +
                          "spiritStr = %d%n" +
                          "intStr = %d%n" +
                          "physEndur = %d%n" +
                          "spiritEndur = %d%n" +
                          "intEndur = %d%n" +
                          "equipped items = %s%n%n",
                          name, Arrays.toString(elements), state, Arrays.toString(attacks), 
                          currentHP, maxHP, speed, physStr, spiritStr, intStr, 
                          physEndur, spiritEndur, intEndur, Arrays.toString(equipment));
    }
    
    //methods to check and resolve a Monster's status at the
    //beginning of each turn
    /*private void checkStatus(Status currentStatus, int[] currentEquip) {
    	currentStatus = this.getStatus();
    	currentEquip = this.getEquip();
    	if (currentStatus != Status.NORMAL) {
    		//count duration
    	}
    	if (currentStatus != Status.NORMAL) {
    		for (int i=0; i<currentEquip.length; i++) {
    			//if (currentEquip[i].getType() == Item.CURE && 
    				//currentEquip[i].getCureType == currentStatus.getStatus()) {
    				//this.useItem(currentEquip[i];
    			//}
    		}
    	}
    }*/
}
