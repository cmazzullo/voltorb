package com.example.statesofmatter;

import java.io.Serializable;


public class Attack implements AttackInterface, Serializable {

    private String name;
    private Element element;
    private int baseDamage;
    private Status stEffect;
    private BuffDebuff debEffect;
    private BuffDebuff bufEffect;
    
    public Attack(String name, Element element, int baseDamage, 
    			  Status stEffect, BuffDebuff debEffect, BuffDebuff bufEffect) {
    	this.name = name;
    	this.element = element;
    	this.baseDamage = baseDamage;
    	this.stEffect = stEffect;
    	this.debEffect = debEffect;
    	this.bufEffect = bufEffect;
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
	
	public BuffDebuff getDebEffect() {
		return debEffect;
	}
	
	public BuffDebuff getbufEffect() {
		return bufEffect;
	}

	//attack applicator methods
	private void applyDamage(Monster myMonster, Monster oppMonster) {
		int damage = baseDamage; //TODO put real equation here later
		oppMonster.setHP(oppMonster.getHP() - damage);
		if (oppMonster.getHP() < 0) {
			oppMonster.setHP(0);
		}
	}
	
	private void applyStatus(Monster oppMonster, Status status) {
		if (oppMonster.getStatus() == Status.NORMAL) {
			//TODO calculate if status should be applied, maybe use Str/Endur check
			oppMonster.setStatus(status);
		}
	}
	
	
	private void applyDebuff(Monster oppMonster, BuffDebuff debuff) {
		// TODO determine if debuff can be applied
		oppMonster.setBuffDebuff(debuff);
	}

	private void applyBuff(Monster myMonster, BuffDebuff buff) {
		// TODO determine in buff can be applied
		myMonster.setBuffDebuff(buff);
	}
	
	public void applyAttack(Monster myMonster, Monster oppMonster) {
		// TODO Auto-generated method stub
		this.applyDamage(myMonster, oppMonster);
		if (!oppMonster.isFainted()) {
			this.applyStatus(oppMonster, stEffect);
			this.applyDebuff(oppMonster, debEffect);
		}
		this.applyBuff(myMonster, bufEffect);
	}

	//string methods
	@Override
	public String toString() {
		return String.format("attack name = %s%n" +
							 "attack element = %s%n" +
							 "base damage = %d%n" +
							 "status effect = %s%n" +
							 "debuff effect = %s%n" +
							 "buff effect = %s%n",
							 name, element, baseDamage, stEffect, debEffect,
							 bufEffect);
	}
	
	public void printAttack() {
		System.out.printf("attack name = %s%n" +
						  "attack element = %s%n" +
						  "base damage = %d%n" +
						  "status effect = %s%n" +
						  "debuff effect = %s%n" +
						  "buff effect = %s%n",
						  name, element, baseDamage, stEffect, debEffect,
						  bufEffect);
	}

}
