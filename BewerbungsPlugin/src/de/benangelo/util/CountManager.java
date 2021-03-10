package de.benangelo.util;

import java.util.UUID;

import de.benangelo.mysql.MySQL;

public class CountManager {

	public static String getOnlineZeitString(UUID uuid) {
		if(MySQL.UserExists(uuid)) {
			long time = System.currentTimeMillis() - MySQL.getStart(uuid);
			long seconds = 0;
			long minutes = 0;
			long hours = 0;
			long days = 0;
			long weeks = 0;
			long months = 0;
			long years = 0;
			
			while(time > 1000) {
				time-=1000;
				seconds++;
			}
			while(seconds > 60) {
				seconds-=60;
				minutes++;
			}
			while(minutes > 60) {
				minutes-=60;
				hours++;
			}
			while(hours > 24) {
				hours-=24;
				days++;
			}
			while(days > 7) {
				days-=7;
				weeks++;
			}
			while(weeks > 4) {
				weeks-=4;
				months++;
			}
			while(months > 12) {
				months-=12;
				years++;
			}
			
			if(!(MySQL.getTime(uuid).equalsIgnoreCase(String.valueOf(0)))) {
				String[] timeStringSplit =  MySQL.getTime(uuid).split(",");
				years = Integer.valueOf(timeStringSplit[0]) + years;
				months = Integer.valueOf(timeStringSplit[1]) + months;
				weeks = Integer.valueOf(timeStringSplit[2]) + weeks;
				days = Integer.valueOf(timeStringSplit[3]) + days;
				hours = Integer.valueOf(timeStringSplit[4]) + hours;
				minutes = Integer.valueOf(timeStringSplit[5]) + minutes;
				seconds = Integer.valueOf(timeStringSplit[6]) + seconds;
			}
			
			return String.valueOf(years) + "," + String.valueOf(months) + "," + String.valueOf(weeks) + "," + String.valueOf(days) + "," + String.valueOf(hours) + "," + String.valueOf(minutes) + "," + String.valueOf(seconds);
		} else
			return "Nicht in der Datenbank!";
		
	}
	
	public static long getTimeType(UUID uuid, int type) {
		
		if(MySQL.UserExists(uuid)) {
			int time;
			
			if(!(MySQL.getTime(uuid).equalsIgnoreCase(String.valueOf(0)))) {
				String[] timeStringSplit =  MySQL.getTime(uuid).split(",");
				time = Integer.valueOf(timeStringSplit[type]);
			} else {
				time = 0;
			}
			
			return time;
		}
		
		return 0;
	}
	
}
