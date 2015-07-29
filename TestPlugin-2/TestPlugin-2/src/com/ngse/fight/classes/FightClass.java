package com.ngse.fight.classes;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.ngse.fight.FIGHT;
import com.ngse.fight.classes.fighters.PsychoKiller;
import com.ngse.fight.classes.mages.Alchemist;
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
		FIGHT.log("FC: " + fightclass.getName());
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
	public boolean useAbility(Player sender) {
		// check if they're holding anything
		if (!(sender.getItemInHand() == null)) {
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
					a.effect(sender);
				}
				return true;
			}
		}
		return false;
	}

	/*
	 * Return: True if they have an ability matching the held item. False if
	 * they do not have any abilities matching held item
	 */
	public boolean useAbility(Player sender, Player target) {
		// check if they're holding anything
		if (!(sender.getItemInHand() == null)) {
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
					a.effect(sender, target);
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
