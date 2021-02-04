package de.benangelo.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import de.benangelo.config.Money;

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
						} else {
						p.sendMessage("§4Bitte benutze Zahlen!");
						}
					} else {
						if(args[0].equalsIgnoreCase("add")) {
							if (args[1].matches("[0-9]+")) {
								double amout = Double.valueOf(args[1]) + money.getMoney(p);
								money.setMoney(p, amout);
							} else {
							p.sendMessage("§4Bitte benutze Zahlen!");
							}
						} else {
							p.sendMessage("§4Bitte benutze §2/value set/add <amout> (Spieler)");
						}
					}
				} else {
					if(args.length == 3) {
						if(args[0].equalsIgnoreCase("set")) {
							if (args[1].matches("[0-9]+")) {
								for(Player all : Bukkit.getOnlinePlayers()) {
									if(all.getName().equalsIgnoreCase(args[2])) {
										money.setMoney(all, Double.valueOf(args[1]));
									} else {
										if(all.getDisplayName().equalsIgnoreCase(args[2])) {
											money.setMoney(all, Double.valueOf(args[1]));
										} else {
											p.sendMessage("§4Dieser Spieler ist gerade nicht Online!");
										}
									}
								}
							} else {
							p.sendMessage("§4Bitte benutze Zahlen!");
							}
						} else {
							if(args[0].equalsIgnoreCase("add")) {
								if (args[1].matches("[0-9]+")) {
									for(Player all : Bukkit.getOnlinePlayers()) {
										if(all.getName().equalsIgnoreCase(args[2])) {
											double amout = Double.valueOf(args[1]) + money.getMoney(all);
											money.setMoney(all, amout);
										} else {
											if(all.getDisplayName().equalsIgnoreCase(args[2])) {
												double amout = Double.valueOf(args[1]) + money.getMoney(all);
												money.setMoney(all, amout);
											} else {
												p.sendMessage("§4Dieser Spieler ist gerade nicht Online!");
											}
										}
									}
								} else {
								p.sendMessage("§4Bitte benutze Zahlen!");
								}
							} else {
								p.sendMessage("§4Bitte benutze §2/value set/add <amout> (Spieler)");
							}
					} 
				} else {
					p.sendMessage("§4Bitte benutze §2/value set/add <amout> (Spieler)");
				}
			} 
		}else {
			p.sendMessage("§4Versuch es erst garnicht du must schon für das Geld arbeiten!");
		}
		
	} else {
		ConsoleCommandSender p = Bukkit.getServer().getConsoleSender();
		if(args.length == 2) {
			p.sendMessage("§4Bitte benutze §2/value set/add <amout> <Spieler>");
		} else {
			if(args.length == 3) {
				if(args[0].equalsIgnoreCase("set")) {
					if (args[1].matches("[0-9]+")) {
						for(Player all : Bukkit.getOnlinePlayers()) {
							if(all.getName().equalsIgnoreCase(args[2])) {
								money.setMoney(all, Double.valueOf(args[1]));
							} else {
								if(all.getDisplayName().equalsIgnoreCase(args[2])) {
									money.setMoney(all, Double.valueOf(args[1]));
								} else {
									p.sendMessage("§4Dieser Spieler ist gerade nicht Online!");
								}
							}
						}
					} else {
					p.sendMessage("§4Bitte benutze Zahlen!");
					}
				} else {
					if(args[0].equalsIgnoreCase("add")) {
						if (args[1].matches("[0-9]+")) {
							for(Player all : Bukkit.getOnlinePlayers()) {
								if(all.getName().equalsIgnoreCase(args[2])) {
									double amout = Double.valueOf(args[1]) + money.getMoney(all);
									money.setMoney(all, amout);
								} else {
									if(all.getDisplayName().equalsIgnoreCase(args[2])) {
										double amout = Double.valueOf(args[1]) + money.getMoney(all);
										money.setMoney(all, amout);
									} else {
										p.sendMessage("§4Dieser Spieler ist gerade nicht Online!");
									}
								}
							}
						} else {
						p.sendMessage("§4Bitte benutze Zahlen!");
						}
					} else {
						p.sendMessage("§4Bitte benutze §2/value set/add <amout> <Spieler>");
					}
			} 
		} else {
			p.sendMessage("§4Bitte benutze §2/value set/add <amout> <Spieler>");
		}
	}
	}
		return false;
}
}

//if (args[0].matches("[0-9]+")) {
//	Money money = new Money();
//	money.setMoney(p, Double.valueOf(args[0]));
//} else {
//	p.sendMessage("§4Bitte benutze Zahlen!");
//}