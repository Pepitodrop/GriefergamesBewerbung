package de.benangelo.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import de.benangelo.config.Money;
import de.benangelo.main.Main;

public class ScoreboardHandler{
	
	public static String ANIMATTION_TITLE; 
	public static long ANIMATION_SPEED;
	
	private static String currentTitle;
	private static SimpleDateFormat date = new SimpleDateFormat ("dd.MM.yyyy");
	private static String now = date.format(new Date());
    
	private Main plugin;
	private char[] letters;
	private int animationState;
	
	public ScoreboardHandler(Main plugin) {
		this.plugin = plugin;
		letters = ANIMATTION_TITLE.toCharArray();
		animationState = 0;
		currentTitle = "";
		animate();
	}
	
	//Sendet den Scoreboard und erstellt ihn
	public static void setup(Player p) {
		
		Money money = new Money();
		Scoreboard scb = Bukkit.getScoreboardManager().getNewScoreboard();
		@SuppressWarnings("deprecation")
		Objective o = scb.registerNewObjective("abcd", "1234");
		o.setDisplayName(currentTitle);
		o.setDisplaySlot(DisplaySlot.SIDEBAR);
		o.getScore("�e�l" + now).setScore(13);
		
		o.getScore("�4 ").setScore(12);
		
		o.getScore("�7> �bServer:").setScore(11);
		o.getScore("�3" + p.getServer().getName()).setScore(10);
		
		o.getScore("�3 ").setScore(9);
		
		o.getScore("�7> �bOnline:").setScore(8);
		o.getScore("�3" + p.getServer().getOnlinePlayers().size() + "�7/�3" + p.getServer().getMaxPlayers()).setScore(7);
		
		o.getScore("�2 ").setScore(6);
		
		o.getScore("�7> �bMoney:").setScore(5);
		o.getScore("�3" + money.getMoney(p) + " $").setScore(4); 
		
		o.getScore("�1 ").setScore(3);
		o.getScore("�7> �bServer-Adress:").setScore(2);
		o.getScore("�3" + p.getServer().getIp().toString()).setScore(1);
		p.setScoreboard(scb);
	}
	
	//Animiert die Schrift
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
	
}
