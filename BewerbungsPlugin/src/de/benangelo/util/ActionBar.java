package de.benangelo.util;

import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_16_R3.ChatMessageType;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;
import net.minecraft.server.v1_16_R3.PacketPlayOutChat;

public class ActionBar {
	
	public void sendActionBar(Player p, String message) {
		actionBar(p, message);
	}
	
	private void actionBar(Player p, String message) {
		PacketPlayOutChat packet = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}"), ChatMessageType.a((byte) 2), p.getUniqueId());
		((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
		}
	
}
