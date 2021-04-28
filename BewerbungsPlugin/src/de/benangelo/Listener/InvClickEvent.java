
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

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import de.benangelo.main.Main;

public class InvClickEvent implements Listener{

	public static String InvName;
	private static Main plugin;
	
	public InvClickEvent(Main m) {
		plugin=m;
	}
	
	//Guckt ob der Spieler klicken darf
	@EventHandler
	public void handleInvClick(InventoryClickEvent e) {
		String n = e.getWhoClicked().getName().toString();
		
		if(plugin.getPlugin().canClick != null) {
			if(!plugin.getPlugin().canClick.isEmpty()) {
				if(plugin.getPlugin().canClick.contains(n))
					e.setCancelled(true);
			}
		}
	}
	
}
