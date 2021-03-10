package de.benangelo.Listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.benangelo.util.BanManager;
import de.benangelo.util.ItemBuilder;

public class JoinListener implements Listener{

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if(BanManager.isBanned(p.getUniqueId())) {
			BanManager.unban(p.getUniqueId());
		}
		e.setJoinMessage("§2>>>     " + p.getName());
		p.getInventory().setItem(0, new ItemBuilder(Material.ENDER_PEARL).setName("§4Enderperle").build());
	}
	
}
