package com.example.statesofmatter;

public interface ItemInterface {
	
	public enum ItemType {
		HEAL, CURE, ARMOR, BUFF, SPECIAL
	}
	
	//accessor/modifier methods
	public String getName();
	
	public ItemType getItemType();
	
	public Status getCureType();
	
	public int getHeal();
	
	//format and print methods
	@Override
	public String toString();
}
