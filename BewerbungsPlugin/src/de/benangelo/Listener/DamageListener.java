package de.benangelo.Listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener implements Listener {
	
	@EventHandler
	public void handlePlayerGetDamage(EntityDamageEvent e) {
		e.setCancelled(true);
	}
	
}
