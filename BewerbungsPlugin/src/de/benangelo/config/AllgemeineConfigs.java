package de.benangelo.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import de.benangelo.commands.ECCommand;

public class AllgemeineConfigs {

	private static int breite;
	private static int höhe;
	private static String InvName;
	private static String InvNAme = "§6§l" + InvName;
	
	public void setStandard() {
		FileConfiguration cfg = getFileConfiguration();
		
		cfg.options().copyDefaults(true);
		
		cfg.addDefault("Breite des Invs", 9);
		cfg.addDefault("Höhe des Invs", 5);
		cfg.addDefault("INV Name", "EC");
		
		try {
			cfg.save(getFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private File getFile() {
		return new File("plugins/PortableEC", "Configs.yml");
	}
	
	private FileConfiguration getFileConfiguration() {
		return YamlConfiguration.loadConfiguration(getFile());
	}
	
	public void readData() {
		FileConfiguration cfg = getFileConfiguration();
		
		breite = cfg.getInt("Breite des Invs");
		höhe = cfg.getInt("Höhe des Invs");
		ECCommand.InvName = cfg.getString("INV Name");
	}

	public static int getHöhe() {
		return höhe;
	}

	public static int getBreite() {
		return breite;
	}

	public static String getInvNAme() {
		return InvNAme;
	}
	
}
