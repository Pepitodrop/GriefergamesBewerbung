package de.benangelo.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Money {
	
	public void setMoney(Player p, int money) {
		FileConfiguration cfg = getFileConfiguration();
		
		cfg.options().copyDefaults(true);
		cfg.addDefault(p.getUniqueId().toString(), money);
		
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
	
	public Object getMoney(Player p) {
		FileConfiguration cfg = getFileConfiguration();
		
		if(cfg.getString(p.getUniqueId().toString()) != null) {
			return cfg.getLong(p.getUniqueId().toString());
		} else {
			return null;
		}
		
	}
	
	
}
