package de.benangelo.Listener;

import java.io.IOException;

import org.bukkit.Material;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.benangelo.commands.ECCommand;

public class ChestListener implements Listener {
	
	@EventHandler
	public void handlePlayerInteract(PlayerInteractEvent e) {
		//e.getPlayer().sendMessage("1+-");
		Player p = e.getPlayer();
		//e.getPlayer().sendMessage("1++-");
			//p.sendMessage("1+");
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			//p.sendMessage("1++");
			if(e.getClickedBlock().getType() == Material.ENDER_CHEST) {
				//p.sendMessage("1+++");
				try {
					ECCommand.openInv(p,p);
					e.setCancelled(true);
				} catch (IllegalArgumentException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
