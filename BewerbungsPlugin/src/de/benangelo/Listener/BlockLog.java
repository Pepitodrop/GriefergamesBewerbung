package de.benangelo.Listener;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import de.benangelo.mysql.MySQL;

public class BlockLog implements Listener{
	
	@EventHandler
	public void handleBlockBreak(BlockBreakEvent e) {
		SimpleDateFormat dateDay = new SimpleDateFormat ("dd.MM.yyyy");
	    String nowDay = dateDay.format(new Date());
	    
	    SimpleDateFormat dateHour = new SimpleDateFormat ("HH:mm:ss");
	    String nowHour = dateHour.format(new Date());
	    
		String p = e.getPlayer().getName();
		Material m = e.getBlock().getType();
		double x = e.getBlock().getLocation().getX();
		double y = e.getBlock().getLocation().getY();
		double z = e.getBlock().getLocation().getZ();
		String hours = nowHour;
		String day = nowDay;
		/*
		 * 
		 * Syntax: Material, Player, Position, Time, Day
		 * 
		 */
		MySQL.update("INSERT INTO BlockLogDestroy (Material, Player, Position, Time, Day) VALUES ('" + m + "', '" + p + "', '" + x + "X " + y + "Y " + z + "Z', '" + hours + "', '" + day + "')");
	}
	
	@EventHandler
	public void handleBlockPlace(BlockPlaceEvent e) {
		SimpleDateFormat dateDay = new SimpleDateFormat ("dd.MM.yyyy");
	    String nowDay = dateDay.format(new Date());
	    
	    SimpleDateFormat dateHour = new SimpleDateFormat ("HH:mm:ss");
	    String nowHour = dateHour.format(new Date());
	    		
		String p = e.getPlayer().getName();
		Material m = e.getBlock().getType();
		double x = e.getBlock().getLocation().getX();
		double y = e.getBlock().getLocation().getY();
		double z = e.getBlock().getLocation().getZ();
		String hours = nowHour;
		String day = nowDay;
		/*
		 * 
		 * Syntax: Material, Player, Position, Time, Day
		 * 
		 */
		MySQL.update("INSERT INTO BlockLogPlace (Material, Player, Position, Time, Day) VALUES ('" + m + "', '" + p + "', '" + x + "X " + y + "Y " + z + "Z', '" + hours + "', '" + day + "')");
	}
	
}
