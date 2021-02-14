package de.benangelo.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
			con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS `" + i + "` (amout varchar(1000), durability varchar(1000), Type varchar(1000), DisplayName varchar(1000), Slot varchar(1000), Lore varchar(1000), Enchants varchar(1000))");
			System.out.println("[MySQL] Tabelle wurde erstellt!");
		} catch (SQLException e) {
			System.out.println("[MySQL] Fehler:"  + e.getMessage() + " und " + e.getSQLState());
			e.printStackTrace();
		}
		}
	}
    
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
    
    public static void update(String qry, String args) {
    	if(isConnected()) {
    		PreparedStatement ps;
    		try {
    			ps = con.prepareStatement(qry);
    			String[] arg = args.split(",");
    			for(int i = 0; i < arg.length; i++) {
    				ps.setString(i+1, arg[i]);
    			}
				ps.executeUpdate();
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
    	PreparedStatement ps;
		try {
			ps = con.prepareStatement("SELECT * FROM " + "Players" + " WHERE UUID=?");
			ps.setString(1, uuid.toString());
	        ResultSet rs = ps.executeQuery();
	 		while(rs.next()) {
	 			   return rs != null;
	 		   }
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
 	return false;
     }
    
    public static boolean setTname(Integer TabelName) {
    	PreparedStatement ps;
        try {
        ps = con.prepareStatement("SELECT * FROM " + "Players" + " WHERE TabelName=?");
        ps.setString(1, TabelName.toString());
        ResultSet rs = ps.executeQuery();
 		while(rs.next()) {
 			   return rs != null;
 		   }
 	} catch (SQLException e) {
 		e.printStackTrace();
 	}
 	return false;
     }
    
    public static String getTName(UUID uuid) {
    	PreparedStatement ps;
        String i = "";
        try {
            ps = con.prepareStatement("SELECT * FROM " + "Players" + " WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            if (!rs.next() || String.valueOf(rs.getString("TabelName")) == null) {}
            i = rs.getString("TabelName");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }
     
     public static String getItem(Integer slot, String TName) {
    	 PreparedStatement ps;
         String i = "";
         try {
             ps = con.prepareStatement("SELECT * FROM `" + TName + "` WHERE Slot=?");
             ps.setString(1, slot.toString());
             ResultSet rs = ps.executeQuery();
             if (!rs.next() || String.valueOf(rs.getString("Type")) == null) {}
             i = rs.getString("Type");
         }
         catch (SQLException e) {
             e.printStackTrace();
         }
         return i;
     }
     
     public static String getAmout(Integer slot, String TName) {
    	 PreparedStatement ps;
         String i = "";
         try {
        	 ps = con.prepareStatement("SELECT * FROM `" + TName + "` WHERE Slot=?");
             ps.setString(1, slot.toString());
             ResultSet rs = ps.executeQuery();
             if (!rs.next() || String.valueOf(rs.getString("amout")) == null) {}
             i = rs.getString("amout");
         }
         catch (SQLException e) {
             e.printStackTrace();
         }
         return i;
     }
     
     public static int getSlot(String item, String TName) {
    	 PreparedStatement ps;
         int i = 0;
         try {
        	 ps = con.prepareStatement("SELECT * FROM `" + TName + "` WHERE Item=?");
             ps.setString(1, item);
             ResultSet rs = ps.executeQuery();
             if (!rs.next() || String.valueOf(rs.getString("Slot")) == null) {}
             i = rs.getInt("Slot");
         }
         catch (SQLException e) {
             e.printStackTrace();
         }
         return i;
     }
     
     public static String getdurability(Integer slot, String TName) {
    	 PreparedStatement ps;
         String i = "";
         try {
        	 ps = con.prepareStatement("SELECT * FROM `" + TName + "` WHERE Slot=?");
             ps.setString(1, slot.toString());
             ResultSet rs = ps.executeQuery();
             if (!rs.next() || String.valueOf(rs.getString("durability")) == null) {}
             i = rs.getString("durability");
         }
         catch (SQLException e) {
             e.printStackTrace();
         }
         return i;
     }
     
     public static String getDisplayName(Integer slot, String TName) {
    	 PreparedStatement ps;
         String i = "";
         try {
        	 ps = con.prepareStatement("SELECT * FROM `" + TName + "` WHERE Slot=?");
             ps.setString(1, slot.toString());
             ResultSet rs = ps.executeQuery();
             if (!rs.next() || String.valueOf(rs.getString("DisplayName")) == null) {}
             i = rs.getString("DisplayName");
         }
         catch (SQLException e) {
             e.printStackTrace();
         }
         return i;
     }
     
     public static String getLore(Integer slot, String TName) {
    	 PreparedStatement ps;
         String i = "";
         try {
        	 ps = con.prepareStatement("SELECT * FROM `" + TName + "` WHERE Slot=?");
             ps.setString(1, slot.toString());
             ResultSet rs = ps.executeQuery();
             if (!rs.next() || String.valueOf(rs.getString("Lore")) == null) {}
             i = rs.getString("Lore");
         }
         catch (SQLException e) {
             e.printStackTrace();
         }
         return i;
     }
     
     public static String getEnchants(Integer slot, String TName) {
    	 PreparedStatement ps;
         String i = "";
         try {
        	 ps = con.prepareStatement("SELECT * FROM `" + TName + "` WHERE Slot=?");
             ps.setString(1, slot.toString());
             ResultSet rs = ps.executeQuery();
             if (!rs.next() || String.valueOf(rs.getString("Enchants")) == null) {}
             i = rs.getString("Enchants");
         }
         catch (SQLException e) {
             e.printStackTrace();
         }
         return i;
     }
     
     
     
     
     
     public static boolean UserExistsBank(UUID uuid) {
    	 PreparedStatement ps;
         try {
        	 ps = con.prepareStatement("SELECT * FROM " + "Bank" + " WHERE UUID=?");
             ps.setString(1, uuid.toString());
             ResultSet rs = ps.executeQuery();
  		while(rs.next()) {
  			   return rs != null;
  		   }
  	} catch (SQLException e) {
  		e.printStackTrace();
  	}
  	return false;
      }
     
     public static String getAmoutBank(UUID uuid) {
    	 PreparedStatement ps;
         String i = "";
         try {
        	 ps = con.prepareStatement("SELECT * FROM " + "Bank" + " WHERE UUID=?");
             ps.setString(1, uuid.toString());
             ResultSet rs = ps.executeQuery();
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
    	 PreparedStatement ps;
         try {
        	 ps = con.prepareStatement("SELECT * FROM " + "BannedPlayers" + " WHERE UUID=?");
             ps.setString(1, uuid.toString());
             ResultSet rs = ps.executeQuery();
  		while(rs.next()) {
  			   return rs != null;
  		   }
  	} catch (SQLException e) {
  		e.printStackTrace();
  	}
  	return false;
      }
     
     public static String getPlayer(UUID uuid) {
    	 PreparedStatement ps;
         String i = "";
         try {
        	 ps = con.prepareStatement("SELECT * FROM " + "BannedPlayers" + " WHERE UUID=?");
             ps.setString(1, uuid.toString());
             ResultSet rs = ps.executeQuery();
             if (!rs.next() || String.valueOf(rs.getString("Spieler")) == null) {}
             i = rs.getString("Spieler");
         }
         catch (SQLException e) {
             e.printStackTrace();
         }
         return i;
     }
     
     public static String getUUID(String spieler) {
    	 PreparedStatement ps;
         String i = "";
         try {
             ps = con.prepareStatement("SELECT * FROM " + "BannedPlayers" + " WHERE Spieler=?");
             ps.setString(1, spieler);
             ResultSet rs = ps.executeQuery();
             if (!rs.next() || String.valueOf(rs.getString("UUID")) == null) {}
             i = rs.getString("UUID");
         }
         catch (SQLException e) {
             e.printStackTrace();
         }
         return i;
     }
     
     public static long getEnde(UUID uuid) {
    	 PreparedStatement ps;
    	 long i = 0;
         try {
        	 ps = con.prepareStatement("SELECT * FROM " + "BannedPlayers" + " WHERE UUID=?");
             ps.setString(1, uuid.toString());
             ResultSet rs = ps.executeQuery();
             if (!rs.next() || String.valueOf(rs.getString("Ende")) == null) {}
             i = rs.getLong("Ende");
         }
         catch (SQLException e) {
             e.printStackTrace();
         }
         return i;
     }
     
     public static String getReason(UUID uuid) {
    	 PreparedStatement ps;
         String i = "";
         try {
        	 ps = con.prepareStatement("SELECT * FROM " + "BannedPlayers" + " WHERE UUID=?");
             ps.setString(1, uuid.toString());
             ResultSet rs = ps.executeQuery();
             if (!rs.next() || String.valueOf(rs.getString("Grund")) == null) {}
             i = rs.getString("Grund");
         }
         catch (SQLException e) {
             e.printStackTrace();
         }
         return i;
     }   
}
