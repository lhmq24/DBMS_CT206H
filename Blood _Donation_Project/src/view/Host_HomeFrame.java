package view;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import controller.EventController;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Host_HomeFrame extends javax.swing.JFrame {
	private JPanel userInfoPanel;
	private JButton LogOutButton;
    private JLabel userIDLabel;
    private JLabel userNameLabel;
	private JPanel panel;
    private JTable eventTable;
    private JScrollPane scrollPane;
    private JPanel buttonPanel;
    private JButton sortEventButton;
    private JButton searchEventButton;
    private JButton removeEventButton;
    private JButton addEventButton;
    private JButton updateEventButton;
    private DefaultTableModel tableModel;
    private EventController controller;
    private JButton btnRefreshTable;
    private String hostID;
    private JButton addRecordButton;

    public Host_HomeFrame(EventController con, String hID, String name) {      
    	this.controller = con;
    	hostID = hID;
    	con.setView(this);
    	
    	// Frame setup
        this.setBounds(100, 100, 800, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Home - Event Management");

        // Create the main panel with BorderLayout
        panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 255, 240));
        getContentPane().add(panel);

        // User Information Panel (added to the top of the main panel)
        userInfoPanel = new JPanel();
        userInfoPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        userIDLabel = new JLabel("User ID: " + hostID);
        userNameLabel = new JLabel("Name: " + name);
        userInfoPanel.add(userIDLabel);
        userInfoPanel.add(userNameLabel);
        panel.add(userInfoPanel, BorderLayout.NORTH); // Add the user info panel to the top
        
        LogOutButton = new JButton("Log Out");
        LogOutButton.setHorizontalAlignment(SwingConstants.RIGHT);
        userInfoPanel.add(LogOutButton);
        ActionListener returnlogin = new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.handleSwitchLogIn(Host_HomeFrame.this);
			}
		};
		LogOutButton.addActionListener(returnlogin);

        // Table to display events
        String[] columnNames = {"Event ID", "Host ID","E_LOCATION", "E_MAXPARTICIPANTS", "E_DAYSTART", "E_DAYEND", "E_NAME"};
        tableModel = new DefaultTableModel(columnNames, 0);
        eventTable = new JTable(tableModel);
        
     // Load events from the database
        controller.loadEvents(tableModel, hostID, "Host");
        // Add table to a scroll pane
        scrollPane = new JScrollPane(eventTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Button panel for actions
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Remove Event Button (initially disabled)
        removeEventButton = new JButton("Remove Event");
        removeEventButton.setEnabled(false);
        buttonPanel.add(removeEventButton);

        // Add Event Button
        addEventButton = new JButton("Add Event");
        buttonPanel.add(addEventButton);

        // Update Event Button
        updateEventButton = new JButton("Update Event");
        updateEventButton.setEnabled(true);
        buttonPanel.add(updateEventButton);

        // Sort Events Button
        sortEventButton = new JButton("Sort Events");
        ActionListener sortEvents = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Host_HomeFrame.this.sortEvents(e);
            }
        
        };
        sortEventButton.addActionListener(sortEvents);
        buttonPanel.add(sortEventButton);

        // Search Events Button
        searchEventButton = new JButton("Search Events");
        ActionListener searchEvents = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = JOptionPane.showInputDialog(
                    Host_HomeFrame.this, 
                    "Enter search term (location, event name, or day start):", 
                    "Search Events", 
                    JOptionPane.QUESTION_MESSAGE
                );

                if (query != null && !query.trim().isEmpty()) {
                    controller.searchEvents(query, tableModel, Host_HomeFrame.this.hostID, "Host");
                } else {
                    // Reload 
//                    controller.loadEvents(tableModel,  Host_HomeFrame.this.hostID, "Host");
                }
            }
        };
        searchEventButton.addActionListener(searchEvents);
        buttonPanel.add(searchEventButton);
        
        //Refresh table Button
        btnRefreshTable = new JButton("Refresh Table");
        ActionListener refreshEvents = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	controller.loadEvents(tableModel, hostID, "Host");
            }
        
        };
        btnRefreshTable.addActionListener(refreshEvents);
        
        //Add record button
        addRecordButton = new JButton("Add Record");
        buttonPanel.add(addRecordButton);
        buttonPanel.add(btnRefreshTable);

        // Enable the Remove/Update buttons when an event is selected
        eventTable.getSelectionModel().addListSelectionListener(e -> {
            boolean isRowSelected = !eventTable.getSelectionModel().isSelectionEmpty();
            removeEventButton.setEnabled(isRowSelected);
            updateEventButton.setEnabled(isRowSelected);    
        });

