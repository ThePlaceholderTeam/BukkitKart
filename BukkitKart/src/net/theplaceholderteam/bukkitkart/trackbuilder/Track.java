package net.theplaceholderteam.bukkitkart.trackbuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import net.theplaceholderteam.bukkitkart.BukkitKart;

import org.bukkit.Location;

public class Track {

	File trackFile;

	private String trackName;

	private Location[] startLine;
	private Location[] finishLine;
	private Location[] checkpoints;

	public Track(String trackName, Location[] startLine, Location[] finishLine,
			Location[] checkpoints) {
		this.setTrackName(trackName);
		this.startLine = startLine;
		this.finishLine = finishLine;
		this.checkpoints = checkpoints;
		trackFile = new File(BukkitKart.trackDir.toString() + trackFile
				+ ".properties");
	}

	public void saveTrack() {
		Properties props = new Properties();
		try {
			if (!trackFile.exists()) {
				trackFile.createNewFile();
			}
			FileOutputStream out = new FileOutputStream(trackFile);
			FileInputStream fis = new FileInputStream(trackFile);
			props.load(fis);
			String startLineStr = "";
			String finishLineStr = "";
			String checkpointsStr = "";
			for (Location loc : startLine) {
				startLineStr = startLineStr + "|"
						+ String.valueOf(loc.getBlockX()) + ":"
						+ String.valueOf(loc.getBlockY()) + ":"
						+ String.valueOf(loc.getBlockZ()) + "|";
			}
			for (Location loc : finishLine) {
				finishLineStr = finishLineStr + "|"
						+ String.valueOf(loc.getBlockX()) + ":"
						+ String.valueOf(loc.getBlockY()) + ":"
						+ String.valueOf(loc.getBlockZ()) + "|";
			}
			for (Location loc : checkpoints) {
				checkpointsStr = checkpointsStr + "|"
						+ String.valueOf(loc.getBlockX()) + ":"
						+ String.valueOf(loc.getBlockY()) + ":"
						+ String.valueOf(loc.getBlockZ()) + "|";
			}
			props.setProperty("startLine", startLineStr);
			props.setProperty("finishLine", finishLineStr);
			props.setProperty("checkpoints", checkpointsStr);
			props.store(out, trackName);
			out.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadTrack() {

	}

	public Location[] getStartLine() {
		return startLine;
	}

	public void setStartLine(Location[] startLine) {
		this.startLine = startLine;
	}

	public Location[] getFinishLine() {
		return finishLine;
	}

	public void setFinishLine(Location[] finishLine) {
		this.finishLine = finishLine;
	}

	public Location[] getCheckpoints() {
		return checkpoints;
	}

	public void setCheckpoints(Location[] checkpoints) {
		this.checkpoints = checkpoints;
	}

	public String getTrackName() {
		return trackName;
	}

	public void setTrackName(String trackName) {
		this.trackName = trackName;
	}

}
