package net.theplaceholderteam.bukkitkart;

import java.io.File;
import java.util.logging.Logger;

import net.theplaceholderteam.bukkitkart.structure.Commands;
import net.theplaceholderteam.bukkitkart.structure.ConfigManager;
import net.theplaceholderteam.bukkitkart.structure.QueueManager;

import org.bukkit.plugin.java.JavaPlugin;

public class BukkitKart extends JavaPlugin {
	
	public static final File mainDir = new File("plugins/BukkitKart/");
	public static final File config = new File("plugins/BukkitKart/config.properties");
	public static final String TITLE = "BukkitKart", VERSION = "0.1a";
	
	ConfigManager cfgManager;
	QueueManager qManager;
	
	Commands cmds;
	
	Logger log = Logger.getLogger("Minecraft");
	
	public void onEnable() {
		
		// Initialize objects needed
		cfgManager = new ConfigManager();
		qManager = new QueueManager(this);
		
		// Register listeners and cmdExecutors
		cmds = new Commands(this);
		
		// Perform setup tasks
		cfgManager.checkConfig();
		
		log.info(BukkitKart.TITLE + " v" + BukkitKart.VERSION + " has been enabled!");
		
	}
	
	public void onDisable() {
		
		log.info(BukkitKart.TITLE + " v" + BukkitKart.VERSION + " has been disabled!");
		
	}
	
	public ConfigManager getCfgManager() {
		return cfgManager;
	}
	
	public QueueManager getQManager() {
		return qManager;
	}

}
