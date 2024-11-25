package controller;

import view.*;
import model.*;
import java.sql.Connection;
import javax.swing.JOptionPane;

public class LogInController {
    private LogIn view;
    private Donor_Account donor_Account;
    private Host_Account host_Account;
    private Donor donor;
    private Host host;
    private Connection conn;
    private boolean isAuthenticated;
    private String authenticatedUserId; 
    private String authenticatedName;

    public LogInController(Connection con, LogIn v) {
        this.conn = con;
        this.view = v;
        this.donor_Account = new Donor_Account(this, con);
        this.host_Account = new Host_Account(this, con);
        this.donor = new Donor(conn, this);
        this.host = new Host(con, this);
        this.isAuthenticated = false;
        this.authenticatedUserId = null;
    }

    public void handleLogin(String username, String password, String role) {
        if (role.equals("Host")) {
            isAuthenticated = host_Account.verify(username, password);
            if (isAuthenticated) {
                authenticatedUserId = host_Account.getHostId(username); 
                authenticatedName = host.getHostName(authenticatedUserId);
            }
        } else if (role.equals("Donor")) {
            isAuthenticated = donor_Account.verify(username, password);
            if (isAuthenticated) {
                authenticatedUserId = donor_Account.getDonorId(username); 
                authenticatedName = donor.getDonorName(authenticatedUserId);
            }
        }

        if (isAuthenticated) {     	
            handleSwitchHome(view, role, authenticatedUserId, authenticatedName); 
        } else {
            JOptionPane.showMessageDialog(null, "Invalid " + role + " information. Please try again.");
        }
    }

    public void handleSwitchForgotPassword() {
        view.setVisible(false); 
        ForgotPassword forgotPassword = new ForgotPassword(); 
        forgotPassword.setVisible(true); 
        ForgotPasswordController controller = new ForgotPasswordController(conn, forgotPassword);
        forgotPassword.setController(controller);
    }

    public void handleSwitchHome(LogIn v, String role, String userId, String name) {
        if (role.equals("Host")) {
            view.setVisible(false);
            EventController controller = new EventController(conn);           
            Host_HomeFrame home = new Host_HomeFrame(controller, userId, name); 
            home.setVisible(true);
        } else {
            view.setVisible(false);
            EventController controller = new EventController(conn);
            Donor_HomeFrame home = new Donor_HomeFrame(controller, userId, name);
            home.setVisible(true);
        }
    }

    public void handleSwitchRegister() {
        view.setVisible(false);
        Register_Frame register = new Register_Frame();
        register.setVisible(true);
        RegisterController con = new RegisterController(conn, register);
        register.setController(con);
    }
}
