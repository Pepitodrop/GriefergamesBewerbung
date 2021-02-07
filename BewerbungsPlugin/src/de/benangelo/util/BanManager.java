package de.benangelo.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;

import de.benangelo.mysql.MySQL;

public class BanManager {

	private static String entbannungsantrag;
	
	/*
	 * 
	 * Syntax: Spieler, UUID, Ende, Grund
	 * 
	 */
	
	public static void ban(UUID uuid, String playername, String reason, long seconds) {
		long end = 0;
		if(seconds == -1) {
			end = -1;
		} else {
			long current  = System.currentTimeMillis();
			long millis = seconds*1000;
			end = current+millis;
		}
		
		MySQL.update("INSERT INTO BannedPlayers (Spieler, UUID, Ende, Grund) VALUES ('"+ playername +"', '"+ uuid +"', '"+ end +"', '"+ reason +"')");
		if(Bukkit.getPlayer(playername) != null) {
			Bukkit.getPlayer(playername).kickPlayer("§4Du wurdest vom Server gebannt!\n" +
													"\n" + 
													"§3Grund: §e" + MySQL.getReason(uuid) + "\n" +
													"\n" +
													"§3Verbleibende Zeit: §e" + getRemainingTime(uuid) + "\n" + 
													"\n" + 
													"§3Du kannst einen Entbannungasantrag stellen bei §e" + entbannungsantrag + " §3!");
		}
	}

	public static void unban(UUID uuid) {
		MySQL.update("DELETE FROM BannedPLayers WHERE UUID='" + uuid + "'");
	}
	
	public static boolean isBanned(UUID uuid) {
		return MySQL.userisBanned(uuid);
	}
	
	public static String getReason(UUID uuid) {
		return MySQL.getReason(uuid);
	}
	
	public static Long getEnd(UUID uuid) {
		return MySQL.getEnde(uuid);
	}
	
	public static List<String> getBannedPlayers() {
		List<String> list = new ArrayList<String>();
		ResultSet rs  = MySQL.getResult("SELECT * FROM BannedPLayers");
		try {
			while(rs.next()) {
				list.add(rs.getString("Spieler"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static String getRemainingTime(UUID uuid) {
		long current = System.currentTimeMillis();
		long end = getEnd(uuid);
		if(end == -1) {
			return "§4Permanent";
		}
		
		long millis = end - current;
		
		int seconds = 0;
		int minutes = 0;
		int hours = 0;
		int days = 0;
		int weeks = 0;
		int months = 0;
		int years = 0;
		
		while(millis > 1000) {
			millis-=1000;
			seconds++;
		}
		while(seconds > 60) {
			seconds-=60;
			minutes++;
		}
		while(minutes > 60) {
			minutes-=60;
			hours++;
		}
		while(hours > 24) {
			hours-=24;
			days++;
		}
		while(days > 7) {
			days-=7;
			weeks++;
		}
		while(weeks > 4) {
			weeks-=4;
			months++;
		}
		while(months > 12) {
			months-=12;
			years++;
		}
		
		return "§e" + years + " Jahr(e) " + months + " Monat(e) " + weeks + " Woche(n) " + days + " Tag(e) " + hours + " Stunde(n) " + minutes + " Minute(n) " + seconds + " Sekunde(n)";
	}
 	
	
	
	
	
	
	public static String getEntbannungsantrag() {
		return entbannungsantrag;
	}

	public static void setEntbannungsantrag(String entbannungsantrag) {
		BanManager.entbannungsantrag = entbannungsantrag;
	}
	
}
