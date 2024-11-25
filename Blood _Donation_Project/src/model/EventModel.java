package model;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.EventController;
import view.Host_HomeFrame;

public class EventModel {
	private Connection conn;
	private EventController controller;
	
	public EventModel(EventController con , Connection c) {
		conn = c;
		controller = con;
	}
	
	public void loadEvents(DefaultTableModel tableModel, String userId, String role) {
	    String query;

	    if ("host".equalsIgnoreCase(role)) {
	        // Query to show events associated with the host
	        query = 
	            "SELECT E_ID, H_ID, E_LOCATION, E_MAXPARTICIPANTS, E_DAYSTART, E_DAYEND, E_NAME " +
	            "FROM DONATION_EVENT " +
	            "WHERE H_ID = ?";
	    } else if ("donor".equalsIgnoreCase(role)) {
	        // Query to show all events with donor registration status
	        query = 
	            "SELECT de.E_ID, de.H_ID, de.E_LOCATION, de.E_MAXPARTICIPANTS, de.E_DAYSTART, de.E_DAYEND, de.E_NAME, " +
	            "CASE " +
	                "WHEN er.D_ID IS NOT NULL THEN 'Yes' " +
	                "ELSE 'No' " +
	            "END AS REGISTERED " +
	            "FROM DONATION_EVENT de " +
	            "LEFT JOIN E_REGISTRATION er ON de.E_ID = er.E_ID AND er.D_ID = ?";
	    } else {
	        throw new IllegalArgumentException("Invalid role: " + role);
	    }

	    try (PreparedStatement stmt = conn.prepareStatement(query)) {
	        tableModel.setRowCount(0); // Clear existing rows

	        if ("host".equalsIgnoreCase(role)) {            
	            stmt.setString(1, userId);
	        } else if ("donor".equalsIgnoreCase(role)) {	            
	            stmt.setString(1, userId);
	        }

	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                Vector<Object> row = new Vector<>();
	                row.add(rs.getString("E_ID"));
	                row.add(rs.getString("H_ID"));
	                row.add(rs.getString("E_LOCATION"));
	                row.add(rs.getInt("E_MAXPARTICIPANTS"));
	                row.add(rs.getTimestamp("E_DAYSTART"));
	                row.add(rs.getTimestamp("E_DAYEND"));
	                row.add(rs.getString("E_NAME"));

	                // Add the "REGISTERED" column only for the donor role
	                if ("donor".equalsIgnoreCase(role)) {
	                    row.add(rs.getString("REGISTERED"));
	                }

	                tableModel.addRow(row);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error loading events: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
	    }
	}


