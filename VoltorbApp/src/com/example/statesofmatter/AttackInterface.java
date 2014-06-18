package com.example.statesofmatter;

public interface AttackInterface {
		
    //accessor/modifier methods
	public String getName();
	
	public Element getElement();
	
	public int getBaseDamage();
	
	public Status getStatEffect();
	
	//attack applicator methods
	public void applyStatus(Monster monster, Status status);
	
	//string methods
	@Override
	public String toString();
	
	public void printAttack();
}
