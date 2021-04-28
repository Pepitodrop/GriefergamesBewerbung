
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

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.benangelo.main.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;

public class TPACommand implements CommandExecutor {
	
	public static ArrayList<String> TPA = new ArrayList<>();
	public static ArrayList<String> DontMove = new ArrayList<>();
	
	private static int taskID1;
	private static int ablaufZeit;
	private static Main plugin;
	
	private int taskID;
	
	
	public TPACommand(Main m) {
		plugin = m;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command commands, String lable, String[] args) {
		
		if(sender instanceof Player) {
			Player p = (Player) sender;
				if(args.length == 2) {
					
					//Ablehnen der TPA
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
						
						//Annahme der TPA und start des Teleports
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

						//Sendet die TPA
						Player target = Bukkit.getPlayer(args[0]);
						
						if(!TPA.contains(p.getName())) {
							TPA.add(p.getName());
							System.out.println();
							ablaufen(target, p);
							p.sendMessage("§6Du hast §2" + target.getName() + " §6eine TPA gesendet!");
							target.sendMessage("§6Dir wurde eine TPA von §c" + p.getName() + " §6gesendet!");
							target.sendMessage("§cUm die tpa abzulehnen verwende bitte §6/tpa deny " + p.getName() + " §6!");
							target.sendMessage("§2Um die tpa anzunehmen verwende bitte §6/tpa accept " + p.getName() + " §6!");
							
							final ComponentBuilder message = new ComponentBuilder();
							
							TextComponent messageAccept = new TextComponent("[AKZEPTIEREN]");
							messageAccept.setColor(ChatColor.DARK_GREEN);
							messageAccept.setBold(true);
							messageAccept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpa accept " + p.getName()));
							messageAccept.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Teleportiert dich zu dem Spieler nach 3 Sekunden Stillstehen")));
							message.append(messageAccept);
                            
							TextComponent messageLücke = new TextComponent(" | ");
                            messageLücke.setColor(ChatColor.GRAY);
                            messageLücke.setBold(true);
                            message.append(messageLücke);
							
                            TextComponent messageDeny = new TextComponent("[ABLEHNEN]");
                            messageDeny.setColor(ChatColor.DARK_RED);
                            messageDeny.setBold(true);
                            messageDeny.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpa deny " + p.getName()));
                            messageDeny.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Lehnt die Teleportationsanfrage ab")));
                            message.append(messageDeny);
                            
                            p.spigot().sendMessage(message.create());
				} else
					p.sendMessage(plugin.getPrefix() + "§cDu hast bereits eine TPA gesendet!");
				} else
					p.sendMessage(plugin.getPrefix() + "§cBitte benutze §2/tpa <Spieler> §c!");
			} else
				sender.sendMessage(plugin.getPrefix() + "Du musst ein spieler sein!");
		
		return false;
	}
	
	//Begrenzt die Verfügbarkeit der TPA
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
	
	//Lässt den Spieler vor dem Teleport warten und informiert ihn dass er bald Teleportiert wird
	private void wartezeit(Player t, Player p) {
		String name = t.getName();
		DontMove.add(name);
		taskID1 = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
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
					Bukkit.getScheduler().cancelTask(taskID1);
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
