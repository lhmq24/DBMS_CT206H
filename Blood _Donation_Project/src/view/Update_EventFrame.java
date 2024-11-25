package view;



import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import controller.EventController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Update_EventFrame {
	
    private JFrame frame;
    private JPanel panel;
    private JLabel eventDateStartLabel;
    private JTextField eventDateStartField;
    private JLabel eventLocationLabel;
    private JTextField eventLocationField;
    private JLabel eventNameLabel;
    private JLabel hostIDLabel;
    private JTextField hostIDField;
    private JLabel eventMaxParticipantsLabel;
    private JTextField eventMaxParticipantstextField;
    private JLabel eventDateEndLabel;
    private JTextField eventDateEndField;
    private JTextField eventNameField;
    private EventController controller;
    private Runnable onEventUpdated;

    public Update_EventFrame(String e_id, String h_id, String loca, int maxparti, 
            Timestamp startTime, Timestamp endTime, String ename,EventController con) {
    	controller = con;
        frame = new JFrame("Update Event");
        frame.setBounds(100, 100, 500, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        panel = new JPanel();
        frame.getContentPane().add(panel);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWeights = new double[]{0.0, 1.0};
        gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
        panel.setLayout(gbl_panel);     
        
        // Event Location
        eventLocationLabel = new JLabel("Event Location:");
        GridBagConstraints gbcLocationLabel = new GridBagConstraints(); // New GridBagConstraints for each component
        gbcLocationLabel.gridx = 0;
        gbcLocationLabel.gridy = 2;
        gbcLocationLabel.anchor = GridBagConstraints.WEST;
        gbcLocationLabel.fill = GridBagConstraints.NONE;
        gbcLocationLabel.insets = new Insets(5, 5, 5, 5); // Padding
        panel.add(eventLocationLabel, gbcLocationLabel);

        eventLocationField = new JTextField(loca,20);
        GridBagConstraints gbcLocationField = new GridBagConstraints(); // New GridBagConstraints for each component
        gbcLocationField.gridy = 2;
        gbcLocationField.gridx = 1;
        gbcLocationField.fill = GridBagConstraints.HORIZONTAL;
        gbcLocationField.insets = new Insets(5, 5, 5, 0); // Padding
        panel.add(eventLocationField, gbcLocationField);
        String eventLocation = eventLocationField.getText().trim();
        
        eventMaxParticipantsLabel = new JLabel("Max Participants:");
        GridBagConstraints gbc_eventMaxParticipantsLabel = new GridBagConstraints();
        gbc_eventMaxParticipantsLabel.anchor = GridBagConstraints.WEST;
        gbc_eventMaxParticipantsLabel.insets = new Insets(5, 5, 5, 5);
        gbc_eventMaxParticipantsLabel.gridx = 0;
        gbc_eventMaxParticipantsLabel.gridy = 3;
        panel.add(eventMaxParticipantsLabel, gbc_eventMaxParticipantsLabel);
               
        eventMaxParticipantstextField = new JTextField(Integer.toString(maxparti),20);
        GridBagConstraints gbc_eventMaxParticipantstextField = new GridBagConstraints();
        gbc_eventMaxParticipantstextField.insets = new Insets(5, 5, 5, 0);
        gbc_eventMaxParticipantstextField.fill = GridBagConstraints.HORIZONTAL;
        gbc_eventMaxParticipantstextField.gridx = 1;
        gbc_eventMaxParticipantstextField.gridy = 3;
        panel.add(eventMaxParticipantstextField, gbc_eventMaxParticipantstextField);

        // Event Date
        eventDateStartLabel = new JLabel("Event Day Start (VD: 2024-01-01 07:00:00):");
        GridBagConstraints gbc_eventDateStartLabel = new GridBagConstraints(); // New GridBagConstraints for each component
        gbc_eventDateStartLabel.anchor = GridBagConstraints.WEST;
        gbc_eventDateStartLabel.gridx = 0;
        gbc_eventDateStartLabel.gridy = 4;
        gbc_eventDateStartLabel.fill = GridBagConstraints.NONE;
        gbc_eventDateStartLabel.insets = new Insets(5, 5, 5, 5); // Padding
        panel.add(eventDateStartLabel, gbc_eventDateStartLabel);

        //format timestamp to present as String
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        String formattedStartTimestamp = formatter.format(startTime);
        eventDateStartField = new JTextField(formattedStartTimestamp,20);
        GridBagConstraints gbc_eventDateStartField = new GridBagConstraints(); // New GridBagConstraints for each component
        gbc_eventDateStartField.gridx = 1;
        gbc_eventDateStartField.gridy = 4;
        gbc_eventDateStartField.fill = GridBagConstraints.HORIZONTAL;
        gbc_eventDateStartField.insets = new Insets(5, 5, 5, 0); // Padding
        panel.add(eventDateStartField, gbc_eventDateStartField);
        String eventDate = eventDateStartField.getText().trim();
        
        eventDateEndLabel = new JLabel("Event Day End (VD: 2024-01-01 09:00:00):");
        GridBagConstraints gbc_eventDateEndLabel = new GridBagConstraints();
        gbc_eventDateEndLabel.anchor = GridBagConstraints.WEST;
        gbc_eventDateEndLabel.insets = new Insets(5, 5, 5, 5);
        gbc_eventDateEndLabel.gridx = 0;
        gbc_eventDateEndLabel.gridy = 5;
        panel.add(eventDateEndLabel, gbc_eventDateEndLabel);
        
        String formattedEndTimestamp = formatter.format(endTime);
        eventDateEndField = new JTextField(formattedEndTimestamp,20);
        GridBagConstraints gbc_eventDateEndField = new GridBagConstraints();
        gbc_eventDateEndField.insets = new Insets(5, 5, 5, 0);
        gbc_eventDateEndField.fill = GridBagConstraints.HORIZONTAL;
        gbc_eventDateEndField.gridx = 1;
        gbc_eventDateEndField.gridy = 5;
        panel.add(eventDateEndField, gbc_eventDateEndField);

        // Event name
        eventNameLabel = new JLabel("Event Name: ");
        GridBagConstraints gbc_eventNameLabel = new GridBagConstraints();
        gbc_eventNameLabel.gridx = 0;
        gbc_eventNameLabel.gridy = 6;
        gbc_eventNameLabel.anchor = GridBagConstraints.WEST;
        gbc_eventNameLabel.insets = new Insets(5, 5, 5, 5); // Padding
        panel.add(eventNameLabel, gbc_eventNameLabel);
                
        eventNameField = new JTextField(ename,20);
        GridBagConstraints gbc_eventNameField = new GridBagConstraints();
        gbc_eventNameField.insets = new Insets(5, 5, 5, 0);
        gbc_eventNameField.fill = GridBagConstraints.HORIZONTAL;
        gbc_eventNameField.gridx = 1;
        gbc_eventNameField.gridy = 6;
        panel.add(eventNameField, gbc_eventNameField);

        // Update Button
        JButton updateButton = new JButton("Update Event");
        GridBagConstraints gbcButton = new GridBagConstraints(); // New GridBagConstraints for each component
        gbcButton.gridx = 0;
        gbcButton.gridy = 7;
        gbcButton.gridwidth = 2; // Button spans two columns
        gbcButton.fill = GridBagConstraints.HORIZONTAL;
        gbcButton.insets = new Insets(15, 5, 0, 0); // Padding
        panel.add(updateButton, gbcButton);

        // Action Listener for Update Button
        updateButton.addActionListener(e -> {     
            String location = eventLocationField.getText().trim();
            String maxparticipants = eventMaxParticipantstextField.getText().trim();  
            int maxparticipant;
            String daystart = eventDateStartField.getText().trim();
            String dayend = eventDateEndField.getText().trim();
            String eventname = eventNameField.getText().trim();
            // validation
            //All fields must not be empty
            if ( location.isEmpty() || maxparticipants.isEmpty() || daystart.isEmpty() || dayend.isEmpty() || eventname.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            //Max participants must be an integer
            try {
                maxparticipant = Integer.parseInt(maxparticipants);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Max participants must be a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            //All the date must be valid
            if (!isValidDate(daystart) || !isValidDate(dayend)) {
                JOptionPane.showMessageDialog(frame, "Invalid date format (YYYY-MM-DD HH:MM:SS).", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
       
            if (!isFutureDate(daystart) || !isFutureDate(dayend)) {
                JOptionPane.showMessageDialog(frame, "Event date must be in the future and at least 1 day after current date.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } 
            
            if (!isStartBeforeEnd(daystart, dayend)) {
                JOptionPane.showMessageDialog(frame, "Event end date must be after event start date at least 1 hour.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Update database
            try {
                Timestamp startTimestamp = convertToTimestamp(daystart);
                Timestamp endTimestamp = convertToTimestamp(dayend);
                
                // Insert into database via controller
                controller.updateEvent(e_id,h_id, location, maxparticipant, startTimestamp, endTimestamp, eventname, onEventUpdated);                           
                //Dispose here if want to dispose view after updated
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error in date conversion: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
        
        private boolean isValidDate(String date) {
        	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
            dateFormat.setLenient(false);
            try {
                dateFormat.parse(date);
                return true;
            } catch (ParseException e) {
                return false;
            }
        }

        private boolean isFutureDate(String date) {
        	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
            try {
                Date eventDate = dateFormat.parse(date);      
                Date currentDate = new Date();

                long currentTime = currentDate.getTime();
                long eventTime = eventDate.getTime();
                
                // Check if the event date is after the current date and at least 1 day in the future
                long oneDayInMillis = 24 * 60 * 60 * 1000; // One day in milliseconds
                if (eventTime >= currentTime + oneDayInMillis) {
                    return true; 
                } else {
                    return false; 
                }
            } catch (ParseException e) {
                return false;
            }
        }
        
        private boolean isStartBeforeEnd(String start, String end) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date startDate = dateFormat.parse(start);
                Date endDate = dateFormat.parse(end);

                long differenceInMillis = endDate.getTime() - startDate.getTime();
                long oneHourInMillis = 60 * 60 * 1000; // 1 hour in milliseconds
                
                // Check if the end time is at least 1 hour after the start time
                return differenceInMillis >= oneHourInMillis;
            } catch (ParseException e) {
                return false;
            }
        }

        private Timestamp convertToTimestamp(String date) throws ParseException {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date parsedDate = dateFormat.parse(date);
            return new Timestamp(parsedDate.getTime());
        }

    public void setOnEventUpdated(Runnable onEventUpdated) {
        this.onEventUpdated = onEventUpdated;
    }

    public void showFrame() {
        frame.setVisible(true);
    }
}
