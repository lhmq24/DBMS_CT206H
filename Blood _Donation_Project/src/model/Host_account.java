package model;

import controller.ForgotPasswordController;
import controller.LogInController;
import controller.RegisterController;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import java.sql.CallableStatement;
import java.sql.Connection;

public class Host_Account {
	private LogInController lgcontroller;
	private ForgotPasswordController fgcontroller;
	private RegisterController rgcontroller;
	private Connection conn;
	
	public Host_Account(LogInController ctl, Connection con) {
		lgcontroller = ctl;
		conn = con;
	}
	
	public Host_Account(ForgotPasswordController ctl, Connection con) {
		fgcontroller = ctl;
		conn = con;
	}
	
	public Host_Account(RegisterController ctl, Connection con) {
		rgcontroller = ctl;
		conn = con;
	}

	public boolean verify(String username, String password) {
		//verify host account in db for log in
		try {
			PreparedStatement stmt = null;
			ResultSet rs = null;
			stmt = conn.prepareStatement("Select * from host_account Where H_Username = ? and H_Password = ? ;");
			stmt.setNString(1, username);
			stmt.setNString(2, password);
			rs = stmt.executeQuery();
			if(rs.next()) {
				return true;
			}
			else {
				JOptionPane.showMessageDialog(null, "No matching record found.");
				return false;
			}
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,e.getMessage());
			e.printStackTrace();
		}
		
		return false;
	}
	
	public String verify(String username, String question, String answer) {
		//verify host account in db for forgot password
		String password = "";
		try {
			PreparedStatement stmt = null;
			ResultSet rs = null;
			stmt = conn.prepareStatement("Select H_Password from host_account Where H_Username = ? and H_RecoveryQuestion = ? and H_RecoveryAnswer = ? ;");
			stmt.setNString(1, username);
			stmt.setNString(2, question);
			stmt.setNString(3, answer);
			rs = stmt.executeQuery();
			
			if(rs.next()) {
				password =  rs.getNString(1);
				return password;
			}
			else {
				JOptionPane.showMessageDialog(null, "No matching record found.");
				return password;
			}
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,e.getMessage());
			e.printStackTrace();
		}
		
		return password;
	}
	
	public boolean addHostAccount(String id, String username, String password, String question, String answer) {
		try {
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO HOST_ACCOUNT (H_ID, H_USERNAME, H_PASSWORD, H_RecoveryQuestion, H_RecoveryAnswer) VALUES (?,?,?,?,?);");
			pstmt.setNString(1, id);
			pstmt.setNString(2, username);
			pstmt.setNString(3, password);
			pstmt.setNString(4,question);
			pstmt.setNString(5, answer);
			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected > 0;  // Return true if account was added successfully
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	public String getHostId(String username) {
	    try {
	        String query = "SELECT h_id FROM host_account WHERE h_username = ?";
	        PreparedStatement stmt = conn.prepareStatement(query);
	        stmt.setString(1, username);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            return rs.getString("h_id");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null; // Return null if not found
	}

}
