
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

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.benangelo.config.Money;
import de.benangelo.main.Main;
import de.benangelo.mysql.MySQL;

public class BankCommand implements CommandExecutor{

	private static Main plugin;
	
	public BankCommand(Main m) {
		plugin=m;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Money money = new Money();
		if(sender instanceof Player) {
			Player p = (Player) sender;
			String uuid = p.getUniqueId().toString();
			String name = p.getName();
			if(p.hasPermission("bewerbungsPLugin.bankCommand")) {
				if(args.length == 2) {
					
					//Lässt den Spieler Geld in sein Konto einzahlen
					if(args[0].equalsIgnoreCase("einzahlen")) {
						if (args[1].matches("[0-9]+")) {
							if(money.getMoney(p) >= Double.valueOf(args[1])) {
								if(MySQL.UserExistsBank(p.getUniqueId())) {
									int amout = Integer.valueOf(args[1]) + Integer.valueOf(MySQL.getAmoutBank(p.getUniqueId()));
									double amoutConfig = money.getMoney(p) - Double.valueOf(args[1]);
									money.setMoney(p, amoutConfig);
									
									MySQL.update("UPDATE Bank SET Amout=? WHERE UUID=?", amout + "," + uuid);
									
									p.sendMessage(plugin.getPrefix() + "§2Du hast §e" + args[1] + "$ §2eingezahlt!");
								} else {
									int amout = Integer.valueOf(args[1]);
									double amoutConfig = money.getMoney(p) - Double.valueOf(args[1]);
									money.setMoney(p, amoutConfig);
									MySQL.update("INSERT INTO "+ "Bank" + " (Player, UUID , Amout) VALUES (?, ?, ?);", name + "," + uuid + "," + amout);
									p.sendMessage(plugin.getPrefix() + "§2Du hast §e" + args[1] + "$ §2eingezahlt!");
								}
							} else
								p.sendMessage(plugin.getPrefix() + "§4Du hast nicht so viel Geld!");
						} else
							p.sendMessage(plugin.getPrefix() + "§4Du musst Zahlen benutzen!");
						
					} else {
						
						//Lässt den Speieler Geld aus seinem Konto nehmen
						if(args[0].equalsIgnoreCase("abheben")) {
							if (args[1].matches("[0-9]+")) {
								if(MySQL.UserExistsBank(p.getUniqueId())) {
									if(Double.valueOf(MySQL.getAmoutBank(p.getUniqueId())) >= Double.valueOf(args[1])) {
										int amout = Integer.valueOf(MySQL.getAmoutBank(p.getUniqueId())) - Integer.valueOf(args[1]);
										double amoutConfig = money.getMoney(p) + Double.valueOf(args[1]);
										money.setMoney(p, amoutConfig);
										
										MySQL.update("UPDATE Bank SET Amout=? WHERE UUID=?", amout + "," + uuid);
										
										p.sendMessage(plugin.getPrefix() + "§4Du hast §e" + args[1] + "$ §4abgehoben!");
									} else
										p.sendMessage(plugin.getPrefix() + "§4Du hast nicht so viel Geld auf der Bank!");
								} else {
									if(Double.valueOf(0) >= Double.valueOf(args[1])) {
										int amout = Integer.valueOf(args[1]) - Integer.valueOf(MySQL.getAmoutBank(p.getUniqueId()));
										double amoutConfig = money.getMoney(p) + Double.valueOf(args[1]);
										money.setMoney(p, amoutConfig);
										
										MySQL.update("UPDATE Bank SET Amout=? WHERE UUID=?", amout + "," + uuid);
										
										p.sendMessage(plugin.getPrefix() + "§4Du hast §e" + args[1] + "$ §4abgehoben!");
									} else
										p.sendMessage(plugin.getPrefix() + "§4Du hast nicht so viel Geld auf der Bank!");

								}
								
							} else
								p.sendMessage(plugin.getPrefix() + "§4Du musst Zahlen benutzen!");
						} else {
							p.sendMessage("§4Bitte benutze §2/bank einzahlen <amout> §4!");
							p.sendMessage("§4Bitte benutze §2/bank abheben <amout> §4!");
							p.sendMessage("§4Bitte benutze §2/bank Guthaben §4!");
							p.sendMessage("§2In der Bank ist dein Geld sicherer als bei Fort Nox!");
						}
					}
				} else {
					if(args.length == 1) {
						
						//Zeigt das Guthaben des Spielers
						if(args[0].equalsIgnoreCase("Guthaben")) {
							if(MySQL.UserExistsBank(p.getUniqueId())) {
								p.sendMessage(plugin.getPrefix() + "§2Dein Guthaben Beträgt §e" + MySQL.getAmoutBank(p.getUniqueId()) + "$§2!");
							} else
								p.sendMessage(plugin.getPrefix() + "§eDu hast noch kein Geld auf der Bank! §9Mit §2/bank einzahlen <amout> §9kannst du welches einzahlen!");
						} else {
							p.sendMessage("§4Bitte benutze §2/bank einzahlen <amout> §4!");
							p.sendMessage("§4Bitte benutze §2/bank abheben <amout> §4!");
							p.sendMessage("§4Bitte benutze §2/bank Guthaben §4!");
							p.sendMessage("§2In der Bank ist dein Geld sicherer als bei Fort Nox!");
						}
					} else {
						p.sendMessage("§4Bitte benutze §2/bank einzahlen <amout> §4!");
						p.sendMessage("§4Bitte benutze §2/bank abheben <amout> §4!");
						p.sendMessage("§4Bitte benutze §2/bank Guthaben §4!");
						p.sendMessage("§2In der Bank ist dein Geld sicherer als bei Fort Nox!");
					}
				}
					
			} else {
				p.sendMessage(plugin.getPrefix() + "§4Dazu hast du keine Rechte!");
			}
		} else
			sender.sendMessage(plugin.getPrefix() + "Du musst ein Spieler sein!");
		return false;
	}

}
