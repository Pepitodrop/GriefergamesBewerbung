
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

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import de.benangelo.mysql.MySQL;
import de.benangelo.util.BanManager;

public class LogInListener implements Listener{

	//Guckt ob der Spieler gebannt ist und auf den Server darf
	@EventHandler
	public void handlePlayerLogin(PlayerLoginEvent e) {
		Player p = e.getPlayer();
		if(BanManager.isBanned(p.getUniqueId())) {
			long current = System.currentTimeMillis();
			long end = BanManager.getEnd(p.getUniqueId());
			if(current < end || end == -1) {
				e.disallow(Result.KICK_BANNED, "§4Du wurdest vom Server gebannt!\n" +
						"\n" + 
						"§3Grund: §e" + MySQL.getReason(p.getUniqueId()) + "\n" +
						"\n" +
						"§3Verbleibende Zeit: §e" + BanManager.getRemainingTime(p.getUniqueId()) + "\n" + 
						"\n" + 
						"§3Du kannst einen Entbannungasantrag stellen bei §e" + BanManager.getEntbannungsantrag() + " §3!");
			}
		}
		
	}
	
}
