package net.theplaceholderteam.bukkitkart.trackbuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import net.theplaceholderteam.bukkitkart.BukkitKart;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BuildManager implements Listener {

	HashMap<String, String> builderList;

	ArrayList<Track> tracks = new ArrayList<Track>();

	public BuildManager(BukkitKart main) {
		main.getServer().getPluginManager().registerEvents(this, main);
		builderList = new HashMap<String, String>();
		checkDirs();
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {

	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (builderList.containsKey(event.getWhoClicked().getName())) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onInventoryCreative(InventoryCreativeEvent event) {
		if (builderList.containsKey(event.getWhoClicked().getName())) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		if (builderList.containsKey(event.getPlayer().getName())) {
			event.setCancelled(true);
		}
	}

	// Handles requests for build mode
	public void handleBuildReq(Player p, String trackName) {
		if (builderList.containsKey(p.getName())) {
			p.sendMessage(ChatColor.GREEN
					+ "You are no longer in build mode for track: "
					+ builderList.get(p.getName()) + ".");
			p.getInventory().clear();
			p.setGameMode(GameMode.SURVIVAL);
			builderList.remove(p.getName());
			return;
		}
		if (getTrack(trackName) == null) {
			// Temporary nullness
			Track t = new Track(trackName, null, null, null);
			tracks.add(t);
		}
		builderList.put(p.getName(), getTrack(trackName).getTrackName());
		p.setGameMode(GameMode.CREATIVE);
		p.getInventory().setItem(0, getStartLineItem());
		p.getInventory().setItem(1, getFinishLineItem());
		p.getInventory().setItem(2, getCheckpointItem());
		p.getInventory().setItem(3, new ItemStack(Material.AIR));
		p.getInventory().setItem(4, new ItemStack(Material.AIR));
		p.getInventory().setItem(5, new ItemStack(Material.AIR));
		p.getInventory().setItem(6, new ItemStack(Material.AIR));
		p.getInventory().setItem(7, new ItemStack(Material.AIR));
		p.getInventory().setItem(8, new ItemStack(Material.AIR));
		p.sendMessage(ChatColor.GREEN + "You are now in build mode for track: "
				+ trackName + ".");
	}

	public static final ItemStack getStartLineItem() {
		ItemStack i = new ItemStack(Material.WOOL, 1, DyeColor.WHITE.getWoolData());
		ItemMeta im = i.getItemMeta();
		im.setDisplayName("Start Line");
		i.setItemMeta(im);
		return i;
	}

	public static final ItemStack getFinishLineItem() {
		ItemStack i = new ItemStack(Material.WOOL, 1, DyeColor.BLACK.getWoolData());
		ItemMeta im = i.getItemMeta();
		im.setDisplayName("Finish Line");
		i.setItemMeta(im);
		return i;
	}

	public static final ItemStack getCheckpointItem() {
		ItemStack i = new ItemStack(Material.LAPIS_BLOCK);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName("Checkpoint");
		i.setItemMeta(im);
		return i;
	}

	public Track getTrack(String trackName) {
		for (int i = 0; i < tracks.size(); i++) {
			if (tracks.get(i).getTrackName().equalsIgnoreCase(trackName)) {
				return tracks.get(i);
			}
		}
		return null;
	}

	public void checkDirs() {
		if (!BukkitKart.trackDir.exists()) {
			BukkitKart.trackDir.mkdir();
		}
	}

	public void loadTracks() {
		for (File f : BukkitKart.trackDir.listFiles()) {
			Track t = Track.loadTrack(f);
			if (!tracks.contains(t)) {
				tracks.add(t);
			}
		}
	}

}
