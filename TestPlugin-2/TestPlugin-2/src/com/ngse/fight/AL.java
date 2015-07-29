package com.ngse.fight;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.bukkit.entity.Player;

import com.ngse.fight.classes.FightClass;
import com.ngse.utilities.Energy;
import com.ngse.utilities.Title;

// this is the actionlistenr for the timer in FIGHT
// this actionPerformed() is for recharging energy

public class AL implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		Title t = new Title("");
		// recharge energy
		for (Player p : FIGHT.plugin.getServer().getOnlinePlayers()) {
			FightClass f = FightClass.get(p);
			if (f != null) {
				if (Energy.get(p) >= FightClass.get(p).getmaxEnergy()) {
					Energy.set(p, FightClass.get(p).getmaxEnergy());
				} else {
					Energy.add(p, Finals.energyCharge * f.getRechargeSpeed());
				}
			}
		}
	}
}
