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

			for(int i = 0; i < AllgemeineConfigs.getBreite()*AllgemeineConfigs.getHöhe(); i++) {

				if(MySQL.getItem(i, MySQL.getTName(target.getUniqueId())).equalsIgnoreCase("AIR")) {
					inventory.setItem(i, new ItemStack(Material.AIR));
				} else {
					if(MySQL.getItem(i, MySQL.getTName(target.getUniqueId())).equalsIgnoreCase("ENCHANTED_BOOK")) {
						
						if(MySQL.getDisplayName(i, MySQL.getTName(target.getUniqueId())) != null) {
							if(MySQL.getLore(i, MySQL.getTName(target.getUniqueId())) != null ) {
								
								if(MySQL.getEnchants(i, MySQL.getTName(target.getUniqueId())) != null) {
									inventory.setItem(i, new ItemBuilder(Material.matchMaterial(MySQL.getItem(i, MySQL.getTName(target.getUniqueId())))).setAmount(Integer.valueOf(MySQL.getAmout(i, MySQL.getTName(target.getUniqueId())))).setDurability(Short.valueOf(MySQL.getdurability(i, MySQL.getTName(target.getUniqueId())))).setName(MySQL.getDisplayName(i, MySQL.getTName(target.getUniqueId()))).setLore(MySQL.getLore(i, MySQL.getTName(target.getUniqueId()))).setEnchantmentBook(MySQL.getEnchants(i, MySQL.getTName(target.getUniqueId())).split(":").length, MySQL.getEnchants(i, MySQL.getTName(target.getUniqueId()))).build());
								} else {
									inventory.setItem(i, new ItemBuilder(Material.matchMaterial(MySQL.getItem(i, MySQL.getTName(target.getUniqueId())))).setAmount(Integer.valueOf(MySQL.getAmout(i, MySQL.getTName(target.getUniqueId())))).setDurability(Short.valueOf(MySQL.getdurability(i, MySQL.getTName(target.getUniqueId())))).setName(MySQL.getDisplayName(i, MySQL.getTName(target.getUniqueId()))).setLore(MySQL.getLore(i, MySQL.getTName(target.getUniqueId()))).build());
								}
								
							} else {
								if(MySQL.getEnchants(i, MySQL.getTName(target.getUniqueId())) != null) {
									inventory.setItem(i, new ItemBuilder(Material.matchMaterial(MySQL.getItem(i, MySQL.getTName(target.getUniqueId())))).setAmount(Integer.valueOf(MySQL.getAmout(i, MySQL.getTName(target.getUniqueId())))).setDurability(Short.valueOf(MySQL.getdurability(i, MySQL.getTName(target.getUniqueId())))).setName(MySQL.getDisplayName(i, MySQL.getTName(target.getUniqueId()))).setEnchantmentBook(MySQL.getEnchants(i, MySQL.getTName(target.getUniqueId())).split(":").length, MySQL.getEnchants(i, MySQL.getTName(target.getUniqueId()))).build());
								} else
								inventory.setItem(i, new ItemBuilder(Material.matchMaterial(MySQL.getItem(i, MySQL.getTName(target.getUniqueId())))).setAmount(Integer.valueOf(MySQL.getAmout(i, MySQL.getTName(target.getUniqueId())))).setDurability(Short.valueOf(MySQL.getdurability(i, MySQL.getTName(target.getUniqueId())))).setName(MySQL.getDisplayName(i, MySQL.getTName(target.getUniqueId()))).build());
							}
								
					} else {
						if(MySQL.getLore(i, MySQL.getTName(target.getUniqueId())) != null ) {
							if(MySQL.getEnchants(i, MySQL.getTName(target.getUniqueId())) != null) {
								inventory.setItem(i, new ItemBuilder(Material.matchMaterial(MySQL.getItem(i, MySQL.getTName(target.getUniqueId())))).setAmount(Integer.valueOf(MySQL.getAmout(i, MySQL.getTName(target.getUniqueId())))).setDurability(Short.valueOf(MySQL.getdurability(i, MySQL.getTName(target.getUniqueId())))).setEnchantmentBook(MySQL.getEnchants(i, MySQL.getTName(target.getUniqueId())).split(":").length, MySQL.getEnchants(i, MySQL.getTName(target.getUniqueId()))).build());
							} else
							inventory.setItem(i, new ItemBuilder(Material.matchMaterial(MySQL.getItem(i, MySQL.getTName(target.getUniqueId())))).setAmount(Integer.valueOf(MySQL.getAmout(i,MySQL.getTName(target.getUniqueId())))).setDurability(Short.valueOf(MySQL.getdurability(i, MySQL.getTName(target.getUniqueId())))).setLore(MySQL.getLore(i, MySQL.getTName(target.getUniqueId()))).build());
						} else
						inventory.setItem(i, new ItemBuilder(Material.matchMaterial(MySQL.getItem(i, MySQL.getTName(target.getUniqueId())))).setAmount(Integer.valueOf(MySQL.getAmout(i,MySQL.getTName(target.getUniqueId())))).setDurability(Short.valueOf(MySQL.getdurability(i, MySQL.getTName(target.getUniqueId())))).build());
					}
						
					} else {
						
						if(MySQL.getDisplayName(i, MySQL.getTName(target.getUniqueId())) != null) {
							if(MySQL.getLore(i, MySQL.getTName(target.getUniqueId())) != null ) {
								
								if(MySQL.getEnchants(i, MySQL.getTName(target.getUniqueId())) != null) {
									inventory.setItem(i, new ItemBuilder(Material.matchMaterial(MySQL.getItem(i, MySQL.getTName(target.getUniqueId())))).setAmount(Integer.valueOf(MySQL.getAmout(i, MySQL.getTName(target.getUniqueId())))).setDurability(Short.valueOf(MySQL.getdurability(i, MySQL.getTName(target.getUniqueId())))).setName(MySQL.getDisplayName(i, MySQL.getTName(target.getUniqueId()))).setLore(MySQL.getLore(i, MySQL.getTName(target.getUniqueId()))).setEnchantment(MySQL.getEnchants(i, MySQL.getTName(target.getUniqueId())).split(":").length, MySQL.getEnchants(i, MySQL.getTName(target.getUniqueId()))).build());
								} else {
									inventory.setItem(i, new ItemBuilder(Material.matchMaterial(MySQL.getItem(i, MySQL.getTName(target.getUniqueId())))).setAmount(Integer.valueOf(MySQL.getAmout(i, MySQL.getTName(target.getUniqueId())))).setDurability(Short.valueOf(MySQL.getdurability(i, MySQL.getTName(target.getUniqueId())))).setName(MySQL.getDisplayName(i, MySQL.getTName(target.getUniqueId()))).setLore(MySQL.getLore(i, MySQL.getTName(target.getUniqueId()))).build());
								}
								
							} else {
								if(MySQL.getEnchants(i, MySQL.getTName(target.getUniqueId())) != null) {
									inventory.setItem(i, new ItemBuilder(Material.matchMaterial(MySQL.getItem(i, MySQL.getTName(target.getUniqueId())))).setAmount(Integer.valueOf(MySQL.getAmout(i, MySQL.getTName(target.getUniqueId())))).setDurability(Short.valueOf(MySQL.getdurability(i, MySQL.getTName(target.getUniqueId())))).setName(MySQL.getDisplayName(i, MySQL.getTName(target.getUniqueId()))).setEnchantment(MySQL.getEnchants(i, MySQL.getTName(target.getUniqueId())).split(":").length, MySQL.getEnchants(i, MySQL.getTName(target.getUniqueId()))).build());
								} else
								inventory.setItem(i, new ItemBuilder(Material.matchMaterial(MySQL.getItem(i, MySQL.getTName(target.getUniqueId())))).setAmount(Integer.valueOf(MySQL.getAmout(i, MySQL.getTName(target.getUniqueId())))).setDurability(Short.valueOf(MySQL.getdurability(i, MySQL.getTName(target.getUniqueId())))).setName(MySQL.getDisplayName(i, MySQL.getTName(target.getUniqueId()))).build());
							}
								
					} else {
						if(MySQL.getLore(i, MySQL.getTName(target.getUniqueId())) != null ) {
							if(MySQL.getEnchants(i, MySQL.getTName(target.getUniqueId())) != null) {
								inventory.setItem(i, new ItemBuilder(Material.matchMaterial(MySQL.getItem(i, MySQL.getTName(target.getUniqueId())))).setAmount(Integer.valueOf(MySQL.getAmout(i, MySQL.getTName(target.getUniqueId())))).setDurability(Short.valueOf(MySQL.getdurability(i, MySQL.getTName(target.getUniqueId())))).setEnchantment(MySQL.getEnchants(i, MySQL.getTName(target.getUniqueId())).split(":").length, MySQL.getEnchants(i, MySQL.getTName(target.getUniqueId()))).build());
							} else
							inventory.setItem(i, new ItemBuilder(Material.matchMaterial(MySQL.getItem(i, MySQL.getTName(target.getUniqueId())))).setAmount(Integer.valueOf(MySQL.getAmout(i,MySQL.getTName(target.getUniqueId())))).setDurability(Short.valueOf(MySQL.getdurability(i, MySQL.getTName(target.getUniqueId())))).setLore(MySQL.getLore(i, MySQL.getTName(target.getUniqueId()))).build());
						} else
						inventory.setItem(i, new ItemBuilder(Material.matchMaterial(MySQL.getItem(i, MySQL.getTName(target.getUniqueId())))).setAmount(Integer.valueOf(MySQL.getAmout(i,MySQL.getTName(target.getUniqueId())))).setDurability(Short.valueOf(MySQL.getdurability(i, MySQL.getTName(target.getUniqueId())))).build());
					}
						
					}				
				}				
			}
			
			sender.openInventory(inventory);
			if(target.getName().equalsIgnoreCase(sender.getName().toString())) {
				sender.sendMessage(Main.getPrefix() + "§2Deine EC wurde geöffnet!");
			} else {
				
				if(sender.hasPermission("EC.canClick")) {
					sender.sendMessage(Main.getPrefix() + "§2Die EC von §6" + target.getName() + " §2wurde  dir eröffnet!");
				} else {
					if(Main.getPlugin().canClick != null) {
						Main.getPlugin().canClick.add(sender.getName().toString());
					}
					sender.sendMessage(Main.getPrefix() + "§2Die EC von §6" + target.getName() + " §2wurde  dir eröffnet!");
				}
				
				
			}
				
		} else {
			while (MySQL.setTname(i)) {
				i++;
			}
			if(!MySQL.setTname(i)) {
				MySQL.createChestTable(i);
				MySQL.update("INSERT INTO "+ "Players" + "(Spielername, UUID, TabelName) VALUES (?,?,?)", target.getName() + "," + target.getUniqueId() + "," + i);
				sender.openInventory(inventory);
				if(target.getName().equalsIgnoreCase(sender.getName())) {
					sender.sendMessage(Main.getPrefix() + "§2Deine EC wurde geöffnet!");
				} else {
					if(sender.hasPermission("EC.canClick")) {
						sender.sendMessage(Main.getPrefix() + "§2Die EC von §6" + target.getName() + " §2wurde  dir eröffnet!");
					} else {
						if(Main.getPlugin().canClick != null) {
							Main.getPlugin().canClick.add(sender.getName().toString());
						}
						sender.sendMessage(Main.getPrefix() + "§2Die EC von §6" + target.getName() + " §2wurde  dir eröffnet!");
					}
				}
				i=1;
			}
		}
			
    		
			
	}
	
	public static void openInvOffline(OfflinePlayer target, Player sender) throws IllegalArgumentException, IOException {
		Inventory inventory = Bukkit.createInventory(null, AllgemeineConfigs.getBreite()*AllgemeineConfigs.getHöhe(), "§4" + InvName + " §2von §6" + target.getName());
		if(MySQL.UserExistsPlayer(target.getUniqueId())) {
			for(int i = 0; i < AllgemeineConfigs.getBreite()*AllgemeineConfigs.getHöhe(); i++) {
				
				if(MySQL.getItem(i, MySQL.getTName(target.getUniqueId())).equalsIgnoreCase("AIR")) {
					inventory.setItem(i, new ItemStack(Material.AIR));
				} else {
					if(MySQL.getDisplayName(i, MySQL.getTName(target.getUniqueId())) != null) {
						if(MySQL.getLore(i, MySQL.getTName(target.getUniqueId())) != null ) {
							
							if(MySQL.getEnchants(i, MySQL.getTName(target.getUniqueId())) != null) {
								inventory.setItem(i, new ItemBuilder(Material.matchMaterial(MySQL.getItem(i, MySQL.getTName(target.getUniqueId())))).setAmount(Integer.valueOf(MySQL.getAmout(i, MySQL.getTName(target.getUniqueId())))).setDurability(Short.valueOf(MySQL.getdurability(i, MySQL.getTName(target.getUniqueId())))).setName(MySQL.getDisplayName(i, MySQL.getTName(target.getUniqueId()))).setLore(MySQL.getLore(i, MySQL.getTName(target.getUniqueId()))).setEnchantment(MySQL.getEnchants(i, MySQL.getTName(target.getUniqueId())).split(":").length, MySQL.getEnchants(i, MySQL.getTName(target.getUniqueId()))).build());
							} else {
								inventory.setItem(i, new ItemBuilder(Material.matchMaterial(MySQL.getItem(i, MySQL.getTName(target.getUniqueId())))).setAmount(Integer.valueOf(MySQL.getAmout(i, MySQL.getTName(target.getUniqueId())))).setDurability(Short.valueOf(MySQL.getdurability(i, MySQL.getTName(target.getUniqueId())))).setName(MySQL.getDisplayName(i, MySQL.getTName(target.getUniqueId()))).setLore(MySQL.getLore(i, MySQL.getTName(target.getUniqueId()))).build());
							}
							
						} else {
							if(MySQL.getEnchants(i, MySQL.getTName(target.getUniqueId())) != null) {
								inventory.setItem(i, new ItemBuilder(Material.matchMaterial(MySQL.getItem(i, MySQL.getTName(target.getUniqueId())))).setAmount(Integer.valueOf(MySQL.getAmout(i, MySQL.getTName(target.getUniqueId())))).setDurability(Short.valueOf(MySQL.getdurability(i, MySQL.getTName(target.getUniqueId())))).setName(MySQL.getDisplayName(i, MySQL.getTName(target.getUniqueId()))).setEnchantment(MySQL.getEnchants(i, MySQL.getTName(target.getUniqueId())).split(":").length, MySQL.getEnchants(i, MySQL.getTName(target.getUniqueId()))).build());
							} else
							inventory.setItem(i, new ItemBuilder(Material.matchMaterial(MySQL.getItem(i, MySQL.getTName(target.getUniqueId())))).setAmount(Integer.valueOf(MySQL.getAmout(i, MySQL.getTName(target.getUniqueId())))).setDurability(Short.valueOf(MySQL.getdurability(i, MySQL.getTName(target.getUniqueId())))).setName(MySQL.getDisplayName(i, MySQL.getTName(target.getUniqueId()))).build());
							}
							
				} else {
					if(MySQL.getLore(i, MySQL.getTName(target.getUniqueId())) != null ) {
						if(MySQL.getEnchants(i, MySQL.getTName(target.getUniqueId())) != null) {
							inventory.setItem(i, new ItemBuilder(Material.matchMaterial(MySQL.getItem(i, MySQL.getTName(target.getUniqueId())))).setAmount(Integer.valueOf(MySQL.getAmout(i, MySQL.getTName(target.getUniqueId())))).setDurability(Short.valueOf(MySQL.getdurability(i, MySQL.getTName(target.getUniqueId())))).setEnchantment(MySQL.getEnchants(i, MySQL.getTName(target.getUniqueId())).split(":").length, MySQL.getEnchants(i, MySQL.getTName(target.getUniqueId()))).build());
						} else
						inventory.setItem(i, new ItemBuilder(Material.matchMaterial(MySQL.getItem(i, MySQL.getTName(target.getUniqueId())))).setAmount(Integer.valueOf(MySQL.getAmout(i,MySQL.getTName(target.getUniqueId())))).setDurability(Short.valueOf(MySQL.getdurability(i, MySQL.getTName(target.getUniqueId())))).setLore(MySQL.getLore(i, MySQL.getTName(target.getUniqueId()))).build());
					} else
					inventory.setItem(i, new ItemBuilder(Material.matchMaterial(MySQL.getItem(i, MySQL.getTName(target.getUniqueId())))).setAmount(Integer.valueOf(MySQL.getAmout(i,MySQL.getTName(target.getUniqueId())))).setDurability(Short.valueOf(MySQL.getdurability(i, MySQL.getTName(target.getUniqueId())))).build());
				}
				}		
				
			}
			sender.openInventory(inventory);
			if(sender.hasPermission("EC.canClick")) {
				sender.sendMessage(Main.getPrefix() + "§2Die EC von §6" + target.getName() + " §2wurde  dir eröffnet!");
			} else {
				if(Main.getPlugin().canClick != null) {
					Main.getPlugin().canClick.add(sender.getName().toString());
				}
				sender.sendMessage(Main.getPrefix() + "§2Die EC von §6" + target.getName() + " §2wurde  dir eröffnet!");
			} 
				
		} else {
			while (MySQL.setTname(i)) {
				i++;
			}
			if(!MySQL.setTname(i)) {
				MySQL.createChestTable(i);
				MySQL.update("INSERT INTO "+ "Players" + "(Spielername, UUID, TabelName) VALUES (?,?,?)", target.getName() + "," + target.getUniqueId() + "," + i);
				sender.openInventory(inventory);
				if(sender.hasPermission("EC.canClick")) {
					sender.sendMessage(Main.getPrefix() + "§2Die EC von §6" + target.getName() + " §2wurde  dir eröffnet!");
				} else {
					if(Main.getPlugin().canClick != null) {
						Main.getPlugin().canClick.add(sender.getName().toString());
					}
					sender.sendMessage(Main.getPrefix() + "§2Die EC von §6" + target.getName() + " §2wurde  dir eröffnet!");
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
						e.printStackTrace();
					}
				} else {
					if(args.length == 1) {
						for(Player all : Bukkit.getOnlinePlayers()) {
							if(all.getName().equalsIgnoreCase(args[0])) {
								try {
									openInv(all, p);
								} catch (IllegalArgumentException | IOException e) {
									e.printStackTrace();
								}
						} else {
								
						for(OfflinePlayer allOFF : Bukkit.getOfflinePlayers()) {
							if(allOFF.getName().equalsIgnoreCase(args[0])) {
								try {
									openInvOffline(allOFF, p);
								} catch (IllegalArgumentException | IOException e) {
									e.printStackTrace();
								}
						} else
							p.sendMessage(Main.getPrefix() + "§4Dieser Spieler exestiert leider nicht auf diesemm Server!");
						}
						}
						}
						
					} else
						p.sendMessage(Main.getPrefix() + "§4Bitte benutze §2/EC (Spieler) §4!");
				}
		} else
			sender.sendMessage(Main.getPrefix() + "Du musst ein Spieler sein");
		return false;
	}
	
}
