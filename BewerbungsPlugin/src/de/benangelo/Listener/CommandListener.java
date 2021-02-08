package de.benangelo.Listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandListener implements Listener{

	@EventHandler
	public void handlePlayerUseCommand(PlayerCommandPreprocessEvent e) {
		if(e.getMessage().equalsIgnoreCase("/pl")) {
			if(!(e.getPlayer().hasPermission("bewerbungsplugin.plCommand"))) {
				e.setCancelled(true);
				e.getPlayer().sendMessage("§4Dazu hast du keine Rechte!");
			}
		}
	}
	
}
