package de.benangelo.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import de.benangelo.commands.ECCommand;
import de.benangelo.commands.TPACommand;
import de.benangelo.util.BanManager;

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
		cfg.addDefault("Entbannungsantrag", "www.deineWebseite.de");
		cfg.addDefault("Ablauf dauer der TPA in sekunden", 90);
		
		try {
			cfg.save(getFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private File getFile() {
		return new File("plugins/BewerbungsPlugin", "Configs.yml");
	}
	
	private FileConfiguration getFileConfiguration() {
		return YamlConfiguration.loadConfiguration(getFile());
	}
	
	public void readData() {
		FileConfiguration cfg = getFileConfiguration();
		
		breite = cfg.getInt("Breite des Invs");
		höhe = cfg.getInt("Höhe des Invs");
		ECCommand.InvName = cfg.getString("INV Name");
		BanManager.setEntbannungsantrag(cfg.getString("Entbannungsantrag"));
		TPACommand.setAblaufZeit(cfg.getInt("Ablauf dauer der TPA in sekunden"));
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
