package de.benangelo.mysql;

import java.sql.ResultSet;
import java.util.concurrent.FutureTask;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import de.benangelo.main.Main;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.Callable;

public class MySQL {
	
	private static Main plugin;
	
    public static String host;

    public static String user;

    public static String password;

    public static String database;

    public static String port;

    public static Connection con;

    private static int MySQLSchedulerID;
    
    public static void setMySQLMain(Main m) {
    	plugin = m;
    }
    
    public static Connection getConnection() {
        return con;
    }

    public static void connect() {
        if (con == null)
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?user=" + user + "&password=" + password + "&autoReconnect=true");
            } catch (SQLException | ClassNotFoundException sQLException) {
            }
    }

    public static void close() {
        if (con != null)
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public static void update(final String qry, String args) {
        if (isConnected()) {
            (new FutureTask<>(new Runnable() {
                PreparedStatement ps;

                public void run() {
                    try {
                    	ps = con.prepareStatement(qry);
            			String[] arg = args.split(",");
            			for(int i = 0; i < arg.length; i++) {
            				ps.setString(i+1, arg[i]);
            			}
            			this.ps.executeUpdate();
                        this.ps.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }, Integer.valueOf(1))).run();
        } else {
            connect();
        }
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
			con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS " + "EC" + " (UUID varchar(1000), Playername varchar(1000), Content LONGTEXT)");
			
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

    public static File getMySQLFile() {
        return new File("plugins/BewerbungsPlugin", "MySQL.yml");
    }

    public static FileConfiguration getMySQLFileConfiguration() {
        return (FileConfiguration) YamlConfiguration.loadConfiguration(getMySQLFile());
    }

    public static void setStandardMySQL() {
        FileConfiguration cfg = getMySQLFileConfiguration();
        cfg.options().copyDefaults(true);
        cfg.addDefault("host", "localhost");
        cfg.addDefault("port", "3306");
        cfg.addDefault("database", "test");
        cfg.addDefault("username", "Benangelo");
        cfg.addDefault("password", "Luis"); 
   
        try {
            cfg.save(getMySQLFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isConnected() {
        return (con != null);
    }

    public static void readMySQL() {
        FileConfiguration cfg = getMySQLFileConfiguration();
        user = cfg.getString("username");
        password = cfg.getString("password");
        database = cfg.getString("database");
        host = cfg.getString("host");
        port = cfg.getString("port");
    }

    @SuppressWarnings("deprecation")
	public static void reconnectScheduler() {
        MySQLSchedulerID = Bukkit.getScheduler().scheduleAsyncRepeatingTask(plugin, () -> onReconnect(), 432000L, 432000L);
    }

    @SuppressWarnings("deprecation")
	public static void onReconnect() {
        if (con != null)
            try {
                con.close();
                System.out.println("[CoinsSQL] Disconnected from current backend!");
            } catch (SQLException exc) {
                System.out.println("[CoinsSQL] Could not disconnect from current backend!");
            }
        Bukkit.getScheduler().scheduleAsyncDelayedTask(plugin, () -> {
            try {
                con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?user=" + user + "&password=" + password + "&autoReconnect=true&useSSL=true");
                System.out.println("[CoinsSQL] MySQL verbunden");
            } catch (SQLException exc) {
                exc.printStackTrace();
                System.out.println("[CoinsSQL] Could not connect do default backend!");
            }
        }, 1L);
    }

    public static void disconnect() {
        if (isConnected())
            try {
                con.close();
                System.out.println("[MySQL] Disconnected!");
                if (Bukkit.getScheduler().isCurrentlyRunning(MySQLSchedulerID))
                    Bukkit.getScheduler().cancelTask(MySQLSchedulerID);
            } catch (SQLException exc) {
                exc.printStackTrace();
            }
    }

    public static ResultSet getResult(final String qry) {
        if (isConnected()) {
            try {
                FutureTask<ResultSet> task = new FutureTask<>(new Callable<ResultSet>() {
                    PreparedStatement ps;

                    public ResultSet call() throws Exception {
                        this.ps = MySQL.con.prepareStatement(qry);
                        return this.ps.executeQuery();
                    }
                });
                task.run();
                return task.get();
            } catch (InterruptedException | java.util.concurrent.ExecutionException e) {
                e.printStackTrace();
            }
        } else {
            connect();
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
