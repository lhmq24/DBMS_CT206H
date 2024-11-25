package controller;

import java.sql.Connection;

import javax.swing.JOptionPane;

import model.Donor_Account;
import model.Donor;
import model.Host_Account;
import model.Host;
import view.LogIn;

import view.Register_Frame;

public class RegisterController {
	private Register_Frame view = null;
	private Donor donor;
	private Donor_Account Donor_Account;
	private Host host;
	private Host_Account Host_Account;
	private Connection conn;
	
	public RegisterController(Connection con, Register_Frame v) {
		conn = con;
		view = v;
		donor = new Donor(conn, this);
		host = new Host(conn, this);
		Donor_Account = new Donor_Account(this, con);
		Host_Account = new Host_Account(this, con);
	}
	
	public void handleSwitchLogIn(Register_Frame v) {
		v.setVisible(false); 
        LogIn login = new LogIn(); 
        login.setVisible(true); 
        LogInController controller = new LogInController(conn, login);
        login.setController(controller);
	}
	
	
	public boolean RegistertoAccount(String id, String name, int age, String address, String mail, String phone,
	        String school, String major, int cohort, String bloodGroup, String sex, 
	        String username, String password, String question, String answer, String role) {
		String hostID = null;
	    try {
	        conn.setAutoCommit(false); // Start the transaction
	        
	        // Check role and add the corresponding user and account
	        boolean userAdded = false;
	        if (role.equalsIgnoreCase("Donor")) {
	            userAdded = donor.addUser(id, name, age, address, mail, phone, school, major, cohort, bloodGroup, sex);
	        } else if (role.equalsIgnoreCase("Host")) {
	        	hostID = host.addUser(name, mail, phone); // Retrieve the generated H_ID
	            if (hostID != null) {
	                userAdded = true;
	            }
	        }
	        
	        boolean accountCreated = false;
	        if (userAdded && role.equalsIgnoreCase("Donor")) {
	            accountCreated = Donor_Account.addDonorAccount(id, username, password, question, answer);
	        } else if (userAdded && role.equalsIgnoreCase("Host")) {
	            accountCreated = Host_Account.addHostAccount(hostID, username, password, question, answer);
	        }

	        // If both user and account are added successfully, commit transaction
	        if (userAdded && accountCreated) {
	            conn.commit(); // Commit the transaction
	            JOptionPane.showMessageDialog(null, role + " account registered successfully!", "Registration Success", JOptionPane.INFORMATION_MESSAGE);
	            return true; // Success
	        } else {
	            conn.rollback(); // Rollback if any operation fails
	            JOptionPane.showMessageDialog(null, "Error during registration.", "Registration Error", JOptionPane.ERROR_MESSAGE);
	            return false; // Failure
	        }

	    } catch (Exception e) {
	        try {
	            conn.rollback(); // Rollback in case of exception
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	        JOptionPane.showMessageDialog(null, "Error occurred during registration: " + e.getMessage(), "Registration Error", JOptionPane.ERROR_MESSAGE);
	        return false; // Failure
	    } finally {
	        try {
	            conn.setAutoCommit(true); // Reset auto-commit after the transaction
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}

}
