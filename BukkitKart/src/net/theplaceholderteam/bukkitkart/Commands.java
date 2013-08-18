package net.theplaceholderteam.bukkitkart;

import net.theplaceholderteam.bukkitkart.structure.ConfigManager;
import net.theplaceholderteam.bukkitkart.structure.QueueManager;
import net.theplaceholderteam.bukkitkart.trackbuilder.BuildManager;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class Commands implements Listener {

	BukkitKart main;
	ConfigManager cfgManager;
	QueueManager qManager;
	BuildManager buildManager;

	public Commands(BukkitKart main) {
		this.main = main;
		cfgManager = main.getCfgManager();
		qManager = main.getQManager();
		buildManager= main.getBuildManager();
		main.getServer().getPluginManager().registerEvents(this, main);
	}

	// Using listener instead of an executor so the command can be configurable
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent event) {
		Player p = event.getPlayer();
		String cmd = event.getMessage().split(" ")[0];
		if (cmd.equalsIgnoreCase("/" + cfgManager.getCmdTag())) {
			// BukkitKart command
			if (event.getMessage().split(" ").length == 2) {
				String cmdType = event.getMessage().split(" ")[1];
				if (cmdType.equalsIgnoreCase("play")) {
					// Queue the player
					qManager.handleQueueReq(p);
				}
				if (cmdType.equalsIgnoreCase("leave")) {
					// Unqueue the player
					qManager.handleQueueLeaveReq(p);
				}
				if (cmdType.equalsIgnoreCase("help")) {
					// Help command
					helpCommand(p);
				}
				if (cmdType.equalsIgnoreCase("build")) {
					// Toggles build mode for a certain track, requires two arguments
					
				}
			} else {
				helpCommand(p);
			}
		}
	}

	public void helpCommand(Player p) {
		p.sendMessage(ChatColor.GRAY + "--- " + ChatColor.RED
				+ "BukkitKart Help" + ChatColor.GRAY + " ---");
		p.sendMessage(ChatColor.GRAY + "/" + cfgManager.getCmdTag()
				+ " play - Use this command to play BukkitKart!");
		p.sendMessage(ChatColor.GRAY
				+ "/"
				+ cfgManager.getCmdTag()
				+ " leave - Use this command if you don't want to play anymore!");
	}

}
