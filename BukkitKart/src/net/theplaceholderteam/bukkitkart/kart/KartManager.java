package net.theplaceholderteam.bukkitkart.kart;

import net.theplaceholderteam.bukkitkart.BukkitKart;
import net.theplaceholderteam.bukkitkart.structure.QueueManager;

import org.bukkit.ChatColor;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.vehicle.VehicleBlockCollisionEvent;
import org.bukkit.event.vehicle.VehicleDamageEvent;
//import org.bukkit.event.vehicle.VehicleCollisionEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.util.Vector;

public class KartManager implements Listener {

	QueueManager qManager;

	public KartManager(BukkitKart main) {
		qManager = main.getQManager();
		main.getServer().getPluginManager().registerEvents(this, main);
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		qManager.addPlayerToGame(event.getPlayer());
	}
	
	@EventHandler
	public void onVehicleMove(VehicleMoveEvent event) {
		if (event.getVehicle() instanceof Boat) {

			if (event.getVehicle().getPassenger() instanceof Player) {
				Player p = (Player) event.getVehicle().getPassenger();
				Boat b = (Boat) event.getVehicle();
				if (qManager.isInGame(p)) {
					b.setWorkOnLand(true);
				} else {
					b.setWorkOnLand(false);
				}
			}
		}
	}

	@EventHandler
	public void onVehicleHitBlock(VehicleBlockCollisionEvent event) {

		if (event.getVehicle() instanceof Boat) {

			if (event.getVehicle().getPassenger() instanceof Player) {
				Player p = (Player) event.getVehicle().getPassenger();
				if (qManager.isInGame(p)) {
					Vector vec = new Vector(0, 0.1, 0);
					event.getVehicle().setVelocity(
							event.getVehicle().getVelocity().add(vec));
				}
			}
		}
	}
	
	@EventHandler
	public void onVehicleDamage(VehicleDamageEvent event) {
		if (event.getVehicle() instanceof Boat) {
			if (event.getVehicle().getPassenger() == null) {
				return;
			}
			if (event.getVehicle().getPassenger() instanceof Player) {
				Player p = (Player) event.getVehicle().getPassenger();
				if (qManager.isInGame(p)) {
					event.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void onVehicleDestroy(VehicleDestroyEvent event) {
		if (event.getVehicle() instanceof Boat) {

			if (event.getVehicle().getPassenger() instanceof Player) {
				Player p = (Player) event.getVehicle().getPassenger();
				if (qManager.isInGame(p)) {
					event.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onVehicleExit(VehicleExitEvent event) {
		if (event.getVehicle() instanceof Boat) {

			if (event.getVehicle().getPassenger() instanceof Player) {
				Player p = (Player) event.getVehicle().getPassenger();
				if (qManager.isInGame(p)) {
					event.setCancelled(true);
					p.sendMessage(ChatColor.DARK_RED + "Can't leave during a game!");
				}
			}
		}
	}

}
