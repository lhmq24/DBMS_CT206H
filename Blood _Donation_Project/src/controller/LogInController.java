package controller;

import view.ForgotPassword;
import view.LogIn;
import model.User_account;
import java.sql.Connection;

public class LogInController {
	private LogIn view;
	private User_account user_account;
	private Connection conn;
	
	public LogInController(Connection con, LogIn v, User_account u) {
		this.conn = con;
		view = v;
		user_account = u;
	}
	
	public void handleLogin() {
		
	}
	
	public void handleSwitchForgotPassword() {
        view.setVisible(false); // Hide the current frame
        ForgotPassword forgotpassword = new ForgotPassword(); // Create new frame
        forgotpassword.setVisible(true); // Show the new frame
	}
	
	public void handleSwitchMain() {
		
	}
}
