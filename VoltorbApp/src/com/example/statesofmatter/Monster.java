package com.example.statesofmatter;

import java.util.ArrayList;

public class Monster {
    
    // Stats that change
    public int hp;
    public int state;

    // Permanent stats
    public String name;
    public ArrayList<String> types;
    public ArrayList<Attack> attacks;
    public int vitality;
    public int speed;
    public int physStr;
    public int spiritStr;
    public int intStr;
    public int physEndur;
    public int spiritEndur;
    public int intEndur;

    public Monster (String name) {
        this.name = name;
        state = 1;
    }
    
}
