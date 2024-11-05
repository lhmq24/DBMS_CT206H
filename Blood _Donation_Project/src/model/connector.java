package model;
import java.sql.Connection;	
//import java.sql.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
public class connector {
	private String host;
	private String db;
	private String user;
	private Connection conn = null;
	private ResultSet rs = null;
	
	
	public connector(String host,String db, String user) {
		try {
			this.host = new String(host);
			this.db = new String(db);
			this.user = new String(user);
			String connStr = ("jdbc:mysql://" + host + "/" + db + "?" + "user=" + user);
			conn = DriverManager.getConnection(connStr);
			System.out.println("Connected.");
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	public Connection getConnection() {
		return conn;
	}
	

	public void close() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
