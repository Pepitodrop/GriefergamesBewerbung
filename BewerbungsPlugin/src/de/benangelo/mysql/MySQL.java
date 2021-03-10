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
    
    public static void createTables() {
		/*
		 * 
		 * Syntax: UUID, Playername, Content
		 * 
		 */
		if(isConnected()) {
		try {
			System.out.println("[MySQL] Tabellen werden erstellt!");
			con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS " + "EC" + " (UUID varchar(1000), Playername varchar(1000), Content varchar(50000))");
			
			con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS " + "Bank" + " (Player varchar(1000), UUID varchar(1000), Amout varchar(1000))");
			
			con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS " + "BannedPlayers" + " (Spieler varchar(1000), UUID varchar(1000), Ende varchar(1000), Grund varchar(1000))");
			
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
    
    
    
    
    public static boolean UserExistsEC(UUID uuid) {
    	PreparedStatement ps;
		try {
			ps = con.prepareStatement("SELECT * FROM " + "EC" + " WHERE UUID=?");
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
    
    public static String getNameEC(UUID uuid) {
   	 PreparedStatement ps;
        String i = "";
        try {
       	 ps = con.prepareStatement("SELECT * FROM " + "EC" + " WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            if (!rs.next() || String.valueOf(rs.getString("Playername")) == null) {}
            i = rs.getString("Playername");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }
    
    public static String getInventoryContent(UUID uuid) {
   	 PreparedStatement ps;
        String i = "";
        try {
       	 ps = con.prepareStatement("SELECT * FROM " + "EC" + " WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            if (!rs.next() || String.valueOf(rs.getString("Content")) == null) {}
            i = rs.getString("Content");
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
     
     
     public static boolean UserExists(UUID uuid) {
     	PreparedStatement ps;
 		try {
 			ps = con.prepareStatement("SELECT * FROM " + "OnlineTime" + " WHERE UUID=?");
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
     
     public static String getPlayerName(UUID uuid) {
     	PreparedStatement ps;
         String i = "";
         try {
             ps = con.prepareStatement("SELECT * FROM " + "OnlineTime" + " WHERE UUID=?");
             ps.setString(1, uuid.toString());
             ResultSet rs = ps.executeQuery();
             if (!rs.next() || String.valueOf(rs.getString("Playername")) == null) {}
             i = rs.getString("Playername");
         }
         catch (SQLException e) {
             e.printStackTrace();
         }
         return i;
     }
      
     public static String getTime(UUID uuid) {
     	PreparedStatement ps;
         String i = "";
         try {
             ps = con.prepareStatement("SELECT * FROM " + "OnlineTime" + " WHERE UUID=?");
             ps.setString(1, uuid.toString());
             ResultSet rs = ps.executeQuery();
             if (!rs.next() || String.valueOf(rs.getString("Time")) == null) {}
             i = rs.getString("Time");
         }
         catch (SQLException e) {
             e.printStackTrace();
         }
         return i;
     }
     
      public static long getStart(UUID uuid) {
     	 PreparedStatement ps;
          long i = 0;
          try {
              ps = con.prepareStatement("SELECT * FROM " + "OnlineTime" + " WHERE UUID=?");
              ps.setString(1, uuid.toString());
              ResultSet rs = ps.executeQuery();
              if (!rs.next() || String.valueOf(rs.getString("Start")) == null) {}
              i = rs.getLong("Start");
          }
          catch (SQLException e) {
              e.printStackTrace();
          }
          return i;
      }
      
      public static long getJoins(UUID uuid) {
     	 PreparedStatement ps;
          long i = 0;
          try {
              ps = con.prepareStatement("SELECT * FROM " + "OnlineTime" + " WHERE UUID=?");
              ps.setString(1, uuid.toString());
              ResultSet rs = ps.executeQuery();
              if (!rs.next() || String.valueOf(rs.getString("Joins")) == null) {}
              i = rs.getLong("Joins");
          }
          catch (SQLException e) {
              e.printStackTrace();
          }
          return i;
      }
}
