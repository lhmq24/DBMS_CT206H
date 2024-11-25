package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import controller.LogInController;
import controller.RegisterController;


public class Donor {
	
	private RegisterController registercontroller = null;
	private Connection conn;
	private LogInController logincontroller = null;
	
	public Donor(Connection con, RegisterController control) {
		conn = con;
		registercontroller = control;
	}
	
	public Donor(Connection con, LogInController co) {
		conn = con;
		logincontroller = co;
	}
	
	public boolean addUser(String s_id,String name,int i_age,String address,String mail,
			String phonenumber,String school,String major, 
		    int i_cohort,String bloodgroup, String sex) {
		try {
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO DONOR VALUES (?,?,?,?,?,?,?,?,?,?,?);");
			pstmt.setString(1, s_id);
			pstmt.setString(2, name);
			pstmt.setInt(3, i_age);
			pstmt.setString(4, address);
			pstmt.setString(5, mail);
			pstmt.setString(6, phonenumber);
			pstmt.setString(7, school);
			pstmt.setString(8, major);
			pstmt.setInt(9, i_cohort);
			pstmt.setString(10, bloodgroup);
			pstmt.setString(11, sex);
			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected > 0;  // Return true if event was added successfully
			
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	public String getDonorName(String id) {
		String query = "SELECT D_NAME FROM DONOR WHERE D_ID = ?;";
		PreparedStatement stmtHost;
		try {
			stmtHost = conn.prepareStatement(query);
			stmtHost.setString(1, id);
			ResultSet rsHost = stmtHost.executeQuery();
			String name = "";
			if (rsHost.next()) {
			    name = rsHost.getString("D_NAME");	    
			}
			return name;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
}
