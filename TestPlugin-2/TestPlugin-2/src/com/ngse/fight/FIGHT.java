package com.ngse.fight;

import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import com.ngse.fight.classes.FightClass;

public class FIGHT extends JavaPlugin {

	public static CommandExecutor commandExecutor;

	public static JavaPlugin plugin;

	public void onEnable() {
		plugin = this;
		this.getLogger().info("FIGHT enabled!");
		// commandexecutor
		initCommands();
		// listeners

		// all initializing stuff
		initClasses();
	}

	private void initCommands() {
		commandExecutor = new FightCommandExecutor();
		this.getCommand("fight").setExecutor(commandExecutor);
		this.getCommand("f").setExecutor(commandExecutor);
	}

	public void onDisable() {
		getLogger().info("FIGHT disabled");
	}

	public static void createPlayer(Player p, String fightclassname) {
		p.setMetadata("fightclass", new FixedMetadataValue(plugin,
				FightClass.allClasses.get(fightclassname)));
	}

	public static void initClasses() {
		// add all classes to FightClass.allClasses
		FightClass.fightClassesArraySetup();
	}
}
