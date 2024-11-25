package view;

import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*;

import controller.RegisterController;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Year;
import java.awt.Font;

public class Register_Frame extends JFrame {

	private JLabel RoleLabel;
    private JTextField SchoolTextField;
    private JTextField CohortTextField;
    private JRadioButton HostRadioButton;
    private JRadioButton DonorRadioButton;
    private ButtonGroup roleGroup;
    private JTextField BloodGroupTextField;
    private JTextField PhoneNumberTextField;
    private JTextField MailTextField;
    private JTextField AddressTextField;
    private JTextField AgeTextField;
    private JTextField NameTextField;
    private JLabel NameLabel;
    private JLabel AgeLabel;
    private JLabel AddressLabel;
    private JLabel MailLabel;
    private JLabel PhoneNumberLabel;
    private JLabel SchoolLabel;
    private JLabel MajorLabel;
    private JLabel CohortLabel;
    private JLabel BloodGroupLabel;
    private JLabel SexLabel;
    private JTextField MajorTextField;
    private JRadioButton MaleRadioButton;
    private JRadioButton FemaleRadioButton;
    private JLabel StudentIdLabel;
    private JTextField StudentIdTextField;
    private JButton registerButton;
    private RegisterController controller;
    private JButton ReturnLogInButton;
    private JLabel UsernameLabel;
    private JLabel PasswordLabel;
    private JLabel RecoveryQuestionLabel;
    private JLabel RecoveryAnswerLabel;
    private JTextField UsernameTextField;
    private JTextField PasswordTextField;
    private JTextField RecoveryAnswerTextField;
	private JComboBox RecoveryQuestionComboBox;
	private JLabel ConfirmPasswordLabel;
	private JTextField ConfirmPasswordTextField;



