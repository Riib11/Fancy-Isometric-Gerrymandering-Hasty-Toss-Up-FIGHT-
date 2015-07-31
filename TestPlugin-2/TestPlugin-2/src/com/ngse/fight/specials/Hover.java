package com.ngse.fight.specials;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import com.ngse.fight.FIGHT;
import com.ngse.fight.classes.Ability;
import com.ngse.fight.classes.PassiveAbility;
import com.ngse.utilities.Blocks;

public class Hover extends PassiveAbility {

	public Hover() {
		super("Hover", 1, "hov");
	}

	@Override
	public void effect(Player user, Player target) {
		effect(user);
	}

	@Override
	public void effect(Player user) {
		// toggle hovering
		togglePassiveAbility(user, this);
	}

	@Override
	public ItemStack getItem() {
		return Ability.setupItem(Material.FEATHER, this);
	}

	@Override
	public void passiveEffect(Player user) {
		Player p = user;

		if (p.hasMetadata(getName())) {
			if (isActive(user)) {
				if (isActive(user)) {
					Location l = p.getLocation();
					Location below = l.add(0, -1, 0);

					destroyHoveringBlock(user, false, this);

					if (!below.getBlock().getType().isSolid()) {
						// if the block isnt solid, then set it to glass
						below.getBlock().getState().setType(Material.GLASS);
						below.getBlock().getState().update(true);
						below.getBlock().setMetadata(getName(),
								new FixedMetadataValue(FIGHT.plugin, true));
					}
				}
			}
		}
	}

	public static void destroyHoveringBlock(Player p, boolean all, Ability a) {
		Location l = p.getLocation();
		Location below = l.add(0, -1, 0);

		List<Block> allBlocks = Blocks.getNearbyBlocks(below, 2);
		// remove bottom block from the list to be vaporized if(all)

		for (Block b : allBlocks) {
			if (b.hasMetadata(a.getName())) {
				if (!(b.getLocation().equals(below) && !all)) {
					b.setType(Material.AIR);
					b.removeMetadata(a.getName(), FIGHT.plugin);
				}
			}
		}
	}

	public static void destroyHoveringBlock(Player p, boolean all) {
		destroyHoveringBlock(p, all, new Hover());
	}

	@Override
	public void endPassiveEffect(Player p) {
		destroyHoveringBlock(p, true, this);
	}

}
