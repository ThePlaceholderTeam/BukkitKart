package net.theplaceholderteam.bukkitkart.structure;

import java.util.ArrayList;

import net.theplaceholderteam.bukkitkart.BukkitKart;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class QueueManager {

	BukkitKart main;

	ArrayList<String> waitingQueue;
	ArrayList<String> playerList;

	BukkitTask timer;
	int timeElapsed;

	public QueueManager(BukkitKart main) {
		timeElapsed = 0;
		waitingQueue = new ArrayList<String>();
		playerList = new ArrayList<String>();
		this.main = main;
	}
	
	// Reset everything for the next game
	public void resetVars() {
		timeElapsed = 0;
		timer = null;
		waitingQueue.clear();
	}

	// Handle player wanting to be queued
	public void handleQueueReq(Player p) {
		// Is the queue full?
		checkQueue();

		// Is the player in the queue?
		if (waitingQueue.contains(p.getName())) {
			p.sendMessage(ChatColor.DARK_RED + "You are already in the queue!");
			return;
		}

		waitingQueue.add(p.getName());
	}

	// Handle player leave request
	public void handleQueueLeaveReq(Player p) {
		// Is the player in the queue?
		if (waitingQueue.contains(p.getName())) {
			waitingQueue.remove(p.getName());
			p.sendMessage(ChatColor.GREEN
					+ "You have successfully been removed from the queue!");
			return;
		}

		if (playerList.contains(p.getName())) {
			// Scumbag player is leaving mid-game

		}
	}

	public void checkQueue() {
		
		// Remember to reset the task + timeElapsed
		if (timer == null) {
			timer = Bukkit.getScheduler().runTaskTimer(main, new Runnable() {
				public void run() {
					timeElapsed++;
				}
			}, 1L, 20L);
		}
		
		if (timeElapsed == 60) {
			// A minute has passed since the first queued player, start the game
			
		}
		
		if (waitingQueue.size() == 4) {
			// 4 players have been queued, start the game
			
		}
		
	}

	public boolean isInGame(Player p) {
		if (playerList.contains(p.getName())) {
			return true;
		}
		return false;
	}

	public boolean isInQueue(Player p) {
		if (waitingQueue.contains(p.getName())) {
			return true;
		}
		return false;
	}

}
