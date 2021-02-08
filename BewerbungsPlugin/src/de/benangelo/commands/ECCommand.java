package de.benangelo.commands;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.benangelo.config.AllgemeineConfigs;
import de.benangelo.main.Main;
import de.benangelo.mysql.MySQL;
import de.benangelo.util.ItemBuilder;

public class ECCommand implements CommandExecutor{
	
	public static String InvName;
	private static int i = 1;
	
	public static void openInv(Player target, Player sender) throws IllegalArgumentException, IOException {
		Inventory inventory = Bukkit.createInventory(null, AllgemeineConfigs.getBreite()*AllgemeineConfigs.getHöhe(), "§4" + InvName + " §2von §6" + target.getName());
		if(MySQL.UserExistsPlayer(target.getUniqueId())) {
			//System.out.println("1+-+-");
			for(int i = 0; i < AllgemeineConfigs.getBreite()*AllgemeineConfigs.getHöhe(); i++) {
				//System.out.println(i);
				//System.out.println("+-+-1 " + i);
				//System.out.println(i);
				//System.out.println("1");
				//System.out.println("2");
				//p.sendMessage("3"); 
				//p.sendMessage(String.valueOf(Integer.valueOf(MySQL.getAmout(i, p.getName().toString()).toString())));
				//p.sendMessage(MySQL.getItem(i, p.getName().toString()).toString());
				//inventory.setItem(i, new ItemStack(Material.SNOW, 45));
				//inventory.setItem(i, new ItemStack(Material.matchMaterial(MySQL.getItem(i, p.getName().toString())), Integer.valueOf(MySQL.getAmout(i, p.getName().toString()).toString()), Short.valueOf(MySQL.getdurability(i, p.getName().toString()))));
				
				if(MySQL.getItem(i, MySQL.getTName(target.getUniqueId())).equalsIgnoreCase("AIR")) {
					inventory.setItem(i, new ItemStack(Material.AIR));
					//System.out.println("Luft!");
				} else {
					//System.out.println("Keine Luft!");
					if(MySQL.getDisplayName(i, MySQL.getTName(target.getUniqueId())) != null) {
						//System.out.println("Kein Name");
					inventory.setItem(i, new ItemBuilder(Material.matchMaterial(MySQL.getItem(i, MySQL.getTName(target.getUniqueId())))).setAmount(Integer.valueOf(MySQL.getAmout(i, MySQL.getTName(target.getUniqueId())))).setDurability(Short.valueOf(MySQL.getdurability(i, MySQL.getTName(target.getUniqueId())))).setName(MySQL.getDisplayName(i, MySQL.getTName(target.getUniqueId()))).build());
				} else {
					//System.out.println("Name");
					inventory.setItem(i, new ItemBuilder(Material.matchMaterial(MySQL.getItem(i, MySQL.getTName(target.getUniqueId())))).setAmount(Integer.valueOf(MySQL.getAmout(i,MySQL.getTName(target.getUniqueId())))).setDurability(Short.valueOf(MySQL.getdurability(i, MySQL.getTName(target.getUniqueId())))).build());
				}
				}
				
				//inventory.setItem(i, new ItemStack(Material.matchMaterial("SNOW"), 1 ));
				
				
			}
			sender.openInventory(inventory);
			if(target.getName().equalsIgnoreCase(sender.getName().toString())) {
				sender.sendMessage("§2Deine EC wurde geöffnet!");
			} else {
				
				if(sender.hasPermission("EC.canClick")) {
					sender.sendMessage("§2Die EC von §6" + target.getName() + " §2wurde  dir eröffnet!");
				} else {
					if(Main.getPlugin().canClick != null) {
						Main.getPlugin().canClick.add(sender.getName().toString());
					}
					sender.sendMessage("§2Die EC von §6" + target.getName() + " §2wurde  dir eröffnet!");
				}
				
				
			}
				
		} else {
			while (MySQL.setTname(i)) {
				i++;
			}
			if(!MySQL.setTname(i)) {
				MySQL.createChestTable(i);
				MySQL.update("INSERT INTO "+ "Players" + "(Spielername, UUID, TabelName) VALUES ('" + target.getName() + "', '" + target.getUniqueId() +"', '" + i +"')");
				//System.out.println("4");
				sender.openInventory(inventory);
				if(target.getName().equalsIgnoreCase(sender.getName())) {
					sender.sendMessage("§2Deine EC wurde geöffnet!");
				} else {
					if(sender.hasPermission("EC.canClick")) {
						sender.sendMessage("§2Die EC von §6" + target.getName() + " §2wurde  dir eröffnet!");
					} else {
						if(Main.getPlugin().canClick != null) {
							Main.getPlugin().canClick.add(sender.getName().toString());
						}
						sender.sendMessage("§2Die EC von §6" + target.getName() + " §2wurde  dir eröffnet!");
					}
				}
				i=1;
			}
		}
			
    		
			
	}
	
