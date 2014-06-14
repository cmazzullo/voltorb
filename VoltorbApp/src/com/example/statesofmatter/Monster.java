/**
 * Interface for a Monster object that has retrievable stats, hit 
 * points and a current state of matter.
 */
package com.example.statesofmatter;

public interface Monster {
    
    public enum State {
        SOLID, LIQUID, GAS, PLASMA
    }
    public enum Element {
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

    /* Boilerplate accessor/modifier methods */
    public int getHP();

    public void setHP(int newHP);

    public State getState();

    public void setState(State newState);
    
    public Status getStatus();
    
    public void setStatus(Status newStatus);
    
    public BuffDebuff getBuffDebuff();
    
    public void setBuffDebuff(BuffDebuff newBuffDebuff);
    
    public boolean isFainted();

    public String getName();    

    public Attack[] getAttacks();

    public Element[] getElements();

    public int getVitality();

    public int getSpeed();

    public int getPhysStr();

    public int getSpiritStr();

    public int getIntStr();

    public int getPhysEndur();

    public int getSpiritEndur();

    public int getIntEndur();
    
    public Item[] getEquip();

    public void printStatus();
}
