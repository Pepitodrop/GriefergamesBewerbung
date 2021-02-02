package de.benangelo.commands;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.benangelo.config.AllgemeineConfigs;
import de.benangelo.mysql.MySQL;
import de.benangelo.util.ItemBuilder;

public class ECCommand implements CommandExecutor{
	
	public static String InvName;
	private static int i = 1;
	
	public static void openInv(Player p) throws IllegalArgumentException, IOException {
		Inventory inventory = Bukkit.createInventory(null, AllgemeineConfigs.getBreite()*AllgemeineConfigs.getHöhe(), "§4" + InvName);
		if(MySQL.UserExists(p.getUniqueId())) {
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
				
				if(MySQL.getItem(i, MySQL.getTName(p.getUniqueId())).equalsIgnoreCase("AIR")) {
					inventory.setItem(i, new ItemStack(Material.AIR));
					//System.out.println("Luft!");
				} else {
					//System.out.println("Keine Luft!");
					if(MySQL.getDisplayName(i, MySQL.getTName(p.getUniqueId())) != null) {
						//System.out.println("Kein Name");
					inventory.setItem(i, new ItemBuilder(Material.matchMaterial(MySQL.getItem(i, MySQL.getTName(p.getUniqueId())))).setAmount(Integer.valueOf(MySQL.getAmout(i, MySQL.getTName(p.getUniqueId())))).setDurability(Short.valueOf(MySQL.getdurability(i, MySQL.getTName(p.getUniqueId())))).setName(MySQL.getDisplayName(i, MySQL.getTName(p.getUniqueId()))).build());
				} else {
					//System.out.println("Name");
					inventory.setItem(i, new ItemBuilder(Material.matchMaterial(MySQL.getItem(i, MySQL.getTName(p.getUniqueId())))).setAmount(Integer.valueOf(MySQL.getAmout(i,MySQL.getTName(p.getUniqueId())))).setDurability(Short.valueOf(MySQL.getdurability(i, MySQL.getTName(p.getUniqueId())))).build());
				}
				}
				
				//inventory.setItem(i, new ItemStack(Material.matchMaterial("SNOW"), 1 ));
				
				
			}
			p.openInventory(inventory);
			p.sendMessage("§2Deine EC wurde geöffnet!");
		} else {
			while (MySQL.setTname(i)) {
				i++;
			}
			if(!MySQL.setTname(i)) {
				MySQL.createTable(i);
				MySQL.update("INSERT INTO "+ "Players" + "(Spielername, UUID, TabelName) VALUES ('" + p.getName() + "', '" + p.getUniqueId() +"', '" + i +"')");
				//System.out.println("4");
				p.openInventory(inventory);
				p.sendMessage("§2Deine EC wurde geöffnet!");
				i=1;
			}
		}
			
    		
			
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String lable, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(p.hasPermission("benangelo.EC")) {
				if(args.length == 0) {
					try {
						openInv(p);
					} catch (IllegalArgumentException | IOException e) {
						p.sendMessage("§4Leider exestiert keine MySQL Verbindung");
						System.out.println("Leider exestiert keine MySQL Verbindung");
					}
				} else 
					p.sendMessage("§cBitte benutze §6/value §c.");
			} else
				p.sendMessage("§c§lDazu hast du keine Rechte!");
		} else
			System.out.println("Du musst ein Spieler sein");
		return false;
	}
	
}
