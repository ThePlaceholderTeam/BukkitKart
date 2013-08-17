package net.theplaceholderteam.bukkitkart.structure;

import net.theplaceholderteam.bukkitkart.BukkitKart;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class Commands implements Listener {

	BukkitKart main;
	ConfigManager cfgManager;

	public Commands(BukkitKart main) {
		this.main = main;
		cfgManager = main.getCfgManager();
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
					
				}
				if (cmdType.equalsIgnoreCase("leave")) {
					// Unqueue the player
					
				}

			} else {
				p.sendMessage(ChatColor.DARK_RED
						+ "You used this command incorrectly! Try again.");
			}
		}
	}

}
