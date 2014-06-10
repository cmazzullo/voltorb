/**
 * Implements a monster object which contains stats (immutable)
 * as well as HP, state, status, and fainted values (mutable).
 */
package com.example.statesofmatter;

import java.util.ArrayList;

public class Monster {
    
    public enum State {
        SOLID, LIQUID, GAS, PLASMA
    }
    public enum Type {
        FIRE, WATER, AIR, EARTH, DARK, LIGHT
    }
    public enum Status {
    	NORMAL, POISON, SLEEP, PARALYSIS //etc, add more here
    }
    public enum BuffDebuff {
    	NONE, DE_SPEED, DE_PSTR, DE_SSTR, DE_ISTR, DE_PEND, 
    	DE_SEND, DE_IEND, BUF_SPEED, BUF_PSTR, BUF_SSTR, 
    	BUF_ISTR, BUF_PEND, BUF_SEND, BUF_IEND
    }
    // Stats that change
    private int hp;
    private State state;
    private Status status;
    private BuffDebuff buffdebuff;

    // Permanent stats
    private String name;
    private Type[] types;
    private Attack[] attacks;
    private int vitality;
    private int speed;
    private int physStr;
    private int spiritStr;
    private int intStr;
    private int physEndur;
    private int spiritEndur;
    private int intEndur;
    
    // Equipables
    private int[] equipment;

    public Monster (String name, Type[] types, Attack[] attacks, 
                    int vitality, int speed, int physStr, 
                    int spiritStr, int intStr, int physEndur, 
                    int spiritEndur, int intEndur, int[] equipment) {
        this.name = name;
        this.types = types;
        this.attacks = attacks;
        this.vitality = vitality;
        this.speed = speed;
        this.physStr = physStr;
        this.spiritStr = spiritStr;
        this.intStr = intStr;
        this.physEndur = physEndur;
        this.spiritEndur = spiritEndur;
        this.intEndur = intEndur;

        this.hp = vitality;
        state = State.SOLID;
        status = Status.NORMAL;
        buffdebuff = BuffDebuff.NONE;
        
        this.equipment = equipment;
        equipment = new int[2];
        
    }

    /* Boilerplate accessor/modifier methods */
    public int getHP() {
        return hp;
    }

    public void setHP(int newHP) {
        hp = newHP;
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
        return (hp <= 0);
    }

    public String getName() {
        return name;
    }    

    public Attack[] getAttacks() {
        return attacks;
    }

    public Type[] getTypes() {
        return types;
    }

    public int getVitality() {
        return vitality;
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
    
    public int[] getEquip() {
    	return equipment;
    }

    public void printStatus() {
        System.out.printf("name = %s%n" +
                          "HP = %d%n" +
                          "vitality = %d%n" +
                          "speed = %d%n" +
                          "physStr = %d%n" +
                          "spiritStr = %d%n" +
                          "intStr = %d%n" +
                          "physEndur = %d%n" +
                          "spiritEndur = %d%n" +
                          "intEndur = %d%n",
                          name, hp, vitality, speed, physStr, 
                          spiritStr, intStr, physEndur, spiritEndur, 
                          intEndur);
    }
    
    //methods to check and resolve a Monster's status at the
    //beginning of each turn
    private void checkStatus(Status currentStatus, int[] currentEquip) {
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
    }
}
