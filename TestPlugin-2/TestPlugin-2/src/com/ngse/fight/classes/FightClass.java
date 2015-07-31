package com.ngse.fight.classes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.common.collect.Iterables;
import com.ngse.fight.classes.fighters.Lumberjack;
import com.ngse.fight.classes.fighters.Ninja;
import com.ngse.fight.classes.fighters.PsychoKiller;
import com.ngse.fight.classes.mages.Alchemist;
import com.ngse.fight.classes.mages.Pyromancer;
import com.ngse.fight.classes.mages.Shadow;
import com.ngse.fight.classes.mages.Teleporter;

public abstract class FightClass {

	public static HashMap<String, FightClass> allClasses = new HashMap<String, FightClass>();

	@SuppressWarnings("unused")
	public static void fightClassesArraySetup() {
		Alchemist ach = new Alchemist();
		Shadow sha = new Shadow();
		Teleporter tel = new Teleporter();
		PsychoKiller psy = new PsychoKiller();
		Pyromancer pyr = new Pyromancer();
		Ninja nin = new Ninja();
		Lumberjack lum = new Lumberjack();
	}

	public abstract ArrayList<ItemStack> getItems();

	public abstract ArrayList<Ability> getAbilities();

	@SuppressWarnings("unused")
	private ArrayList<Ability> abilities;
	@SuppressWarnings("unused")
	private ArrayList<ItemStack> items;
	private String name;
	private int maxEnergy;
	private int rechargeSpeed;

	public String getName() {
		return this.name;
	}

	public int getmaxEnergy() {
		return maxEnergy;
	}

	public int getRechargeSpeed() {
		return rechargeSpeed;
	}

	public FightClass(String name, int maxEnergy) {
		this.name = name;
		this.maxEnergy = maxEnergy;
		items = getItems();
		abilities = getAbilities();
		rechargeSpeed = 1;
		allClasses.put(this.name, this);
	}

	public FightClass(String name, int maxEnergy, int rechargeSpeed) {
		this(name, maxEnergy);
		this.rechargeSpeed = rechargeSpeed;
	}

	public static void initiateClass(Player sender, FightClass fightclass) {
		// give items
		for (ItemStack is : fightclass.getItems()) {
			sender.getInventory().addItem(is);
		}
		// give abilities (activated by these items)
		for (Ability a : fightclass.getAbilities()) {
			sender.getInventory().addItem(a.getItem());
		}
	}

	/*
	 * Return: True if they have an ability matching the held item. False if
	 * they do not have any abilities matching held item
	 */
	public boolean useAbility(Player sender, Block b) {
		// check if they're holding anything
		if (sender.getItemInHand() == null) {
			return false;
		}

		ItemStack i = sender.getItemInHand();

		// sees if they are fightclassed
		FightClass f = FightClass.get(sender);
		// goes through each ability, seeeing if any of the MID's match the
		// lore of the item
		for (Ability a : f.getAbilities()) {
			if (i.getItemMeta().hasLore()
					&& i.getItemMeta().getLore().get(0)
							.equalsIgnoreCase(a.getMID())) {
				// check if cost is able to be paid, and pay it
				if (a.useCost(sender)) {

					// check to see if there is a target
					Player target = null;
					boolean s = true;
					Location l = sender.getLocation();

					/*
					 * loop through nearby entites to positions x blocks in the
					 * direction the player is looking, starting at 0 and ending
					 * at a.getRange()
					 */
					for (int x = 2; x < a.getRange() && (s); x++) {
						Location lt = l;

						Collection<Entity> et = lt.getWorld()
								.getNearbyEntities(
										lt.add(lt.getDirection().multiply(x)),
										2, 2, 2);

						if (!et.isEmpty()) {
							for (int j = 0; j < et.size(); j++) {
								if (Iterables.get(et, j) instanceof Player) {

									if (!(((Player) Iterables.get(et, j))
											.equals(sender))) {
										target = (Player) Iterables.get(et, 0);
										s = false;
									}
								}
							}
						}
					}

					// see if target got set to anything
					if (target != null) {
						a.effect(sender, target);
					} else {
						if (a instanceof BlockTargettingAbility) {
							if (b != null && !b.getType().equals(Material.AIR)) {
								((BlockTargettingAbility) a).effect(sender, b);
							} else {
								// nothing, needs a block for target it
								// BlockTargettingAbility
							}
						}
						a.effect(sender);
					}
				} else {
					// nothing
				}
				return true;
			}
		}
		return false;
	}

	/*
	 * @Return: True if they have an ability matching the held item. False if
	 * they do not have any abilities matching held item
	 */
	public boolean useAbility(Player sender, Player target) {
		// check if they're holding anything
		if (sender.getItemInHand() == null) {
			return false;
		}

		ItemStack i = sender.getItemInHand();

		// sees if they are fightclassed
		FightClass f = FightClass.get(sender);
		// goes through each ability, seeeing if any of the MID's match the
		// lore of the item
		for (Ability a : f.getAbilities()) {
			if (i.getItemMeta().hasLore()
					&& i.getItemMeta().getLore().get(0)
							.equalsIgnoreCase(a.getMID())) {
				// check if cost is able to be paid, and pay it
				if (a.useCost(sender)) {
					if (!sender.equals(target)) {
						a.effect(sender, target);
					}
				} else {
					// nothing
				}
				return true;
			}
		}
		return false;
	}

	public static FightClass get(Player p) {
		return p.hasMetadata("fightclass") ? (FightClass) p
				.getMetadata("fightclass").get(0).value() : null;
	}

	// class creation utilities

	public static ItemStack createItemStack(Material m, int amount,
			String name, String lore) {
		ItemStack it = new ItemStack(m, amount);
		ItemMeta im = it.getItemMeta();
		im.setDisplayName(name);
		ArrayList<String> l = new ArrayList<String>();
		l.add(lore);
		im.setLore(l);
		it.setItemMeta(im);
		return it;
	}

	public static ItemStack setupSpecialItem(Material m, String name,
			String lore, Enchantment e, int l) {
		ItemStack i = new ItemStack(m);
		i.addUnsafeEnchantment(e, l);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(name);
		List<String> lorelist = new ArrayList<String>();
		lorelist.add(lore);
		im.setLore(lorelist);
		i.setItemMeta(im);
		return i;
	}

	public static ArrayList<Ability> abilitiesArray(Ability[] a) {
		ArrayList<Ability> as = new ArrayList<Ability>();
		for (Ability ab : a) {
			as.add(ab);
		}
		return as;
	}

	public static ArrayList<ItemStack> itemstacksArray(ItemStack[] a) {
		ArrayList<ItemStack> as = new ArrayList<ItemStack>();
		for (ItemStack ab : a) {
			as.add(ab);
		}
		return as;
	}

	public static ItemStack is(Material m, int a) {
		return new ItemStack(m, a);
	}

	public static ItemStack is(Material m) {
		return new ItemStack(m);
	}
}
