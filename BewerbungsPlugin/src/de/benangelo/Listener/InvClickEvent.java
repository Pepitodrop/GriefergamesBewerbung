package de.benangelo.Listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import de.benangelo.main.Main;

public class InvClickEvent implements Listener{

	public static String InvName;
	
	@EventHandler
	public void handleInvClick(InventoryClickEvent e) {
		String n = e.getWhoClicked().getName().toString();
		
		if(Main.canClick != null) {
			if(!Main.canClick.isEmpty()) {
				if(Main.canClick.contains(n))
					e.setCancelled(true);
			}
		}
	}
	
}