//         Action listener for the Remove Event button
        removeEventButton.addActionListener(e -> {
            int selectedRow = eventTable.getSelectedRow();  
            if(selectedRow == -1) {
            	JOptionPane.showMessageDialog(this, "Please select the event you want to delete!", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (selectedRow != -1) {                
                try {
                	
                	int eventId = Integer.parseInt(eventTable.getValueAt(selectedRow, 0).toString());
                    if(controller.removeEvent(eventId)) {
                    	JOptionPane.showMessageDialog(this, "Event removed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    	tableModel.removeRow(selectedRow);
                    }
                  
                } catch (Exception ex) {
                	ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error removing event: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Action listener for the Add Event button
        addEventButton.addActionListener(e -> {
            EventQueue.invokeLater(() -> {
                try {
                    // Create a new Add_EventFrame instance
                    Add_EventFrame addEventWindow = new Add_EventFrame(controller, hostID);

                    // Set the callback for successful event addition
                    addEventWindow.setOnEventAdded(() -> {
                        try {
                            // Reload events in the main table
                            controller.loadEvents(tableModel, hostID, "Host");

                            // Show a success message
                            JOptionPane.showMessageDialog(this, "Event added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(this, "Error reloading events: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    });

                    // Show the Add_EventFrame
                    addEventWindow.showFrame();

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error opening Add Event window: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        });
        
     

//         Action listener for the Update Event button
        updateEventButton.addActionListener(e -> {        	
            int selectedRow = eventTable.getSelectedRow();
            if (selectedRow != -1) {
                String eventId = (eventTable.getValueAt(selectedRow, 0).toString());
                String hostid = (eventTable.getValueAt(selectedRow, 1).toString());
                String location = (eventTable.getValueAt(selectedRow, 2).toString());
                int maxparticipant = Integer.parseInt(eventTable.getValueAt(selectedRow, 3).toString());
                Timestamp eventStartDate = (Timestamp) eventTable.getValueAt(selectedRow, 4);
                Timestamp eventEndDate = (Timestamp) eventTable.getValueAt(selectedRow, 5);
                String eventName = eventTable.getValueAt(selectedRow, 6).toString();
         
                EventQueue.invokeLater(() -> {
                    try {
                        Update_EventFrame updateWindow = new Update_EventFrame(eventId, hostid, location, maxparticipant, 
                        		eventStartDate,eventEndDate, eventName, controller);
                        
                        updateWindow.setOnEventUpdated(() -> {
                            try {
                                // Reload events in the main table
                                controller.loadEvents(tableModel, hostID, "Host");
                                	
                                // Show a success message
                                JOptionPane.showMessageDialog(this, "Event updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);                               
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                JOptionPane.showMessageDialog(this, "Error reloading events: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        });
                        updateWindow.showFrame();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Error opening update window: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                });
            } else {
                JOptionPane.showMessageDialog(this, "Please select an event to update.", "No Selection", JOptionPane.WARNING_MESSAGE);
            }
        });
             
        //Add listener for addRecordButton
        addRecordButton.addActionListener(e -> {
            int selectedRow = eventTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select an event to add a record.", "No Selection", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String eventID = eventTable.getValueAt(selectedRow, 0).toString();

            try {
                Add_RecordFrame addRecordWindow = new Add_RecordFrame(controller, eventID);

                addRecordWindow.setOnRecordUpdated(() -> {
                    try {
                        JOptionPane.showMessageDialog(this, "Record added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);                    
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Error while updating record: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                });

                addRecordWindow.showFrame();
            } catch (Exception ex) {
                ex.printStackTrace(); 
                JOptionPane.showMessageDialog(this, "Error opening add record window: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    
    private void sortEvents(ActionEvent e) {
        String[] options = {"Event Name (DESC)", "Date (DESC)", "Location (DESC)"};
        String choice = (String) JOptionPane.showInputDialog(this, "Sort by:", "Sort Events",
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (choice != null) {
            int columnIndex = switch (choice) {
                case "Event Name (DESC)" -> 6;
                case "Date (DESC)" -> 4;
                case "Location (DESC)" -> 2;
                default -> -1;
            };

            if (columnIndex != -1) {
                tableModel.getDataVector().sort((o1, o2) -> {
                    Object v1 = ((Vector<?>) o1).get(columnIndex);
                    Object v2 = ((Vector<?>) o2).get(columnIndex);

                    if (v1 != null && v2 != null) {
                        // Sorting logic based on type
                        if (v1 instanceof Timestamp && v2 instanceof Timestamp) {
                            return ((Timestamp) v2).compareTo((Timestamp) v1); // Descending order
                        } else {
                            String str1 = v1.toString();
                            String str2 = v2.toString();
                            return str2.compareToIgnoreCase(str1); 
                        }
                    } else {
                        // If any value is null, show an error message
                        JOptionPane.showMessageDialog(null, "Error: A column value is null while sorting.", 
                                                      "Sorting Error", JOptionPane.ERROR_MESSAGE);
                        return 0; 
                    }
                });
                tableModel.fireTableDataChanged();
            }
        } 
    }
}
