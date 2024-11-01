package demo;

import java.awt.EventQueue;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class JFrame extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtEnterUsername;
	private JPasswordField passwordField;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame frame = new JFrame();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public JFrame() {
		
		setTitle("Log In");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 605, 495);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 255, 240));
		contentPane.setBorder(new EmptyBorder(20, 5, 5, 5));
		
		//Primary color for pane, theme
		Color primaryColor = contentPane.getBackground();
		
		
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel usernameLabel = new JLabel("Username");
		usernameLabel.setBounds(10, 13, 197, 80);
		usernameLabel.setFont(new Font("Arial", Font.BOLD, 18));
		usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(usernameLabel);
		
		
		txtEnterUsername = new JTextField();
		txtEnterUsername.setBounds(217, 31, 330, 40);
		txtEnterUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(txtEnterUsername);
		txtEnterUsername.setColumns(12);

		
		JLabel passLabel = new JLabel("Password");
		passLabel.setBounds(10, 133, 197, 80);
		passLabel.setHorizontalAlignment(SwingConstants.CENTER);
		passLabel.setFont(new Font("Arial", Font.BOLD, 18));
		contentPane.add(passLabel);
		
		JCheckBox Host = new JCheckBox("Host");
		Host.setBounds(172, 294, 124, 47);
		Host.setBackground(primaryColor);
		Host.setFont(new Font("Arial", Font.PLAIN, 16));
		contentPane.add(Host);
		
		JCheckBox Donor = new JCheckBox("Donor");
		Donor.setBounds(341, 294, 114, 47);
		Donor.setBackground(primaryColor);
		Donor.setFont(new Font("Arial", Font.PLAIN, 16));
		contentPane.add(Donor);
		
		JButton btnLogin = new JButton("Log In");
		btnLogin.setBounds(172, 348, 244, 47);
		btnLogin.setFont(new Font("Arial", Font.BOLD, 16));
		btnLogin.setForeground(new Color(0, 0, 51));
		btnLogin.setBackground(new Color(102, 153, 51));
		contentPane.add(btnLogin);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(217, 145, 330, 40);
		passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(passwordField);
		
		//Add show password checkbox
		JCheckBox showPasswordCheckBox = new JCheckBox("Show Password");
		showPasswordCheckBox.setBounds(217, 202, 199, 52);
		showPasswordCheckBox.setEnabled(true);
		showPasswordCheckBox.setForeground(new Color(0, 0, 51));
		showPasswordCheckBox.setBackground(new Color(204, 255, 204));
		showPasswordCheckBox.setFont(new Font("Arial", Font.PLAIN, 16));
		contentPane.add(showPasswordCheckBox);	
        showPasswordCheckBox.setBackground(primaryColor);
     
		
		// Action listener to toggle password visibility
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
        
        //Disable when role checkbox is selected
        Host.addActionListener(e -> {
        		Donor.setEnabled(!Host.isSelected());
        	});
        
        Donor.addActionListener(e -> {
    		Host.setEnabled(!Donor.isSelected());
    	});
        
		
        }
}
        
