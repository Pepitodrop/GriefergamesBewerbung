package de.benangelo.Listener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import de.benangelo.mysql.MySQL;

public class CommandLog implements Listener{
	
	@EventHandler
	public void handleCommands(PlayerCommandPreprocessEvent e) {
		SimpleDateFormat dateDay = new SimpleDateFormat ("dd.MM.yyyy");
	    String nowDay = dateDay.format(new Date());
	    
	    SimpleDateFormat dateHour = new SimpleDateFormat ("HH:mm:ss");
	    String nowHour = dateHour.format(new Date());
	    
		String p = e.getPlayer().getName();
		UUID u = e.getPlayer().getUniqueId();
		String c = e.getMessage();
		String hours = nowHour;
		String day = nowDay;
		/*
		 * 
		 * Syntax: Playername, UUID, Command, Time, Day
		 * 
		 */
		MySQL.update("INSERT INTO CommandLog (Playername, UUID, Command, Time, Day) VALUES (?,?,?,?,?)", p + "," + u + "," + c + "," + hours + "," + day);
	}

}
