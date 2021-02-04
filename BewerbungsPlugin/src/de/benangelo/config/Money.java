package de.benangelo.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Money {
	
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
	
	private File getFile() {
		return new File("plugins/BewerbungsPlugin", "Money.yml");
	}
	
	private FileConfiguration getFileConfiguration() {
		return YamlConfiguration.loadConfiguration(getFile());
	}
	
	public double getMoney(Player p) {
		FileConfiguration cfg = getFileConfiguration();
		
		if(cfg.contains(p.getUniqueId().toString())) {
			return cfg.getLong(p.getUniqueId().toString());
		} else {
			return 0.00;
		}
		
	}
	
	
}
