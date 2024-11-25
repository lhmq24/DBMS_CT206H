package model;

import controller.LogInController;
import controller.RegisterController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Host {
	private RegisterController registercontroller = null;
	private Connection conn;
	private LogInController logincontroller = null;
	
	public Host(Connection con, RegisterController control) {
		conn = con;
		registercontroller = control;
	}
	
	public Host(Connection con, LogInController control) {
		conn = con;
		logincontroller = control;
	}
	
	public String addUser(String name, String mail, String phonenumber) {
	    try {
	        String query = "INSERT INTO HOST (H_NAME, H_PHONENUMBER, H_MAIL, H_ID) VALUES (?, ?, ?, NULL);";
	        PreparedStatement pstmt = conn.prepareStatement(query);
	        pstmt.setString(1, name);
	        pstmt.setString(2, phonenumber);
	        pstmt.setString(3, mail);

	        int rowsAffected = pstmt.executeUpdate();
	        if (rowsAffected > 0) {
	            // Retrieve the last generated H_ID
	            String idQuery = "SELECT H_ID FROM HOST WHERE H_NAME = ? AND H_MAIL = ? AND H_PHONENUMBER = ? ORDER BY H_ID DESC LIMIT 1;";
	            PreparedStatement idStmt = conn.prepareStatement(idQuery);
	            idStmt.setString(1, name);
	            idStmt.setString(2, mail);
	            idStmt.setString(3, phonenumber);

	            ResultSet rs = idStmt.executeQuery();
	            if (rs.next()) {
	                return rs.getString("H_ID"); // Return the generated H_ID
	            }
	        }
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return null; // Return null if insertion or retrieval fails
	}

	
	public String getHostName(String id) {
		String query = "SELECT H_NAME FROM HOST WHERE H_ID = ?;";
		PreparedStatement stmtHost;
		try {
			stmtHost = conn.prepareStatement(query);
			stmtHost.setString(1, id);
			ResultSet rsHost = stmtHost.executeQuery();
			String name = "";
			if (rsHost.next()) {
			    name = rsHost.getString("H_NAME");	    
			}
			return name;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

}
