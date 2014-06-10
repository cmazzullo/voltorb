/**
 * Implements an item object which can be either wearable (permanent while equipped)
 *  or consumable (disappears after use)
 */
package com.example.statesofmatter;

public class Item {
	
	public enum Type {
		CURE, ARMOR, BUFF, SPECIAL
	}
}