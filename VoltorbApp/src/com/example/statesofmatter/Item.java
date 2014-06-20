/**
 * Implements an item object which can be either wearable (permanent while equipped)
 *  or consumable (disappears after use)
 */
package com.example.statesofmatter;

public class Item implements ItemInterface {
	
	private String itemName;
	private ItemType itemType;
	private Status cure;
	private int heal;
	
	//constructor methods
	public Item (String name, ItemType itemType) {
		this.itemName = name;
		this.itemType = itemType;
	}
	
	public Item (String name, ItemType itemType, Status cure) {
		this.itemName = name;
		this.itemType = itemType;
		this.cure = cure;
	}
	
	public Item (String name, ItemType itemType, int heal) {
		this.itemName = name;
		this.itemType = itemType;
		this.heal = heal;
	}
	
	//accessor/modifier methods
	public String getName() {
		return itemName;
	}
	
	public ItemType getItemType() {
		return itemType;
	}
	
	public Status getCureType() {
		return cure;
	}
	
	public int getHeal() {
		return heal;
	}
	
	public void cureStatus(Monster monster) {
		Status currentStatus = monster.getStatus();
		if (currentStatus == this.cure) {
			monster.setStatus(Status.NORMAL);
		}
	}
	
	//format and print methods
	@Override
	public String toString() {
		if (itemType == ItemType.HEAL) {
			return String.format("%s:%s:%d",
								 itemName, itemType, heal);
		} else if (itemType == ItemType.CURE) {
			return String.format("%s:%s:%s",
					 			 itemName, itemType, cure);
		} else {
			return String.format("%s:%s", itemName, itemType);
		}
	}
}