
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
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import de.benangelo.main.Main;

public class CommandListener implements Listener{

	private static Main plugin;
	
	public CommandListener(Main m) {
		plugin = m;
	}
	
	//Guckt ob der Spieler /pl machen darf
	@EventHandler
	public void handlePlayerUseCommand(PlayerCommandPreprocessEvent e) {
		if(e.getMessage().equalsIgnoreCase("/pl")) {
			if(!(e.getPlayer().hasPermission("bewerbungsplugin.plCommand"))) {
				e.setCancelled(true);
				e.getPlayer().sendMessage(plugin.getPrefix() + "§4Dazu hast du keine Rechte!");
			}
		}
	}
	
}
