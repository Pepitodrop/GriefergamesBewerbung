package de.benangelo.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import de.benangelo.main.Main;

public class ScoreboardHandler implements Listener{

	private Main plugin;
	
	public static String ANIMATTION_TITLE; 
	public static long ANIMATION_SPEED;
	
	private char[] letters;
	private int animationState;
	private static String currentTitle;
	
	public ScoreboardHandler(Main plugin) {
		this.plugin = plugin;
		letters = ANIMATTION_TITLE.toCharArray();
		animationState = 0;
		currentTitle = "";
		animate();
	}
	
	public static void setup(Player p) {
		Scoreboard scb = Bukkit.getScoreboardManager().getNewScoreboard();
		@SuppressWarnings("deprecation")
		Objective o = scb.registerNewObjective("abcd", "1234");
		o.setDisplayName(currentTitle);
		o.setDisplaySlot(DisplaySlot.SIDEBAR);
		o.getScore("").setScore(1);
		o.getScore("§2Testnachricht").setScore(0);
		o.getScore("§1Testnachricht").setScore(-1);
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
