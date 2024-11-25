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
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
	private JLabel nullUsernameMessage;
	private JLabel nullPasswordMessage;
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
		passLabel.setBounds(10, 104, 197, 80);
		contentPane.add(passLabel);
		
		//Init password field
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
		passwordField.setBounds(217, 123, 330, 40);
		contentPane.add(passwordField);
		
		//Init show password checkbox
		showPasswordCheckBox = new JCheckBox("Show Password");
		showPasswordCheckBox.setEnabled(true);
		showPasswordCheckBox.setForeground(new Color(0, 0, 51));
		showPasswordCheckBox.setBackground(new Color(204, 255, 204));
		showPasswordCheckBox.setFont(new Font("Arial", Font.PLAIN, 16));
		showPasswordCheckBox.setLocation(217, 202);
		showPasswordCheckBox.setSize(148, 52);
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
		forgotPasswordLabel.setFont(new Font("Arial", Font.BOLD, 16));
		forgotPasswordLabel.setBounds(409, 213, 172, 31);
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
		Host.setBounds(182, 257, 124, 47);
		contentPane.add(Host);
		
		//Init Donor checkbox
		Donor = new JCheckBox("Donor");
		Donor.setBackground(primaryColor);
		Donor.setFont(new Font("Arial", Font.PLAIN, 16));
		Donor.setBounds(366, 257, 114, 47);
		contentPane.add(Donor);
		
		//Disable a role checkbox when other role checkbox is selected
        Host.addActionListener(e -> {
        		Donor.setEnabled(!Host.isSelected());
        	});
        
        Donor.addActionListener(e -> {
    		Host.setEnabled(!Donor.isSelected());
    	});
		
        //Init button LogIn
  		btnLogin = new JButton("Log In");
		btnLogin.setFont(new Font("Arial", Font.BOLD, 16));
		btnLogin.setForeground(new Color(0, 0, 51));
		btnLogin.setBackground(new Color(102, 153, 51));
		btnLogin.setBounds(185, 336, 244, 47);
		btnLogin.setFocusPainted(false);
		btnLogin.setEnabled(false);
		contentPane.add(btnLogin);
		
		nullUsernameMessage = new JLabel("Username can't be empty!");
		nullUsernameMessage.setForeground(new Color(255, 0, 0));
		nullUsernameMessage.setFont(new Font("Arial", Font.PLAIN, 11));
		nullUsernameMessage.setBounds(217, 82, 320, 14);
		nullUsernameMessage.setVisible(false);
		contentPane.add(nullUsernameMessage);
			
		nullPasswordMessage = new JLabel("Password can't be empty");
		nullPasswordMessage.setForeground(new Color(255, 0, 0));
		nullPasswordMessage.setFont(new Font("Arial", Font.PLAIN, 11));
		nullPasswordMessage.setBounds(217, 174, 330, 14);
		nullPasswordMessage.setVisible(false);
		contentPane.add(nullPasswordMessage);
		
		JLabel registerLabel = new JLabel("Don't have account? Register here!");
		registerLabel.setFont(new Font("Arial", Font.BOLD, 16));
		registerLabel.setBounds(178, 394, 314, 31);
		contentPane.add(registerLabel);
		
		//Add listener for register label
		registerLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {			
				controller.handleSwitchRegister();
			}
		});
		
		//check empty boxes - add listener for input fields
		DocumentListener fieldListener = new DocumentListener() {
		    @Override
		    public void insertUpdate(DocumentEvent e) {
		        validateInputs();
		    }

		    @Override
		    public void removeUpdate(DocumentEvent e) {
		        validateInputs();
		    }

		    @Override
		    public void changedUpdate(DocumentEvent e) {
		        validateInputs();
		    }
		    		 
		};
		txtEnterUsername.getDocument().addDocumentListener(fieldListener);
		passwordField.getDocument().addDocumentListener(fieldListener);

		// Add Item Listeners for Checkboxes
		Host.addItemListener(new ItemListener() {
		    @Override
		    public void itemStateChanged(ItemEvent e) {
		        validateInputs();
		    }
		});

		Donor.addItemListener(new ItemListener() {
		    @Override
		    public void itemStateChanged(ItemEvent e) {
		        validateInputs();
		    }
		});

			
		//Add listener for log in button, handle log in event when user clicks
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//Connect to database to verify user, then redirect to main page
				String username = txtEnterUsername.getText().trim();
			    String password = new String(passwordField.getPassword());
				String role = Host.isSelected() ? new String("Host") : new String("Donor");
				controller.handleLogin(username, password, role);
			}
		});
	}

	// Validation Method
	private void validateInputs() {
	    String username = txtEnterUsername.getText().trim();
	    String password = new String(passwordField.getPassword());
	    boolean isHostSelected = Host.isSelected();
	    boolean isDonorSelected = Donor.isSelected();

	    //Show caution if text field is empty
	    nullUsernameMessage.setVisible(username.isEmpty());
	    nullPasswordMessage.setVisible(password.isEmpty());
	    // Enable the button only if all conditions are met
	    btnLogin.setEnabled(!username.isEmpty() && !password.isEmpty() && (isHostSelected ^ isDonorSelected));
	}
	
	
	//set controller for view
	public void setController(LogInController ctl) {
		controller = ctl;
	}
}
        
