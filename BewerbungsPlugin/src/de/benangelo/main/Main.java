package de.benangelo.main;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.benangelo.Listener.BlockLog;
import de.benangelo.Listener.ChatLog;
import de.benangelo.Listener.ChestListener;
import de.benangelo.Listener.CommandLog;
import de.benangelo.Listener.InvClickEvent;
import de.benangelo.Listener.InvCloseListener;
import de.benangelo.Listener.JoinListener;
import de.benangelo.Listener.LogInListener;
import de.benangelo.commands.BanCommand;
import de.benangelo.commands.BankCommand;
import de.benangelo.commands.CraftingTableCommand;
import de.benangelo.commands.ECCommand;
import de.benangelo.commands.PayCommand;
import de.benangelo.commands.ValueCommand;
import de.benangelo.config.AllgemeineConfigs;
import de.benangelo.mysql.MySQL;
import de.benangelo.mysql.MySQLFile;
import de.benangelo.util.ActionBar;
import de.benangelo.util.AnimatedScoreboardFile;
import de.benangelo.util.RecipesLoader;
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
		MySQL.createTables();
		
		new RecipesLoader().registerRecipes();
		
		updateSB();
		
		getCommand("EC").setExecutor(new ECCommand());
		
		getCommand("value").setExecutor(new ValueCommand());
		
		getCommand("pay").setExecutor(new PayCommand());
		
		getCommand("bank").setExecutor(new BankCommand());
		
		getCommand("ban").setExecutor(new BanCommand());
		getCommand("tempban").setExecutor(new BanCommand());
		getCommand("unban").setExecutor(new BanCommand());
		getCommand("check").setExecutor(new BanCommand());
		
		getCommand("crafting").setExecutor(new CraftingTableCommand());
		
		PluginManager pluginManager = Bukkit.getPluginManager();
		pluginManager.registerEvents(new ChestListener(), this);
		pluginManager.registerEvents(new InvCloseListener(), this);
		pluginManager.registerEvents(new InvClickEvent(), this);
		pluginManager.registerEvents(new ScoreboardHandler(this), this);
		pluginManager.registerEvents(new BlockLog(), this);
		pluginManager.registerEvents(new CommandLog(), this);
		pluginManager.registerEvents(new ChatLog(), this);
		pluginManager.registerEvents(new LogInListener(), this);
		pluginManager.registerEvents(new JoinListener(), this);
		
		send();
		
		Bukkit.getConsoleSender().sendMessage("§2Das Plugin wurde erfolgreich geladen!");
		
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
	
	public void send() {
		ActionBar actionBar = new ActionBar();
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			
			@Override
			public void run() {
					for(Player all : Bukkit.getOnlinePlayers()) {
						
						SimpleDateFormat dateHour = new SimpleDateFormat ("HH:mm:ss");
					    String nowHour = dateHour.format(new Date());
					    
					    actionBar.sendActionBar(all, "§2Wir haben §6" + nowHour + "§2 Uhr§1!");
					}
			}
		}, 0, 0);
	}
	
	
	
	public static Main getPlugin() {
		return plugin;
	}

	public static void setUpdateSekunde(long updateSekunde) {
		UpdateSekunde = updateSekunde;
	}

}
