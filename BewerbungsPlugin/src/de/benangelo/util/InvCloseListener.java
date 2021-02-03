package de.benangelo.util;

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
		if(Main.canClick != null) {
			if(!Main.canClick.isEmpty()) {
				if(Main.canClick.contains(p.getName().toString()))
					Main.canClick.remove(p.getName().toString());
			}
		}
		//e.getPlayer().sendMessage("a");
		//System.out.println("a");
		if(e.getPlayer().getOpenInventory().getTitle().equals("§4" + ECCommand.InvName + " §2von §6" + p.getName())) {
			//e.getPlayer().sendMessage("b");
			//System.out.println("b");
			for(int i = 0; i < AllgemeineConfigs.getBreite()*AllgemeineConfigs.getHöhe(); i++) {
				if(e.getInventory().getItem(i) != null) {
					
					//System.out.println("0");
					String amout = String.valueOf(e.getInventory().getItem(i).getAmount());
					@SuppressWarnings("deprecation")
					String durability = String.valueOf(e.getInventory().getItem(i).getDurability());
					//String enchants =  all.getOpenInventory().getItem(i).getEnchantments().toString();
					//String enchantsLevel = String.valueOf(all.getOpenInventory().getItem(i).getEnchantments().get(enchants));
					String Type = e.getInventory().getItem(i).getType().toString();
					String DisplayName = e.getInventory().getItem(i).getItemMeta().getDisplayName().toString();
					//String ItemFlags = all.getOpenInventory().getItem(i).getItemMeta().getItemFlags().toString();
					
					//System.out.println(Type);
					//all.sendMessage(Type);
					
					MySQL.update("DELETE FROM `" + MySQL.getTName(p.getUniqueId()) + "` WHERE Slot='" + i + "'");
					//MySQL.update("INSERT INTO "+ all.getName().toString() + " (amout, durability , Type, DisplayName, Slot) VALUES ('" + amout + "', '" + durability + "', '" + enchants + "', '" + Type + "', '" + DisplayName +"', '" + i +"');");
            		MySQL.update("INSERT INTO `"+ MySQL.getTName(p.getUniqueId()) + "` (amout, durability , Type, DisplayName, Slot) VALUES ('" + amout + "', '" + durability + "', '" + Type + "', '" + DisplayName +"', '" + i +"');");
				} else {
					//System.out.println("1");
					MySQL.update("DELETE FROM `" + MySQL.getTName(p.getUniqueId()) + "` WHERE Slot='" + i + "'");
					//MySQL.update("INSERT INTO "+ all.getName().toString() + " (amout, durability , Type, DisplayName, Slot) VALUES ('" + 1 + "', '" + 0 + "', '" + "" + "', '" + Material.AIR.toString() + "', '" + "" +"', '" + i +"');");
					MySQL.update("INSERT INTO `"+ MySQL.getTName(p.getUniqueId()) + "` (amout, durability , Type, DisplayName, Slot) VALUES ('" + 1 + "', '" + 0 + "', '" + Material.AIR.toString() + "', '" + "" +"', '" + i +"');");
				
				
			}
			
						
	}
}
	}
	
}
