package no.hvl.dat152.obl4.database;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;

import org.apache.commons.codec.digest.DigestUtils;

public class AppUserDAO {

public AppUser getAuthenticatedUser(String username, String password) {

    String hashedPassword = generatePassHash(password);

   /*
    PreparedStatement sql = connection.prepareStatement("SELECT * FROM users WHERE username=? AND passhash=?");
    sql.setString(1, username);
    sql.setString(2, hashedPassword);
    //ResultSet r = sql.executeQuery();
   
     */
    
    /*
    String sql = "SELECT * FROM SecOblig.AppUser" 
        + " WHERE username = '" + username + "'"
        + " AND passhash = '" + hashedPassword + "'";
    */
    
    String sql = "SELECT * FROM secOblig.AppUser WHERE username=? AND passhash=?";
    
    
    AppUser user = null;

    Connection c = null;
    PreparedStatement s = null;
    ResultSet r = null;
   

    try {        
      c = DatabaseHelper.getConnection();
      
      s = c.prepareStatement(sql);
      s.setString(1, username);
      s.setString(2, hashedPassword);
      
      r = s.executeQuery();

      if (r.next()) {
        user = new AppUser(
            r.getString("username"),
            r.getString("passhash"),
            r.getString("firstname"),
            r.getString("lastname"),
            r.getString("mobilephone"),
            r.getString("role")
            );
      }

    } catch (Exception e) {
      System.out.println(e);
    } finally {
      DatabaseHelper.closeConnection(r, s, c);
    }

    return user;
  }

  public boolean saveUser(AppUser user) {
	  
	  /* Before
      String sql = "INSERT INTO SecOblig.AppUser VALUES ("
              + "'" + user.getUsername() + "', "
              + "'" + user.getPasshash() + "', "
              + "'" + user.getFirstname() + "', "
              + "'" + user.getLastname() + "', "
              + "'" + user.getMobilephone() + "', "
              + "'" + user.getRole() + "')";
      */
	  
	  
	  

    String sql = "INSERT INTO SecOblig.AppUser VALUES (?,?,?,?,?,?)";
        

    Connection c = null;
    PreparedStatement s = null;
    ResultSet r = null;

    try {        
      c = DatabaseHelper.getConnection();
      s = c.prepareStatement(sql);
      s.setString(1, user.getUsername());
      s.setString(2, user.getPasshash());
      s.setString(3, user.getFirstname());
      s.setString(4, user.getLastname());
      s.setString(5, user.getMobilephone());
      s.setString(6, user.getRole());
      
      
      int row = s.executeUpdate(sql);
      if(row >= 0)
    	  return true;
    } catch (Exception e) {
    	System.out.println(e);
    	return false;
    } finally {
      DatabaseHelper.closeConnection(r, s, c);
    }
    
    return false;
  }
  
  public List<String> getUsernames() {
	  
	  List<String> usernames = new ArrayList<String>();
	  
	  String sql = "SELECT username FROM SecOblig.AppUser";

		    Connection c = null;
		    Statement s = null;
		    ResultSet r = null;

		    try {        
		      c = DatabaseHelper.getConnection();
		      s = c.createStatement();       
		      r = s.executeQuery(sql);

		      while (r.next()) {
		    	  usernames.add(r.getString("username"));
		      }

		    } catch (Exception e) {
		      System.out.println(e);
		    } finally {
		      DatabaseHelper.closeConnection(r, s, c);
		    }
	  
	  return usernames;
  }
  
  public boolean updateUserPassword(String username, String passwordnew) {
	  
	  String hashedPassword = generatePassHash(passwordnew);
	  
	  /* Before
	    String sql = "UPDATE SecOblig.AppUser "
	    		+ "SET passhash = '" + hashedPassword + "' "
	    				+ "WHERE username = '" + username + "'";
	    				
	    				*/
	  String sql = "UPDATE SecOblig.AppUser SET passhash=? WHERE username=?";
	  
	
	    Connection c = null;
	    PreparedStatement s = null;
	    ResultSet r = null;
	
	    try {        
	      c = DatabaseHelper.getConnection();
	      s = c.prepareStatement(sql); 
	      s.setString(1, hashedPassword);
	      s.setString(2, username);
	      int row = s.executeUpdate(sql);
	      System.out.println("Password update successful for "+username);
	      if(row >= 0)
	    	  return true;
	      
	    } catch (Exception e) {
	      System.out.println(e);
	      return false;
	    } finally {
	      DatabaseHelper.closeConnection(r, s, c);
	    }
	    
	    return false;
  }
  
  public boolean updateUserRole(String username, String role) {

	  /* Before
	    String sql = "UPDATE SecOblig.AppUser "
	    		+ "SET role = '" + role + "' "
	    				+ "WHERE username = '" + username + "'";
	    				
	    				*/
	  String sql = "Update SecOblig.AppUser Set role=? WHERE username=?";
	
	    Connection c = null;
	    PreparedStatement s = null;
	    ResultSet r = null;
	
	    try {        
	      c = DatabaseHelper.getConnection();
	      s = c.prepareStatement(sql);
	      s.setString(1, role);
	      s.setString(2, username);
	      
	      int row = s.executeUpdate(sql);
	      System.out.println("Role update successful for "+username+" New role = "+role);
	      if(row >= 0)
	    	  return true;
	      
	    } catch (Exception e) {
	      System.out.println(e);
	      return false;
	    } finally {
	      DatabaseHelper.closeConnection(r, s, c);
	    }
	    return false;
  }
  
  public String generatePassHash(String password) {
    return DigestUtils.md5Hex(password);
  }
  
  public String generateCSRFToken() {
	  SecureRandom sr = new SecureRandom();
	  byte[] csrf = new byte[16];
	  sr.nextBytes(csrf);
	  return DigestUtils.md5Hex(csrf);
  }
  

}

