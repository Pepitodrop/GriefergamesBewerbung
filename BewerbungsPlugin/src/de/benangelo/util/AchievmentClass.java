
/*
 * Luis Benedikt
 * 
 * 2.5.2021
 * 
 * Die Benutzung nur nach Absprache Erlaubt
 * 
 * Dieses Plugin soll meine Programmierkünste in Spigot zeigen
 * 
 */

package de.benangelo.util;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import de.benangelo.config.Money;
import de.benangelo.main.Main;

public class AchievmentClass {

	private static Main plugin;
	
	//Setzt die Main
	public static void setAchievmentClassMain(Main m) {
    	plugin = m;
    }
	
	//Guckt ob der Spieler bereits das Achievment hat
	public boolean hasAchievment(Player p, Achievments achievments) {
		return plugin.getConfig().get(p.getName() + ".Achievments." + achievments.getName()) != null ? true : false;
	}
	
	//Gibt das Achievment!
	public void giveAchievment(Player p, Achievments achievments) {
		if(!hasAchievment(p, achievments)) {
			plugin.getConfig().set(p.getName() + ".Achievments." + achievments.getName(), true);
			plugin.saveConfig();
			
			p.sendMessage("§6Du hast das Achievment \'§b" + achievments.getName() + "§6\' freigeschalten!");
			p.sendMessage("§a-> " + achievments.getText());
			
			Money moneyCfg = new Money();
			
			double money = moneyCfg.getMoney(p) + achievments.getMoney();
			
			moneyCfg.setMoney(p, money);
			
			p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
		}
	}
	
}

