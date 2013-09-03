package com.moosemanstudios.MemberYourNot;

import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class MemberYourNot extends JavaPlugin {
	WorldGuardPlugin wgPlugin = null;
	String prefix = "[MemberYourNot] ";
	Logger log = Bukkit.getLogger();
	Boolean debug;
	String messageOneRegion, messageMultiRegion;
	Boolean globalAllowSethome;
	
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
			return;
		}
		
		// load config
		loadConfig();
		
		// register the command executor
		getCommand("notmember").setExecutor(new MemberCommandExecutor(this));
		
		// start metrics
		Metrics metrics;
		try {
			metrics = new Metrics(this);
			metrics.start();
		} catch (IOException e) {
			e.printStackTrace();
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
	
	private void loadConfig() {
		if (!getConfig().contains("debug")) getConfig().set("debug", false);
		if (!getConfig().contains("message-one-region")) getConfig().set("message-one-region", "You are not a member of this region and cannot set home here");
		if (!getConfig().contains("message-multi-region")) getConfig().set("message-multi-region", "You are in multiple regions, at least one of which your not a member");
		if (!getConfig().contains("global-allow-sethome")) getConfig().set("global-allow-sethome", false);
		saveConfig();
		
		debug = getConfig().getBoolean("debug");
		messageOneRegion = getConfig().getString("message-one-region");
		messageMultiRegion = getConfig().getString("message-multi-region");
		globalAllowSethome = getConfig().getBoolean("global-allow-sethome");		
	}
	
	public void setConfigValue(String key, Object value) {
		getConfig().set(key, value);
		saveConfig();
	}
}
