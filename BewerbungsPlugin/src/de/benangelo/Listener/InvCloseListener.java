package de.benangelo.Listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import de.benangelo.commands.ECCommand;
import de.benangelo.config.AllgemeineConfigs;
import de.benangelo.main.Main;
import de.benangelo.mysql.MySQL;

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
			for(int i = 0; i < AllgemeineConfigs.getBreite()*AllgemeineConfigs.getHöhe(); i++) {
				if(e.getInventory().getItem(i) != null) {
					String lores;
					String amout = String.valueOf(e.getInventory().getItem(i).getAmount());
					@SuppressWarnings("deprecation")
					String durability = String.valueOf(e.getInventory().getItem(i).getDurability());
					String Type = e.getInventory().getItem(i).getType().toString();
					String DisplayName = e.getInventory().getItem(i).getItemMeta().getDisplayName().toString();
					if(e.getInventory().getItem(i).getItemMeta().getLore() != null) {
						lores = "";
						for(int l = 0; l < e.getInventory().getItem(i).getItemMeta().getLore().size(); l++) {
							lores += e.getInventory().getItem(i).getItemMeta().getLore().get(l) + " ' ";
						}
						if(!e.getInventory().getItem(i).getEnchantments().isEmpty()) {
							MySQL.update("DELETE FROM `" + MySQL.getTName(p.getUniqueId()) + "` WHERE Slot=?", String.valueOf(i));
							MySQL.update("INSERT INTO `"+ MySQL.getTName(p.getUniqueId()) + "` (amout, durability , Type, DisplayName, Slot, Lore, Enchants) VALUES (?,?,?,?,?,?,?);", amout + "," + durability + "," + Type + "," + DisplayName + "," + i + "," + lores + "," + Main.getEnchants(e.getInventory().getItem(i)));
						} else {
							MySQL.update("DELETE FROM `" + MySQL.getTName(p.getUniqueId()) + "` WHERE Slot=?", String.valueOf(i));
							MySQL.update("INSERT INTO `"+ MySQL.getTName(p.getUniqueId()) + "` (amout, durability , Type, DisplayName, Slot, Lore) VALUES (?,?,?,?,?,?);", amout + "," + durability + "," + Type + "," + DisplayName + "," + i + "," + lores);
						}	
					}  else {
						if(!e.getInventory().getItem(i).getEnchantments().isEmpty()) {
							MySQL.update("DELETE FROM `" + MySQL.getTName(p.getUniqueId()) + "` WHERE Slot=?", String.valueOf(i));
							MySQL.update("INSERT INTO `"+ MySQL.getTName(p.getUniqueId()) + "` (amout, durability , Type, DisplayName, Slot, Enchants) VALUES (?,?,?,?,?,?);", amout + "," + durability + "," + Type + "," + DisplayName + "," + i + "," + Main.getEnchants(e.getInventory().getItem(i)));
						} else {
							MySQL.update("DELETE FROM `" + MySQL.getTName(p.getUniqueId()) + "` WHERE Slot=?", String.valueOf(i));
							MySQL.update("INSERT INTO `"+ MySQL.getTName(p.getUniqueId()) + "` (amout, durability , Type, DisplayName, Slot) VALUES (?,?,?,?,?);", amout + "," + durability + "," + Type + "," + DisplayName + "," + i);
						}	
					}
					
					
				} else {
					
					MySQL.update("DELETE FROM `" + MySQL.getTName(p.getUniqueId()) + "` WHERE Slot=?", String.valueOf(i));
					MySQL.update("INSERT INTO `"+ MySQL.getTName(p.getUniqueId()) + "` (amout, durability , Type, DisplayName, Slot) VALUES (?,?,?,?,?);", 1 + "," + 0 + "," + Material.AIR.toString() + "," + "" + "," + i);
			}
			
						
	}
}
	}
	
}
