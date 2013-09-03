package com.moosemanstudios.MemberYourNot;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.sk89q.worldguard.protection.ApplicableRegionSet;

public class CommandListener implements Listener {
	MemberYourNot plugin;
	
	public CommandListener(MemberYourNot plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void PlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {
		if (event.getMessage().toLowerCase().startsWith("/sethome")) {
			// player issued the command to create a home, now get list of all regions they are currently standing in
			ApplicableRegionSet regionSet = plugin.wgPlugin.getRegionManager(event.getPlayer().getWorld()).getApplicableRegions(event.getPlayer().getLocation());
			
			// now see if the player is allowed
			if (!regionSet.isMemberOfAll(plugin.wgPlugin.wrapPlayer(event.getPlayer()))) {
				// player isn't a member of this region, deny the command!
				event.setCancelled(true);
				event.getPlayer().sendMessage("You are not a member or owner of this region.  Unable to set home here!");
			}
			
		}
	}
}
