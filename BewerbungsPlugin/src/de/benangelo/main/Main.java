package de.benangelo.main;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.benangelo.commands.ECCommand;
import de.benangelo.commands.PayCommand;
import de.benangelo.commands.ValueCommand;
import de.benangelo.config.AllgemeineConfigs;
import de.benangelo.mysql.MySQL;
import de.benangelo.mysql.MySQLFile;
import de.benangelo.util.AnimatedScoreboardFile;
import de.benangelo.util.ChestListener;
import de.benangelo.util.InvClickEvent;
import de.benangelo.util.InvCloseListener;
import de.benangelo.util.ScoreboardHandler;

public class Main extends JavaPlugin{
	
	public static ArrayList<String> canClick = new ArrayList<>();
	
	private static Main plugin;

	private static long UpdateSekunde;
	
	@Override
	public void onEnable() {
		System.out.println("Das Plugin wird geladen!");
		
		MySQLFile file = new MySQLFile();
		file.setStandard();
		file.readData();
		
		AllgemeineConfigs file2 = new AllgemeineConfigs();
		file2.setStandard();
		file2.readData();
		
		AnimatedScoreboardFile file3 = new AnimatedScoreboardFile();
		file3.setStandard();
		file3.readData();
		
		MySQL.connect();
		MySQL.createPlayerTable();
		updateSB();
		
		getCommand("EC").setExecutor(new ECCommand());
		getCommand("value").setExecutor(new ValueCommand());
		getCommand("pay").setExecutor(new PayCommand());
		
		PluginManager pluginManager = Bukkit.getPluginManager();
		pluginManager.registerEvents(new ChestListener(), this);
		pluginManager.registerEvents(new InvCloseListener(), this);
		pluginManager.registerEvents(new InvClickEvent(), this);
		pluginManager.registerEvents(new ScoreboardHandler(this), this);
		
		
		super.onEnable();
		
	}
	
	@Override
	public void onDisable() {
		MySQL.disconnect();
		for(Player p : Bukkit.getOnlinePlayers()) {
			p.getOpenInventory().close();
		}
		super.onDisable();
	}
	
	private void updateSB() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			
			@Override
			public void run() {
				
				for(Player p : Bukkit.getOnlinePlayers()) {
					ScoreboardHandler.setup(p);
				}
				
			}
		}, 0L, (long)(20 * UpdateSekunde));
	}
	
	
	
	public static Main getPlugin() {
		return plugin;
	}

	public static void setUpdateSekunde(long updateSekunde) {
		UpdateSekunde = updateSekunde;
	}

}
