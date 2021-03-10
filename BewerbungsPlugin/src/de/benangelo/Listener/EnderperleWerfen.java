package de.benangelo.Listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.benangelo.main.Main;
import de.benangelo.util.ItemBuilder;

public class EnderperleWerfen implements Listener{

	private int taskID;
	private int count = 10;
	private float time = count;
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void handlePlayerThrowEP(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(p.getInventory().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§4Enderperle")) {
				p.setExp(1f);
				p.setLevel(count);
				
				taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
					int countdown = count;
					
					@Override
					public void run() {
						
						countdown--;
						
						if(countdown <= 0) {
							p.getInventory().setItem(0, new ItemBuilder(Material.ENDER_PEARL).setName("§4Enderperle").build());
							p.setLevel(0);
							p.setExp(p.getExp()-p.getExp());
							Bukkit.getScheduler().cancelTask(taskID);
						}
						
						float exp = p.getExp();
						float remove = (float)1/time;
						float newEXP = exp - remove;
						p.setLevel(countdown);
						p.setExp(newEXP);						
						
					}
				}, 0, 20);
			}
		}
	}
	
}
