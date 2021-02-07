package de.benangelo.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CraftingTableCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(p.hasPermission("bewerbungsplugin.craftingCommand")) {
				if(args.length == 0) {
					p.openWorkbench(p.getLocation(), true);
				} else
					p.sendMessage("§4Bitte benutze §2/crafting §4!");
			} else
				p.sendMessage("§4Dazu hast du keine Rechte!");
		} else
			sender.sendMessage("§4Du musst ein Spieler sein!");
		return false;
	}

}
