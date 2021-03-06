
/*
 * Luis Benedikt
 * 
 * 28.4.2021
 * 
 * Die Benutzung nur nach Absprache Erlaubt
 * 
 * Dieses Plugin soll meine Programmierkünste in Spigot zeigen
 * 
 */

package de.benangelo.Listener;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import de.benangelo.mysql.MySQL;

public class BlockLog implements Listener{
	
	//Speichert einen zerstörten Block
	@EventHandler
	public void handleBlockBreak(BlockBreakEvent e) {
		SimpleDateFormat dateDay = new SimpleDateFormat ("dd.MM.yyyy");
	    String nowDay = dateDay.format(new Date());
	    
	    SimpleDateFormat dateHour = new SimpleDateFormat ("HH:mm:ss");
	    String nowHour = dateHour.format(new Date());
	    
		String p = e.getPlayer().getName();
		Material m = e.getBlock().getType();
		Location l = e.getBlock().getLocation();
		double x = l.getX();
		double y = l.getY();
		double z = l.getZ();
		String hours = nowHour;
		String day = nowDay;
		/*
		 * 
		 * Syntax: Material, Player, Position, Time, Day
		 * 
		 */
		MySQL.update("INSERT INTO BlockLogDestroy (Material, Player, Position, Time, Day) VALUES (?,?,?,?,?)", m + "," + p + "," + x + "X " + y + "Y " + z + "Z," + hours+ ","+ day);
	}
	
	//Speichert einen gesetzten Block
	@EventHandler
	public void handleBlockPlace(BlockPlaceEvent e) {
		SimpleDateFormat dateDay = new SimpleDateFormat ("dd.MM.yyyy");
	    String nowDay = dateDay.format(new Date());
	    
	    SimpleDateFormat dateHour = new SimpleDateFormat ("HH:mm:ss");
	    String nowHour = dateHour.format(new Date());
	    		
		String p = e.getPlayer().getName();
		Material m = e.getBlock().getType();
		Location l = e.getBlock().getLocation();
		double x = l.getX();
		double y = l.getY();
		double z = l.getZ();
		String hours = nowHour;
		String day = nowDay;
		/*
		 * 
		 * Syntax: Material, Player, Position, Time, Day
		 * 
		 */
		MySQL.update("INSERT INTO BlockLogPlace (Material, Player, Position, Time, Day) VALUES (?,?,?,?,?)", m + "," + p + "," + x + "X " + y + "Y " + z + "Z," + hours+ ","+ day);
	}
}
