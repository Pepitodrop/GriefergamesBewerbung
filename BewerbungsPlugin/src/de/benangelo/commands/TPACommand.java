package de.benangelo.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.benangelo.main.Main;

public class TPACommand implements CommandExecutor {
	
	public static ArrayList<String> TPA = new ArrayList<>();
	public static ArrayList<String> DontMove = new ArrayList<>();
	
	private int taskID;
	private static int taskID1;
	private static int ablaufZeit;
	private static Main plugin;
	
	public TPACommand(Main m) {
		plugin = m;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command commands, String lable, String[] args) {
		
		if(sender instanceof Player) {
			Player p = (Player) sender;
				if(args.length == 2) {
					
					if(args[0].equalsIgnoreCase("deny")) {
						Player target = Bukkit.getPlayer(args[1]);
						if(target != null) {
							if(TPA.contains(target.getName())) {
							TPA.remove(target.getName());
							Bukkit.getScheduler().cancelTask(taskID);
							target.sendMessage(plugin.getPrefix() + "§cDeine TPA wurde abgelehnt!");
							p.sendMessage(plugin.getPrefix() + "§4Du hast die TPA von §6" + target.getName() + " §4abgelehnt!");
									
								
					} else
						p.sendMessage(plugin.getPrefix() + "§cDer Spieler hat dir keine TPA gesendet!");
					} else
						p.sendMessage(plugin.getPrefix() + "§cDer Spieler ist nicht auf dem Server!");
						
						 
					} else
						if(args[0].equalsIgnoreCase("accept")) {
							Player target = Bukkit.getPlayer(args[1]);
							if(target != null) {
							if(TPA.contains(target.getName())) {
							TPA.remove(target.getName());
							Bukkit.getScheduler().cancelTask(taskID);
							target.sendMessage(plugin.getPrefix() + "§2Deine TPA wurde angenommen!");
							p.sendMessage(plugin.getPrefix() + "§2Du hast die TPA von §6" + target.getName() + " §2angenommen!");
							wartezeit(target, p);
				} else
					p.sendMessage(plugin.getPrefix() + "§cDer Spieler hat dir keine TPA gesendet!");
				} else
					p.sendMessage(plugin.getPrefix() + "§cDer Spieler ist nicht auf dem Server!");
				} else
					p.sendMessage(plugin.getPrefix() + "§cBitte benutze §2/tpa accept/deny <Spieler>");
						
					
				} else
					if(args.length == 1) {

						Player target = Bukkit.getPlayer(args[0]);
						
						if(!TPA.contains(p.getName())) {
							TPA.add(p.getName());
							System.out.println();
							ablaufen(target, p);
							p.sendMessage("§6Du hast §2" + target.getName() + " §6eine TPA gesendet!");
							target.sendMessage("§6Dir wurde eine TPA von §c" + p.getName() + " §6gesendet!");
							target.sendMessage("§cUm die tpa abzulehnen verwende bitte §6/tpa deny " + p.getName() + " §6!");
							target.sendMessage("§2Um die tpa anzunehmen verwende bitte §6/tpa accept " + p.getName() + " §6!");
				} else
					p.sendMessage(plugin.getPrefix() + "§cDu hast bereits eine TPA gesendet!");
				} else
					p.sendMessage(plugin.getPrefix() + "§cBitte benutze §2/tpa <Spieler> §c!");
			} else
				sender.sendMessage(plugin.getPrefix() + "Du musst ein spieler sein!");
		
		return false;
	}
	
	@SuppressWarnings("deprecation")
	private void ablaufen(Player t, Player p) {
		p.sendMessage("§6Deine TPA läuft in §4" + getAblaufZeit() + " sekunden §6ab!");
		taskID = Bukkit.getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable() {
			int countdown = getAblaufZeit();
			@Override
			public void run() {
				switch(countdown) {
				
				case 60:
					p.sendMessage(plugin.getPrefix() + "§6Deine TPA läuft in §4einer Minute §6ab!");
					t.sendMessage(plugin.getPrefix() + "§6Deine TPA läuft in §4einer Minute §6ab!");
					break;
				
				case 30:case 15: case 10: case 5: case 3: case 2:
					p.sendMessage(plugin.getPrefix() + "§6Deine TPA läuft in §4" + countdown + " sekunden §6ab!");
					t.sendMessage(plugin.getPrefix() + "§6Deine TPA läuft in §4" + countdown + " sekunden §6ab!");
					break;
					
				case 1:
					p.sendMessage(plugin.getPrefix() + "§6Deine TPA läuft in §4" + "einer" + " sekunde §6ab!");
					t.sendMessage(plugin.getPrefix() + "§6Deine TPA läuft in §4" + "einer" + " sekunde §6ab!");
					break;
					
				case 0:
					Bukkit.getScheduler().cancelTask(taskID);
					p.sendMessage(plugin.getPrefix() + "§6Deine TPA ist §4§labgelaufen!");
					t.sendMessage(plugin.getPrefix() + "§6Deine TPA ist §4§labgelaufen!");
					TPA.remove(p.getName());
					return;
				}
				countdown--;
			}
		}, 0, 20);
	}
	
	@SuppressWarnings("deprecation")
	private void wartezeit(Player t, Player p) {
		String name = t.getName();
		DontMove.add(name);
		taskID1 = Bukkit.getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable() {
			int countdown = 3;
			@SuppressWarnings("unused")
			@Override
			public void run() {
				switch(countdown) {
				
				case 3:case 2:
					t.sendMessage("§6Du darfst dich §c" + countdown + " sekunden §4§lnicht §6bewegen!");
					break;
					
				case 1:
					t.sendMessage("§6Du darfst dich §c" + "eine" + " sekunde §4§lnicht §6bewegen!");
					break;
					
				case 0:
					Bukkit.getScheduler().cancelTask(taskID);
					if(t != null) {
						t.teleport(p);
						t.sendMessage(plugin.getPrefix() + "§6Du wurdest teleportiert!");
					} else
						p.sendMessage(plugin.getPrefix() + "§6" + name + " §4ist disconnected!");
					DontMove.remove(name);
					return;
					
				}
				countdown--;
			}
		}, 0, 20);
	}

	public int getAblaufZeit() {
		return ablaufZeit;
	}

	public void setAblaufZeit(int ablaufZeit) {
		TPACommand.ablaufZeit = ablaufZeit;
	}

	public int getTaskID() {
		return taskID1;
	}
	
}
