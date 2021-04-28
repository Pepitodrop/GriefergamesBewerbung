
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

import de.benangelo.main.Main;

public class InvseeCommand implements CommandExecutor {

	private static Main plugin;
	
	public InvseeCommand(Main m) {
		plugin = m;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command commands, String label, String[] args) {
		
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(p.hasPermission("see.inInventory")) {
				if(args.length == 1) {
					Player target = Bukkit.getPlayerExact(args[0]);
					if(target != null) {
						
						//Öffnet das Inventar eines anderen Spielers
						if(!(p.hasPermission("bewerbungsplugin.canClickInventory"))) {
							plugin.getPlugin().canClick.add(p.getName());
						}
						p.openInventory(target.getInventory());
			} else
				p.sendMessage(plugin.getPrefix() + "§cDieser spieler ist nicht online!");	
			} else
				p.sendMessage(plugin.getPrefix() + "§cBitte benutze §2/invsee <Spieler>§c !");
			} else
				p.sendMessage(plugin.getPrefix() + "§cDazu hast du keine Rechte");
		} else
			sender.sendMessage(plugin.getPrefix() + "Du musst ein Spieler sein!");
		return false;
	}
}
