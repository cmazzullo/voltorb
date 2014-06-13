package com.example.statesofmatter;


public class Attack {

    public enum Element {
        FIRE, WATER, AIR, EARTH, DARK, LIGHT
    }    

    private String name;
    private Element element;
    private int baseDamage;
    
    public Attack(String name, Element element, int baseDamage) {
    	this.name = name;
    	this.element = element;
    	this.baseDamage = baseDamage;
    }

    //get and set methods
	public String getName() {
		return name;
	}
	
	public Element getElement() {
		return element;
	}
	
	public int getBaseDamage () {
		return baseDamage;
	}
	
	@Override
	public String toString() {
		return String.format("%s:%s:%d",
							 name, element, baseDamage);
	}
	
	public void printAttack() {
		System.out.printf("attack name = %s%n" +
						  "attack element = %s%n" +
						  "base damage = %d%n%n",
						  name, element, baseDamage);
	}
}
