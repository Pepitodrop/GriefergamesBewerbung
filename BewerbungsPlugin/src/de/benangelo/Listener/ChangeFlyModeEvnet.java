
/*
 * Luis Benedikt
 * 
 * 28.4.2021
 * 
 * Die Benutzung nur nach Absprache Erlaubt
 * 
 * Dieses Plugin soll meine Programmierk�nste in Spigot zeigen
 * 
 */

package de.benangelo.Listener;

import java.util.ArrayList;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.util.Vector;

public class ChangeFlyModeEvnet implements Listener{

	private static ArrayList<Player> getCanFly = new ArrayList<>();
	
	//Gibt dem Spieler einen Boost
	@EventHandler
	public void onFly(PlayerToggleFlightEvent e) {
		Player p = e.getPlayer();
		if(p.getGameMode() == GameMode.SURVIVAL || p.getGameMode() == GameMode.ADVENTURE) {
			if(getCanFly.isEmpty() || !(getCanFly.contains(p)))  {
			e.setCancelled(true);
			p.setAllowFlight(false);
			p.setFlying(false);
			p.setVelocity(p.getLocation().getDirection().multiply(2).add(new Vector(0, 1.5, 0)));
		}
		}
	}

	public static ArrayList<Player> getCanFly() {
		return getCanFly;
	}

}
