
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

public enum Achievments {

	FIRSTJOIN("FirstJoin","Du bist das erste mal hier drauf!",25),
	FIRSTMOVE("FirstMove","Du kannst ja laufen!",40),
	FIRSTCHAT("FirstChat","Du hast das Schreiben gelernt!",15);
	
	String name;
	String text;
	int money;
	
	private Achievments(String name, String text, int money) {
		this.name = name;
		this.text = text;
		this.money = money;
	}
	
	public String getName() {
		return name;
	}
	
	public String getText() {
		return text;
	}
	
	public int getMoney() {
		return money;
	}
	
}
