
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

package de.benangelo.commands;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import de.benangelo.config.AllgemeineConfigs;
import de.benangelo.main.Main;
import de.benangelo.mysql.MySQL;
import de.benangelo.util.InventoryUtil;

public class ECCommand implements CommandExecutor{
	
	public static String InvName;
	private static Main plugin;
	
	public ECCommand(Main m) {
		plugin = m;
	}
	
	//Lädt die Enderchest
	public static void openInvOffline(OfflinePlayer target, Player sender) throws IllegalArgumentException, IOException {
		Inventory inventory = Bukkit.createInventory(null, new AllgemeineConfigs(plugin).getBreite()*new AllgemeineConfigs(plugin).getHöhe(), "§4" + InvName + " §2von §6" + target.getName());
		if(MySQL.UserExistsEC(target.getUniqueId())) {

			inventory.setContents(InventoryUtil.itemStackArrayFromBase64(MySQL.getInventoryContent(target.getUniqueId())));
			
			sender.openInventory(inventory);
			if(target.getName().equalsIgnoreCase(sender.getName().toString())) {
				sender.sendMessage(plugin.getPrefix() + "§2Deine EC wurde geöffnet!");
			} else {
				
				if(sender.hasPermission("EC.canClick")) {
					sender.sendMessage(plugin.getPrefix() + "§2Die EC von §6" + target.getName() + " §2wurde  dir eröffnet!");
				} else {
					if(plugin.getPlugin().canClick != null) {
						plugin.getPlugin().canClick.add(sender.getName().toString());
					}
					sender.sendMessage(plugin.getPrefix() + "§2Die EC von §6" + target.getName() + " §2wurde  dir eröffnet!");
				}
				
				
			}
				
		} else {
				
				if(!target.getName().equalsIgnoreCase(sender.getName().toString())) {
					sender.sendMessage(plugin.getPrefix() + "§4Dieser Spieler hat eine Enderchest auf diesem Server!");
				}
				sender.openInventory(inventory);
			}	
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String lable, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
				if(args.length == 0) {
					try {
						//Öffnet dem Spieler seine Ender Chest
						OfflinePlayer pOff = Bukkit.getOfflinePlayer(p.getUniqueId());
						openInvOffline(pOff, p);
					} catch (IllegalArgumentException | IOException e) {
						e.printStackTrace();
					}
				} else {
					if(args.length == 1) {
								
						for(OfflinePlayer allOFF : Bukkit.getOfflinePlayers()) {
							if(allOFF.getName().equalsIgnoreCase(args[0])) {
								try {
									
									//Öffnet dem Spieler eine Ender Chest von einem anderen Spieler
									openInvOffline(allOFF, p);
								} catch (IllegalArgumentException | IOException e) {
									e.printStackTrace();
								}
						} else
							p.sendMessage(plugin.getPrefix() + "§4Dieser Spieler exestiert leider nicht auf diesemm Server!");
						}
						
					} else
						p.sendMessage(plugin.getPrefix() + "§4Bitte benutze §2/EC (Spieler) §4!");
				}
		} else
			sender.sendMessage(plugin.getPrefix() + "Du musst ein Spieler sein");
		return false;
	}
	
}
