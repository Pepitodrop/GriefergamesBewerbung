package de.benangelo.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Bukkit;

import com.mysql.jdbc.Statement;

public class MySQL {

	public static String host;
	public static String port;
	public static String database;
	public static String username;
	public static String password;
	public static Connection con;
	
	public static void connect() {
	if(!isConnected()) {
	try {
	con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
	Bukkit.getConsoleSender().sendMessage("§7[§6MySQL§7] §r§2Verbindung erfolgreich aufgebaut ist das nicht super!");
	} catch (SQLException e) {
	e.printStackTrace();
	}
	}
	}
	
	public static void disconnect() {
	if(isConnected()) {
	try {
	con.close();
	Bukkit.getConsoleSender().sendMessage("§7[§6MySQL§7] §r§4Verbindung erfolgreich beendet schade oder!");
	} catch (SQLException e) {
	e.printStackTrace();
	}
	}	
	}
	
	public static boolean isConnected() {
		return con != null;
	}
	
	public static Connection getConnection() {
		return con;
	}
	
	public static ResultSet query(final String qre) {
        if (MySQL.con != null) {
            ResultSet rs = null;
            try {
                final Statement st = (Statement)MySQL.con.createStatement();
                rs = st.executeQuery(qre);
            }
            catch (SQLException e) {
                MySQL.connect();
                System.err.print(e);

            }
            return rs;
        }
        return null;
    }
    
    public static void createChestTable(Integer i) {
		/*
		 * 
		 * Syntax: amout, durability, MaxStackSize, Type, DisplayName, localizedName, Slot
		 * 
		 */
		if(isConnected()) {
		try {
			System.out.println("[MySQL] Tabelle wird erstellt!");
			con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS `" + i + "` (amout varchar(1000), durability varchar(1000), Type varchar(1000), DisplayName varchar(1000), Slot varchar(1000))");
			System.out.println("[MySQL] Tabelle wurde erstellt!");
		} catch (SQLException e) {
			System.out.println("[MySQL] Fehler:"  + e.getMessage() + " und " + e.getSQLState());
			e.printStackTrace();
		}
		}
	}
    
//    public static void createBankTable() {
//		/*
//		 * 
//		 * Syntax: Player, UUID, Amout
//		 * 
//		 */
//		if(isConnected()) {
//		try {
//			System.out.println("[MySQL] Tabelle wird erstellt!");
//			con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS " + "Bank" + " (Player varchar(1000), UUID varchar(1000), Amout varchar(1000))");
//			System.out.println("[MySQL] Tabelle wurde erstellt!");
//		} catch (SQLException e) {
//			System.out.println("[MySQL] Fehler:"  + e.getMessage() + " und " + e.getSQLState());
//			e.printStackTrace();
//		}
//		}
//	}
//    
//    public static void createBanTable() {
//		/*
//		 * 
//		 * Syntax: Spieler, UUID, Ende, Grund
//		 * 
//		 */
//		if(isConnected()) {
//		try {
//			System.out.println("[MySQL] Tabelle wird erstellt!");
//			con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS " + "BannedPlayers" + " (Spieler varchar(1000), UUID varchar(1000), Ende varchar(1000), Grund varchar(1000))");
//			System.out.println("[MySQL] Tabelle wurde erstellt!");
//		} catch (SQLException e) {
//			System.out.println("[MySQL] Fehler:"  + e.getMessage() + " und " + e.getSQLState());
//			e.printStackTrace();
//		}
//		}
//	}
//    
//    public static void createPlayerTable() {
//		/*
//		 * 
//		 * Syntax: Spielername, UUID, Username
//		 * 
//		 */
//		if(isConnected()) {
//		try {
//			System.out.println("[MySQL] Tabelle wird erstellt!");
//			con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS Players (Spielername VARCHAR(1000), UUID VARCHAR(1000), TabelName VARCHAR(1000))");
//			System.out.println("[MySQL] Tabelle wurde erstellt!");
//		} catch (SQLException e) {
//			System.out.println("[MySQL] Fehler:"  + e.getMessage() + " und " + e.getSQLState());
//			e.printStackTrace();
//		}
//		}
//	}
//    
//    public static void createBlockLogTable() {
//		/*
//		 * 
//		 * Syntax: Material, Player, Position, Time, Day
//		 * 
//		 */
//		if(isConnected()) {
//		try {
//			System.out.println("[MySQL] Tabelle wird erstellt!");
//			con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS BlockLogDestroy (Material VARCHAR(1000), Player VARCHAR(1000), Position VARCHAR(1000), Time(1000), Day(1000)) ");
//			con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS BlockLogPlace (Material VARCHAR(1000), Player VARCHAR(1000), Position VARCHAR(1000), Time(1000), Day(1000)) ");
//			con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS BlockLogExplosed (Material VARCHAR(1000), Position VARCHAR(1000), Time(1000), Day(1000)) ");
//			System.out.println("[MySQL] Tabelle wurde erstellt!");
//		} catch (SQLException e) {
//			System.out.println("[MySQL] Fehler:"  + e.getMessage() + " und " + e.getSQLState());
//			e.printStackTrace();
//		}
//		}
//	}
    
