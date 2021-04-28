
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

package de.benangelo.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Money {
	
	//Legt das Geld eines Spielers fest
	public void setMoney(Player p, double money) {
		FileConfiguration cfg = getFileConfiguration();
		
		cfg.options().copyDefaults(true);
		if(!(cfg.contains(p.getUniqueId().toString()))) {
			cfg.addDefault(p.getUniqueId().toString(), money);
		} else {
			cfg.set(p.getUniqueId().toString(), money);
		}
		
		try {
			cfg.save(getFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Lädt die Datei
	private File getFile() {
		return new File("plugins/BewerbungsPlugin", "Money.yml");
	}
	
	//Übergibt die Datei
	private FileConfiguration getFileConfiguration() {
		return YamlConfiguration.loadConfiguration(getFile());
	}
	
	//Gibt das Guthaben des gesuchten Spielers raus
	public double getMoney(Player p) {
		FileConfiguration cfg = getFileConfiguration();
		
		if(cfg.contains(p.getUniqueId().toString())) {
			return cfg.getLong(p.getUniqueId().toString());
		} else {
			return 0.00;
		}
		
	}
	
	
}
