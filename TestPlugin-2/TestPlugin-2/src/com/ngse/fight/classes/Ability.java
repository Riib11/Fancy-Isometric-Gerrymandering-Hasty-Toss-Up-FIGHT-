package com.ngse.fight.classes;

import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import com.ngse.fight.FightPlayer;

public abstract class Ability {

	public FightPlayer user;

	public boolean passive;
	public ItemStack itemsRecieved;

	public Ability(FightPlayer user, boolean passive, ItemStack itemsRecieved) {
		super();
		this.user = user;
		this.passive = passive;
		this.itemsRecieved = itemsRecieved;
	}

	public FightPlayer getUser() {
		return user;
	}

	public void setUser(FightPlayer user) {
		this.user = user;
	}

	public boolean isPassive() {
		return passive;
	}

	public void setPassive(boolean passive) {
		this.passive = passive;
	}

	public ItemStack getItemsRecieved() {
		return itemsRecieved;
	}

	public void setItemsRecieved(ItemStack itemsRecieved) {
		this.itemsRecieved = itemsRecieved;
	}

	public abstract void specificEffect();

}
