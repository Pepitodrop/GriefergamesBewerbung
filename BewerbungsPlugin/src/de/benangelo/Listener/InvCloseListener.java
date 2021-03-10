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
	
	@EventHandler
	public void handleInvClose(InventoryCloseEvent e) {
		Player p = (Player) e.getPlayer();
		if(Main.getPlugin().canClick != null) {
			if(!Main.getPlugin().canClick.isEmpty()) {
				if(Main.getPlugin().canClick.contains(p.getName().toString()))
					Main.getPlugin().canClick.remove(p.getName().toString());
			}
		}
		
		if(e.getPlayer().getOpenInventory().getTitle().equals("§4" + ECCommand.InvName + " §2von §6" + p.getName())) {
			UUID uuid = e.getPlayer().getUniqueId();
			String name = e.getPlayer().getName();
			String content = InventoryUtil.itemStackArrayToBase64(e.getInventory().getContents());
			
			MySQL.update("DELETE FROM " + "EC" + " WHERE UUID=?", e.getPlayer().getUniqueId().toString());
			MySQL.update("INSERT INTO " + "EC" + " (UUID, Playername , Content) VALUES (?,?,?);", uuid + "," + name + "," + content);
		}
	}
	
}
