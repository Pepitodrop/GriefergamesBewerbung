
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

import de.benangelo.Listener.BuildAndDropListener;
import de.benangelo.Listener.PickUpItemEvent;
import de.benangelo.main.Main;

public class BuildAndDropCommand implements CommandExecutor{

	private static Main plugin;
	
	public BuildAndDropCommand(Main m) {
		plugin = m;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(p.hasPermission("BewerbungsPlugin.CanBuildAndDrop")) {
				
				if(cmd.getName().equalsIgnoreCase("build")) {
					if(BuildAndDropListener.getCanBuild().contains(p)) {
						//Erlaubt dem Spieler nicht mehr zu bauen und zu droppen!
						BuildAndDropListener.getCanBuild().remove(p);
						BuildAndDropListener.getCanDrop().remove(p);
						PickUpItemEvent.getCanPickItem().remove(p);
						p.sendMessage(plugin.getPrefix() + "§4§lDu kannst nun nicht mehr Bauen!");
						p.setGameMode(GameMode.SURVIVAL);
						p.sendTitle("§2TestServer", "§7Can Build §4§l>>> FALSE", 10, 20*2, 10);
						
					} else {
						//Erlaubt dem Spieler zu bauen und zu droppen!
						BuildAndDropListener.getCanBuild().add(p);
						BuildAndDropListener.getCanDrop().add(p);
						PickUpItemEvent.getCanPickItem().add(p);
						p.sendMessage(plugin.getPrefix() + "§2§lDu kannst nun Bauen!");
						p.setGameMode(GameMode.CREATIVE);
						p.sendTitle("§2TestServer", "§7Can Build §2§l>>> TRUE", 10, 20*2, 10);
					}
				} else {
					if(cmd.getName().equalsIgnoreCase("drop")) {
						
						if(BuildAndDropListener.getCanDrop().contains(p)) {
							//Erlaubt dem Spieler nicht mehr zu droppen!
							PickUpItemEvent.getCanPickItem().remove(p);
							BuildAndDropListener.getCanDrop().remove(p);
							p.sendMessage(plugin.getPrefix() + "§4§lDu kannst nun nicht mehr Droppen!");
							p.sendTitle("§2TestServer", "§7Can Drop §4§l>>> FALSE", 10, 20*2, 10);
							
						} else {
							//Erlaubt dem Spieler zu droppen!
							PickUpItemEvent.getCanPickItem().add(p);
							BuildAndDropListener.getCanDrop().add(p);
							p.sendMessage(plugin.getPrefix() + "§2§lDu kannst nun Droppen!");
							p.sendTitle("§2TestServer", "§7Can Drop §2§l>>> TRUE", 10, 20*2, 10);
						}
					} else {
						p.sendMessage(plugin.getPrefix() + "§4Bitte benutze §6/build oder /drop §4!");
					}
				}
			} else
				p.sendMessage(plugin.getPrefix() + "§4Dazu hast du keine Rechte du Bauer!");
		} else
			Bukkit.getConsoleSender().sendMessage(plugin.getPrefix() + "§4Du musst ein Spieler sein!");
		return false;
	}

	
	
}
