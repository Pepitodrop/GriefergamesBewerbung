package de.benangelo.commands;

import java.util.Random;

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

public class NasaCommand implements CommandExecutor{

	private Main plugin;
	
	public NasaCommand(Main m) {
		this.plugin = m;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(p.hasPermission("bewerbungsPlugin.nasa")) {
				if(args.length == 0) {
					
					//Schickt dem Spieler ein Bild zu einem Zufälligen Bild eines Marsrovers
					Random rndm = new Random();
					int picture = rndm.nextInt(plugin.nasaPictures.size());
					
					final ComponentBuilder message = new ComponentBuilder();
					
					TextComponent messageOpen = new TextComponent("[OPEN NASA PICTURE]");
					messageOpen.setColor(ChatColor.DARK_GREEN);
					messageOpen.setBold(true);
					messageOpen.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, plugin.nasaPictures.get(picture)));
					messageOpen.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Öffnet dir ein Bild von einem Marsrover")));
					message.append(messageOpen);

                    
                    p.spigot().sendMessage(message.create());
					
				} else
					p.sendMessage(plugin.getPrefix() + "§7Bitte benutze §3/nasa §7!");
			} else
				p.sendMessage(plugin.getPrefix() + "§4Du hast dafür keine Rechte!");
		} else
			sender.sendMessage(plugin.getPrefix() + "§cDu musst ein Spieler sein!");
		return false;
	}

	
	
}
