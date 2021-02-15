package de.benangelo.util;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilder {
	
	private ItemStack item;
	private ItemMeta itemMeta;
	
	@SuppressWarnings("deprecation")
	public ItemBuilder(Material material, short subID) {
		item = new ItemStack(material, 1, subID);
		itemMeta = item.getItemMeta();
	}
	
	public ItemBuilder(Material material) {
		this(material, (short)0);
	}
	
	public ItemBuilder setName(String name) {
		itemMeta.setDisplayName(name);
		return this;
	}
	
	public ItemBuilder setLore(String lore) {
		ArrayList<String> loresList = new ArrayList<>();
		String[] lores = lore.split(" ' ");
		for(int i = 0; i < lores.length; i++) {
			loresList.add(lores[i]);
		}
		itemMeta.setLore(loresList);
		return this;
	}
	
	
	
	public ItemBuilder setAmount(int amount) {
		item.setAmount(amount);
		return this;
	}
	
	@SuppressWarnings("deprecation")
	public ItemBuilder setDurability(short durability) {
		item.setDurability(durability);
		return this;
	}
	
	public ItemStack build() {
		item.setItemMeta(itemMeta);
		return item;
	}

	@SuppressWarnings("deprecation")
	public ItemBuilder setEnchantment(int entchantsmentsCount, String enchantments) {
		for(int i = 0; i < entchantsmentsCount; i = i + 2) {
			String[] enchantsSplittet = enchantments.split(":");
			itemMeta.addEnchant(Enchantment.getByName(enchantsSplittet[i]), Integer.valueOf(enchantsSplittet[i+1]), true);
		}
		return this;
	}
		
		@SuppressWarnings("deprecation")
		public ItemBuilder setEnchantmentBook(int entchantsmentsCount, String enchantments) {
			for(int i = 0; i < entchantsmentsCount; i = i + 2) {
				String[] enchantsSplittet = enchantments.split(":");
				EnchantmentStorageMeta meta = (EnchantmentStorageMeta) itemMeta;
				meta.addStoredEnchant(Enchantment.getByName(enchantsSplittet[i]), Integer.valueOf(enchantsSplittet[i+1]), true);
				itemMeta = meta;
			}
		
		return this;
		}

}
