package de.benangelo.Listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import de.benangelo.commands.TPACommand;

public class PlayerMove implements Listener{

	@EventHandler
	public void handlePlayerMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		
		if(!(p.getLocation().getX() == e.getTo().getX() && p.getLocation().getZ() == e.getTo().getZ())) {
			if(TPACommand.DontMove.contains(p.getName())) {
				Bukkit.getScheduler().cancelTask(TPACommand.getTaskID());
				p.sendMessage("§4Deine Teleportation wurde abgebrochen!");
				TPACommand.DontMove.remove(p.getName());
		}	
	}
	
}
}
