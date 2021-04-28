package de.benangelo.Listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import de.benangelo.commands.TPACommand;
import de.benangelo.main.Main;

public class PlayerQuit implements Listener{

	private static Main plugin;
	
	public PlayerQuit(Main m) {
		plugin=m;
	}
	
	@EventHandler
	public void handlePlayerOuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		String name = p.getName();
		e.setQuitMessage("§4<<<     " + p.getDisplayName());
		
		if(TPACommand.TPA.contains(name)) {
			TPACommand.TPA.remove(name);
		} else {
			if(TPACommand.DontMove.contains(name)) {
				TPACommand.DontMove.remove(name);
			} else {
				if(plugin.canClick.contains(name)) {
					plugin.canClick.remove(name);
				}
			}
		}
	}
	
}
