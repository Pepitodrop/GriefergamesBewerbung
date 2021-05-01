
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

package de.benangelo.Listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import de.benangelo.commands.TPACommand;
import de.benangelo.main.Main;
import de.benangelo.util.AchievmentClass;
import de.benangelo.util.Achievments;

public class PlayerMove implements Listener{

	private static Main plugin;
	
	public PlayerMove(Main m) {
		plugin=m;
	}
	
	//Guckt ob und wo sich der Spieler bewegt und ist
	@EventHandler
	public void handlePlayerMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		
		new AchievmentClass().giveAchievment(p, Achievments.FIRSTMOVE);
		
		if(!(p.getLocation().getX() == e.getTo().getX() && p.getLocation().getZ() == e.getTo().getZ())) {
			if(TPACommand.DontMove.contains(p.getName())) {
				Bukkit.getScheduler().cancelTask(new TPACommand(plugin).getTaskID());
				p.sendMessage(plugin.getPrefix() + "§4Deine Teleportation wurde abgebrochen!");
				TPACommand.DontMove.remove(p.getName());
		}
			
		if(p.getGameMode() == GameMode.SURVIVAL || p.getGameMode() == GameMode.ADVENTURE) {
			if(p.getLocation().add(0, -1, 0).getBlock().getType() != Material.AIR) {
				if(ChangeFlyModeEvnet.getCanFly().isEmpty() || !(ChangeFlyModeEvnet.getCanFly().contains(p)))  {
					p.setAllowFlight(true);
					p.setFlying(false);
				}
			}
		}
		
		if(p.getLocation().getBlock().getType() == Material.LIGHT_WEIGHTED_PRESSURE_PLATE) {
			if(p.getLocation().add(0, -1, 0).getBlock().getType() == Material.EMERALD_BLOCK) {
				p.setVelocity(p.getLocation().getDirection().multiply(1).add(new Vector(0, 2, 0)));
			}
		}
		
		if(p.getLocation().getBlock().getType() == Material.HEAVY_WEIGHTED_PRESSURE_PLATE) {
			if(p.getLocation().add(0, -1, 0).getBlock().getType() == Material.EMERALD_BLOCK) {
				p.setVelocity(p.getLocation().getDirection().multiply(2).add(new Vector(0, 2, 0)));
			}
		}
			
	}
	
}
}
