package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class services {
	private connector conn;
	private ResultSet rs = null;
	private PreparedStatement stmt = null;
	
//	public boolean verifyUser(String username, String pass) {
//		try {
//			conn.getConnection();
//			return 
//		} catch (Exception e) {
//			// TODO: handle exception
//			return false;
//		}
//	}
	
	public void handleLogin() {
		System.out.println("Login successfully");
	}
}
