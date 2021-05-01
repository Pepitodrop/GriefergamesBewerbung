
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

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.benangelo.config.Money;
import de.benangelo.main.Main;

public class ValueCommand implements CommandExecutor{

	private static Main plugin;
	
	public ValueCommand(Main m) {
		plugin = m;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Money money = new Money();
			if(sender.hasPermission("bewerbungsPLugin.valueCommand")) {
				if(args.length == 2) {
					
					if(sender instanceof Player) {
						Player p = (Player) sender;
						
					//Setzt das Guthaben des Spielers auf ein bestimmtes Budget
					if(args[0].equalsIgnoreCase("set")) {
						if (args[1].matches("[0-9]+")) {
							money.setMoney(p, Double.valueOf(args[1]));
							p.sendMessage(plugin.getPrefix() + "§2Du hast dein Kontostand auf §e" + args[1] + "$ §2gesetzt!");
						} else {
						p.sendMessage(plugin.getPrefix() + "§4Bitte benutze Zahlen!");
						}
					} else {
						
						//Fügt dem Spieler einen festgelegten Betrag seines Guthabens hinzu
						if(args[0].equalsIgnoreCase("add")) {
							if (args[1].matches("[0-9]+")) {
								double amout = Double.valueOf(args[1]) + money.getMoney(p);
								money.setMoney(p, amout);
								p.sendMessage(plugin.getPrefix() + "§2Du hast deinem Kontostand §e" + args[1] + "$ §2hinzugefügt!");
							} else {
							p.sendMessage(plugin.getPrefix() + "§4Bitte benutze Zahlen!");
							}
						} else {
							p.sendMessage(plugin.getPrefix() + "§4Bitte benutze §2/value set/add <amout> (Spieler)");
						}
					}
					} else
						sender.sendMessage(plugin.getPrefix() + "§cDu kannst nur Spieler Geld geben!");
				} else {
					if(args.length == 3) {
						
						//Setzt das Guthaben eines anderen Spielers auf ein bestimmtes Budget
						if(args[0].equalsIgnoreCase("set")) {
							if (args[1].matches("[0-9]+")) {
								Player target = Bukkit.getPlayerExact(args[2]);
								if(target != null) {
									money.setMoney(target, Double.valueOf(args[1]));
									sender.sendMessage(plugin.getPrefix() + "§2Du hast dem Kontostand von §d" + target.getName() + " §2auf §e" + args[1] + "$ §2gesetzt!");
									
									if(sender instanceof Player) {
										Player p = (Player) sender;
										target.sendMessage(plugin.getPrefix() + "§2Dein Kontostand wurden von §d" + p.getDisplayName() + " §2auf §e" + args[1] + "$ §2gesetzt!");
									} else
										target.sendMessage(plugin.getPrefix() + "§2Dein Kontostand wurden von §d" + "Der KONSOLE" + " §2auf §e" + args[1] + "$ §2gesetzt!");
									
								} else {
									sender.sendMessage(plugin.getPrefix() + "§4Dieser Spieler ist gerade nicht Online!");
									}
								} else {
									sender.sendMessage(plugin.getPrefix() + "§4Bitte benutze Zahlen!");
							} 
							}else {
								
								//Fügt einem anderen Spieler einen festgelegten Betrag seines Guthabens hinzu
								if(args[0].equalsIgnoreCase("add")) {
									if (args[1].matches("[0-9]+")) {
										Player target = Bukkit.getPlayerExact(args[2]);
										if(target != null) {
											double amout = Double.valueOf(args[1]) + money.getMoney(target);
											money.setMoney(target, amout);
											sender.sendMessage(plugin.getPrefix() + "§2Du hast dem Kontostand von §d" + target.getName() + " §e" + args[1] + "$ §2hinzugefügt!");
											
											if(sender instanceof Player) {
												Player p = (Player) sender;
												target.sendMessage(plugin.getPrefix() + "§2Deinem Kontostand wurden §e" + args[1] + "$ §2 von §d"+ p.getDisplayName() + " §2hinzugefügt!");
											} else
												target.sendMessage(plugin.getPrefix() + "§2Deinem Kontostand wurden §e" + args[1] + "$ §2 von §d"+ "Der Konsole" + " §2hinzugefügt!");
											
										} else {
											sender.sendMessage(plugin.getPrefix() + "§4Dieser Spieler ist gerade nicht Online!");
											}
									} else {
									sender.sendMessage(plugin.getPrefix() + "§4Bitte benutze Zahlen!");
									}
								} else {
									sender.sendMessage(plugin.getPrefix() + "§4Bitte benutze §2/value set/add <amout> (Spieler)");
								}
						}
						}  else {
							sender.sendMessage(plugin.getPrefix() + "§4Bitte benutze §2/value set/add <amout> (Spieler)");
				} 
				}
			} else {
				sender.sendMessage(plugin.getPrefix() + "§4Versuch es erst garnicht du must schon für das Geld arbeiten!");
		}
	return false;
	}
}