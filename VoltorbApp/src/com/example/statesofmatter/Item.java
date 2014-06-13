/**
 * Implements an item object which can be either wearable (permanent while equipped)
 *  or consumable (disappears after use)
 */
package com.example.statesofmatter;

public class Item {
	
	public enum ItemType {
		CURE, ARMOR, BUFF, SPECIAL
	}
	
	public enum CureType {
		CURE_HP, CURE_PSN, CURE_SLP, CURE_PRLZ
	}
	
	private String itemName;
	private ItemType itemType;
	private CureType cure;
	private int heal;
	
	//constructor methods
	public Item (String name, ItemType itemType) {
		this.itemName = name;
		this.itemType = itemType;
	}
	
	public Item (String name, ItemType itemType, CureType cure) {
		this.itemName = name;
		this.itemType = itemType;
		this.cure = cure;
	}
	
	public Item (String name, ItemType itemType, CureType cure, int heal) {
		this.itemName = name;
		this.itemType = itemType;
		this.cure = cure;
		this.heal = heal;
	}
	
	//accessor/modifier methods
	public String getName() {
		return itemName;
	}
	
	public ItemType getItemType() {
		return itemType;
	}
	
	public CureType getCureType() {
		return cure;
	}
	
	public int getHeal() {
		return heal;
	}
	
	//format and print methods
	@Override
	public String toString() {
		if (cure == CureType.CURE_HP) {
			return String.format("%s:%s:%s:%d",
								 itemName, itemType, cure, heal);
		} else if (cure != CureType.CURE_HP && cure != null) {
			return String.format("%s:%s:%s",
					 			 itemName, itemType, cure);
		} else {
			return String.format("%s:%s", itemName, itemType);
		}
	}
}