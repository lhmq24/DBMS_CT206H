package view;
import java.sql.Connection;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.*;
import controller.*;

public class LogIn extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel usernameLabel;
	private JTextField txtEnterUsername;
	private JLabel passLabel;
	private JPasswordField passwordField;
	private JCheckBox showPasswordCheckBox;
	private JLabel forgotPasswordLabel;
	private JCheckBox Host;
	private JCheckBox Donor;
	private JButton btnLogin;
	private LogInController controller;
	

	/**
	 * Create the frame.
	 */

	public LogIn() {
		
		//Init Frame 
		this.setTitle("Log In");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
            public void windowClosing(WindowEvent e) {
                ;
            }
		});
		this.setBounds(100, 100, 605, 495);
		
		//Init contentPane
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 255, 240));
		contentPane.setBorder(new EmptyBorder(20, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		//Primary color for pane, theme
		Color primaryColor = contentPane.getBackground();
		
		
		
		
		//Init username label
		usernameLabel = new JLabel("Username");
		usernameLabel.setFont(new Font("Arial", Font.BOLD, 18));
		usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		usernameLabel.setBounds(10, 13, 197, 80);
		contentPane.add(usernameLabel);
		
		//Init username textfield
		txtEnterUsername = new JTextField();
		txtEnterUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtEnterUsername.setBounds(217, 31, 330, 40);
		contentPane.add(txtEnterUsername);
		txtEnterUsername.setColumns(12);

		//Init password label
		passLabel = new JLabel("Password");
		passLabel.setHorizontalAlignment(SwingConstants.CENTER);
		passLabel.setFont(new Font("Arial", Font.BOLD, 18));
		passLabel.setBounds(10, 133, 197, 80);
		contentPane.add(passLabel);
		
		//Init password field
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
		passwordField.setBounds(217, 145, 330, 40);
		contentPane.add(passwordField);
		
		//Init show password checkbox
		showPasswordCheckBox = new JCheckBox("Show Password");
		showPasswordCheckBox.setEnabled(true);
		showPasswordCheckBox.setForeground(new Color(0, 0, 51));
		showPasswordCheckBox.setBackground(new Color(204, 255, 204));
		showPasswordCheckBox.setFont(new Font("Arial", Font.PLAIN, 16));
		showPasswordCheckBox.setLocation(217, 202);
		showPasswordCheckBox.setSize(199, 52);
		contentPane.add(showPasswordCheckBox);	
        showPasswordCheckBox.setBackground(primaryColor);
     
		
		//Add Action listener to toggle password visibility
		ActionListener showPassword = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (showPasswordCheckBox.isSelected()) {
                    passwordField.setEchoChar((char) 0); // Show password
                } else {
                    passwordField.setEchoChar('●'); // Hide password
                }
            }
        
        };
        showPasswordCheckBox.addActionListener(showPassword);
        
        //Init forgot password label
        forgotPasswordLabel = new JLabel("Forgot pasword?");
		forgotPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		forgotPasswordLabel.setBounds(423, 213, 124, 31);
		forgotPasswordLabel.setEnabled(true);
		forgotPasswordLabel.setVisible(true);
		contentPane.add(forgotPasswordLabel);
		// Hide the current frame and show the second frame
		forgotPasswordLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {           
            	controller.handleSwitchForgotPassword();
            }
        });
		
		//Init Host checkbox
		Host = new JCheckBox("Host");
		Host.setBackground(primaryColor);
		Host.setFont(new Font("Arial", Font.PLAIN, 16));
		Host.setBounds(172, 294, 124, 47);
		contentPane.add(Host);
		
		//Init Donor checkbox
		Donor = new JCheckBox("Donor");
		Donor.setBackground(primaryColor);
		Donor.setFont(new Font("Arial", Font.PLAIN, 16));
		Donor.setBounds(341, 294, 114, 47);
		contentPane.add(Donor);
		
		//Disable a role checkbox when other role checkbox is selected
        Host.addActionListener(e -> {
        		Donor.setEnabled(!Host.isSelected());
        	});
        
        Donor.addActionListener(e -> {
    		Host.setEnabled(!Donor.isSelected());
    	});
		
		//Init button LogIn, handle login event when user click
		btnLogin = new JButton("Log In");
		//If user is Host
		if(Host.isEnabled()) {
			btnLogin.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
//					//Connect to database to verify user, then redirect to main page
//					
				}
			});
		}
		btnLogin.setFont(new Font("Arial", Font.BOLD, 16));
		btnLogin.setForeground(new Color(0, 0, 51));
		btnLogin.setBackground(new Color(102, 153, 51));
		btnLogin.setBounds(172, 348, 244, 47);
		btnLogin.setFocusPainted(false);
		contentPane.add(btnLogin);
		

        }
		//set controller for view
		public void setController(LogInController ctl) {
			controller = ctl;
		}
}
        
