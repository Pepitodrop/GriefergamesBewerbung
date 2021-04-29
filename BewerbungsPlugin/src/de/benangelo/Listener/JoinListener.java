
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

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.benangelo.util.BanManager;
import de.benangelo.util.ItemBuilder;
import de.benangelo.util.ScoreboardHandler;

public class JoinListener implements Listener{
	
	//Gibt dem Spieler Grundaustatung Grundrechte und entabnnt ihn soweit er gebannt wurde
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if(BanManager.isBanned(p.getUniqueId())) {
			BanManager.unban(p.getUniqueId());
		}
		
		ScoreboardHandler.setup(p);
		e.setJoinMessage("§2>>>     " + p.getName());
		p.getInventory().setItem(0, new ItemBuilder(Material.ENDER_PEARL).setName("§4Enderperle").build());
		
		p.setAllowFlight(true);
		p.setFlying(false);
	}
	
}
