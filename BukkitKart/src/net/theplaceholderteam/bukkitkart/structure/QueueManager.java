package net.theplaceholderteam.bukkitkart.structure;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public class QueueManager {

	ArrayList<String> waitingQueue;
	ArrayList<String> playerList;
	
	public QueueManager() {
		waitingQueue = new ArrayList<String>();
		playerList = new ArrayList<String>();
	}
	
	// Handle player wanting to be queued
	public void handleQueueReq(Player p) {
		
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
