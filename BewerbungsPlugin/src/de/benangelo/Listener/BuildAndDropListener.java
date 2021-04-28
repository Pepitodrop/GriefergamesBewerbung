
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

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent; 

public class BuildAndDropListener implements Listener{

	private static ArrayList<Player> canBuild = new ArrayList<>();
	private static ArrayList<Player> canDrop = new ArrayList<>();
	
	//Guckt ob der Spieler droppen darf
	@EventHandler
	public void handlePlayerDrop(PlayerDropItemEvent e) {
		if(!canDrop.contains(e.getPlayer())) {
			e.setCancelled(true);
		}
	}
	
	//Guckt ob der Spieler bauen darf
	@SuppressWarnings("deprecation")
	@EventHandler
	public void handlePlayerPlace(PlayerInteractEvent e) {
		if(e.getAction() == Action.LEFT_CLICK_BLOCK) {
			if(!canBuild.contains(e.getPlayer())) {
				e.setCancelled(true);
			}
		} else {
			if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if(e.getPlayer().getItemInHand().getType().isBlock() != false) {
					if(!canBuild.contains(e.getPlayer())) {
						e.setCancelled(true);
					}
				}
			}
		}
	}

	public static ArrayList<Player> getCanBuild() {
		return canBuild;
	}

	public static void setCanBuild(ArrayList<Player> canBuild) {
		BuildAndDropListener.canBuild = canBuild;
	}

	public static ArrayList<Player> getCanDrop() {
		return canDrop;
	}

	public static void setCanDrop(ArrayList<Player> canDrop) {
		BuildAndDropListener.canDrop = canDrop;
	}
}
