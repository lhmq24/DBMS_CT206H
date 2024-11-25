package controller;

import view.Donor_HomeFrame;
import view.Host_HomeFrame;
import view.LogIn;
import view.Register_Frame;
import model.EventModel;
import java.sql.Connection;
import java.sql.Timestamp;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class EventController {
	private Donor_HomeFrame donorview;
	private Host_HomeFrame hostview;
	private EventModel model;
	private Connection conn;

	public EventController(Connection c) {
		conn = c;
		model = new EventModel(this, conn);
	}
	
	public void loadEvents(DefaultTableModel tableModel, String userID, String role) {
	    if ("host".equalsIgnoreCase(role)) {
	        // Load events specific to the host
	        model.loadEvents(tableModel, userID, role);
	    } else if ("donor".equalsIgnoreCase(role)) {
	        // Load events for general view (donor)
	        model.loadEvents(tableModel, userID, role);
	    } else {
	        throw new IllegalArgumentException("Invalid role: " + role);
	    }
	}

	
	//Method for Host
	public void setView(Host_HomeFrame home) {
		this.hostview = home;
	}
	
	public void handleSwitchLogIn(Host_HomeFrame h) {
		h.setVisible(false); 
        LogIn login = new LogIn(); 
        login.setVisible(true); 
        LogInController controller = new LogInController(conn, login);
        login.setController(controller);
	}
	
	
	public boolean removeEvent(int EventID) {
		int i = model.removeEvent(EventID);
		if(i==1) return true;
		if(i==0) {		
			return false;
		}
		return false;
	}
	
	public void searchEvents(String query, DefaultTableModel tableModel, String userId, String role) {
	    model.searchEvents(query, tableModel, userId, role);
	}
	
	public void addEvent(String host_id, String location, int maxparticipants, 
        Timestamp startTimestamp, Timestamp endTimestamp, String eventname, 
        Runnable onEventAdded) {
			// Add event to the database			
			boolean success = model.addEvent( host_id, location, maxparticipants, 
			                                      startTimestamp, endTimestamp, eventname);
			
			// If event was added successfully, run the callback
			if (success) {
			onEventAdded.run();  // Trigger the callback to reload events or update the UI
			} else {
			// Handle failure to add event
			JOptionPane.showMessageDialog(null, "Failed to add event.", "Database Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	
	public void updateEvent(String event_id, String host_id, String location, int maxparticipants, 
	        Timestamp startTimestamp, Timestamp endTimestamp, String eventname, 
	        Runnable onEventAdded) {
				// Add event to the database			
				int success = model.updateEvent(event_id, host_id, location, maxparticipants, 
				                                      startTimestamp, endTimestamp, eventname);
				
				// If event was added successfully, run the callback
				if (success > 0) {				
				onEventAdded.run();  // Trigger the callback to reload events 
				
				} else {
				// Handle failure to add event
				JOptionPane.showMessageDialog(null, "Failed to update event.", "Database Error", JOptionPane.ERROR_MESSAGE);
				
				}
			}
	
	//For Donor method
	public void setView(Donor_HomeFrame home) {
		this.donorview = home;
	}
	
	public void handleSwitchLogIn(Donor_HomeFrame d) {
		d.setVisible(false); 
        LogIn login = new LogIn(); 
        login.setVisible(true); 
        LogInController controller = new LogInController(conn, login);
        login.setController(controller);
	}
	
	
	public void register(DefaultTableModel tableModel, ListSelectionModel selectionModel, String donorID) {
	    int selectedRow = selectionModel.getMinSelectionIndex();
	    if (selectedRow != -1) {
	        String eventID = (String) tableModel.getValueAt(selectedRow, 0); // get value column 0 E_ID
	        boolean success = model.registerToEvent(donorID, eventID);
	        if (success) {
	            JOptionPane.showMessageDialog(null, "Registered successfully!",null,JOptionPane.INFORMATION_MESSAGE);
	            loadEvents(tableModel, donorID , "Donor"); // Refresh table
	        } else {
	            JOptionPane.showMessageDialog(null, "Registration failed.", "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    }
	}
	
	public void unregister(DefaultTableModel tableModel, ListSelectionModel selectionModel, String donorID) {
	    int selectedRow = selectionModel.getMinSelectionIndex();
	    if (selectedRow != -1) {
	        String eventID = (String) tableModel.getValueAt(selectedRow, 0); // get value column 0 E_ID
	        boolean success = model.unregisterFromEvent(donorID, eventID);
	        if (success) {
	            JOptionPane.showMessageDialog(null, "Unregistered successfully!",null,JOptionPane.INFORMATION_MESSAGE);
	            loadEvents(tableModel, donorID , "Donor"); 
	        } else {
	            JOptionPane.showMessageDialog(null, "Unregistration failed.", "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    }
	}
	
	public void showHistory(DefaultTableModel tableModel, String donorID) {
		// Clear rows in the table model
	    tableModel.setRowCount(0);	   
	    model.loadHistory(tableModel, donorID);
	}
	
	public boolean isRegistered(String donorID, String eventID) {
		return model.isRegistered(donorID, eventID);
	}

	public boolean addRecord(String donorID, String eventID, String bloodPressure, int i_bloodHeartrate, int i_bloodVolume) {
	     return model.addRecord(donorID, eventID, bloodPressure, i_bloodHeartrate, i_bloodVolume);	  
	}
}
