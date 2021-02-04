package de.benangelo.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import de.benangelo.config.Money;
import de.benangelo.main.Main;

public class ScoreboardHandler implements Listener{

	private Main plugin;
	
	public static String ANIMATTION_TITLE; 
	public static long ANIMATION_SPEED;
	
	private char[] letters;
	private int animationState;
	private static String currentTitle;
	
	static SimpleDateFormat date = new SimpleDateFormat ("dd.MM.yyyy");
    static String now = date.format(new Date());
    
	public ScoreboardHandler(Main plugin) {
		this.plugin = plugin;
		letters = ANIMATTION_TITLE.toCharArray();
		animationState = 0;
		currentTitle = "";
		animate();
	}
	
	public static void setup(Player p) {
		Money money = new Money();
		Scoreboard scb = Bukkit.getScoreboardManager().getNewScoreboard();
		@SuppressWarnings("deprecation")
		Objective o = scb.registerNewObjective("abcd", "1234");
		o.setDisplayName(currentTitle);
		o.setDisplaySlot(DisplaySlot.SIDEBAR);
		o.getScore("§e§l" + now).setScore(15);
		o.getScore("").setScore(14);
		o.getScore("").setScore(13);
		o.getScore("§7> §bServer:").setScore(12);
		o.getScore("§3" + p.getServer().getName()).setScore(11);
		o.getScore("").setScore(10);
		o.getScore("§7> §bOnline:").setScore(9);
		o.getScore("§3" + p.getServer().getOnlinePlayers().size() + "§7/§3" + p.getServer().getMaxPlayers()).setScore(8);
		o.getScore("").setScore(7);
		o.getScore("§7> §bMoney:").setScore(6);
		if(money.getMoney(p) != null) {
			o.getScore("§3" + money.getMoney(p)).setScore(5); 
		} else {
			o.getScore("§3" + "0").setScore(4); 
		}
		o.getScore("").setScore(3);
		o.getScore("§7> §bServer-Adress:").setScore(2);
		o.getScore("§3" + p.getServer().getIp()).setScore(1);
		p.setScoreboard(scb);
	}
	
	public void animate() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			
			@Override
			public void run() {
				for(Player current : Bukkit.getOnlinePlayers()) {
				if(current.getScoreboard() == null)
					setup(current);
					current.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(currentTitle);
				
				if(animationState >= letters.length) {
					currentTitle = "";
					animationState = 0;
				}
					currentTitle = currentTitle + letters[animationState];
				}
					animationState ++;
			}
		}, 0, ANIMATION_SPEED);
	}
	
	@EventHandler
	public void handlePalyerJoin(PlayerJoinEvent e) {
		setup(e.getPlayer());
	}
	
}
