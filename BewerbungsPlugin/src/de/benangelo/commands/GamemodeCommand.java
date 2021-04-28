
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
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.benangelo.main.Main;

public class GamemodeCommand implements CommandExecutor {

	private static Main plugin;
	
	public GamemodeCommand(Main m) {
		plugin = m;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command commands, String lable, String[] args) {
			if(sender.hasPermission("BewerbungsPLugin.Gamemode")) {
				if(args.length == 1) {
					if(sender instanceof Player) {
						Player p = (Player) sender;
						
						if(GameMode.getByValue(Integer.valueOf(args[0])) != null) {
							
							//Ändert den Gamemode des Spielers
							p.setGameMode(GameMode.getByValue(Integer.valueOf(args[0])));
							p.sendMessage(plugin.getPrefix() + "§6Dein Spielmodus wurde auf §c§l" + GameMode.getByValue(Integer.valueOf(args[0])).name() + " §6gesetzt!");
						} else
							p.sendMessage(plugin.getPrefix() + "§cBitte benutze §2/gm 0/1/2/3 (<Spieler>) §c!");
					} else
						sender.sendMessage(plugin.getPrefix() + "§cDu musst ein Spieler sein!");
			} else
					if(args.length == 2) {
						Player target = Bukkit.getPlayer(args[1]);
						if(target != null) {
							if(GameMode.getByValue(Integer.valueOf(args[0])) != null) {
								
								//Ändert den Gamemode eines anderen Spielers
								target.setGameMode(GameMode.getByValue(Integer.valueOf(args[0])));
								target.sendMessage(plugin.getPrefix() + "§6Dein Spielmodus wurde auf §c§l" + GameMode.getByValue(Integer.valueOf(args[0])).name() + " §6gesetzt!");
								sender.sendMessage(plugin.getPrefix() + "§6Du hast §2" + target.getDisplayName() + "§6 auf §c" + GameMode.getByValue(Integer.valueOf(args[0])).name() + " §6gesetzt!");
							} else
								sender.sendMessage(plugin.getPrefix() + "§cBitte benutze §2/gm 0/1/2/3 (<Spieler>) §c!");
							
						} else
							sender.sendMessage(plugin.getPrefix() + "§4Der Spieler ist gerade nicht online!");
			} else
				sender.sendMessage(plugin.getPrefix() + "§cBitte benutze §2/gm 0/1/2/3 (<Spieler>) §c!");
						
			} else
				sender.sendMessage(plugin.getPrefix() + "§cDazu hast du keine Rechte!");
		return false;
	}

}
