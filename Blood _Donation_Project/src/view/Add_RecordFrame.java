package view;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import controller.EventController;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Add_RecordFrame {
	
    private JFrame frame;
    private JPanel panel;
    private JLabel heartRateLabel;
    private JTextField heartRateTextField;
    private JLabel eventIDLabel;
    private Runnable onRecordAdded; // callback to notify when an event is added(?)
    private EventController controller;
    private JLabel donorIDLabel;
    private JTextField eventIDField;
    private JLabel bloodPressureLabel;
    private JTextField bloodPressureTextField;
    private JLabel bloodVolumeLabel;
    private JTextField bloodVolumnTextField;
    private JTextField donorIDTextField;

    public Add_RecordFrame(EventController c, String eventID) {  
    	controller = c;
        frame = new JFrame("Add Record");
        frame.setBounds(100, 100, 500, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        panel = new JPanel();
        frame.getContentPane().add(panel);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWeights = new double[]{0.0, 1.0};
        gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
        panel.setLayout(gbl_panel);
    
        //Host ID
        donorIDLabel = new JLabel("Donor ID:");
        GridBagConstraints gbc_donorIDLabel = new GridBagConstraints();
        gbc_donorIDLabel.anchor = GridBagConstraints.WEST;
        gbc_donorIDLabel.insets = new Insets(5, 5, 5, 5);
        gbc_donorIDLabel.gridx = 0;
        gbc_donorIDLabel.gridy = 1;
        panel.add(donorIDLabel, gbc_donorIDLabel);
        
        donorIDTextField = new JTextField(20);
        GridBagConstraints gbc_donorIDTextField = new GridBagConstraints();
        gbc_donorIDTextField.insets = new Insets(0, 0, 5, 0);
        gbc_donorIDTextField.fill = GridBagConstraints.HORIZONTAL;
        gbc_donorIDTextField.gridx = 1;
        gbc_donorIDTextField.gridy = 1;
        panel.add(donorIDTextField, gbc_donorIDTextField);
        
        // Event Location
        eventIDLabel = new JLabel("Event_ID:");
        GridBagConstraints gbc_eventIDLabel = new GridBagConstraints(); // New GridBagConstraints for each component
        gbc_eventIDLabel.gridx = 0;
        gbc_eventIDLabel.gridy = 2;
        gbc_eventIDLabel.anchor = GridBagConstraints.WEST;
        gbc_eventIDLabel.fill = GridBagConstraints.NONE;
        gbc_eventIDLabel.insets = new Insets(5, 5, 5, 5); // Padding
        panel.add(eventIDLabel, gbc_eventIDLabel);
        
        eventIDField = new JTextField(eventID, 20);
        GridBagConstraints gbc_eventIDField = new GridBagConstraints();
        gbc_eventIDField.insets = new Insets(5, 5, 5, 0);
        gbc_eventIDField.fill = GridBagConstraints.HORIZONTAL;
        gbc_eventIDField.gridx = 1;
        gbc_eventIDField.gridy = 2;
        panel.add(eventIDField, gbc_eventIDField);
        String host_id = eventIDField.getText().trim();
        
        bloodPressureLabel = new JLabel("Blood Pressure (VD: 120/80):");
        GridBagConstraints gbc_bloodPressureLabel = new GridBagConstraints();
        gbc_bloodPressureLabel.anchor = GridBagConstraints.WEST;
        gbc_bloodPressureLabel.insets = new Insets(5, 5, 5, 5);
        gbc_bloodPressureLabel.gridx = 0;
        gbc_bloodPressureLabel.gridy = 3;
        panel.add(bloodPressureLabel, gbc_bloodPressureLabel);
        
        bloodPressureTextField = new JTextField(20);
        GridBagConstraints gbc_bloodPressureTextField = new GridBagConstraints();
        gbc_bloodPressureTextField.insets = new Insets(5, 5, 5, 0);
        gbc_bloodPressureTextField.fill = GridBagConstraints.HORIZONTAL;
        gbc_bloodPressureTextField.gridx = 1;
        gbc_bloodPressureTextField.gridy = 3;
        panel.add(bloodPressureTextField, gbc_bloodPressureTextField);

        // Event Date
        heartRateLabel = new JLabel("Heart Rate (VD: 80):");
        GridBagConstraints gbc_heartRateLabel = new GridBagConstraints(); // New GridBagConstraints for each component
        gbc_heartRateLabel.anchor = GridBagConstraints.WEST;
        gbc_heartRateLabel.gridx = 0;
        gbc_heartRateLabel.gridy = 4;
        gbc_heartRateLabel.fill = GridBagConstraints.NONE;
        gbc_heartRateLabel.insets = new Insets(5, 5, 5, 5); // Padding
        panel.add(heartRateLabel, gbc_heartRateLabel);

        heartRateTextField = new JTextField(20);
        GridBagConstraints gbc_heartRateTextField = new GridBagConstraints(); // New GridBagConstraints for each component
        gbc_heartRateTextField.gridx = 1;
        gbc_heartRateTextField.gridy = 4;
        gbc_heartRateTextField.fill = GridBagConstraints.HORIZONTAL;
        gbc_heartRateTextField.insets = new Insets(5, 5, 5, 0); // Padding
        panel.add(heartRateTextField, gbc_heartRateTextField);
        String eventDate = heartRateTextField.getText().trim();
        
        bloodVolumeLabel = new JLabel("Blood Volume (ml):");
        GridBagConstraints gbc_bloodVolumeLabel = new GridBagConstraints();
        gbc_bloodVolumeLabel.anchor = GridBagConstraints.WEST;
        gbc_bloodVolumeLabel.insets = new Insets(5, 5, 5, 5);
        gbc_bloodVolumeLabel.gridx = 0;
        gbc_bloodVolumeLabel.gridy = 5;
        panel.add(bloodVolumeLabel, gbc_bloodVolumeLabel);
                        
        bloodVolumnTextField = new JTextField(20);
        GridBagConstraints gbc_bloodVolumnTextField = new GridBagConstraints();
        gbc_bloodVolumnTextField.insets = new Insets(5, 5, 5, 0);
        gbc_bloodVolumnTextField.fill = GridBagConstraints.HORIZONTAL;
        gbc_bloodVolumnTextField.gridx = 1;
        gbc_bloodVolumnTextField.gridy = 5;
        panel.add(bloodVolumnTextField, gbc_bloodVolumnTextField);

        // Add Event Button
        JButton addRecordButton = new JButton("Add Record");
        addRecordButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        GridBagConstraints gbc_addRecordButton = new GridBagConstraints(); // New GridBagConstraints for each component
        gbc_addRecordButton.gridx = 0;
        gbc_addRecordButton.gridy = 7;
        gbc_addRecordButton.gridwidth = 2; // Button spans two columns
        gbc_addRecordButton.fill = GridBagConstraints.HORIZONTAL;
        gbc_addRecordButton.insets = new Insets(15, 5, 0, 0); // Padding
        panel.add(addRecordButton, gbc_addRecordButton);


        // Action Listener for Add Record Button
        addRecordButton.addActionListener(e -> {
            String donorID =   donorIDTextField.getText().trim();        
            //eventID
            String bloodPressure = bloodPressureTextField.getText().trim(); 
            String s_bloodHeartrate = heartRateTextField.getText().trim();
            String s_bloodVolume = bloodVolumnTextField.getText().trim();
            int i_bloodHeartrate = 0;
            int i_bloodVolume = 0;
            
            // validation
            //All fields must not be empty
            if (donorID.isEmpty() || bloodPressure.isEmpty() || s_bloodHeartrate.isEmpty() || s_bloodVolume.isEmpty() ) {
                JOptionPane.showMessageDialog(frame, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            //Blood pressure must be in range and valid
            if(!isValidBloodPressure(bloodPressure)) {
            	JOptionPane.showMessageDialog(frame, "Heart pressure must be in valid format (xxx/xxx).", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            //Heart rate must be an integer
            try {
                i_bloodHeartrate = Integer.parseInt(s_bloodHeartrate);                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Heartrate must be a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            //Blood volume must be an integer
            try {
                i_bloodVolume = Integer.parseInt(s_bloodVolume);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Heart volume must be a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Insert into database
            try {
                //Check if donor ID exists in registration table
            	if(controller.isRegistered(donorID, eventID)) {
            		// Insert record into database via controller
            		boolean success = controller.addRecord(donorID, eventID, bloodPressure, i_bloodHeartrate, i_bloodVolume);
                    if (success) {
                        JOptionPane.showMessageDialog(frame, "Record added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Failed to add the record. Please try again.", "Database Error", JOptionPane.ERROR_MESSAGE);
                    }
            	}
            	else {
            		JOptionPane.showMessageDialog(frame, "The donor is not registered for this event.", "Registration Error", JOptionPane.ERROR_MESSAGE);
                    return;
            	}
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error in date conversion: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private boolean isValidBloodPressure(String bloodpressure) {
		String bloodpressureRegex = "^[0-9]{2,3}/[0-9]{2,3}";
		if(bloodpressure.matches(bloodpressureRegex)) {
			String part[] = bloodpressure.split("/");
			
			int systolic = Integer.parseInt(part[0]);
			int diastolic = Integer.parseInt(part[1]);
			
			int systolicMAX = 130;
			int systolicMIN = 90;
			int diastolicMAX = 80;
			int diastolicMIN = 60;
			if(systolic <= systolicMAX && systolic >= systolicMIN && diastolic <= diastolicMAX && diastolic >= diastolicMIN ) {
				return true;
			}
		}
		return false;
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


    public void showFrame() {
        frame.setVisible(true);
    }
    
    public void setController(EventController c) {
    	controller = c;
    }

	public void setOnRecordUpdated(Runnable onRecordAdded) {
		this.onRecordAdded = onRecordAdded;
	}
}
