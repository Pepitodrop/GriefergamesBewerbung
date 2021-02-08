package de.benangelo.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.benangelo.config.Money;
import de.benangelo.main.Main;

public class PayCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Money money = new Money();
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(p.hasPermission("bewerbungsPlugin.pay")) {
				if(args.length == 2) {
					if(args[1].matches("[0-9]+")) {
						if(money.getMoney(p) >= Double.valueOf(args[1])) {
							Player target = Bukkit.getPlayerExact(args[0]);
							if(target != null) {
								double amoutSender = money.getMoney(p) - Double.valueOf(args[1]);
								money.setMoney(p, amoutSender);
								double amoutTarget = money.getMoney(p) + Double.valueOf(args[1]);
								money.setMoney(p, amoutTarget);
								p.sendMessage(Main.getPrefix() + "§2Du hast dem Spieler §3" + target.getDisplayName() + " §d" + args[1] + "$§2 Gesendet!");
								target.sendMessage(Main.getPrefix() + "§2Du hast von dem Spieler §3" + p.getDisplayName() + " §d" + args[1] + "$§2 Bekommen!");
							} else {
								p.sendMessage(Main.getPrefix() + "§4Dieser Spieler ist nicht Online!");
							}
							
						} else
							p.sendMessage(Main.getPrefix() + "§4Du hast nicht genug Geld!");
					} else {
						p.sendMessage(Main.getPrefix() + "§4Bitte benutze Zahlen!");
					}
				} else 
					p.sendMessage(Main.getPrefix() + "§4Bitte beuntze §4/pay <Spieler> <amout> §4!");
			} else {
				p.sendMessage(Main.getPrefix() + "§4Dazu hast du leider keine Rechte");
			}
		} else
			Bukkit.getServer().getConsoleSender().sendMessage(Main.getPrefix() + "§4Du musst ein Spieler sein!");
		return false;
	}

}
