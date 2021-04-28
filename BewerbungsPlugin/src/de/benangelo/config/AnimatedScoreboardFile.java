package de.benangelo.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import de.benangelo.main.Main;
import de.benangelo.util.ScoreboardHandler;

public class AnimatedScoreboardFile {
	
	private static Main plugin;
	
	public AnimatedScoreboardFile(Main m) {
		plugin = m;
	}
	
	public void setStandard() {
		FileConfiguration cfg = getFileConfiguration();
		
		cfg.options().copyDefaults(true);
		
		cfg.addDefault("ANIMATTION_TITLE", "Willkommen");
		cfg.addDefault("ANIMATION_SPEED", 20);
		cfg.addDefault("Update-Sekunde", 5);
		
		try {
			cfg.save(getFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private File getFile() {
		return new File("plugins/BewerbungsPlugin", "ScoreboardConfig.yml");
	}
	
	private FileConfiguration getFileConfiguration() {
		return YamlConfiguration.loadConfiguration(getFile());
	}
	
	public void readData() {
		FileConfiguration cfg = getFileConfiguration();
		
		ScoreboardHandler.ANIMATTION_TITLE = cfg.getString("ANIMATTION_TITLE");
		ScoreboardHandler.ANIMATION_SPEED = cfg.getLong("ANIMATION_SPEED");
		plugin.setUpdateSekunde(cfg.getLong("Update-Sekunde"));
	}

}
