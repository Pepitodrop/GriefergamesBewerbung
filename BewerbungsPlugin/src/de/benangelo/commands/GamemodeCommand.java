package de.benangelo.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command commands, String lable, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(p.hasPermission("GM.Gamemode")) {
				if(args.length == 1) {
					
					switch(args[0]) {
					
					case "0":
						p.setGameMode(GameMode.SURVIVAL);
						p.sendMessage("§6Dein Spielmodus wurde auf §c§lUeberleben §6gesetzt!");
						break;
						
					case "1":
						p.setGameMode(GameMode.CREATIVE);
						p.sendMessage("§6Dein Spielmodus wurde auf §c§lKreativ §6gesetzt!");
						break;
					
					case "2":
						p.setGameMode(GameMode.ADVENTURE);
						p.sendMessage("§6Dein Spielmodus wurde auf §c§lAbenteuer §6gesetzt!");
						break;
						
					case "3":
						p.setGameMode(GameMode.SPECTATOR);
						p.sendMessage("§6Dein Spielmodus wurde auf §c§lZuschauer §6gesetzt!");
						break;
						
					default:
						p.sendMessage("§cBitte benutze §2/gm 0/1/2/3 §c!");
						break;
					}
					
			} else
					if(args.length == 2) {
						Player target = Bukkit.getPlayer(args[1]);
						if(target != null) {
							
							switch(args[0]) {
							
							case "0":
								target.setGameMode(GameMode.SURVIVAL);
								target.sendMessage("§6Dein Spielmodus wurde auf §c§lUeberleben §6gesetzt!");
								p.sendMessage("§6Du hast §2" + target.getName() + "§6 auf §cUeberleben §6gesetzt!");
								break;
								
							case "1":
								target.setGameMode(GameMode.CREATIVE);
								target.sendMessage("§6Dein Spielmodus wurde auf §c§lKreativ §6gesetzt!");
								p.sendMessage("§6Du hast §2" + target.getName() + "§6 auf §cKreativ §6gesetzt!");
								break;
							
							case "2":
								target.setGameMode(GameMode.ADVENTURE);
								target.sendMessage("§6Dein Spielmodus wurde auf §c§lAbenteuer §6gesetzt!");
								p.sendMessage("§6Du hast §2" + target.getName() + "§6 auf §cAbenteuer §6gesetzt!");
								break;
								
							case "3":
								target.setGameMode(GameMode.SPECTATOR);
								target.sendMessage("§6Dein Spielmodus wurde auf §c§lZuschauer §6gesetzt!");
								p.sendMessage("§6Du hast §2" + target.getName() + "§6 auf §cZuschauer §6gesetzt!");
								break;
								
							default:
								p.sendMessage("§cBitte benutze §2/gm 0/1/2/3 (<Spieler>) §c!");
								break;
							}
							
						} else
							p.sendMessage("§4Der Spieler ist gerade nicht online!");
			} else
				p.sendMessage("§cBitte benutze §2/gm 0/1/2/3 (<Spieler>) §c!");
						
			} else
				p.sendMessage("§cDazu hast du keine Rechte!");
			} else  {
				if(args.length == 2) {
					Player target = Bukkit.getPlayer(args[1]);
					if(target != null) {
						
						switch(args[0]) {
						
						case "0":
							target.setGameMode(GameMode.SURVIVAL);
							target.sendMessage("§6Dein Spielmodus wurde auf §c§lUeberleben §6gesetzt!");
							sender.sendMessage("§6Du hast §2" + target.getName() + "§6 auf §cUeberleben §6gesetzt!");
							break;
							
						case "1":
							target.setGameMode(GameMode.CREATIVE);
							target.sendMessage("§6Dein Spielmodus wurde auf §c§lKreativ §6gesetzt!");
							sender.sendMessage("§6Du hast §2" + target.getName() + "§6 auf §cKreativ §6gesetzt!");
							break;
						
						case "2":
							target.setGameMode(GameMode.ADVENTURE);
							target.sendMessage("§6Dein Spielmodus wurde auf §c§lAbenteuer §6gesetzt!");
							sender.sendMessage("§6Du hast §2" + target.getName() + "§6 auf §cAbenteuer §6gesetzt!");
							break;
							
						case "3":
							target.setGameMode(GameMode.SPECTATOR);
							target.sendMessage("§6Dein Spielmodus wurde auf §c§lZuschauer §6gesetzt!");
							sender.sendMessage("§6Du hast §2" + target.getName() + "§6 auf §cZuschauer §6gesetzt!");
							break;
							
						default:
							sender.sendMessage("§cBitte benutze §2/gm 0/1/2/3 (<Spieler>) §c!");
							break;
						}
						
					} else
						sender.sendMessage("§4Dieser Spieler ist leider nicht Online!");
		} else {
			sender.sendMessage("§cBitte benutze §2/gm 0/1/2/3 (<Spieler>) §c!");
		}
			}
		return false;
	}

}
