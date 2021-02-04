package de.benangelo.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.benangelo.config.Money;
import de.benangelo.mysql.MySQL;

public class BankCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Money money = new Money();
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(p.hasPermission("bewerbungsPLugin.bankCommand")) {
				if(args.length == 2) {
					if(args[0].equalsIgnoreCase("einzahlen")) {
						if (args[1].matches("[0-9]+")) {
							if(money.getMoney(p) >= Double.valueOf(args[1])) {
								if(MySQL.UserExistsBank(p.getUniqueId())) {
									int amout = Integer.valueOf(args[1]) + Integer.valueOf(MySQL.getAmoutBank(p.getUniqueId()));
									double amoutConfig = money.getMoney(p) - Double.valueOf(args[1]);
									money.setMoney(p, amoutConfig);
									MySQL.update("DELETE FROM " + "Bank" + " WHERE UUID='" + p.getUniqueId() + "'");
									MySQL.update("INSERT INTO "+ "Bank" + " (Player, UUID , Amout) VALUES ('" + p.getName() + "', '" + p.getUniqueId() + "', '" + amout +"');");
									p.sendMessage("§2Du hast §e" + args[1] + "$ §2eingezahlt!");
								} else {
									int amout = Integer.valueOf(args[1]) + 0;
									double amoutConfig = money.getMoney(p) - Double.valueOf(args[1]);
									money.setMoney(p, amoutConfig);
									MySQL.update("INSERT INTO "+ "Bank" + " (Player, UUID , Amout) VALUES ('" + p.getName() + "', '" + p.getUniqueId() + "', '" + amout +"');");
									p.sendMessage("§2Du hast §e" + args[1] + "$ §2eingezahlt!");
								}
							} else
								p.sendMessage("§4Du hast nicht so viel Geld!");
						} else
							p.sendMessage("§4Du musst Zahlen benutzen!");
						
					} else {
						if(args[0].equalsIgnoreCase("abheben")) {
							if (args[1].matches("[0-9]+")) {
								if(MySQL.UserExistsBank(p.getUniqueId())) {
									if(Double.valueOf(MySQL.getAmoutBank(p.getUniqueId())) >= Double.valueOf(args[1])) {
										int amout = Integer.valueOf(MySQL.getAmoutBank(p.getUniqueId())) - Integer.valueOf(args[1]);
										double amoutConfig = money.getMoney(p) + Double.valueOf(args[1]);
										money.setMoney(p, amoutConfig);
										MySQL.update("DELETE FROM " + "Bank" + " WHERE UUID='" + p.getUniqueId() + "'");
										MySQL.update("INSERT INTO "+ "Bank" + " (Player, UUID , Amout) VALUES ('" + p.getName() + "', '" + p.getUniqueId() + "', '" + amout +"');");
										p.sendMessage("§4Du hast §e" + args[1] + "$ §4abgehoben!");
									} else
										p.sendMessage("§4Du hast nicht so viel Geld auf der Bank!");
								} else {
									if(Double.valueOf(0) >= Double.valueOf(args[1])) {
										int amout = Integer.valueOf(args[1]) - Integer.valueOf(MySQL.getAmoutBank(p.getUniqueId()));
										double amoutConfig = money.getMoney(p) + Double.valueOf(args[1]);
										money.setMoney(p, amoutConfig);
										MySQL.update("DELETE FROM " + "Bank" + " WHERE UUID='" + p.getUniqueId() + "'");
										MySQL.update("INSERT INTO "+ "Bank" + " (Player, UUID , Amout) VALUES ('" + p.getName() + "', '" + p.getUniqueId() + "', '" + amout +"');");
										p.sendMessage("§4Du hast §e" + args[1] + "$ §4abgehoben!");
									} else
										p.sendMessage("§4Du hast nicht so viel Geld auf der Bank!");

								}
								
							} else
								p.sendMessage("§4Du musst Zahlen benutzen!");
						} else {
							p.sendMessage("§4Bitte benutze §2/bank einzahlen <amout> §4!");
							p.sendMessage("§4Bitte benutze §2/bank abheben <amout> §4!");
							p.sendMessage("§4Bitte benutze §2/bank Guthaben §4!");
							p.sendMessage("§2In der Bank ist dein Geld sicherer als bei Fort Nox!");
						}
					}
				} else {
					if(args.length == 1) {
						if(args[0].equalsIgnoreCase("Guthaben")) {
							if(MySQL.UserExistsBank(p.getUniqueId())) {
								p.sendMessage("§2Dein Guthaben Beträgt §e" + MySQL.getAmoutBank(p.getUniqueId()) + "$§2!");
							} else
								p.sendMessage("§eDu hast noch kein Geld auf der Bank! §9Mit §2/bank einzahlen <amout> §9kannst du welches einzahlen!");
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
				p.sendMessage("§4Dazu hast du keine Rechte!");
			}
		} else
			System.out.println("Du musst ein Spieler sein!");
		return false;
	}

}
