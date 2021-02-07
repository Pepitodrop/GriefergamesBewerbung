package de.benangelo.commands;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.benangelo.util.BanManager;
import de.benangelo.util.BanUnit;

public class BanCommand implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
			if(cmd.getName().equalsIgnoreCase("ban")) {
				if(sender.hasPermission("bewerbungsPLugin.ban")) {
					if(args.length >= 2) {
						String playername = args[0];
						Player target = Bukkit.getPlayer(playername);
						String reason = "";
						for(int i = 1; i < args.length; i++) {
							reason += args[i] + " ";
						}
						
						if(target != null) {
							
							if(BanManager.isBanned(target.getUniqueId())) {
								sender.sendMessage("�2Der Spieler ist bereits gebannt!");
								return false;
							} else
							BanManager.ban(target.getUniqueId(), playername, reason, -1);
							
						} else {
							OfflinePlayer op = Bukkit.getOfflinePlayer(playername);
							if(BanManager.isBanned(op.getUniqueId())) {
								sender.sendMessage("�2Der Spieler ist bereits gebannt!");
							} else
							BanManager.ban(op.getUniqueId(), playername, reason, -1);
						}
						sender.sendMessage("�2Du hast den Spieler �c" + playername + " �4�lPERMANENT �2gebannt! Danke f�r das Sauberhalten des Servers!");
					} else
						sender.sendMessage("�4Bitte benutze �2/ban <Spieler> <Grund> �4!");
				} else
					sender.sendMessage("�4Dazu hast du keine Rechte!");
			} else {
				
				
				if(cmd.getName().equalsIgnoreCase("tempban")) {
					if(sender.hasPermission("bewerbungsPLugin.tempBan")) {
						if(args.length >= 4) {
							String playername = args[0];
							long value;
							
							try {
								value = Long.valueOf(args[1]);
							} catch(NumberFormatException e) {
								sender.sendMessage("�c<Zahlenwert> muss eine Zahl sein!");
								return false;
							}
							String unitString = args[2];
							Player target = Bukkit.getPlayer(playername);
							String reason = "";
							for(int i = 3; i < args.length; i++) {
								reason += args[i] + " ";
							}	
							List<String> units = BanUnit.getUnitsAsString();
							if(units.contains(unitString.toLowerCase())) {
								BanUnit un = BanUnit.getUnit(unitString);
								long seconds = value * un.getToSeconds();
								
								if(target != null) {
									
									if(BanManager.isBanned(target.getUniqueId())) {
										sender.sendMessage("�2Der Spieler ist bereits gebannt!");
										return true;
									} else
									BanManager.ban(target.getUniqueId(), playername, reason, seconds);
									
								} else {
									OfflinePlayer op = Bukkit.getOfflinePlayer(playername);
									if(BanManager.isBanned(op.getUniqueId())) {
										sender.sendMessage("�2Der Spieler ist bereits gebannt!");
									} else
									BanManager.ban(op.getUniqueId(), playername, reason, seconds);
								}
								sender.sendMessage("�2Du hast den Spieler �c" + playername + " �2f�r �4�l" + value + un.getName() +  "�2gebannt! Danke f�r das Sauberhalten des Servers!");
								
							}
							sender.sendMessage("�cDiese <Einheit> exestiert nicht!");
							return false;
						} else
							sender.sendMessage("�4Bitte benutze �2/tampban <Spieler> <Zahlenwert> <Einheit> <Grund> �4!");
					} else
						sender.sendMessage("�4Dazu hast du keine Rechte!");
				} else {
					
					
					if(cmd.getName().equalsIgnoreCase("check")) {
						if(sender.hasPermission("bewerbungsPLugin.checkBan")) {
							if(args.length == 1) {
								
								if(args[0].equalsIgnoreCase("list")) {
									List<String> list = BanManager.getBannedPlayers();
									if(list.size() == 0) {
										sender.sendMessage("�cEs sind aktuell keine Spieler gebannt!");
										return false;
									}
									sender.sendMessage("�7---------- �6�lBan-Liste �7----------");
									for(String allBanned : BanManager.getBannedPlayers()) {
										OfflinePlayer op = Bukkit.getOfflinePlayer(allBanned);
										sender.sendMessage("�r" + allBanned + " �7(Grund: �r" + BanManager.getReason(op.getUniqueId()) + "�7)");
									}
								}
								
								String playername = args[0];
								Player target = Bukkit.getPlayer(playername);
								UUID uuid = null;
								if(target != null) {
									uuid = target.getUniqueId();
								} else
									uuid = Bukkit.getOfflinePlayer(playername).getUniqueId();
								
								sender.sendMessage("�7---------- �6�lBan-Infos �7----------");
								sender.sendMessage("�eName: �r" + playername);
								sender.sendMessage("�eGebannt: �r" + (BanManager.isBanned(uuid) ? "�aJa!" : "�cNein!"));
								if(BanManager.isBanned(uuid)) {
									sender.sendMessage("�eGrund: �r" + BanManager.getReason(uuid));
									sender.sendMessage("�everbleibende Zeit: �r" + BanManager.getRemainingTime(uuid));
								}
								
//								sender.sendMessage("�eName:" + );
								
							} else
								sender.sendMessage("�4Bitte benutze �2/check (list)/<spieler> �4!");
						} else
							sender.sendMessage("�4Dazu hast du keine Rechte!");
					} else {
						
						
						if(cmd.getName().equalsIgnoreCase("unban")) {
							if(sender.hasPermission("bewerbungsPLugin.checkBan")) {
								if(args.length == 1) {
									
									String playername = args[0];
									Player target = Bukkit.getPlayer(playername);
									UUID uuid = null;
									if(target != null) {
										uuid = target.getUniqueId();
									} else
										uuid = Bukkit.getOfflinePlayer(playername).getUniqueId();
									
									if(BanManager.isBanned(uuid)) {
										BanManager.unban(uuid);
										sender.sendMessage("�2Du hast �e" + playername + " �2entabnnt!");
									} else {
										sender.sendMessage("�4Dieser Spieler ist nicht gebannt!");
									}
									
								} else
									sender.sendMessage("�4Bitte benutze �2/unban <spieler> �4!");
							} else
								sender.sendMessage("�4Dazu hast du keine Rechte!");
						} else {
							sender.sendMessage("");
						}
					}
				}
			}
		return false;
	}

	
	
}