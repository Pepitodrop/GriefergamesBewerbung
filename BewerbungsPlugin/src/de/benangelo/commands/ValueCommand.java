package de.benangelo.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import de.benangelo.config.Money;
import de.benangelo.main.Main;

public class ValueCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Money money = new Money();
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(p.hasPermission("bewerbungsPLugin.valueCommand")) {
				if(args.length == 2) {
					if(args[0].equalsIgnoreCase("set")) {
						if (args[1].matches("[0-9]+")) {
							money.setMoney(p, Double.valueOf(args[1]));
							p.sendMessage(Main.getPrefix() + "§2Du hast deinem Kontostand §e" + args[1] + "$ §2hinugrfügt!");
						} else {
						p.sendMessage(Main.getPrefix() + "§4Bitte benutze Zahlen!");
						}
					} else {
						if(args[0].equalsIgnoreCase("add")) {
							if (args[1].matches("[0-9]+")) {
								double amout = Double.valueOf(args[1]) + money.getMoney(p);
								money.setMoney(p, amout);
								p.sendMessage(Main.getPrefix() + "§2Du hast deinen Kontostand auf §e" + args[1] + "$ §2gesetzt!");
							} else {
							p.sendMessage(Main.getPrefix() + "§4Bitte benutze Zahlen!");
							}
						} else {
							p.sendMessage(Main.getPrefix() + "§4Bitte benutze §2/value set/add <amout> (Spieler)");
						}
					}
				} else {
					if(args.length == 3) {
						if(args[0].equalsIgnoreCase("set")) {
							if (args[1].matches("[0-9]+")) {
								Player target = Bukkit.getPlayerExact(args[2]);
								if(target != null) {
									money.setMoney(target, Double.valueOf(args[1]));
									p.sendMessage(Main.getPrefix() + "§2Du hast dem Kontostand von §d" + target.getName() + " §2auf §e" + args[1] + "$ §2gesetzt!");
									target.sendMessage(Main.getPrefix() + "§2Dein Kontostand wurden von §d" + p.getDisplayName() + " §2auf §e" + args[1] + "$ §2gesetzt!");
								} else {
										p.sendMessage(Main.getPrefix() + "§4Dieser Spieler ist gerade nicht Online!");
									}
								} else {
									p.sendMessage(Main.getPrefix() + "§4Bitte benutze Zahlen!");
							} 
							}else {
								if(args[0].equalsIgnoreCase("add")) {
									if (args[1].matches("[0-9]+")) {
										Player target = Bukkit.getPlayerExact(args[2]);
										if(target != null) {
											double amout = Double.valueOf(args[1]) + money.getMoney(target);
											money.setMoney(target, amout);
											p.sendMessage(Main.getPrefix() + "§2Du hast dem Kontostand von §d" + target.getName() + " §e" + args[1] + "$ §2hinzugefügt!");
											target.sendMessage(Main.getPrefix() + "§2Deinem Kontostand wurden §e" + args[1] + "$ §2 von §d"+p.getDisplayName() + " §2hinzugefügt!");
										} else {
												p.sendMessage(Main.getPrefix() + "§4Dieser Spieler ist gerade nicht Online!");
											}
									} else {
									p.sendMessage(Main.getPrefix() + "§4Bitte benutze Zahlen!");
									}
								} else {
									p.sendMessage(Main.getPrefix() + "§4Bitte benutze §2/value set/add <amout> (Spieler)");
								}
						}
						}  else {
							p.sendMessage(Main.getPrefix() + "§4Bitte benutze §2/value set/add <amout> (Spieler)");
				} 
				}
			} else {
				p.sendMessage(Main.getPrefix() + "§4Versuch es erst garnicht du must schon für das Geld arbeiten!");
		}
		}else {
			ConsoleCommandSender p = Bukkit.getServer().getConsoleSender();
			if(args.length == 2) {
				p.sendMessage(Main.getPrefix() + "§4Bitte benutze §2/value set/add <amout> <Spieler>");
			} else {
				if(args.length == 3) {
					if(args[0].equalsIgnoreCase("set")) {
						if (args[1].matches("[0-9]+")) {
							Player target = Bukkit.getPlayerExact(args[2]);
							if(target != null) {
								money.setMoney(target, Double.valueOf(args[1]));
								p.sendMessage(Main.getPrefix() + "§2Du hast den Kontostand von §d" + target.getName() + " §2auf §e" + args[1] + "$ §2gesetzt!");
								target.sendMessage(Main.getPrefix() + "§2Dein Kontostand wurden von §dder Konsole §2auf §e" + args[1] + "$ §2gesetzt!");
							} else {
									p.sendMessage(Main.getPrefix() + "§4Dieser Spieler ist gerade nicht Online!");
							}
						} else {
						p.sendMessage("§4Bitte benutze Zahlen!");
						}
					} else {
						if(args[0].equalsIgnoreCase("add")) {
							if (args[1].matches("[0-9]+")) {
								Player target = Bukkit.getPlayerExact(args[2]);
								if(target != null) {
									double amout = Double.valueOf(args[1]) + money.getMoney(target);
									money.setMoney(target, amout);
									p.sendMessage(Main.getPrefix() + "§2Du hast dem Kontostand von §d" + target.getName() + " §e" + args[1] + "$ §2hinzugefügt!");
									target.sendMessage(Main.getPrefix() + "§2Deinem Kontostand wurden §e" + args[1] + "$ §2 von §dder Konsole §2hinzugefügt!");
								} else {
										p.sendMessage(Main.getPrefix() + "§4Dieser Spieler ist gerade nicht Online!");
									}
							} else {
							p.sendMessage(Main.getPrefix() + "§4Bitte benutze Zahlen!");
							}
						} else {
							p.sendMessage(Main.getPrefix() + "§4Bitte benutze §2/value set/add <amout> <Spieler>");
						}
				} 
			} else {
				p.sendMessage(Main.getPrefix() + "§4Bitte benutze §2/value set/add <amout> <Spieler>");
			}
		}
		}
	return false;
}
}