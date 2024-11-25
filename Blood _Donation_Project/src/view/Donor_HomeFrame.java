package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.Vector;
import javax.swing.SwingConstants;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.SwingConstants;

import controller.EventController;


public class Donor_HomeFrame extends JFrame{
	private JPanel panel;
	private JPanel userInfoPanel;
    private JLabel userIDLabel;
    private JLabel userNameLabel;
    private JTable eventTable;
    private JScrollPane scrollPane;
    private JPanel buttonPanel;
    private JButton ShowEventButton;
    private JButton SearchEventButton;
    private JButton RegisterEventButton;
    private JButton UnregisterEventButton;
    private JButton ShowHistoryButton;
    private DefaultTableModel tableModel;
    private EventController controller;
    private JButton SortTableButton;
    private JButton LogOutButton;
    private String donorID;

    public Donor_HomeFrame(EventController con, String uID, String name) {      
    	this.controller = con;
    	donorID = uID;
    	con.setView(this);
    	
    	// Frame setup
        this.setBounds(100, 100, 800, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Home - Event Management");

        // Create the main panel with BorderLayout
        panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 255, 240));
        getContentPane().add(panel);

        // User Information Panel
        userInfoPanel = new JPanel();
        userInfoPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        userIDLabel = new JLabel("User ID: " + donorID);
        userNameLabel = new JLabel("Name: " + name);
        userInfoPanel.add(userIDLabel);
        userInfoPanel.add(userNameLabel);
        panel.add(userInfoPanel, BorderLayout.NORTH); 
        
        LogOutButton = new JButton("Log Out");
        LogOutButton.setHorizontalAlignment(SwingConstants.RIGHT);
        userInfoPanel.add(LogOutButton);
        ActionListener returnlogin = new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.handleSwitchLogIn(Donor_HomeFrame.this);
			}
		};
		LogOutButton.addActionListener(returnlogin);
   

        // Table to display events
        String[] columnNames = {"Event ID", "Host ID","E_LOCATION", "E_MAXPARTICIPANTS", "E_DAYSTART", "E_DAYEND", "E_NAME", "REGISTERED"};
        tableModel = new DefaultTableModel(columnNames, 0);
        eventTable = new JTable(tableModel);
        
        // Load events from the database
        controller.loadEvents(tableModel,donorID, "Donor");
        
        // Add table to a scroll pane
        scrollPane = new JScrollPane(eventTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Button panel for actions
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // RegistereEvent Button
        RegisterEventButton = new JButton("Register to event");
        RegisterEventButton.setEnabled(false);
        buttonPanel.add(RegisterEventButton);

        // Unregister Event Button
        UnregisterEventButton = new JButton("Unregister the event");
        UnregisterEventButton.setEnabled(false);
        buttonPanel.add(UnregisterEventButton);

        // Show History Button
        ShowHistoryButton = new JButton("Show History");
        ShowHistoryButton.setEnabled(true);
        buttonPanel.add(ShowHistoryButton);

        // Show Events Button
        ShowEventButton = new JButton("Show Events\r\n");    
        buttonPanel.add(ShowEventButton);

        // Search Events Button
        SearchEventButton = new JButton("Search Events");        
        buttonPanel.add(SearchEventButton);
        
        //Sort table Button
        SortTableButton = new JButton("Sort Table");
        buttonPanel.add(SortTableButton);

        // Enable the Register/Unregister buttons when an event is selected
        eventTable.getSelectionModel().addListSelectionListener(e -> {
            boolean isRowSelected = !eventTable.getSelectionModel().isSelectionEmpty();
            RegisterEventButton.setEnabled(isRowSelected);
            UnregisterEventButton.setEnabled(isRowSelected);    
        });
        
        //Add listener to SortTableButton
        ActionListener sortEvents = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Donor_HomeFrame.this.sortEvents(e);
            }
        
        };
        SortTableButton.addActionListener(sortEvents);
        
        //Add listener for ShowEventButton
        ShowEventButton.addActionListener(e -> {
            controller.loadEvents(tableModel, donorID, "Donor"); 
        });
    
    	//Add listener for SearchEventButton
        ActionListener searchEvents = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = JOptionPane.showInputDialog(
                    Donor_HomeFrame.this, 
                    "Enter search term (location, event name, or day start):", 
                    "Search Events", 
                    JOptionPane.QUESTION_MESSAGE
                );

                if (query != null && !query.trim().isEmpty()) {
                    controller.searchEvents(query, tableModel, Donor_HomeFrame.this.donorID, "Donor");
                } else {
                    // Reload
//                    controller.loadEvents(tableModel, Donor_HomeFrame.this.donorID, "Donor");
                }
            }
        };
        SearchEventButton.addActionListener(searchEvents);
        
        //Add listener to ShowHistoryButton
        ActionListener showHistory = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {    	
                controller.showHistory(tableModel, donorID);
            }	     
        };
        ShowHistoryButton.addActionListener(showHistory);
        
        //Add listener to RegisterEventButton     
        ActionListener register = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {    	
            	controller.register(tableModel, eventTable.getSelectionModel(), donorID);
            }	     
        };
        RegisterEventButton.addActionListener(register);
        
        //Add listener to UnregisterEventButton
        ActionListener unregister = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {    	
            	controller.unregister(tableModel, eventTable.getSelectionModel(), donorID);
            }	     
        };
        UnregisterEventButton.addActionListener(unregister);
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
    
