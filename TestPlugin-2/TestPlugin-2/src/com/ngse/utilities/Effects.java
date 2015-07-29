package com.ngse.utilities;

import org.bukkit.Effect;
import org.bukkit.Location;

public class Effects {

	public static void play(Location l, Effect e, int times) {
		for (int x = 0; x <= times; x++) {
			l.getWorld().playEffect(l, e, 1);
		}
	}
}
