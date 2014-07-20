/**
 * Interface for a Monster object that has retrievable stats, hit 
 * points and a current state of matter.
 */
package com.example.statesofmatter;

public interface MonsterInterface {
    
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

    public int getMaxHP();

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
