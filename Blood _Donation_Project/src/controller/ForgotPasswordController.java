package controller;

import java.sql.Connection;

import javax.swing.JOptionPane;

import model.Host_Account;
import model.Donor_Account;
import view.ForgotPassword;
import view.LogIn;

public class ForgotPasswordController {

	private ForgotPassword view;
	private Donor_Account donor_Account;
	private Host_Account host_Account; 
	private Connection conn;
	private boolean isAuthenticated;
	
	public ForgotPasswordController(Connection con, ForgotPassword v) {
		conn = con;
		view = v;
		donor_Account = new Donor_Account(this, con);
		host_Account = new Host_Account(this, con);
		isAuthenticated = false;
	}
	
	public void handleSwitchLogIn(ForgotPassword view) {
		 // Hide the current frame and show the second frame
        view.setVisible(false); // Hide the current frame
        LogIn login = new LogIn(); // Create new frame
        login.setVisible(true); // Show the new frame
        LogInController controller = new LogInController(conn, login);
        login.setController(controller);
	}
	
	public void handleForgotPassword(String username, String question, String answer, String role) {
		String password = "";
		if (role.equalsIgnoreCase("Host")) {
			password = host_Account.verify(username, question, answer); //verify Host info
			if(!password.equals("")) {
				isAuthenticated = true;
			}				        
	    }
		if (role.equalsIgnoreCase("Donor")) {
			password = donor_Account.verify(username, question, answer); // verify Donor info
			if(!password.equals("")) {
				isAuthenticated = true;
			}			
		}

	    if (isAuthenticated) {
	    	JOptionPane.showMessageDialog(null, "Your password is: " + password, "Success", JOptionPane.INFORMATION_MESSAGE);
	        handleSwitchLogIn(this.view); // Redirect to log in page
	    } else {
	        JOptionPane msg = new JOptionPane();
//    		msg.showMessageDialog(null, "Invalid information. Please try again.");
	    }
	}
}
