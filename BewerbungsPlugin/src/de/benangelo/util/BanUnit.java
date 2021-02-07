package de.benangelo.util;

import java.util.ArrayList;
import java.util.List;

public enum BanUnit {

	SECOND("Sekunde(n)", 1, "sec"),
	MINUTE("Minute(n)", 60, "min"),
	HOUR("Stunde(n)", 60*60, "hours"),
	DAY("Tage(n)", 60*60*24, "day"),
	WEEK("Woche(n)", 60*60*24*7, "week"),
	MONTH("Monat(e)", 60*60*24*7*4, "month"),
	YEAR("Jahr(e)", 60*60*24*7*4*12, "year");
	
	private String name;
	private int toSeconds;
	private String shortcut;
	
	private BanUnit(String name, int toSeconds, String shortcut) {
		this.name = name;
		this.toSeconds = toSeconds;
		this.shortcut = shortcut;
	}
	
	public String getName() {
		return name;
	}
	
	public int getToSeconds() {
		return toSeconds;
	}
	
	public String getshortcut() {
		return shortcut;
	}
	
	public static List<String> getUnitsAsString() {
		List<String> units = new ArrayList<String>();
		for(BanUnit unit : BanUnit.values()) {
			units.add(unit.getshortcut().toLowerCase());
		}
		return units;
	}
	
	public static BanUnit getUnit(String unit) {
		for(BanUnit units : BanUnit.values()) {
			if(units.getshortcut().toLowerCase().equals(unit.toLowerCase())) {
				return units;
			}
		}
		return null;
	}
}
