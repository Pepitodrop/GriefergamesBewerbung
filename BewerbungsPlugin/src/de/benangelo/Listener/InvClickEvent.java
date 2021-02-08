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
		
		if(Main.getPlugin().canClick != null) {
			if(!Main.getPlugin().canClick.isEmpty()) {
				if(Main.getPlugin().canClick.contains(n))
					e.setCancelled(true);
			}
		}
	}
	
}
