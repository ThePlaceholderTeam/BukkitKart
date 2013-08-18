package net.theplaceholderteam.bukkitkart.trackbuilder;

import java.util.HashMap;

import net.theplaceholderteam.bukkitkart.BukkitKart;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BuildManager implements Listener {

	HashMap<String, String> builderList;

	public BuildManager(BukkitKart main) {
		main.getServer().getPluginManager().registerEvents(this, main);
		builderList = new HashMap<String, String>();
		checkDirs();
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Player p = event.getPlayer();
	}

	public void handleBuildReq(Player p, String trackName) {
		if (builderList.containsKey(p.getName())) {
			p.sendMessage(ChatColor.GREEN + "You are no longer in build mode for track: " + builderList.get(p.getName()) + ".");
			builderList.remove(p.getName());
		}
		
	}
	
	public void checkDirs() {
		if (!BukkitKart.trackDir.exists()) {
			BukkitKart.trackDir.mkdir();
		}
	}

}
