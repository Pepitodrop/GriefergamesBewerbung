
/*
 * Luis Benedikt
 * 
 * 28.4.2021
 * 
 * Die Benutzung nur nach Absprache Erlaubt
 * 
 * Dieses Plugin soll meine Programmierk�nste in Spigot zeigen
 * 
 */

package de.benangelo.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.benangelo.main.Main;

public class CraftingTableCommand implements CommandExecutor{

	private static Main plugin;
	
	public CraftingTableCommand(Main m) {
		plugin = m;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(p.hasPermission("bewerbungsplugin.craftingCommand")) {
				if(args.length == 0) {
					
					//�ffnet dem Spieler eine Werkbank
					p.openWorkbench(p.getLocation(), true);
				} else
					p.sendMessage(plugin.getPrefix() + "�4Bitte benutze �2/crafting �4!");
			} else
				p.sendMessage(plugin.getPrefix() + "�4Dazu hast du keine Rechte!");
		} else
			sender.sendMessage(plugin.getPrefix() + "�4Du musst ein Spieler sein!");
		return false;
	}

}
