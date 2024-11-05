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
import javax.swing.JButton;
import javax.swing.JComboBox;

public class ForgotPassword extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField recoveryAnswertextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ForgotPassword frame = new ForgotPassword();
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
	public ForgotPassword() {
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
		JButton btnNewButton = new JButton(" ← Back to Log In");
		btnNewButton.setHorizontalAlignment(SwingConstants.LEFT);
		btnNewButton.setBounds(0, 13, 150, 23);
		btnNewButton.setBackground(primaryColor);
		btnNewButton.setBorderPainted(false);
		btnNewButton.setFocusPainted(false);
		btnNewButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Hide the current frame and show the second frame
                ForgotPassword.this.setVisible(false); // Hide the current frame
                LogIn login = new LogIn(); // Create new frame
                login.setVisible(true); // Show the new frame
            }
        });
		contentPane.add(btnNewButton);
		
		//Set enter username label
		JLabel usernameLabel = new JLabel("Enter Username");
		usernameLabel.setFont(new Font("Arial", Font.BOLD, 16));
		usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		usernameLabel.setBounds(0, 13, 171, 80);
		contentPane.add(usernameLabel);
		
		//Set Username textfield
		JTextField txtEnterUsername = new JTextField();
		txtEnterUsername.setFont(new Font("Arial", Font.PLAIN, 14));
		txtEnterUsername.setBounds(217, 31, 197, 40);
		contentPane.add(txtEnterUsername);
		txtEnterUsername.setColumns(12);
		
		//Set recovery label		
		JLabel recoveryQuestionLabel = new JLabel("Choose your \r\nrecovery question");
		recoveryQuestionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		recoveryQuestionLabel.setFont(new Font("Arial", Font.BOLD, 16));
		recoveryQuestionLabel.setBounds(0, 69, 270, 80);
		contentPane.add(recoveryQuestionLabel);
		
		//Set recovery combobox
		String[] options = {" Your phone number", " Your favorite celebrity", " Your pet name", " Your favorite film name"};     
		JComboBox recoveryCombobox = new JComboBox<>(options);
		recoveryCombobox.setSelectedIndex(0); // Set default selection
		recoveryCombobox.setFont(new Font("Arial", Font.PLAIN, 14));
		recoveryCombobox.setEditable(true);
		recoveryCombobox.setBounds(265, 90, 171, 40);
		recoveryCombobox.setEditable(false);
		contentPane.add(recoveryCombobox);
		
		//set recovery question answer label
		JLabel recoveryQuestionAnswerLabel = new JLabel("Enter Username");
		recoveryQuestionAnswerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		recoveryQuestionAnswerLabel.setFont(new Font("Arial", Font.BOLD, 16));
		recoveryQuestionAnswerLabel.setBounds(0, 118, 171, 80);
		contentPane.add(recoveryQuestionAnswerLabel);
		
		
		//set recovery answer textField
		recoveryAnswertextField = new JTextField();
		recoveryAnswertextField.setFont(new Font("Arial", Font.PLAIN, 14));
		recoveryAnswertextField.setColumns(12);
		recoveryAnswertextField.setBounds(217, 138, 197, 40);
		contentPane.add(recoveryAnswertextField);
		
		//Set submit button
		JButton changepasswordButton = new JButton("Change Password");
		changepasswordButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//Check database for verify
			}
		});
		changepasswordButton.setBounds(140, 229, 170, 23);
		changepasswordButton.setFocusPainted(false);
		contentPane.add(changepasswordButton);
		
		
		
		
		
	}
}