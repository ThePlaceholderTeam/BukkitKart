package net.theplaceholderteam.bukkitkart;

import net.theplaceholderteam.bukkitkart.structure.ConfigManager;
import net.theplaceholderteam.bukkitkart.structure.QueueManager;
import net.theplaceholderteam.bukkitkart.trackbuilder.BuildManager;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

	BukkitKart main;
	ConfigManager cfgManager;
	QueueManager qManager;
	BuildManager buildManager;

	public Commands(BukkitKart main) {
		this.main = main;
		cfgManager = main.getCfgManager();
		qManager = main.getQManager();
		buildManager = main.getBuildManager();
	}

	public void helpCommand(Player p) {
		p.sendMessage(ChatColor.GRAY + "--- " + ChatColor.RED
				+ "BukkitKart Help" + ChatColor.GRAY + " ---");
		p.sendMessage(ChatColor.GRAY + "/bukkitkart"
				+ " play - Enter the BukkitKart queue");
		p.sendMessage(ChatColor.GRAY
				+ "/bukkitkart"
				+ " leave - Leave the BukkitKart queue");
		p.sendMessage(ChatColor.GRAY
				+ "/bukkitkart build [trackname] - Build or edit a track");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (commandLabel.equalsIgnoreCase("bukkitkart")) {
				if (args.length == 1) {
					String cmdType = args[0];
					if (cmdType.equalsIgnoreCase("play")) {
						qManager.handleQueueReq(p);
					}
					if (cmdType.equalsIgnoreCase("leave")) {
						qManager.handleQueueLeaveReq(p);
					}
					if (cmdType.equalsIgnoreCase("help")) {
						helpCommand(p);
					}
					if (cmdType.equalsIgnoreCase("build")) {
						buildManager.handleBuildReq(p, "");
					}
					return true;
				} else if (args.length == 2) {
					String cmdType = args[0];
					String trackName = args[1];
					if (cmdType.equalsIgnoreCase("build")) {
						buildManager.handleBuildReq(p, trackName);
					}
					return true;
				} else {
					helpCommand(p);
					return true;
				}
			}
		}
		return false;
	}

}
