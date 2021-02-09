package de.benangelo.Listener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import de.benangelo.mysql.MySQL;

@SuppressWarnings("deprecation")
public class ChatLog implements Listener{

	@EventHandler
	public void handleChat(PlayerChatEvent e) {
		SimpleDateFormat dateDay = new SimpleDateFormat ("dd.MM.yyyy");
	    String nowDay = dateDay.format(new Date());
	    
	    SimpleDateFormat dateHour = new SimpleDateFormat ("HH:mm:ss");
	    String nowHour = dateHour.format(new Date());
	    
		String p = e.getPlayer().getName();
		UUID u = e.getPlayer().getUniqueId();
		String m = e.getMessage().toString();
		String hours = nowHour;
		String day = nowDay;
		/*
		 * 
		 * Syntax: Playername, UUID, Message, Time, Day
		 * 
		 */
		MySQL.update("INSERT INTO ChatLog (Playername, UUID, Message, Time, Day) VALUES (?,?,?,?,?)" , p + "," + u + "," + m + "," + hours + "," + day);
	}
	
}
