package net.theplaceholderteam.bukkitkart.trackbuilder;

import java.io.File;

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
		trackFile = new File(BukkitKart.trackDir.toString() + trackFile + ".properties");
	}
	
	public void saveTrack() {
		
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