    public static void createTables() {
		/*
		 * 
		 * Syntax: Playername, UUID, Anzahl
		 * 
		 */
		if(isConnected()) {
		try {
			System.out.println("[MySQL] Tabellen werden erstellt!");
			con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS " + "Bank" + " (Player varchar(1000), UUID varchar(1000), Amout varchar(1000))");
			
			con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS " + "BannedPlayers" + " (Spieler varchar(1000), UUID varchar(1000), Ende varchar(1000), Grund varchar(1000))");
			
			con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS Players (Spielername VARCHAR(1000), UUID VARCHAR(1000), TabelName VARCHAR(1000))");
			
			con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS BlockLogDestroy (Material VARCHAR(1000), Player VARCHAR(1000), Position VARCHAR(1000), Time VARCHAR(1000), Day VARCHAR(1000))");
			con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS BlockLogPlace (Material VARCHAR(1000), Player VARCHAR(1000), Position VARCHAR(1000), Time VARCHAR(1000), Day VARCHAR(1000))");
			con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS CommandLog (Playername VARCHAR(1000), UUID VARCHAR(1000), Command VARCHAR(1000), Time VARCHAR(1000), Day VARCHAR(1000))");			
			con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS ChatLog (Playername VARCHAR(1000), UUID VARCHAR(1000), Message VARCHAR(1000), Time VARCHAR(1000), Day VARCHAR(1000))");
			System.out.println("[MySQL] Tabellen wurden erstellt!");
		} catch (SQLException e) {
			System.out.println("[MySQL] Fehler:"  + e.getMessage() + " und " + e.getSQLState());
			e.printStackTrace();
		}
		}
	}
    
