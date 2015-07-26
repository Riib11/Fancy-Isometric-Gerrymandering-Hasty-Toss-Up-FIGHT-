package com.ngse.fight.classes.mages;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.ngse.fight.classes.Ability;
import com.ngse.fight.classes.FightClass;
import com.ngse.fight.specials.Hover;
import com.ngse.fight.specials.TeleSwitch;
import com.ngse.fight.specials.TeleportForward;

public class Teleporter extends FightClass {

	public Teleporter() {
		super("Teleporter", 20);
	}

	@Override
	public ArrayList<ItemStack> getItems() {
		ArrayList<ItemStack> is = new ArrayList<ItemStack>();
		is.add(new ItemStack(Material.APPLE, 64));
		is.add(createItemStack(Material.STONE_SWORD, 1, "Sticker Wand",
				"Sword of the teleportation mage"));
		is.add(new ItemStack(Material.LEATHER_BOOTS));
		is.add(new ItemStack(Material.LEATHER_CHESTPLATE));
		return is;
	}

	@Override
	public ArrayList<Ability> getAbilities() {
		ArrayList<Ability> as = new ArrayList<Ability>();
		as.add(new Hover());
		as.add(new TeleportForward());
		as.add(new TeleSwitch());
		return as;
	}
}
