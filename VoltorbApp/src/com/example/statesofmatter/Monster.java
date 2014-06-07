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
    // Stats that change
    private int hp;
    private State state;
    private Status status;

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

    public Monster (String name, Type[] types, Attack[] attacks, 
                    int vitality, int speed, int physStr, 
                    int spiritStr, int intStr, int physEndur, 
                    int spiritEndur, int intEndur) {
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
    
    public Status getStatus() {
    	return status;
    }

    public void setState(State newState) {
        state = newState;
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
}
