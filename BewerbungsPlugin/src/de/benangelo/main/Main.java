package de.benangelo.main;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.benangelo.commands.ECCommand;
import de.benangelo.config.AllgemeineConfigs;
import de.benangelo.mysql.MySQL;
import de.benangelo.mysql.MySQLFile;
import de.benangelo.util.ChestListener;
import de.benangelo.util.InvClickEvent;
import de.benangelo.util.InvCloseListener;

public class Main extends JavaPlugin{
	
	public static ArrayList<String> canClick = new ArrayList<>();
	
	private static Main plugin;
	
	@Override
	public void onEnable() {
		System.out.println("Das Plugin wird geladen!");
		
		MySQLFile file = new MySQLFile();
		file.setStandard();
		file.readData();
		
		AllgemeineConfigs file2 = new AllgemeineConfigs();
		file2.setStandard();
		file2.readData();
		
		MySQL.connect();
		MySQL.createPlayerTable();
		
		getCommand("EC").setExecutor(new ECCommand());
		
		PluginManager pluginManager = Bukkit.getPluginManager();
		pluginManager.registerEvents(new ChestListener(), this);
		pluginManager.registerEvents(new InvCloseListener(), this);
		pluginManager.registerEvents(new InvClickEvent(), this);
		super.onEnable();
		
	}
	
	@Override
	public void onDisable() {
		MySQL.disconnect();
		super.onDisable();
	}
	
	public static Main getPlugin() {
		return plugin;
	}

}
