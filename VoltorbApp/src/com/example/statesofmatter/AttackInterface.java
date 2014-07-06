package com.example.statesofmatter;

public interface AttackInterface {
		
    //accessor/modifier methods
	public String getName();
	
	public Element getElement();
	
	public int getBaseDamage();
	
	public Status getStatEffect();
	
	//string methods
	@Override
	public String toString();
	
	public void printAttack();
}
