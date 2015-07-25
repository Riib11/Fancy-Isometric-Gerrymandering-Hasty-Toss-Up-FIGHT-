package com.ngse.fight.specials;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ngse.fight.classes.Ability;

public class TeleSwitch extends Ability {

	public TeleSwitch() {
		super("Teleswitch", 3, false, "telswi");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void effect(Player user, Player target) {
		Location pla = user.getLocation();
		Location tar = target.getLocation();

		// switch places
		user.teleport(tar);
		target.teleport(pla);
	}

	@Override
	public void effect(Player user) {
		// nothing, needs targetF
	}

	@Override
	public ItemStack getItem() {
		setupItem(Material.ARROW, this);
		return null;
	}

}