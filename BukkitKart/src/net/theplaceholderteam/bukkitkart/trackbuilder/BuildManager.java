package net.theplaceholderteam.bukkitkart.trackbuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import net.theplaceholderteam.bukkitkart.BukkitKart;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;

public class BuildManager implements Listener {

	HashMap<String, String> builderList;

	ArrayList<Track> tracks = new ArrayList<Track>();

	public BuildManager(BukkitKart main) {
		main.getServer().getPluginManager().registerEvents(this, main);
		builderList = new HashMap<String, String>();
		checkDirs();
		loadTracks();
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Block b = event.getBlock();
		Player p = event.getPlayer();
		if (builderList.containsKey(p.getName())) {
			Track t = getTrack(builderList.get(p.getName()));
			if (b.getType() == Material.WOOL) {
				Wool wool = new Wool(b.getType(), b.getData());
				if (wool.getColor() == DyeColor.BLACK) {
					t.getFinishLine().remove(b.getLocation());
					t.saveTrack();
				}
				if (wool.getColor() == DyeColor.WHITE) {
					t.getStartLine().remove(b.getLocation());
					t.saveTrack();
				}
			}
			if (b.getType() == Material.LAPIS_BLOCK) {
				t.getCheckpoints().remove(b.getLocation());
				t.saveTrack();
			}
			reloadTracks();
		} else {
			for (Track t : tracks) {
				for (Location l : t.getCheckpoints()) {
					if (l.equals(b.getLocation())) {
						event.setCancelled(true);
						p.sendMessage(ChatColor.DARK_RED + "You must be in build mode!");
					}
				}
				for (Location l : t.getFinishLine()) {
					if (l.equals(b.getLocation())) {
						event.setCancelled(true);
						p.sendMessage(ChatColor.DARK_RED + "You must be in build mode!");
					}
				}
				for (Location l : t.getStartLine()) {
					if (l.equals(b.getLocation())) {
						event.setCancelled(true);
						p.sendMessage(ChatColor.DARK_RED + "You must be in build mode!");
					}
				}
			}
		}
	}

	// Handles the saving of blocks
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Block b = event.getBlock();
		Player p = event.getPlayer();
		if (builderList.containsKey(p.getName())) {
			Track t = getTrack(builderList.get(p.getName()));
			if (b.getType() == Material.WOOL) {
				Wool wool = new Wool(b.getType(), b.getData());
				if (wool.getColor() == DyeColor.BLACK) {
					ArrayList<Location> temp = t.getFinishLine();
					temp.add(b.getLocation());
					t.setFinishLine(temp);
					t.saveTrack();
				}
				if (wool.getColor() == DyeColor.WHITE) {
					ArrayList<Location> temp = t.getStartLine();
					temp.add(b.getLocation());
					t.setStartLine(temp);
					t.saveTrack();
				}
			}
			if (b.getType() == Material.LAPIS_BLOCK) {
				ArrayList<Location> temp = t.getCheckpoints();
				temp.add(b.getLocation());
				t.setCheckpoints(temp);
				t.saveTrack();
			}
			reloadTracks();
		}
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
					+ builderList.get(p.getName()));
			p.getInventory().clear();
			p.setGameMode(GameMode.SURVIVAL);
			builderList.remove(p.getName());
			return;
		}
		if (getTrack(trackName) == null) {
			// Temporary nullness
			ArrayList<Location> temporary = new ArrayList<Location>();
			Track t = new Track(trackName, temporary, temporary, temporary);
			t.saveTrack();
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
				+ trackName);
	}

	public static final ItemStack getStartLineItem() {
		ItemStack i = new ItemStack(Material.WOOL, 1,
				DyeColor.WHITE.getWoolData());
		ItemMeta im = i.getItemMeta();
		im.setDisplayName("Start Line");
		i.setItemMeta(im);
		return i;
	}

	public static final ItemStack getFinishLineItem() {
		ItemStack i = new ItemStack(Material.WOOL, 1,
				DyeColor.BLACK.getWoolData());
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
	
	public void reloadTracks() {
		for (Track t : tracks) {
			t.reloadTrack();
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
