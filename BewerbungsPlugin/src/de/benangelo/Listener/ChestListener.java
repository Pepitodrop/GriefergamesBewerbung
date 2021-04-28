package de.benangelo.Listener;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.benangelo.commands.ECCommand;

public class ChestListener implements Listener {

	@EventHandler
	public void handlePlayerInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getClickedBlock().getType() == Material.ENDER_CHEST) {
				try {
					OfflinePlayer pOff = Bukkit.getOfflinePlayer(p.getUniqueId());
					ECCommand.openInvOffline(pOff,p);
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
