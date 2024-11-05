package view;

import java.awt.EventQueue;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;


public class LogIn extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtEnterUsername;
	private JPasswordField passwordField;
	

	/**
	 * Create the frame.
	 */
	public LogIn() {
		//Init Frame 
		setTitle("Log In");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 605, 495);
		
		//Init contentPane
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 255, 240));
		contentPane.setBorder(new EmptyBorder(20, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		//Primary color for pane, theme
		Color primaryColor = contentPane.getBackground();
		
		
		
		
		//Init username label
		JLabel usernameLabel = new JLabel("Username");
		usernameLabel.setFont(new Font("Arial", Font.BOLD, 18));
		usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		usernameLabel.setBounds(10, 13, 197, 80);
		contentPane.add(usernameLabel);
		
		//Init username textfield
		JTextField txtEnterUsername = new JTextField();
		txtEnterUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtEnterUsername.setBounds(217, 31, 330, 40);
		contentPane.add(txtEnterUsername);
		txtEnterUsername.setColumns(12);

		//Init password label
		JLabel passLabel = new JLabel("Password");
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
		JCheckBox showPasswordCheckBox = new JCheckBox("Show Password");
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
        JLabel forgotPasswordLabel = new JLabel("Forgot pasword?");
		forgotPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		forgotPasswordLabel.setBounds(423, 213, 124, 31);
		forgotPasswordLabel.setEnabled(true);
		forgotPasswordLabel.setVisible(true);
		contentPane.add(forgotPasswordLabel);
		
		forgotPasswordLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Hide the current frame and show the second frame
                LogIn.this.setVisible(false); // Hide the current frame
                ForgotPassword forgotpassword = new ForgotPassword(); // Create new frame
                forgotpassword.setVisible(true); // Show the new frame
            }
        });
		
		//Init Host checkbox
		JCheckBox Host = new JCheckBox("Host");
		Host.setBackground(primaryColor);
		Host.setFont(new Font("Arial", Font.PLAIN, 16));
		Host.setBounds(172, 294, 124, 47);
		contentPane.add(Host);
		
		//Init Donor checkbox
		JCheckBox Donor = new JCheckBox("Donor");
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
		
		//Init button LogIn
		JButton btnLogin = new JButton("Log In");
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//Connect to database to verify user, then redirect to main page
		
			}
		});
		btnLogin.setFont(new Font("Arial", Font.BOLD, 16));
		btnLogin.setForeground(new Color(0, 0, 51));
		btnLogin.setBackground(new Color(102, 153, 51));
		btnLogin.setBounds(172, 348, 244, 47);
		btnLogin.setFocusPainted(false);
		contentPane.add(btnLogin);
		

        }
}
        