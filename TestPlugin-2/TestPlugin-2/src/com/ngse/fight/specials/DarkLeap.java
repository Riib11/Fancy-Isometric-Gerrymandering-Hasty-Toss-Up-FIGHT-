package com.ngse.fight.specials;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.ngse.fight.classes.PassiveAbility;
import com.ngse.utilities.Effects;
import com.ngse.utilities.Toggle;

public class DarkLeap extends PassiveAbility {

	public DarkLeap() {
		super("Dark Leap", 1, "darlea");
	}

	@Override
	public void passiveEffect(Player p) {
		p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 100));
		p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 100, 100));
		for (int x = 0; x <= 10; x++) {
			p.getWorld().playEffect(p.getLocation().add(0, 1, 0),
					Effect.MOBSPAWNER_FLAMES, 7);
		}
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
		Effects.play(user.getLocation(), Effect.LARGE_SMOKE, 10);
	}

	@Override
	public ItemStack getItem() {
		return setupItem(Material.BONE, this);
	}

}
