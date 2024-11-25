package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import controller.ForgotPasswordController;
import controller.LogInController;
import controller.RegisterController;

public class Donor_Account {
	private LogInController lgcontroller;
	private ForgotPasswordController fgcontroller;
	private RegisterController rgcontroller;
	private Connection conn;
	
	public Donor_Account(LogInController ctl, Connection con) {
		lgcontroller = ctl;
		conn = con;
	}
	
	public Donor_Account(ForgotPasswordController ctl, Connection con) {
		fgcontroller = ctl;
		conn = con;
	}
	
	public Donor_Account(RegisterController con, Connection c) {
		rgcontroller = con;
		conn = c;
	}

	public boolean verify(String username, String password) {
		//verify user account in db for login (test redirect to forgot password)
		try {
			PreparedStatement stmt = null;
			ResultSet rs = null;
			stmt = conn.prepareStatement("Select * from donor_account Where d_username = ? and d_password = ? ;");
			stmt.setString(1, username);
			stmt.setString(2, password);
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
		//verify donor account in db for forgot password
		String password = "";
		try {
			PreparedStatement stmt = null;
			ResultSet rs = null;
			stmt = conn.prepareStatement("Select D_Password from donor_account Where D_Username = ? and D_RecoveryQuestion = ? and D_RecoveryAnswer = ? ;");
			stmt.setString(1, username);
			stmt.setString(2, question);
			stmt.setString(3, answer);
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
	
	public boolean addDonorAccount(String id, String username, String password, String question, String answer) {
		try {
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO DONOR_ACCOUNT (D_ID, D_USERNAME, D_PASSWORD, D_RecoveryQuestion, D_RecoveryAnswer) VALUES (?,?,?,?,?);");
			pstmt.setString(1, id);
			pstmt.setString(2, username);
			pstmt.setString(3, password);
			pstmt.setString(4, question);
			pstmt.setString(5, answer);
			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected > 0;  
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	public String getDonorId(String username) {
	    try {
	        String query = "SELECT d_id FROM donor_account WHERE d_username = ?";
	        PreparedStatement stmt = conn.prepareStatement(query);
	        stmt.setString(1, username);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            return rs.getString("d_id");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null; 
	}

}
