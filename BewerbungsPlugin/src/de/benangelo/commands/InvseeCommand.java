package de.benangelo.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.benangelo.main.Main;

public class InvseeCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command commands, String label, String[] args) {
		
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(p.hasPermission("see.inInventory")) {
				if(args.length == 1) {
					Player target = Bukkit.getPlayerExact(args[0]);
					if(target != null) {
						if(!(p.hasPermission("bewerbungsplugin.canClickInventory"))) {
							Main.getPlugin().canClick.add(p.getName());
						}
						p.openInventory(target.getInventory());
			} else
				p.sendMessage("§cDieser spieler ist nicht online!");	
			} else
				p.sendMessage("§cBitte benutze §2/invsee <Spieler>§c !");
			} else
				p.sendMessage("§cDazu hast du keine Rechte");
		} else
			System.out.println("Du musst ein Spieler sein!");
		return false;
	}
}
