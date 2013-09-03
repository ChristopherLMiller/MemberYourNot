package com.moosemanstudios.MemberYourNot;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class MemberYourNot extends JavaPlugin {
	WorldGuardPlugin wgPlugin = null;
	String prefix = "[MemberYourNot] ";
	Logger log = Bukkit.getLogger();
	
	@Override
	public void onEnable() {
		// see if worldguard is found and enabled
		if (getWorldGuard() != null) {
			// register the event
			getServer().getPluginManager().registerEvents(new CommandListener(this), this);
			wgPlugin = getWorldGuard();
		} else {
			log.severe(prefix + "Worldguard was not found.  Disabling plugin.");
			Bukkit.getPluginManager().disablePlugin(this);

		}
		
		log.info(prefix + "version " + this.getDescription().getVersion() + " is enabled");
	}
	
	@Override
	public void onDisable() {
		log.info(prefix + "is disabled");
	}
	
	public WorldGuardPlugin getWorldGuard() {
		Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");
		
		// worldguard may not be loaded
		if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
			return null;
		}
		
		return (WorldGuardPlugin) plugin;
	}
}