	public static void openInvOffline(OfflinePlayer target, Player sender) throws IllegalArgumentException, IOException {
		Inventory inventory = Bukkit.createInventory(null, AllgemeineConfigs.getBreite()*AllgemeineConfigs.getHöhe(), "§4" + InvName + " §2von §6" + target.getName());
		if(MySQL.UserExistsPlayer(target.getUniqueId())) {
			//System.out.println("1+-+-");
			for(int i = 0; i < AllgemeineConfigs.getBreite()*AllgemeineConfigs.getHöhe(); i++) {
				//System.out.println(i);
				//System.out.println("+-+-1 " + i);
				//System.out.println(i);
				//System.out.println("1");
				//System.out.println("2");
				//p.sendMessage("3"); 
				//p.sendMessage(String.valueOf(Integer.valueOf(MySQL.getAmout(i, p.getName().toString()).toString())));
				//p.sendMessage(MySQL.getItem(i, p.getName().toString()).toString());
				//inventory.setItem(i, new ItemStack(Material.SNOW, 45));
				//inventory.setItem(i, new ItemStack(Material.matchMaterial(MySQL.getItem(i, p.getName().toString())), Integer.valueOf(MySQL.getAmout(i, p.getName().toString()).toString()), Short.valueOf(MySQL.getdurability(i, p.getName().toString()))));
				
				if(MySQL.getItem(i, MySQL.getTName(target.getUniqueId())).equalsIgnoreCase("AIR")) {
					inventory.setItem(i, new ItemStack(Material.AIR));
					//System.out.println("Luft!");
				} else {
					//System.out.println("Keine Luft!");
					if(MySQL.getDisplayName(i, MySQL.getTName(target.getUniqueId())) != null) {
						//System.out.println("Kein Name");
					inventory.setItem(i, new ItemBuilder(Material.matchMaterial(MySQL.getItem(i, MySQL.getTName(target.getUniqueId())))).setAmount(Integer.valueOf(MySQL.getAmout(i, MySQL.getTName(target.getUniqueId())))).setDurability(Short.valueOf(MySQL.getdurability(i, MySQL.getTName(target.getUniqueId())))).setName(MySQL.getDisplayName(i, MySQL.getTName(target.getUniqueId()))).build());
				} else {
					//System.out.println("Name");
					inventory.setItem(i, new ItemBuilder(Material.matchMaterial(MySQL.getItem(i, MySQL.getTName(target.getUniqueId())))).setAmount(Integer.valueOf(MySQL.getAmout(i,MySQL.getTName(target.getUniqueId())))).setDurability(Short.valueOf(MySQL.getdurability(i, MySQL.getTName(target.getUniqueId())))).build());
				}
				}
				
				//inventory.setItem(i, new ItemStack(Material.matchMaterial("SNOW"), 1 ));
				
				
			}
			sender.openInventory(inventory);
			if(sender.hasPermission("EC.canClick")) {
				sender.sendMessage("§2Die EC von §6" + target.getName() + " §2wurde  dir eröffnet!");
			} else {
				if(Main.getPlugin().canClick != null) {
					Main.getPlugin().canClick.add(sender.getName().toString());
				}
				sender.sendMessage("§2Die EC von §6" + target.getName() + " §2wurde  dir eröffnet!");
			} 
				
		} else {
			while (MySQL.setTname(i)) {
				i++;
			}
			if(!MySQL.setTname(i)) {
				MySQL.createChestTable(i);
				MySQL.update("INSERT INTO "+ "Players" + "(Spielername, UUID, TabelName) VALUES ('" + target.getName() + "', '" + target.getUniqueId() +"', '" + i +"')");
				//System.out.println("4");
				sender.openInventory(inventory);
				if(sender.hasPermission("EC.canClick")) {
					sender.sendMessage("§2Die EC von §6" + target.getName() + " §2wurde  dir eröffnet!");
				} else {
					if(Main.getPlugin().canClick != null) {
						Main.getPlugin().canClick.add(sender.getName().toString());
					}
					sender.sendMessage("§2Die EC von §6" + target.getName() + " §2wurde  dir eröffnet!");
				}
				i=1;
			}
		}
			
    		
			
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String lable, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
				if(args.length == 0) {
					try {
						openInv(p, p);
					} catch (IllegalArgumentException | IOException e) {
						p.sendMessage("§4Leider exestiert keine MySQL Verbindung");
						System.out.println("Leider exestiert keine MySQL Verbindung");
					}
				} else {
					if(args.length == 1) {
						for(Player all : Bukkit.getOnlinePlayers()) {
							if(all.getName().equalsIgnoreCase(args[0])) {
								try {
									openInv(all, p);
								} catch (IllegalArgumentException | IOException e) {
									p.sendMessage("§4Leider exestiert keine MySQL Verbindung");
									System.out.println("Leider exestiert keine MySQL Verbindung");
								}
						} else {
								
						for(OfflinePlayer allOFF : Bukkit.getOfflinePlayers()) {
							if(allOFF.getName().equalsIgnoreCase(args[0])) {
								try {
									openInvOffline(allOFF, p);
								} catch (IllegalArgumentException | IOException e) {
									p.sendMessage("§4Leider exestiert keine MySQL Verbindung");
									System.out.println("Leider exestiert keine MySQL Verbindung");
								}
						} else
							p.sendMessage("§4Dieser Spieler exestiert leider nicht auf diesemm Server!");
						}
						}
						}
						
					} else
						p.sendMessage("§4Bitte benutze §2/EC (Spieler) §4!");
				}
		} else
			System.out.println("Du musst ein Spieler sein");
		return false;
	}
	
}
