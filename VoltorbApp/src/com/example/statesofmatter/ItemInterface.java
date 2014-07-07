package com.example.statesofmatter;

public interface ItemInterface {
	
	//accessor/modifier methods
	public String getName();
	
	public ItemType getItemType();
	
	public Status getCureType();
	
	public int getHeal();
	
	//format and print methods
	@Override
	public String toString();
}