	 public boolean addEvent(String host_id, String location, int maxparticipants, 
             Timestamp startTimestamp, Timestamp endTimestamp, String eventname) {
		String query = "INSERT INTO donation_event (E_ID, H_ID, E_LOCATION, E_MAXPARTICIPANTS, E_DAYSTART, E_DAYEND, E_NAME) VALUES (null, ?, ?, ?, ?, ?, ?)";
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, host_id);
			pstmt.setString(2, location);
			pstmt.setInt(3, maxparticipants);
			pstmt.setTimestamp(4, startTimestamp);
			pstmt.setTimestamp(5, endTimestamp);
			pstmt.setString(6, eventname);
			
			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected > 0;  // Return true if event was added successfully
			
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	 }			
	 
    public int removeEvent(int eventId) {
        String sql = "Delete from DONATION_EVENT WHERE E_ID = ?";
        
        try (
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, eventId);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                return 1;
            } else {
            	JOptionPane.showMessageDialog(null, "Event ID not found!", "Input Error", JOptionPane.ERROR_MESSAGE);
                return 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error removing events: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
    }
    
    
    public void searchEvents(String query, DefaultTableModel tableModel, String userId, String role) {
        String sql;

        if ("host".equalsIgnoreCase(role)) {         
            sql = "SELECT E_ID, H_ID, E_LOCATION, E_MAXPARTICIPANTS, E_DAYSTART, E_DAYEND, E_NAME " +
                  "FROM DONATION_EVENT " +
                  "WHERE H_ID = ? AND (E_NAME LIKE ? OR E_LOCATION LIKE ? OR DATE(E_DAYSTART) LIKE ?)";
        } else if ("donor".equalsIgnoreCase(role)) {
            sql = "SELECT de.E_ID, de.H_ID, de.E_LOCATION, de.E_MAXPARTICIPANTS, de.E_DAYSTART, de.E_DAYEND, de.E_NAME, " +
                  "CASE " +
                  "  WHEN er.D_ID IS NOT NULL THEN 'Yes' " +
                  "  ELSE 'No' " +
                  "END AS REGISTERED " +
                  "FROM DONATION_EVENT de " +
                  "LEFT JOIN E_REGISTRATION er ON de.E_ID = er.E_ID AND er.D_ID = ? " +
                  "WHERE E_NAME LIKE ? OR E_LOCATION LIKE ? OR DATE(E_DAYSTART) LIKE ?";
        } else {
            throw new IllegalArgumentException("Invalid role: " + role);
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            tableModel.setRowCount(0); 

            String searchQuery = "%" + query + "%";

            if ("host".equalsIgnoreCase(role)) {
                stmt.setString(1, userId);
                stmt.setString(2, searchQuery);
                stmt.setString(3, searchQuery);
                stmt.setString(4, searchQuery);
            } else if ("donor".equalsIgnoreCase(role)) {
                stmt.setString(1, userId);
                stmt.setString(2, searchQuery);
                stmt.setString(3, searchQuery);
                stmt.setString(4, searchQuery);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Vector<Object> row = new Vector<>();
                    row.add(rs.getString("E_ID"));
                    row.add(rs.getString("H_ID"));
                    row.add(rs.getString("E_LOCATION"));
                    row.add(rs.getInt("E_MAXPARTICIPANTS"));
                    row.add(rs.getTimestamp("E_DAYSTART"));
                    row.add(rs.getTimestamp("E_DAYEND"));
                    row.add(rs.getString("E_NAME"));

                    if ("donor".equalsIgnoreCase(role)) {
                        row.add(rs.getString("REGISTERED"));
                    }

                    tableModel.addRow(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                null, 
                "Error searching events: " + e.getMessage(), 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

 
    public int updateEvent(String event_id, String host_id, String location, int maxparticipants, 
             Timestamp startTimestamp, Timestamp endTimestamp, String eventname) {
    	try {
    		String query= "UPDATE DONATION_EVENT SET H_ID = ?, E_LOCATION = ?, E_MAXPARTICIPANTS = ?, E_DAYSTART = ?, E_DAYEND = ?, E_NAME = ? WHERE E_ID = ?";
    		PreparedStatement stmt = conn.prepareStatement(query);
    		stmt.setString(7, event_id);
			stmt.setString(1, host_id);
			stmt.setString(2, location);
			stmt.setInt(3, maxparticipants);
			stmt.setTimestamp(4, startTimestamp);
			stmt.setTimestamp(5, endTimestamp);
			stmt.setString(6, eventname);
			return stmt.executeUpdate();	
            } catch (SQLException e) {
                e.printStackTrace();            
                return -1;
            }
    }
    
    
    //Method for Donor( query 3 table Donation_event, E_Registration, E_Record)
    
    public boolean registerToEvent(String donorId, String eventId) {        
        PreparedStatement countStmt = null;
        PreparedStatement maxStmt = null;
        PreparedStatement insertStmt = null;
        PreparedStatement lockStmt = null;
        ResultSet rs = null;
        int currentParticipants = 0;
        int maxParticipants = 0;
        
        try {         
            conn.setAutoCommit(false); // Start transaction

            // Lock the event rows to prevent another transaction modifies it
            String lockQuery = "SELECT * FROM DONATION_EVENT WHERE E_ID = ? FOR UPDATE";
            lockStmt = conn.prepareStatement(lockQuery);
            lockStmt.setString(1, eventId);
            rs = lockStmt.executeQuery();

            if (!rs.next()) {
                conn.rollback(); // Rollback if event does not exist anymore 
                return false;
            }

            // Check the current number of registered donors
            String countQuery = "SELECT COUNT(*) FROM E_REGISTRATION WHERE E_ID = ?";
            countStmt = conn.prepareStatement(countQuery);
            countStmt.setString(1, eventId);
            rs = countStmt.executeQuery();
            
            if (rs.next()) {
                currentParticipants = rs.getInt(1);
            }

            // Get max participants of the event
            String maxQuery = "SELECT E_MAXPARTICIPANTS FROM DONATION_EVENT WHERE E_ID = ?";
            maxStmt = conn.prepareStatement(maxQuery);
            maxStmt.setString(1, eventId);
            rs = maxStmt.executeQuery();
            
            if (rs.next()) {
                maxParticipants = rs.getInt("E_MAXPARTICIPANTS");
            }

            // Check if can adding more donor
            if (currentParticipants >= maxParticipants) {
                conn.rollback(); 
                return false; 
            }
            

            // Adding new registration
            String insertQuery = "INSERT INTO E_REGISTRATION (D_ID, E_ID, REGISTRATIONTIME) VALUES (?, ?, CURRENT_TIMESTAMP)";
            insertStmt = conn.prepareStatement(insertQuery);
            insertStmt.setString(1, donorId);
            insertStmt.setString(2, eventId);
            int rowsInserted = insertStmt.executeUpdate();

            if (rowsInserted > 0) {
                conn.commit(); // Commit transaction if successful
                return true;
            } else {
                conn.rollback(); // Rollback if insert fails
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (conn != null) conn.rollback();  //Rollback if the error occurs
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        }
    }
    
    public boolean unregisterFromEvent(String donorId, String eventId) {
        
        PreparedStatement deleteStmt = null;
        PreparedStatement lockStmt = null;
        
        try {         
            conn.setAutoCommit(false); // Start transaction

            // Lock the event rows to prevent another transaction modifies it
            String lockQuery = "SELECT * FROM DONATION_EVENT WHERE E_ID = ? FOR UPDATE";
            lockStmt = conn.prepareStatement(lockQuery);
            lockStmt.setString(1, eventId);
            ResultSet rs = lockStmt.executeQuery();

            if (!rs.next()) {
                conn.rollback(); // Rollback if event does not exist
                return false;
            }

            //Remove the record
            String deleteQuery = "DELETE FROM E_REGISTRATION WHERE D_ID = ? AND E_ID = ?";
            deleteStmt = conn.prepareStatement(deleteQuery);
            deleteStmt.setString(1, donorId);
            deleteStmt.setString(2, eventId);
            int rowsDeleted = deleteStmt.executeUpdate();

            if (rowsDeleted > 0) {
                conn.commit(); // Commit if successful
                return true;
            } else {
                conn.rollback(); // Rollback if fails
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } 
    }

    
    public void loadHistory(DefaultTableModel tableModel, String donorID) {
        String query = "SELECT d.E_ID, d.H_ID, d.E_LOCATION, d.E_MAXPARTICIPANTS, d.E_DAYSTART, d.E_DAYEND, d.E_NAME "
            + "FROM E_RECORD r "
            + "JOIN DONATION_EVENT d ON r.E_ID = d.E_ID "
            + "WHERE r.D_ID = ? "
            + "ORDER BY d.E_DAYSTART DESC;"
        ;

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, donorID);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Object[] row = {
                    rs.getString("E_ID"),
                    rs.getString("H_ID"),
                    rs.getString("E_LOCATION"),
                    rs.getInt("E_MAXPARTICIPANTS"),
                    rs.getTimestamp("E_DAYSTART"),
                    rs.getTimestamp("E_DAYEND"),
                    rs.getString("E_NAME"),
                    "YES"
                };
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public boolean isRegistered(String donorID, String eventID) {
    	String query = "Select * from E_REGISTRATION where D_ID = ? and E_ID = ? ;";
    	
    	try(PreparedStatement pstmt = conn.prepareStatement(query)){
    		pstmt.setString(1, donorID);
    		pstmt.setString(2, eventID);
            ResultSet rs = pstmt.executeQuery();
            
            if(rs.next()) {
            	return true;
            }
            else return false;
    	} catch (SQLException e) {		
			e.printStackTrace();
			return false;
		}
    }
    
    public boolean addRecord(String donorID, String eventID, String bloodPressure, int i_bloodHeartrate, int i_bloodVolume) {
    	String query = "Insert into E_RECORD(D_ID, E_ID, BLOODPRESSURE, HEARTRATE_BPM, BLOODVOLUME_ML, CREATEDAT)  VALUES (?,?,?,?,?, current_timestamp) ;";
    	
    	try(PreparedStatement pstmt = conn.prepareStatement(query)){
    		pstmt.setString(1, donorID);
    		pstmt.setString(2, eventID);
    		pstmt.setString(3, bloodPressure);
    		pstmt.setInt(4, i_bloodHeartrate);
    		pstmt.setInt(5, i_bloodVolume );         
            
            int rowsAffected = pstmt.executeUpdate();
            
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();          
            return false;
        } 
    }

}

