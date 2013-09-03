package com.moosemanstudios.MemberYourNot;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MemberCommandExecutor implements CommandExecutor {
	private MemberYourNot plugin;
	
	public MemberCommandExecutor(MemberYourNot plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("notmember") || cmd.getName().equalsIgnoreCase("nm")) {
			if (args.length == 0) {
				sender.sendMessage(ChatColor.RED + "Type " + ChatColor.WHITE + " /notmember help" + ChatColor.RED + " for help");
			} else {
				if (args[0].equalsIgnoreCase("help")) {
					sender.sendMessage("/notmember help" + ChatColor.RED + ": Display this help screen");
					sender.sendMessage("/notmember version" + ChatColor.RED + ": Show plugin version");
					
					if (sender.hasPermission("notmember.admin")) {
						sender.sendMessage("/notmember values" + ChatColor.RED + ": Show current configuration values");
						sender.sendMessage("/notmember member-one [message]" + ChatColor.RED + ": Change value of the member-one value to new message");
						sender.sendMessage("/notmember member-multi [message]" + ChatColor.RED + ": Change value of the member-multi value to new message");
						sender.sendMessage("/notmember global-sethome <true/false>" + ChatColor.RED + ": Enable or disable global sethome in region your not member of");
					}
				} else if (args[0].equalsIgnoreCase("version")) {
					sender.sendMessage(ChatColor.GOLD + plugin.getDescription().getName() + " Version: " + ChatColor.WHITE + plugin.getDescription().getVersion() + " - Author: moose517");
				} else if (args[0].equalsIgnoreCase("values")) {
					if (sender.hasPermission("notmember.admin")) {
						// show the current values
						sender.sendMessage(ChatColor.GOLD + "MemberYourNot - current values");
						sender.sendMessage(ChatColor.GOLD + "---------------------------------------");
						sender.sendMessage("member-one-region: " + plugin.messageOneRegion);
						sender.sendMessage("member-multi-region: " + plugin.messageMultiRegion);
						sender.sendMessage("global-set-homes: " + plugin.globalAllowSethome);
					} else {
						sender.sendMessage(ChatColor.RED + "Missing required permission: " + ChatColor.WHITE + "notmember.admin");
					}
				} else if (args[0].equalsIgnoreCase("member-one")) {
					if (sender.hasPermission("notmember.admin")) {
						plugin.setConfigValue("message-one-region", stringArrayToString(args, "member-one"));
						plugin.messageOneRegion = plugin.getConfig().getString("message-one-region");
						sender.sendMessage("Message changed successfully");
					} else {
						sender.sendMessage(ChatColor.RED + "Missing required permission: " + ChatColor.WHITE + "notmember.admin");
					}
				} else if (args[0].equalsIgnoreCase("member-multi")) {
					if (sender.hasPermission("notmember.admin")) {
						plugin.setConfigValue("message-multi-region", stringArrayToString(args, "member-multi"));
						plugin.messageMultiRegion = plugin.getConfig().getString("message-multi-region");
						sender.sendMessage("Message changed successfully");
					} else {
						sender.sendMessage(ChatColor.RED + "Missing required permission: " + ChatColor.WHITE + "notmember.admin");
					}
				} else if (args[0].equalsIgnoreCase("global-sethome")) {
					if (sender.hasPermission("notmember.admin")) {
						if (args.length == 2) {
							if (args[1].equalsIgnoreCase("true")) {
								plugin.setConfigValue("global-set-home", true);
								plugin.globalAllowSethome = plugin.getConfig().getBoolean("global-set-home");
								sender.sendMessage("Set successfully");
							} else if (args[1].equalsIgnoreCase("false")) {
								plugin.setConfigValue("global-set-home", false);
								plugin.globalAllowSethome = plugin.getConfig().getBoolean("global-set-home");
								sender.sendMessage("Set successfully");
							} else {
								sender.sendMessage("Invalid option");
							}
						} else {
							sender.sendMessage("Must supply only true or false");
						}
					} else {
						sender.sendMessage(ChatColor.RED + "Missing required permission: " + ChatColor.WHITE + "notmember.admin");
					}
				} else {
					sender.sendMessage("Unknown Command");
				}
			} 
		}
		return false;
	}
	
	private String stringArrayToString(String[] values, String key) {
		StringBuilder newString = new StringBuilder();
		for (String value : values) {
			if (value.equalsIgnoreCase(key)) {
				continue;
			} else {
				newString.append(value + " ");
			}
		}
		
		return newString.toString();
	}

}
