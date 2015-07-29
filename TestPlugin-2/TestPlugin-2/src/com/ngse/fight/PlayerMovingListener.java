package com.ngse.fight;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.ngse.fight.classes.Ability;
import com.ngse.fight.classes.FightClass;
import com.ngse.fight.classes.PassiveAbility;

public class PlayerMovingListener implements Listener {

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		FightClass f = FightClass.get(p);

		if (f != null) {
			// check passive abilities
			for (Ability a : f.getAbilities()) {
				if (a instanceof PassiveAbility) {
					if (((PassiveAbility) a).isActive(p)) {
						/*
						 * if can pay cost, passiveEffect keeps going
						 */
						// use ability
						if (a.useCost(p)) {
							((PassiveAbility) a).passiveEffect(p);
						} else {
							((PassiveAbility) a).endPassiveEffect(p);
							a.effect(p);
						}
					}
				}
			}
		}
	}
}
