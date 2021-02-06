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
	
	public static void ban(UUID uuid, String playername, String reason, int seconds) {
		long current = System.currentTimeMillis();
		long millis = seconds*1000;
		long end = current+millis;
		MySQL.update("INSERT INTO BannedPlayers (Spieler, UUID, Ende, Grund) VALUES ('"+ playername +"', '"+ uuid +"', '"+ end +"', '"+ reason +"')");
		if(Bukkit.getPlayer(playername) != null) {
			Bukkit.getPlayer(playername).kickPlayer("§4Du wurdest vom Server gebannt!\n" +
													"\n" + 
													"§3Grund: §e" + MySQL.getReason(uuid) + "\n" +
													"\n" +
													"§3Verbleibende Zeit: §e" + /*getRemainingTime(uuid)*/ "Nie" + "\n" + 
													"\n" + 
													"§3Du kannst einen Entbannungasantrag stellen bei §e" + entbannungsantrag + " §3!");
		}
	}

	public static void unban(UUID uuid) {
		MySQL.update("DELETE * FROM BannedPLayers WHERE UUID='" + uuid + "'");
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
	
//	public static String getRemainingTime(UUID uuid) {
//		
//	}
 	
	
	
	
	
	
	public static String getEntbannungsantrag() {
		return entbannungsantrag;
	}

	public static void setEntbannungsantrag(String entbannungsantrag) {
		BanManager.entbannungsantrag = entbannungsantrag;
	}
	
}
