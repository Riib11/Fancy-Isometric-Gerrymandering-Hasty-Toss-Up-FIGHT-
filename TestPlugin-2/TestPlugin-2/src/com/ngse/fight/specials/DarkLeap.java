package com.ngse.fight.specials;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.ngse.fight.classes.PassiveAbility;
import com.ngse.utilities.Toggle;

public class DarkLeap extends PassiveAbility {

	public DarkLeap() {
		super("Dark Leap", 1, "darlea");
	}

	@Override
	public void passiveEffect(Player p) {
		p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 100));
		p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 100, 100));
		p.setAllowFlight(true);
	}

	@Override
	public void endPassiveEffect(Player p) {
		p.removePotionEffect(PotionEffectType.BLINDNESS);
		p.removePotionEffect(PotionEffectType.WEAKNESS);
		p.setAllowFlight(false);
	}

	@Override
	public void effect(Player user, Player target) {
		effect(user);
	}

	@Override
	public void effect(Player user) {
		togglePassiveAbility(user, this);
	}

	@Override
	public ItemStack getItem() {
		return setupItem(Material.BONE, this);
	}

}
