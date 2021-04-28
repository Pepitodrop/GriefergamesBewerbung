package de.benangelo.Listener;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

@SuppressWarnings("deprecation")
public class PickUpItemEvent implements Listener{
	
	private static ArrayList<Player> canPickItem = new ArrayList<>();
	
	@EventHandler
	public void handlePlayerPickUpItem(PlayerPickupItemEvent e) {
		Player p = e.getPlayer();
		if(canPickItem.isEmpty() || !(canPickItem.contains(p))) {
			e.setCancelled(true);
		}
	}

	public static ArrayList<Player> getCanPickItem() {
		return canPickItem;
	}

	public static void setCanPickItem(ArrayList<Player> canPickItem) {
		PickUpItemEvent.canPickItem = canPickItem;
	}

}