    public Register_Frame() {
    	
        this.setTitle("Register");
        this.setBounds(100, 100, 600, 650);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        getContentPane().add(panel);
        GridBagLayout layout = new GridBagLayout();
        layout.columnWeights = new double[]{0.0, 1.0, 0.0};
        layout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        panel.setLayout(layout);

        // Create a new GridBagConstraints object for each component
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding for all components
        
        ReturnLogInButton = new JButton("← Return to log in page");
        ReturnLogInButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        GridBagConstraints gbc_returnLogInButton = new GridBagConstraints();
        gbc_returnLogInButton.insets = new Insets(5, 5, 5, 5);
        gbc_returnLogInButton.gridx = 0;
        gbc_returnLogInButton.gridy = 0;
        panel.add(ReturnLogInButton, gbc_returnLogInButton);
        //Add listener to ReturnLogInButton
        ActionListener returnlogin = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.handleSwitchLogIn(Register_Frame.this);
			}
		};
        ReturnLogInButton.addActionListener(returnlogin);
        

        // Role Selection
        RoleLabel = new JLabel("Select Role:");
        GridBagConstraints gbc_RoleLabel = new GridBagConstraints();
        gbc_RoleLabel.insets = new Insets(5, 5, 5, 5);
        gbc_RoleLabel.gridx = 0;
        gbc_RoleLabel.gridy = 1;
        gbc_RoleLabel.anchor = GridBagConstraints.WEST;
        gbc_RoleLabel.fill = GridBagConstraints.NONE;
        panel.add(RoleLabel, gbc_RoleLabel);
        
        //Host
        roleGroup = new ButtonGroup();
        HostRadioButton = new JRadioButton("Host");
        GridBagConstraints gbc_HostRadioButton = new GridBagConstraints();
        gbc_HostRadioButton.insets = new Insets(5,5,5,5);
        gbc_HostRadioButton.gridx = 1;
        gbc_HostRadioButton.gridy = 1;
        panel.add(HostRadioButton, gbc_HostRadioButton);
        roleGroup.add(HostRadioButton);
        //Disable textfields if user choose Host
        HostRadioButton.addActionListener(e -> {
            if (HostRadioButton.isSelected()) {
                // Disable the fields that are not required for Host
                StudentIdTextField.setEnabled(false); 
                AgeTextField.setEnabled(false);
                AddressTextField.setEnabled(false);
                SchoolTextField.setEnabled(false); 
                MajorTextField.setEnabled(false); 
                CohortTextField.setEnabled(false); 
                BloodGroupTextField.setEnabled(false);               
                MaleRadioButton.setEnabled(false); // Sex is not needed for Host
                FemaleRadioButton.setEnabled(false); // Sex is not needed for Host
                
                //Disable label corresponding
                StudentIdLabel.setEnabled(false); 
                AgeLabel.setEnabled(false);
                AddressLabel.setEnabled(false);               
                SchoolLabel.setEnabled(false); 
                MajorLabel.setEnabled(false); 
                CohortLabel.setEnabled(false); 
                BloodGroupLabel.setEnabled(false);
                SexLabel.setEnabled(false);
            }
        });
        
        //Donor
        DonorRadioButton = new JRadioButton("Donor", true);
        GridBagConstraints gbc_donorRadioButton = new GridBagConstraints();
        gbc_donorRadioButton.insets = new Insets(5,5,5,5);
        gbc_donorRadioButton.gridx = 2;
        gbc_donorRadioButton.gridy = 1;
        panel.add(DonorRadioButton, gbc_donorRadioButton);
        roleGroup.add(DonorRadioButton);     
        
        //Disable textfields if user choose Donor
        DonorRadioButton.addActionListener(e -> {
            if (DonorRadioButton.isSelected()) {
                // Enable the fields that are required for Donor
                StudentIdTextField.setEnabled(true); 
                AgeTextField.setEnabled(true);
                AddressTextField.setEnabled(true);
                SchoolTextField.setEnabled(true); 
                MajorTextField.setEnabled(true); 
                CohortTextField.setEnabled(true);
                BloodGroupTextField.setEnabled(true);
                MaleRadioButton.setEnabled(true); 
                FemaleRadioButton.setEnabled(true); 
                
              //Enable label corresponding
                StudentIdLabel.setEnabled(true); 
                AgeLabel.setEnabled(true);
                AddressLabel.setEnabled(true);
                SchoolLabel.setEnabled(true); 
                MajorLabel.setEnabled(true); 
                CohortLabel.setEnabled(true); 
                BloodGroupLabel.setEnabled(true);
                SexLabel.setEnabled(true);
            }
        });
        
 
        StudentIdLabel = new JLabel("Your Student ID:");
        GridBagConstraints gbc_studentIdLabel = new GridBagConstraints();
        gbc_studentIdLabel.anchor = GridBagConstraints.WEST;
        gbc_studentIdLabel.insets = new Insets(5,5,5,5);
        gbc_studentIdLabel.gridx = 0;
        gbc_studentIdLabel.gridy = 2;
        panel.add(StudentIdLabel, gbc_studentIdLabel);
        
        StudentIdTextField = new JTextField(20);
        GridBagConstraints gbc_studentIdTextField = new GridBagConstraints();
        gbc_studentIdTextField.gridwidth = 2;
        gbc_studentIdTextField.insets = new Insets(5,5,5,5);
        gbc_studentIdTextField.fill = GridBagConstraints.HORIZONTAL;
        gbc_studentIdTextField.gridx = 1;
        gbc_studentIdTextField.gridy = 2;
        panel.add(StudentIdTextField, gbc_studentIdTextField);
        
        NameLabel = new JLabel("Your name:");
        GridBagConstraints gbc_nameLabel = new GridBagConstraints();
        gbc_nameLabel.insets = new Insets(5,5,5,5);
        gbc_nameLabel.anchor = GridBagConstraints.WEST;
        gbc_nameLabel.gridx = 0;
        gbc_nameLabel.gridy = 3;
        panel.add(NameLabel, gbc_nameLabel);
        
        NameTextField = new JTextField(20);
        GridBagConstraints gbc_nameTextField = new GridBagConstraints();
        gbc_nameTextField.gridwidth = 2;
        gbc_nameTextField.insets = new Insets(5,5,5,5);
        gbc_nameTextField.fill = GridBagConstraints.HORIZONTAL;
        gbc_nameTextField.gridx = 1;
        gbc_nameTextField.gridy = 3;
        panel.add(NameTextField, gbc_nameTextField);
        
        AgeLabel = new JLabel("Your age:");
        GridBagConstraints gbc_ageLabel = new GridBagConstraints();
        gbc_ageLabel.insets = new Insets(5,5,5,5);
        gbc_ageLabel.anchor = GridBagConstraints.WEST;
        gbc_ageLabel.gridx = 0;
        gbc_ageLabel.gridy = 4;
        panel.add(AgeLabel, gbc_ageLabel);
        
        AgeTextField = new JTextField(20);
        GridBagConstraints gbc_ageTextField = new GridBagConstraints();
        gbc_ageTextField.gridwidth = 2;
        gbc_ageTextField.insets = new Insets(5,5,5,5);
        gbc_ageTextField.fill = GridBagConstraints.HORIZONTAL;
        gbc_ageTextField.gridx = 1;
        gbc_ageTextField.gridy = 4;
        panel.add(AgeTextField, gbc_ageTextField);
        
        AddressLabel = new JLabel("Your address:");
        GridBagConstraints gbc_addressLabel = new GridBagConstraints();
        gbc_addressLabel.insets = new Insets(5,5,5,5);
        gbc_addressLabel.anchor = GridBagConstraints.WEST;
        gbc_addressLabel.gridx = 0;
        gbc_addressLabel.gridy = 5;
        panel.add(AddressLabel, gbc_addressLabel);
        
        AddressTextField = new JTextField(20);
        GridBagConstraints gbc_addressTextField = new GridBagConstraints();
        gbc_addressTextField.gridwidth = 2;
        gbc_addressTextField.insets = new Insets(5,5,5,5);
        gbc_addressTextField.fill = GridBagConstraints.HORIZONTAL;
        gbc_addressTextField.gridx = 1;
        gbc_addressTextField.gridy = 5;
        panel.add(AddressTextField, gbc_addressTextField);
        
        MailLabel = new JLabel("Your mail:");
        GridBagConstraints gbc_mailLabel = new GridBagConstraints();
        gbc_mailLabel.insets = new Insets(5,5,5,5);
        gbc_mailLabel.anchor = GridBagConstraints.WEST;
        gbc_mailLabel.gridx = 0;
        gbc_mailLabel.gridy = 6;
        panel.add(MailLabel, gbc_mailLabel);
        
        MailTextField = new JTextField(20);
        GridBagConstraints gbc_mailTextField = new GridBagConstraints();
        gbc_mailTextField.gridwidth = 2;
        gbc_mailTextField.insets = new Insets(5,5,5,5);
        gbc_mailTextField.fill = GridBagConstraints.HORIZONTAL;
        gbc_mailTextField.gridx = 1;
        gbc_mailTextField.gridy = 6;
        panel.add(MailTextField, gbc_mailTextField);
        
        PhoneNumberLabel = new JLabel("Your  phone number:");
        GridBagConstraints gbc_phoneNumberLabel = new GridBagConstraints();
        gbc_phoneNumberLabel.insets = new Insets(5,5,5,5);
        gbc_phoneNumberLabel.anchor = GridBagConstraints.WEST;
        gbc_phoneNumberLabel.gridx = 0;
        gbc_phoneNumberLabel.gridy = 7;
        panel.add(PhoneNumberLabel, gbc_phoneNumberLabel);
        
        PhoneNumberTextField = new JTextField(20);
        GridBagConstraints gbc_phoneNumberTextField = new GridBagConstraints();
        gbc_phoneNumberTextField.gridwidth = 2;
        gbc_phoneNumberTextField.insets = new Insets(5,5,5,5);
        gbc_phoneNumberTextField.fill = GridBagConstraints.HORIZONTAL;
        gbc_phoneNumberTextField.gridx = 1;
        gbc_phoneNumberTextField.gridy = 7;
        panel.add(PhoneNumberTextField, gbc_phoneNumberTextField);

        // Username Label and Field
        SchoolLabel = new JLabel("Your school:");
        GridBagConstraints gbc_schoolLabel = new GridBagConstraints();
        gbc_schoolLabel.insets = new Insets(5, 5, 5, 5);
        gbc_schoolLabel.gridx = 0;
        gbc_schoolLabel.gridy = 8;
        gbc_schoolLabel.anchor = GridBagConstraints.WEST;
        gbc_schoolLabel.fill = GridBagConstraints.NONE;
        panel.add(SchoolLabel, gbc_schoolLabel);

        SchoolTextField = new JTextField(20);
        GridBagConstraints gbc_schoolTextField = new GridBagConstraints();
        gbc_schoolTextField.gridwidth = 2;
        gbc_schoolTextField.insets = new Insets(5,5,5,5);
        gbc_schoolTextField.gridx = 1;
        gbc_schoolTextField.gridy = 8;
        gbc_schoolTextField.fill = GridBagConstraints.HORIZONTAL; // Allow text field to stretch horizontally
        panel.add(SchoolTextField, gbc_schoolTextField);

        // Password Label and Field
        MajorLabel = new JLabel("Your major:\r\n");
        GridBagConstraints gbc_majorLabel = new GridBagConstraints();
        gbc_majorLabel.insets = new Insets(5, 5, 5, 5);
        gbc_majorLabel.gridx = 0;
        gbc_majorLabel.gridy = 9;
        gbc_majorLabel.anchor = GridBagConstraints.WEST;
        gbc_majorLabel.fill = GridBagConstraints.NONE;
        panel.add(MajorLabel, gbc_majorLabel);
        
        MajorTextField = new JTextField(20);
        GridBagConstraints gbc_majorTextField = new GridBagConstraints();
        gbc_majorTextField.gridwidth = 2;
        gbc_majorTextField.insets = new Insets(5,5,5,5);
        gbc_majorTextField.fill = GridBagConstraints.HORIZONTAL;
        gbc_majorTextField.gridx = 1;
        gbc_majorTextField.gridy = 9;
        panel.add(MajorTextField, gbc_majorTextField);

        // Email Label and Field
        CohortLabel = new JLabel("Your cohort (VD: 48) :");
        GridBagConstraints gbc_cohortLabel = new GridBagConstraints();
        gbc_cohortLabel.insets = new Insets(5, 5, 5, 5);
        gbc_cohortLabel.gridx = 0;
        gbc_cohortLabel.gridy = 10;
        gbc_cohortLabel.anchor = GridBagConstraints.WEST;
        gbc_cohortLabel.fill = GridBagConstraints.NONE;
        panel.add(CohortLabel, gbc_cohortLabel);

        CohortTextField = new JTextField(20);
        GridBagConstraints gbc_cohortTextField = new GridBagConstraints();
        gbc_cohortTextField.gridwidth = 2;
        gbc_cohortTextField.insets = new Insets(5,5,5,5);
        gbc_cohortTextField.gridx = 1;
        gbc_cohortTextField.gridy = 10;
        gbc_cohortTextField.fill = GridBagConstraints.HORIZONTAL; // Allow text field to stretch horizontally
        panel.add(CohortTextField, gbc_cohortTextField);
        roleGroup = new ButtonGroup();
                        
        BloodGroupLabel = new JLabel("Your blood group:");
        GridBagConstraints gbc_bloodGroupLabel = new GridBagConstraints();
        gbc_bloodGroupLabel.insets = new Insets(5, 5, 5, 5);
        gbc_bloodGroupLabel.anchor = GridBagConstraints.WEST;
        gbc_bloodGroupLabel.gridx = 0;
        gbc_bloodGroupLabel.gridy = 11;
        panel.add(BloodGroupLabel, gbc_bloodGroupLabel);
        
        BloodGroupTextField = new JTextField(20);
        GridBagConstraints gbc_bloodGroupTextField = new GridBagConstraints();
        gbc_bloodGroupTextField.gridwidth = 2;
        gbc_bloodGroupTextField.insets = new Insets(5,5,5,5);
        gbc_bloodGroupTextField.fill = GridBagConstraints.HORIZONTAL;
        gbc_bloodGroupTextField.gridx = 1;
        gbc_bloodGroupTextField.gridy = 11;
        panel.add(BloodGroupTextField, gbc_bloodGroupTextField);
        
        UsernameLabel = new JLabel("Your username:");
        GridBagConstraints gbc_usernameLabel = new GridBagConstraints();
        gbc_usernameLabel.anchor = GridBagConstraints.WEST;
        gbc_usernameLabel.insets = new Insets(5, 5, 5, 5);
        gbc_usernameLabel.gridx = 0;
        gbc_usernameLabel.gridy = 12;
        panel.add(UsernameLabel, gbc_usernameLabel);
        
        UsernameTextField = new JTextField(20);
        GridBagConstraints gbc_usernameTextField = new GridBagConstraints();
        gbc_usernameTextField.gridwidth = 2;
        gbc_usernameTextField.insets = new Insets(5,5,5,5);
        gbc_usernameTextField.fill = GridBagConstraints.HORIZONTAL;
        gbc_usernameTextField.gridx = 1;
        gbc_usernameTextField.gridy = 12;
        panel.add(UsernameTextField, gbc_usernameTextField);
        
        PasswordLabel = new JLabel("Your password:");
        GridBagConstraints gbc_confirmPasswordLabel = new GridBagConstraints();
        gbc_confirmPasswordLabel.anchor = GridBagConstraints.WEST;
        gbc_confirmPasswordLabel.insets = new Insets(5,5,5,5);
        gbc_confirmPasswordLabel.gridx = 0;
        gbc_confirmPasswordLabel.gridy = 13;
        panel.add(PasswordLabel, gbc_confirmPasswordLabel);
        
        PasswordTextField = new JTextField(20);
        GridBagConstraints gbc_passwordTextField = new GridBagConstraints();
        gbc_passwordTextField.gridwidth = 2;
        gbc_passwordTextField.insets = new Insets(5,5,5,5);
        gbc_passwordTextField.fill = GridBagConstraints.HORIZONTAL;
        gbc_passwordTextField.gridx = 1;
        gbc_passwordTextField.gridy = 13;
        panel.add(PasswordTextField, gbc_passwordTextField);
        gbc_confirmPasswordLabel.anchor = GridBagConstraints.WEST;
        gbc_confirmPasswordLabel.insets = new Insets(5, 5, 5, 5);
        gbc_confirmPasswordLabel.gridx = 0;
        gbc_confirmPasswordLabel.gridy = 14;
        
        ConfirmPasswordLabel = new JLabel("Confirm password:");
        GridBagConstraints gbc_confirmPasswordLabel1 = new GridBagConstraints();
        gbc_confirmPasswordLabel1.anchor = GridBagConstraints.WEST;
        gbc_confirmPasswordLabel1.insets = new Insets(5, 5, 5, 5);
        gbc_confirmPasswordLabel1.gridx = 0;
        gbc_confirmPasswordLabel1.gridy = 14;
        panel.add(ConfirmPasswordLabel, gbc_confirmPasswordLabel1);
        
        ConfirmPasswordTextField = new JTextField(20);
        GridBagConstraints gbc_confirmPasswordTextField = new GridBagConstraints();
        gbc_confirmPasswordTextField.gridwidth = 2;
        gbc_confirmPasswordTextField.insets = new Insets(5, 5, 5, 5);
        gbc_confirmPasswordTextField.fill = GridBagConstraints.HORIZONTAL;
        gbc_confirmPasswordTextField.gridx = 1;
        gbc_confirmPasswordTextField.gridy = 14;
        panel.add(ConfirmPasswordTextField, gbc_confirmPasswordTextField);
        
        
        RecoveryQuestionLabel = new JLabel("Your recovery question:");
        GridBagConstraints gbc_recoveryQuestionLabel = new GridBagConstraints();
        gbc_recoveryQuestionLabel.anchor = GridBagConstraints.WEST;
        gbc_recoveryQuestionLabel.insets = new Insets(5,5,5,5);
        gbc_recoveryQuestionLabel.gridx = 0;
        gbc_recoveryQuestionLabel.gridy = 15;
        panel.add(RecoveryQuestionLabel, gbc_recoveryQuestionLabel);
        
        String[] options = {" Your phone number", " Your favorite celebrity", " Your pet name", " Your favorite film name"};     
        RecoveryQuestionComboBox = new JComboBox<>(options);
        GridBagConstraints gbc_recoveryQuestionComboBox = new GridBagConstraints();
        gbc_recoveryQuestionComboBox.gridwidth = 2;
        gbc_recoveryQuestionComboBox.insets = new Insets(5, 5, 5, 5);
        gbc_recoveryQuestionComboBox.fill = GridBagConstraints.HORIZONTAL;
        gbc_recoveryQuestionComboBox.gridx = 1;
        gbc_recoveryQuestionComboBox.gridy = 15;
        panel.add(RecoveryQuestionComboBox, gbc_recoveryQuestionComboBox);
        
        RecoveryAnswerLabel = new JLabel("Your recovery answer:");
        GridBagConstraints gbc_recoveryAnswerLabel = new GridBagConstraints();
        gbc_recoveryAnswerLabel.anchor = GridBagConstraints.WEST;
        gbc_recoveryAnswerLabel.insets = new Insets(5,5,5,5);
        gbc_recoveryAnswerLabel.gridx = 0;
        gbc_recoveryAnswerLabel.gridy = 16;
        panel.add(RecoveryAnswerLabel, gbc_recoveryAnswerLabel);
        
        RecoveryAnswerTextField = new JTextField(20);
        GridBagConstraints gbc_recoveryAnswerTextField = new GridBagConstraints();
        gbc_recoveryAnswerTextField.gridwidth = 2;
        gbc_recoveryAnswerTextField.insets = new Insets(5,5,5,5);
        gbc_recoveryAnswerTextField.fill = GridBagConstraints.HORIZONTAL;
        gbc_recoveryAnswerTextField.gridx = 1;
        gbc_recoveryAnswerTextField.gridy = 16;
        panel.add(RecoveryAnswerTextField, gbc_recoveryAnswerTextField);
        
        SexLabel = new JLabel("Male/Female:");
        GridBagConstraints gbc_sexLabel = new GridBagConstraints();
        gbc_sexLabel.insets = new Insets(5,5,5,5);
        gbc_sexLabel.anchor = GridBagConstraints.WEST;
        gbc_sexLabel.gridx = 0;
        gbc_sexLabel.gridy = 17;
        panel.add(SexLabel, gbc_sexLabel);
        
        MaleRadioButton = new JRadioButton("Male",true);
        GridBagConstraints gbc_maleRadioButton = new GridBagConstraints();
        gbc_maleRadioButton.insets = new Insets(5,5,5,5);
        gbc_maleRadioButton.gridx = 1;
        gbc_maleRadioButton.gridy = 17;
        panel.add(MaleRadioButton, gbc_maleRadioButton);
        
        FemaleRadioButton = new JRadioButton("Female");
        GridBagConstraints gbc_femaleRadioButton = new GridBagConstraints();
        gbc_femaleRadioButton.insets = new Insets(5,5,5,5);
        gbc_femaleRadioButton.gridx = 2;
        gbc_femaleRadioButton.gridy = 17;
        panel.add(FemaleRadioButton, gbc_femaleRadioButton);

        // Register Button
        registerButton = new JButton("Register");
        GridBagConstraints gbcRegisterButton = new GridBagConstraints();
        gbcRegisterButton.gridx = 0;
        gbcRegisterButton.gridy = 18;
        gbcRegisterButton.gridwidth = 4; // Button spans two columns
        gbcRegisterButton.fill = GridBagConstraints.HORIZONTAL; // Button stretches horizontally
        gbcRegisterButton.insets = new Insets(15, 5, 0, 0); // Increased bottom padding
        panel.add(registerButton, gbcRegisterButton);

        registerButton.addActionListener(e -> {
        	String name = NameTextField.getText().trim();
        	String mail  = MailTextField.getText().trim();
        	String phonenumber = PhoneNumberTextField.getText().trim();    
        	String username  = UsernameTextField.getText().trim();
        	String password = PasswordTextField.getText().trim();       	
        	String confirmpassword = ConfirmPasswordTextField.getText().trim();
        	String question= (String)RecoveryQuestionComboBox.getSelectedItem();
        	String answer = RecoveryAnswerTextField.getText().trim();
        	String role = DonorRadioButton.isSelected() ? "Donor" : "Host";
        	
        	if (name.isEmpty() || mail.isEmpty() || phonenumber.isEmpty() || username.isEmpty() || password.isEmpty() || confirmpassword.isEmpty() || answer.isEmpty()) {
        		    JOptionPane.showMessageDialog(this, "Please fill in all information.", "Input Error", JOptionPane.ERROR_MESSAGE);
        		    return;
        	}
        	
        	if (!isValidEmail(mail)) {
                JOptionPane.showMessageDialog(this, "Please enter a valid email address. Email format: user@example.com", "Invalid Email", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if(!isValidPhoneNumber(phonenumber)) {
            	JOptionPane.showMessageDialog(this, "Phone number must contain only digits and be 10 or 11 characters long!", "Invalid Phone number", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if(!isPasswordRight(password, confirmpassword)) {
            	JOptionPane.showMessageDialog(this, "Your confirm password is not the same with your password!", "Invalid password", JOptionPane.ERROR_MESSAGE);
                return;
            }
        	//If user is donor
        	if(role.equals("Donor")) {
        		String s_id = StudentIdTextField.getText().trim();           	
            	String s_age = AgeTextField.getText().trim();
            	String address = AddressTextField.getText().trim();     	
            	String school = SchoolTextField.getText().trim();
            	String major = MajorTextField.getText().trim();
            	String s_cohort = CohortTextField.getText().trim();
            	String bloodgroup = BloodGroupTextField.getText().trim();
            	String sex = MaleRadioButton.isSelected() ? "Male" : "Female";           	
            	int i_age=0;
            	int i_cohort=0;
            	
            	if(!isValidID(s_id)) {
            		JOptionPane.showMessageDialog(this, "Your Student ID must be valid!", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
            	}
            	
            	//validate age
            	try {
    				i_age = Integer.parseInt(s_age);			
    				if(i_age < 18 || i_age >60) {
    					JOptionPane.showMessageDialog(this, "The age must be in range 18-60!", "Input Error", JOptionPane.ERROR_MESSAGE);
    	                return;
    				}
    			} catch (NumberFormatException e2) {
    				JOptionPane.showMessageDialog(this, "The age must be a valid number!", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
    			}
            	//validate cohort
            	try {
            		i_cohort = Integer.parseInt(s_cohort);
    				if(i_cohort <= Year.now().getValue() - 1984 || i_cohort >= Year.now().getValue() - 1973) {					
    					JOptionPane.showMessageDialog(this, "Your cohort is invalid", "Input Error", JOptionPane.ERROR_MESSAGE);
    	                return;
    				}
    			} catch (NumberFormatException e2) {
    				JOptionPane.showMessageDialog(this, "The cohort must be a valid number!", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
    			}
            	
            	if(!isValidBloodGroup(bloodgroup)) {
            		JOptionPane.showMessageDialog(this, "Your blood group must be valid!", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
            	}
            	try {
            		//adding donor to database
            		boolean success = controller.RegistertoAccount(s_id, name, i_age, address, mail, 
                		    phonenumber, school, major, 
                		    i_cohort, bloodgroup,sex, username, password, question, answer, role);
            		if(success) {
            			JOptionPane.showMessageDialog(this, "Donor registered successfully!");
            			//Switch to Log In
            			controller.handleSwitchLogIn(this);
            			return;
            		}
                    
				} catch (Exception e2) {
					e2.printStackTrace();
					JOptionPane.showMessageDialog(this, "Create donor account error: " + e2.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
				}
            	
        	}
        	//Adding host to database
            boolean success = controller.RegistertoAccount("", name, 0, "", mail, phonenumber, "", "", 0, "", "", username, password, question, answer, role);
            if(success) {
            	JOptionPane.showMessageDialog(this, "Host registered successfully!");
            	//Switch to Log In
    			controller.handleSwitchLogIn(this);
            	return;
            }
            else {
            	JOptionPane.showMessageDialog(this, "Adding user error!", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
			
        });
    }
    
    private boolean isPasswordRight(String password, String confirmpassword) {
		return password.equals(confirmpassword);
	}

	private boolean isValidID(String id) {
    	String idRegex = "^[Bb][0-9]{7}$";
    	return id.matches(idRegex);
    }

    private boolean isValidPhoneNumber(String number) {
    	String numberRegex = "^[0-9]{10,11}$";
        return number.matches(numberRegex);
    }
    
    private boolean isValidEmail(String email) {
    	String emailRegex = "^(?!.*\\.\\.)([\\w.%+-]+)@([\\w-]+\\.)+[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }
    
    private boolean isValidBloodGroup(String bloodgroup) {
        String bloodgroupRegex = "^(A|B|AB|O)[+-]$";
        return bloodgroup.matches(bloodgroupRegex);
    }
    
    public void setController(RegisterController con) {
    	controller = con;
    }
}
