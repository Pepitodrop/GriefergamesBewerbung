package de.benangelo.main;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.benangelo.Listener.BlockLog;
import de.benangelo.Listener.ChatLog;
import de.benangelo.Listener.ChestListener;
import de.benangelo.Listener.CommandListener;
import de.benangelo.Listener.CommandLog;
import de.benangelo.Listener.InvClickEvent;
import de.benangelo.Listener.InvCloseListener;
import de.benangelo.Listener.JoinListener;
import de.benangelo.Listener.LogInListener;
import de.benangelo.Listener.PlayerMove;
import de.benangelo.Listener.PlayerQuit;
import de.benangelo.commands.BanCommand;
import de.benangelo.commands.BankCommand;
import de.benangelo.commands.CraftingTableCommand;
import de.benangelo.commands.ECCommand;
import de.benangelo.commands.GamemodeCommand;
import de.benangelo.commands.InvseeCommand;
import de.benangelo.commands.PayCommand;
import de.benangelo.commands.TPACommand;
import de.benangelo.commands.ValueCommand;
import de.benangelo.config.AllgemeineConfigs;
import de.benangelo.config.AnimatedScoreboardFile;
import de.benangelo.mysql.MySQL;
import de.benangelo.mysql.MySQLFile;
import de.benangelo.util.ActionBar;
import de.benangelo.util.RecipesLoader;
import de.benangelo.util.ScoreboardHandler;

public class Main extends JavaPlugin{
	
	public ArrayList<String> canClick = new ArrayList<>();
	
	private static Main plugin;
	
	private static String prefix;

	private static long UpdateSekunde;
	
	@Override
	public void onEnable() {
		System.out.println("Das Plugin wird geladen!");
		
		plugin = this;
		
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

		ScoreboardHandler sb = new ScoreboardHandler(this);
		

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

		getCommand("gm").setExecutor(new GamemodeCommand());
		
		getCommand("invsee").setExecutor(new InvseeCommand());
		
		getCommand("tpa").setExecutor(new TPACommand());

		PluginManager pluginManager = Bukkit.getPluginManager();
		pluginManager.registerEvents(new ChestListener(), this);
		pluginManager.registerEvents(new InvCloseListener(), this);
		pluginManager.registerEvents(new InvClickEvent(), this);
		pluginManager.registerEvents(sb, this);
		pluginManager.registerEvents(new BlockLog(), this);
		pluginManager.registerEvents(new CommandLog(), this);
		pluginManager.registerEvents(new ChatLog(), this);
		pluginManager.registerEvents(new LogInListener(), this);
		pluginManager.registerEvents(new JoinListener(), this);
		pluginManager.registerEvents(new CommandListener(), this);
		pluginManager.registerEvents(new PlayerQuit(), this);
		pluginManager.registerEvents(new PlayerMove(), this);
		
		send();
		
		Bukkit.getConsoleSender().sendMessage("�2Das Plugin wurde erfolgreich geladen!");
		
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
					    
					    actionBar.sendActionBar(all, "�2Wir haben �6" + nowHour + "�2 Uhr�1!");
					}
			}
		}, 0, 0);
	}

	@SuppressWarnings("deprecation")
	public static String getEnchants(ItemStack is){
		String enchants = "";
		if(is.getType() != Material.ENCHANTED_BOOK) {
			List<String> e = new ArrayList<String>();
			Map<Enchantment, Integer> en = is.getEnchantments();
			for(Enchantment t : en.keySet()) {
				e.add(t.getName() + ":" +en.get(t));
			}
			
			for(int i = 0; i < e.size(); i++) {
				enchants += e.get(i) + ":";
			}
			return enchants;
		} else {
			
			ItemStack item = is;
			
			EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();
			
			List<Enchantment> enchs = new ArrayList<Enchantment>(meta.getStoredEnchants().keySet());
		
			List<String> e = new ArrayList<String>();

			for(int en = 0; en < enchs.size(); en++) {
				e.add(enchs.get(en).getName() + ":" + meta.getStoredEnchantLevel(enchs.get(en)));
			}	
			
			for(int i = 0; i < e.size(); i++) {
				enchants += e.get(i) + ":";
			}

			return enchants;
		}
			
		}
	
	public static void setUpdateSekunde(long updateSekunde) {
		UpdateSekunde = updateSekunde;
	}

	public static Main getPlugin() {
		return plugin;
	}

	public static String getPrefix() {
		return prefix;
	}

	public static void setPrefix(String prefix) {
		Main.prefix = prefix;
	}

}
