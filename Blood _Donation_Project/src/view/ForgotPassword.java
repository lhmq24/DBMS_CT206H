package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import controller.ForgotPasswordController;
import model.Connector;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import java.sql.Connection;
import java.sql.PreparedStatement;
import model.Connector;


public class ForgotPassword extends JFrame {

	private static final long serialVersionUID = 1L;
	private ForgotPasswordController controller;
	private JPanel contentPane;
	private JButton btnNewButton;
	private JLabel usernameLabel;
	private JTextField txtEnterUsername;
	private JLabel recoveryQuestionLabel;
	private JComboBox recoveryCombobox;
	private JLabel recoveryQuestionAnswerLabel;
	private JTextField recoveryAnswertextField;
	private JCheckBox Host;
	private JCheckBox Donor;
	private JButton changepasswordButton;

	/**
	 * Create the frame.
	 */
	public ForgotPassword() {
		//add connection to the frame

		 
		setTitle("Forgot Password");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 255, 240));
		contentPane.setBorder(new EmptyBorder(20, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Primary color for pane, theme
		Color primaryColor = contentPane.getBackground();
		
		//Set Back to Log in button
		//Error: log in -> forgot OK, forgot -> login ERROR
		btnNewButton = new JButton(" ← Back to Log In");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton.setHorizontalAlignment(SwingConstants.LEFT);
		btnNewButton.setBounds(0, 13, 150, 23);
		btnNewButton.setBackground(primaryColor);
		btnNewButton.setBorderPainted(false);
		btnNewButton.setFocusPainted(false);
		btnNewButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               controller.handleSwitchLogIn(ForgotPassword.this);
            }
        });
		contentPane.add(btnNewButton);
		
		//Set enter username label
		usernameLabel = new JLabel("Enter Username");
		usernameLabel.setFont(new Font("Arial", Font.BOLD, 16));
		usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		usernameLabel.setBounds(0, 43, 161, 47);
		contentPane.add(usernameLabel);
		
		//Set Username textfield
		txtEnterUsername = new JTextField();
		txtEnterUsername.setFont(new Font("Arial", Font.PLAIN, 14));
		txtEnterUsername.setBounds(159, 39, 277, 40);
		contentPane.add(txtEnterUsername);
		txtEnterUsername.setColumns(12);
		
		//Set recovery label		
		recoveryQuestionLabel = new JLabel("Choose your \r\nrecovery question");
		recoveryQuestionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		recoveryQuestionLabel.setFont(new Font("Arial", Font.BOLD, 16));
		recoveryQuestionLabel.setBounds(0, 69, 270, 80);
		contentPane.add(recoveryQuestionLabel);
		
		//Set recovery combobox
		String[] options = {" Your phone number", " Your favorite celebrity", " Your pet name", " Your favorite film name"};     
		recoveryCombobox = new JComboBox<>(options);
		recoveryCombobox.setSelectedIndex(0); // Set default selection
		recoveryCombobox.setFont(new Font("Arial", Font.PLAIN, 14));
		recoveryCombobox.setEditable(true);
		recoveryCombobox.setBounds(265, 90, 171, 40);
		recoveryCombobox.setEditable(false);
		contentPane.add(recoveryCombobox);
		
		//set recovery question answer label
		recoveryQuestionAnswerLabel = new JLabel("Enter your answer");
		recoveryQuestionAnswerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		recoveryQuestionAnswerLabel.setFont(new Font("Arial", Font.BOLD, 16));
		recoveryQuestionAnswerLabel.setBounds(0, 118, 171, 80);
		contentPane.add(recoveryQuestionAnswerLabel);
		
		
		//set recovery answer textField
		recoveryAnswertextField = new JTextField();
		recoveryAnswertextField.setFont(new Font("Arial", Font.PLAIN, 14));
		recoveryAnswertextField.setColumns(12);
		recoveryAnswertextField.setBounds(181, 138, 255, 40);
		contentPane.add(recoveryAnswertextField);
		
		//Add 2 role checkboxes
		Host = new JCheckBox("Host");
		Host.setBackground(primaryColor);
		Host.setFont(new Font("Arial", Font.PLAIN, 16));
		Host.setBounds(109, 185, 124, 47);
		contentPane.add(Host);
		
		Donor = new JCheckBox("Donor");
		Donor.setBackground(primaryColor);
		Donor.setFont(new Font("Arial", Font.PLAIN, 16));
		Donor.setBounds(276, 185, 114, 47);
		contentPane.add(Donor);
		
		//Disable a role checkbox when other role checkbox is selected
        Host.addActionListener(e -> {
        		Donor.setEnabled(!Host.isSelected());
        	});
        
        Donor.addActionListener(e -> {
    		Host.setEnabled(!Donor.isSelected());
    	});
		
		//Set submit button
		changepasswordButton = new JButton("Retrieve Password");
		changepasswordButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//Check database for verify
				String username = txtEnterUsername.getText().trim();
				String question= (String)recoveryCombobox.getSelectedItem();
			    String answer = recoveryAnswertextField.getText().trim();
			    String role = Host.isSelected() ? new String("Host") : new String("Donor");
				controller.handleForgotPassword(username, question, answer, role);
			}
		});
		changepasswordButton.setBounds(140, 229, 170, 23);
		changepasswordButton.setFocusPainted(false);
		changepasswordButton.setEnabled(false);
		contentPane.add(changepasswordButton);
		
		//Add listeners for text fields to set enable submit button
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
		recoveryAnswertextField.getDocument().addDocumentListener(fieldListener);
		
		//Add listeners to checkboxes
		Host.addItemListener(e -> validateInputs());
		Donor.addItemListener(e -> validateInputs());

	}
	
	private void validateInputs() {
	    String username = txtEnterUsername.getText().trim();
	    String answer = recoveryAnswertextField.getText().trim();
	    boolean isHostSelected = Host.isSelected();
	    boolean isDonorSelected = Donor.isSelected();

	    // Enable the button only if all conditions are met
	    changepasswordButton.setEnabled(!username.isEmpty() && !answer.isEmpty() && (isHostSelected ^ isDonorSelected));
	}
	
	public void setController(ForgotPasswordController ctl) {
		this.controller = ctl;
	}
}
