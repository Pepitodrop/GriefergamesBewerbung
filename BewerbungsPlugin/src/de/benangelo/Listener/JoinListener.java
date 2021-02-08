package de.benangelo.Listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.benangelo.util.BanManager;

public class JoinListener implements Listener{

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if(BanManager.isBanned(p.getUniqueId())) {
			BanManager.unban(p.getUniqueId());
		}
		e.setJoinMessage("§2>>>     " + p.getName());
	}
	
}
