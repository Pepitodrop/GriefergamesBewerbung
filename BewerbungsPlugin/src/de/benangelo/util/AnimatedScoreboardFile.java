package de.benangelo.util;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import de.benangelo.main.Main;

public class AnimatedScoreboardFile {
	
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
		Main.setUpdateSekunde(cfg.getLong("Update-Sekunde"));
	}

}
