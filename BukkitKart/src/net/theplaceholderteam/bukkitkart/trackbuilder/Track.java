package net.theplaceholderteam.bukkitkart.trackbuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import net.theplaceholderteam.bukkitkart.BukkitKart;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Track {

	File trackFile;

	private String trackName;

	private ArrayList<Location> startLine;
	private ArrayList<Location> finishLine;
	private ArrayList<Location> checkpoints;

	public Track(String trackName, ArrayList<Location> startLine,
			ArrayList<Location> finishLine, ArrayList<Location> checkpoints) {
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
			props.setProperty("trackName", trackName);
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

	public void reloadTrack() {

		Properties props = new Properties();
		if (!trackFile.exists()) {
			return;
		}
		try {
			FileInputStream fis = new FileInputStream(trackFile);
			props.load(fis);
			String startLineStr = props.getProperty("startLine");
			String finishLineStr = props.getProperty("finishLine");
			String checkpointsStr = props.getProperty("checkpointss");
			String[] startLineStrArr = startLineStr.split("|");
			String[] finishLineStrArr = finishLineStr.split("|");
			String[] checkpointsStrArr = checkpointsStr.split("|");
			ArrayList<Location> startLine2 = new ArrayList<Location>();
			ArrayList<Location> finishLine2 = new ArrayList<Location>();
			ArrayList<Location> checkpoints2 = new ArrayList<Location>();
			for (int i = 0; i < startLineStrArr.length; i++) {
				String world = startLineStrArr[i].split(":")[0];
				int x = Integer.valueOf(startLineStrArr[i].split(":")[1]);
				int y = Integer.valueOf(startLineStrArr[i].split(":")[2]);
				int z = Integer.valueOf(startLineStrArr[i].split(":")[3]);
				Location loc = new Location(Bukkit.getWorld(world), x, y, z);
				startLine2.add(loc);
			}
			for (int i = 0; i < finishLineStrArr.length; i++) {
				String world = finishLineStrArr[i].split(":")[0];
				int x = Integer.valueOf(finishLineStrArr[i].split(":")[1]);
				int y = Integer.valueOf(finishLineStrArr[i].split(":")[2]);
				int z = Integer.valueOf(finishLineStrArr[i].split(":")[3]);
				Location loc = new Location(Bukkit.getWorld(world), x, y, z);
				finishLine2.add(loc);
			}
			for (int i = 0; i < checkpointsStrArr.length; i++) {
				String world = checkpointsStrArr[i].split(":")[0];
				int x = Integer.valueOf(checkpointsStrArr[i].split(":")[1]);
				int y = Integer.valueOf(checkpointsStrArr[i].split(":")[2]);
				int z = Integer.valueOf(checkpointsStrArr[i].split(":")[3]);
				Location loc = new Location(Bukkit.getWorld(world), x, y, z);
				checkpoints2.add(loc);
			}
			this.setStartLine(startLine2);
			this.setFinishLine(finishLine2);
			this.setCheckpoints(checkpoints2);
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static Track loadTrack(File f) {

		Properties props = new Properties();

		try {
			FileInputStream fis = new FileInputStream(f);
			props.load(fis);
			String trackName = props.getProperty("trackName");
			String startLineStr = props.getProperty("startLine");
			String finishLineStr = props.getProperty("finishLine");
			String checkpointsStr = props.getProperty("checkpointss");
			String[] startLineStrArr = startLineStr.split("|");
			String[] finishLineStrArr = finishLineStr.split("|");
			String[] checkpointsStrArr = checkpointsStr.split("|");
			ArrayList<Location> startLine2 = new ArrayList<Location>();
			ArrayList<Location> finishLine2 = new ArrayList<Location>();
			ArrayList<Location> checkpoints2 = new ArrayList<Location>();
			for (int i = 0; i < startLineStrArr.length; i++) {
				String world = startLineStrArr[i].split(":")[0];
				int x = Integer.valueOf(startLineStrArr[i].split(":")[1]);
				int y = Integer.valueOf(startLineStrArr[i].split(":")[2]);
				int z = Integer.valueOf(startLineStrArr[i].split(":")[3]);
				Location loc = new Location(Bukkit.getWorld(world), x, y, z);
				startLine2.add(loc);
			}
			for (int i = 0; i < finishLineStrArr.length; i++) {
				String world = finishLineStrArr[i].split(":")[0];
				int x = Integer.valueOf(finishLineStrArr[i].split(":")[1]);
				int y = Integer.valueOf(finishLineStrArr[i].split(":")[2]);
				int z = Integer.valueOf(finishLineStrArr[i].split(":")[3]);
				Location loc = new Location(Bukkit.getWorld(world), x, y, z);
				finishLine2.add(loc);
			}
			for (int i = 0; i < checkpointsStrArr.length; i++) {
				String world = checkpointsStrArr[i].split(":")[0];
				int x = Integer.valueOf(checkpointsStrArr[i].split(":")[1]);
				int y = Integer.valueOf(checkpointsStrArr[i].split(":")[2]);
				int z = Integer.valueOf(checkpointsStrArr[i].split(":")[3]);
				Location loc = new Location(Bukkit.getWorld(world), x, y, z);
				checkpoints2.add(loc);
			}
			fis.close();
			return new Track(trackName, startLine2, finishLine2, checkpoints2);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}

	public ArrayList<Location> getStartLine() {
		return startLine;
	}

	public void setStartLine(ArrayList<Location> startLine) {
		this.startLine = startLine;
	}

	public ArrayList<Location> getFinishLine() {
		return finishLine;
	}

	public void setFinishLine(ArrayList<Location> finishLine) {
		this.finishLine = finishLine;
	}

	public ArrayList<Location> getCheckpoints() {
		return checkpoints;
	}

	public void setCheckpoints(ArrayList<Location> checkpoints) {
		this.checkpoints = checkpoints;
	}

	public String getTrackName() {
		return trackName;
	}

	public void setTrackName(String trackName) {
		this.trackName = trackName;
	}

}
