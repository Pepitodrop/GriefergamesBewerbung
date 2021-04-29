
/*
 * Luis Benedikt
 * 
 * 28.4.2021
 * 
 * Die Benutzung nur nach Absprache Erlaubt
 * 
 * Dieses Plugin soll meine Programmierkünste in Spigot zeigen
 * 
 */

package de.benangelo.main;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.IOUtils;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import de.benangelo.Listener.BlockLog;
import de.benangelo.Listener.BuildAndDropListener;
import de.benangelo.Listener.ChangeFlyModeEvnet;
import de.benangelo.Listener.ChatLog;
import de.benangelo.Listener.ChestListener;
import de.benangelo.Listener.CommandListener;
import de.benangelo.Listener.CommandLog;
import de.benangelo.Listener.DamageListener;
import de.benangelo.Listener.EnderperleWerfen;
import de.benangelo.Listener.InvClickEvent;
import de.benangelo.Listener.InvCloseListener;
import de.benangelo.Listener.JoinListener;
import de.benangelo.Listener.LogInListener;
import de.benangelo.Listener.PickUpItemEvent;
import de.benangelo.Listener.PlayerMove;
import de.benangelo.Listener.PlayerQuit;
import de.benangelo.commands.BanCommand;
import de.benangelo.commands.BankCommand;
import de.benangelo.commands.BuildAndDropCommand;
import de.benangelo.commands.CraftingTableCommand;
import de.benangelo.commands.ECCommand;
import de.benangelo.commands.FlyCommand;
import de.benangelo.commands.GamemodeCommand;
import de.benangelo.commands.InvseeCommand;
import de.benangelo.commands.NasaCommand;
import de.benangelo.commands.PayCommand;
import de.benangelo.commands.TPACommand;
import de.benangelo.commands.ValueCommand;
import de.benangelo.config.AllgemeineConfigs;
import de.benangelo.config.AnimatedScoreboardFile;
import de.benangelo.mysql.MySQL;
import de.benangelo.util.ActionBar;
import de.benangelo.util.ItemBuilder;
import de.benangelo.util.RecipesLoader;
import de.benangelo.util.ScoreboardHandler;


public class Main extends JavaPlugin{
	
	private static Main plugin;
	
	private static String prefix;

	private static long UpdateSekunde;
	
	public ArrayList<String> canClick = new ArrayList<>();
	public ArrayList<String> nasaPictures = new ArrayList<>();
	
	//Lädt das Plugin
	@Override
	public void onEnable() {
		System.out.println("Das Plugin wird geladen!");
		
		plugin = this;
		
		AllgemeineConfigs file2 = new AllgemeineConfigs(this);
		file2.setStandard();
		file2.readData();
		
		AnimatedScoreboardFile file3 = new AnimatedScoreboardFile(this);
		file3.setStandard();
		file3.readData();
		
		MySQL.setMySQLMain(this);
		MySQL.setStandardMySQL();
		MySQL.readMySQL();
		MySQL.connect();
		MySQL.createTables();
		MySQL.reconnectScheduler();
		
		new RecipesLoader().registerRecipes();

		new ScoreboardHandler(plugin);
		updateSB();

		registerCommand();
		registerListener();
		
		send();
		
		Bukkit.getConsoleSender().sendMessage("§2Das Plugin wurde erfolgreich geladen!");
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			p.getInventory().setItem(0, new ItemBuilder(Material.ENDER_PEARL).setName("§4Enderperle").build());
			p.setLevel(0);
			p.setExp(0);
		}
		
		getNasaLink();
		
		super.onEnable();
	}
	
	//Beendet MySQL und schließt die EC
	@Override
	public void onDisable() {
		MySQL.disconnect();
		for(Player p : Bukkit.getOnlinePlayers()) {
			p.getOpenInventory().close();
		}
		super.onDisable();
	}
	
	
	
	
	//Registriert die Commands
	private void registerCommand() {
		getCommand("EC").setExecutor(new ECCommand(this));

		getCommand("value").setExecutor(new ValueCommand(this));

		getCommand("pay").setExecutor(new PayCommand(this));

		getCommand("bank").setExecutor(new BankCommand(this));

		getCommand("ban").setExecutor(new BanCommand(this));
		getCommand("tempban").setExecutor(new BanCommand(this));
		getCommand("unban").setExecutor(new BanCommand(this));
		getCommand("check").setExecutor(new BanCommand(this));

		getCommand("crafting").setExecutor(new CraftingTableCommand(this));

		getCommand("gm").setExecutor(new GamemodeCommand(this));
		
		getCommand("invsee").setExecutor(new InvseeCommand(this));
		
		getCommand("tpa").setExecutor(new TPACommand(this));
		
		getCommand("build").setExecutor(new BuildAndDropCommand(this));
		getCommand("drop").setExecutor(new BuildAndDropCommand(this));
		
		getCommand("nasa").setExecutor(new NasaCommand(this));
		
		getCommand("fly").setExecutor(new FlyCommand(this));
	}
	
	
	//Registriert die Listener
	private void registerListener() {
		PluginManager pluginManager = Bukkit.getPluginManager();
		pluginManager.registerEvents(new ChestListener(), this);
		pluginManager.registerEvents(new InvCloseListener(this), this);
		pluginManager.registerEvents(new InvClickEvent(this), this);
		pluginManager.registerEvents(new BlockLog(), this);
		pluginManager.registerEvents(new CommandLog(), this);
		pluginManager.registerEvents(new ChatLog(), this);
		pluginManager.registerEvents(new LogInListener(), this);
		pluginManager.registerEvents(new JoinListener(), this);
		pluginManager.registerEvents(new CommandListener(this), this);
		pluginManager.registerEvents(new PlayerQuit(this), this);
		pluginManager.registerEvents(new PlayerMove(this), this);
		pluginManager.registerEvents(new BuildAndDropListener(), this);
		pluginManager.registerEvents(new EnderperleWerfen(this), this);
		pluginManager.registerEvents(new DamageListener(), this);
		pluginManager.registerEvents(new PickUpItemEvent(), this);
		pluginManager.registerEvents(new ChangeFlyModeEvnet(), this);
	}
	
	
	
	
	
	//Lädt den Scoreboard neu
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
	
	//Sendet dem Spieler die Actionbar
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
	
	//Holt die Nasa Links zu den Bildern von der Website
	private void getNasaLink() {
        try {
            String url = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=1000&api_key=DEMO_KEY";

            @SuppressWarnings("deprecation")
			String NasaJson = IOUtils.toString(new URL(url));

            JSONObject NasaObject = (JSONObject) JSONValue.parseWithException(NasaJson);

            JSONArray namearr = (JSONArray) NasaObject.get("photos");
            for (Object objInArr : namearr) {
                JSONObject jsonKunde = (JSONObject) objInArr;
                nasaPictures.add(jsonKunde.get("img_src").toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	public void setUpdateSekunde(long updateSekunde) {
		UpdateSekunde = updateSekunde;
	}

	public Main getPlugin() {
		return plugin;
	}

	public String getPrefix() {
		return ChatColor.translateAlternateColorCodes('&', prefix);
	}

	public void setPrefix(String prefix) {
		Main.prefix = prefix;
	}

}
