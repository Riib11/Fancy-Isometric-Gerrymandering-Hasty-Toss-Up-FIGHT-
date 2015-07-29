package com.ngse.fight.specials;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ngse.fight.classes.Ability;

public class Cleavers extends Ability {

	public Cleavers() {
		super("Cleavers", 7, "cleav");
	}

	@Override
	public void effect(Player user, Player target) {

	}

	@Override
	public void effect(Player user) {

	}

	@Override
	public ItemStack getItem() {
		return setupItem(Material.SHEARS, this);
	}

}
