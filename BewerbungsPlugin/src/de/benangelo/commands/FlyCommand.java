
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

import de.benangelo.Listener.ChangeFlyModeEvnet;
import de.benangelo.main.Main;

public class FlyCommand implements CommandExecutor {

	private Main plugin;
	
	public FlyCommand(Main m) {
		this.plugin = m;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(p.hasPermission("bewerbungsPlugin.fly")) {
				if(ChangeFlyModeEvnet.getCanFly().isEmpty() || !(ChangeFlyModeEvnet.getCanFly().contains(p)))  {
					
					//Erlaubt dem Spieler zu fliegen
					ChangeFlyModeEvnet.getCanFly().add(p);
					p.sendMessage(plugin.getPrefix() + "§2§lDu kannst nun fliegen!");
					p.setAllowFlight(true);
					p.setFlying(true);
					p.sendTitle("§2TestServer", "§7Can Fly §2§l>>> TRUE", 10, 20*2, 10);
				} else {
					
					//Verbietet dem Spieler zu fliegen
					ChangeFlyModeEvnet.getCanFly().remove(p);
					p.sendMessage(plugin.getPrefix() + "§4§lDu kannst nun nicht mehr fliegen!");
					p.setAllowFlight(false);
					p.setFlying(false);
					p.sendTitle("§2TestServer", "§7Can Fly §4§l>>> FALSE", 10, 20*2, 10);
				}
				
			} else
				p.sendMessage(plugin.getPrefix() + "§4Dazu hast du keine Rechte du Bauer!");
		} else
			Bukkit.getConsoleSender().sendMessage(plugin.getPrefix() + "§4Du musst ein Spieler sein!");
		return false;
	}

	
	
}
