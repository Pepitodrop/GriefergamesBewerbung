package de.benangelo.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class RecipesLoader {

	@SuppressWarnings("deprecation")
	public void registerRecipes() {
		ItemStack GoodBow = new ItemStack(Material.BOW);
		ItemMeta GoodBowMeta = GoodBow.getItemMeta();
		GoodBowMeta.setDisplayName("§1§lGuter Bogen!");
		GoodBowMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, false);
		GoodBowMeta.addEnchant(Enchantment.ARROW_FIRE, 1, false);
		GoodBowMeta.addEnchant(Enchantment.ARROW_KNOCKBACK, 1, false);
		GoodBowMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, false);
		GoodBow.setItemMeta(GoodBowMeta);
		ShapelessRecipe GoodBowRecipe = new ShapelessRecipe(GoodBow);
		GoodBowRecipe.addIngredient(Material.DIAMOND_ORE);
		GoodBowRecipe.addIngredient(Material.DIAMOND);
		GoodBowRecipe.addIngredient(Material.STICK);
		GoodBowRecipe.addIngredient(Material.GLOWSTONE);
		GoodBowRecipe.addIngredient(Material.GLOWSTONE_DUST);
		GoodBowRecipe.addIngredient(Material.IRON_BLOCK);
		GoodBowRecipe.addIngredient(Material.GOLD_BLOCK);
		
		ItemStack OPBow = new ItemStack(Material.BOW);
		ItemMeta OPBowMeta = OPBow.getItemMeta();
		OPBowMeta.setDisplayName("§c§lOP Bogen!");
		OPBowMeta.addEnchant(Enchantment.ARROW_DAMAGE, 3000, true);
		OPBowMeta.addEnchant(Enchantment.ARROW_FIRE, 3000, true);
		OPBowMeta.addEnchant(Enchantment.ARROW_KNOCKBACK, 3000, true);
		OPBowMeta.addEnchant(Enchantment.ARROW_INFINITE, 3000, true);
		OPBow.setItemMeta(OPBowMeta);
		
		ShapedRecipe OPBowRecipe = new ShapedRecipe(OPBow);
		OPBowRecipe.shape("DSF", "SGF", "ESF");
		OPBowRecipe.setIngredient('D', Material.DIAMOND_BLOCK);
		OPBowRecipe.setIngredient('S', Material.DIAMOND_SWORD);
		OPBowRecipe.setIngredient('F', Material.STRING);
		OPBowRecipe.setIngredient('G', Material.GOLD_BLOCK);
		OPBowRecipe.setIngredient('E', Material.EMERALD_BLOCK);
		
		Bukkit.addRecipe(GoodBowRecipe);
		Bukkit.addRecipe(OPBowRecipe);
	}
	
}
