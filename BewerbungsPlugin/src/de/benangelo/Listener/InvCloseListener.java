package de.benangelo.Listener;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import de.benangelo.commands.ECCommand;
import de.benangelo.main.Main;
import de.benangelo.mysql.MySQL;
import de.benangelo.util.InventoryUtil;

public class InvCloseListener implements Listener{
	
	private static Main plugin;
	
	public InvCloseListener(Main m) {
		plugin=m;
	}
	
	@EventHandler
	public void handleInvClose(InventoryCloseEvent e) {
		Player p = (Player) e.getPlayer();
			if(!plugin.getPlugin().canClick.isEmpty()) {
				if(plugin.getPlugin().canClick.contains(p.getName().toString()))
					plugin.getPlugin().canClick.remove(p.getName().toString());
			}
		
		if(e.getPlayer().getOpenInventory().getTitle().equals("§4" + ECCommand.InvName + " §2von §6" + p.getName())) {
			UUID uuid = e.getPlayer().getUniqueId();
			String name = e.getPlayer().getName();
			String content = InventoryUtil.itemStackArrayToBase64(e.getInventory().getContents());
			
			if(MySQL.UserExistsEC(uuid)) {
				MySQL.update("UPDATE EC SET Content=? WHERE UUID=?", content + "," + uuid);
			} else
				MySQL.update("INSERT INTO " + "EC" + " (UUID, Playername , Content) VALUES (?,?,?);", uuid + "," + name + "," + content);
			
		}
	}
	
}