    public static void update(String qry) {
    	if(isConnected()) {
    		try {
				con.createStatement().executeUpdate(qry);
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    }
    
    public static ResultSet getResult(String qry) {
    	if(isConnected()) {
    		try {
				return con.createStatement().executeQuery(qry);
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
		return null;
    }
    
    public static boolean UserExistsPlayer(UUID uuid) {
        ResultSet rs = MySQL.getResult("SELECT * FROM " + "Players" + " WHERE UUID= '" + uuid + "'");
        try {
 		while(rs.next()) {
 			   return rs != null;
 		   }
 	} catch (SQLException e) {
 		e.printStackTrace();
 	}
 	return false;
     }
    
    public static boolean setTname(Integer TabelName) {
        ResultSet rs = MySQL.getResult("SELECT * FROM " + "Players" + " WHERE TabelName= '" + TabelName + "'");
        try {
 		while(rs.next()) {
 			   return rs != null;
 		   }
 	} catch (SQLException e) {
 		e.printStackTrace();
 	}
 	return false;
     }
    
    public static String getTName(UUID uuid) {
        String i = "";
        try {
            final ResultSet rs = MySQL.query("SELECT * FROM " + "Players" + " WHERE UUID= '" + uuid + "'");
            if (!rs.next() || String.valueOf(rs.getString("TabelName")) == null) {}
            i = rs.getString("TabelName");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }
     
     public static String getItem(Integer slot, String TName) {
         String i = "";
         try {
             final ResultSet rs = MySQL.query("SELECT * FROM `" + TName + "` WHERE Slot= '" + slot + "'");
             if (!rs.next() || String.valueOf(rs.getString("Type")) == null) {}
             i = rs.getString("Type");
         }
         catch (SQLException e) {
             e.printStackTrace();
         }
         return i;
     }
     
     public static String getAmout(Integer slot, String TName) {
         String i = "";
         try {
             final ResultSet rs = MySQL.query("SELECT * FROM `" + TName + "` WHERE Slot= '" + slot + "'");
             if (!rs.next() || String.valueOf(rs.getString("amout")) == null) {}
             i = rs.getString("amout");
         }
         catch (SQLException e) {
             e.printStackTrace();
         }
         return i;
     }
     
     public static int getSlot(String item, String TName) {
         int i = 0;
         try {
             final ResultSet rs = MySQL.query("SELECT * FROM `" + TName + "` WHERE Item= '" + item + "'");
             if (!rs.next() || String.valueOf(rs.getString("Slot")) == null) {}
             i = rs.getInt("Slot");
         }
         catch (SQLException e) {
             e.printStackTrace();
         }
         return i;
     }
     
     public static String getdurability(Integer slot, String TName) {
         String i = "";
         try {
             final ResultSet rs = MySQL.query("SELECT * FROM `" + TName + "` WHERE Slot= '" + slot + "'");
             if (!rs.next() || String.valueOf(rs.getString("durability")) == null) {}
             i = rs.getString("durability");
         }
         catch (SQLException e) {
             e.printStackTrace();
         }
         return i;
     }
     
     public static String getDisplayName(Integer slot, String TName) {
         String i = "";
         try {
             final ResultSet rs = MySQL.query("SELECT * FROM `" + TName + "` WHERE Slot= '" + slot + "'");
             if (!rs.next() || String.valueOf(rs.getString("DisplayName")) == null) {}
             i = rs.getString("DisplayName");
         }
         catch (SQLException e) {
             e.printStackTrace();
         }
         return i;
     }
     
     
     
     
     
     public static boolean UserExistsBank(UUID uuid) {
         ResultSet rs = MySQL.getResult("SELECT * FROM " + "Bank" + " WHERE UUID= '" + uuid + "'");
         try {
  		while(rs.next()) {
  			   return rs != null;
  		   }
  	} catch (SQLException e) {
  		e.printStackTrace();
  	}
  	return false;
      }
     
     public static String getAmoutBank(UUID uuid) {
         String i = "";
         try {
             final ResultSet rs = MySQL.query("SELECT * FROM " + "Bank" + " WHERE UUID= '" + uuid + "'");
             if (!rs.next() || String.valueOf(rs.getString("Amout")) == null) {}
             i = rs.getString("Amout");
         }
         catch (SQLException e) {
             e.printStackTrace();
         }
         return i;
     }
     
     
     
     
     
     /*
		 * 
		 * Syntax: Spieler, UUID, Ende, Grund
		 * 
		 */
     public static boolean userisBanned(UUID uuid) {
         ResultSet rs = MySQL.getResult("SELECT * FROM " + "BannedPlayers" + " WHERE UUID= '" + uuid + "'");
         try {
  		while(rs.next()) {
  			   return rs != null;
  		   }
  	} catch (SQLException e) {
  		e.printStackTrace();
  	}
  	return false;
      }
     
     public static String getPlayer(UUID uuid) {
         String i = "";
         try {
             final ResultSet rs = MySQL.query("SELECT * FROM " + "BannedPlayers" + " WHERE UUID= '" + uuid + "'");
             if (!rs.next() || String.valueOf(rs.getString("Spieler")) == null) {}
             i = rs.getString("Spieler");
         }
         catch (SQLException e) {
             e.printStackTrace();
         }
         return i;
     }
     
     public static String getUUID(String spieler) {
         String i = "";
         try {
             final ResultSet rs = MySQL.query("SELECT * FROM " + "BannedPlayers" + " WHERE Spieler= '" + spieler + "'");
             if (!rs.next() || String.valueOf(rs.getString("UUID")) == null) {}
             i = rs.getString("UUID");
         }
         catch (SQLException e) {
             e.printStackTrace();
         }
         return i;
     }
     
     public static long getEnde(UUID uuid) {
    	 long i = 0;
         try {
             final ResultSet rs = MySQL.query("SELECT * FROM " + "BannedPlayers" + " WHERE UUID= '" + uuid + "'");
             if (!rs.next() || String.valueOf(rs.getString("Ende")) == null) {}
             i = rs.getLong("Ende");
         }
         catch (SQLException e) {
             e.printStackTrace();
         }
         return i;
     }
     
     public static String getReason(UUID uuid) {
         String i = "";
         try {
             final ResultSet rs = MySQL.query("SELECT * FROM " + "BannedPlayers" + " WHERE UUID= '" + uuid + "'");
             if (!rs.next() || String.valueOf(rs.getString("Grund")) == null) {}
             i = rs.getString("Grund");
         }
         catch (SQLException e) {
             e.printStackTrace();
         }
         return i;
     }   
}
