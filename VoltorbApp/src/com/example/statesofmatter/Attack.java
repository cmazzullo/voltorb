package com.example.statesofmatter;


public class Attack implements AttackInterface {

    private String name;
    private Element element;
    private int baseDamage;
    private Status stEffect;
    
    public Attack(String name, Element element, int baseDamage) {
    	this.name = name;
    	this.element = element;
    	this.baseDamage = baseDamage;
    }
    
    public Attack(String name, Element element, int baseDamage, Status stEffect) {
    	this.name = name;
    	this.element = element;
    	this.baseDamage = baseDamage;
    	this.stEffect = stEffect;
    }

    //accessor/modifier methods
	public String getName() {
		return name;
	}
	
	public Element getElement() {
		return element;
	}
	
	public int getBaseDamage () {
		return baseDamage;
	}
	
	public Status getStatEffect() {
		return stEffect;
	}

	//attack applicator methods
	public void applyStatus(Monster monster, Status status) {
		Status currentStatus = monster.getStatus();
		if (currentStatus == Status.NORMAL) {
			monster.setStatus(status);
		}
	}
	
	//string methods
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
