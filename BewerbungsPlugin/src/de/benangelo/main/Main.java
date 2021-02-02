package de.benangelo.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.benangelo.commands.ECCommand;
import de.benangelo.config.AllgemeineConfigs;
import de.benangelo.mysql.MySQL;
import de.benangelo.mysql.MySQLFile;
import de.benangelo.util.ChestListener;
import de.benangelo.util.InvCloseListener;

public class Main extends JavaPlugin{
	
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
		super.onEnable();
	}

}
