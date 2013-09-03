package com.moosemanstudios.MemberYourNot;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.ApplicableRegionSet;

public class CommandListener implements Listener {
	MemberYourNot plugin;
	
	public CommandListener(MemberYourNot plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void PlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {
		// first off see if we are allowing globally anybody to se home
		if (!plugin.globalAllowSethome) { 
			if (event.getMessage().toLowerCase().startsWith("/sethome")) {
				// player issued the command to create a home, now get list of all regions they are currently standing in
				ApplicableRegionSet regionSet = plugin.wgPlugin.getRegionManager(event.getPlayer().getWorld()).getApplicableRegions(event.getPlayer().getLocation());
				
				// revision starts here!
				LocalPlayer lPlayer = plugin.wgPlugin.wrapPlayer(event.getPlayer());
				if (regionSet.size() == 1) {
					if (!regionSet.isMemberOfAll(lPlayer)) {
						if (!event.getPlayer().hasPermission("notmember.bypass")) {
							event.setCancelled(true);
							event.getPlayer().sendMessage(ChatColor.RED + plugin.messageOneRegion);
						}
					}
				} else {
					if (!regionSet.isMemberOfAll(lPlayer)) {
						if (!event.getPlayer().hasPermission("notmember.bypass")) {
							event.setCancelled(true);
							event.getPlayer().sendMessage(ChatColor.RED + plugin.messageMultiRegion);
						}
					}
				}			
			}
		}
	}
}
