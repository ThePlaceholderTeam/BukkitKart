package net.theplaceholderteam.bukkitkart.structure;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import net.theplaceholderteam.bukkitkart.BukkitKart;

public class ConfigManager {
	
	private String cmdTag;
	
	public void checkConfig() {
		
		Properties props = new Properties();
		
		if (!BukkitKart.mainDir.exists()) {
			BukkitKart.mainDir.mkdir();
		}
		if (!BukkitKart.config.exists()) {
			try {
				BukkitKart.config.createNewFile();
				FileInputStream fis = new FileInputStream(BukkitKart.config);
				FileOutputStream out = new FileOutputStream(BukkitKart.config);
				props.load(fis);
				props.setProperty("command-Tag", "bukkitkart");
				props.store(out, "--BukkitKart Config--");
				out.close();
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			FileInputStream fis = new FileInputStream(BukkitKart.config);
			props.load(fis);
			this.cmdTag = props.getProperty("command-Tag");
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getCmdTag() {
		return cmdTag;
	}
	
	public void setCmdTag(String cmdTag) {
		this.cmdTag = cmdTag;
	}

}
