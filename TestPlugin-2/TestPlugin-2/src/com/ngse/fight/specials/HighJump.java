package com.ngse.fight.specials;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ngse.fight.classes.Ability;
import com.ngse.utilities.Effects;

public class HighJump extends Ability {

	public HighJump() {
		super("High Jump", 15, "higjum");
	}

	@Override
	public void effect(Player user, Player target) {
		effect(user);
	}

	@Override
	public void effect(Player user) {
		user.getLocation().getDirection().setY(10);
		Effects.sound(user.getLocation(), Sound.ENDERMAN_TELEPORT);
	}

	@Override
	public ItemStack getItem() {
		return setupItem(Material.FEATHER, this);
	}

}
