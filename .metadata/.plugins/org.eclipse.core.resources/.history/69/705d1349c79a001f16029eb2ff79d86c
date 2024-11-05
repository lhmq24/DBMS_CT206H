package demo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Jcombobox extends JFrame {
    private JComboBox<String> comboBox;
    private JLabel selectedLabel;

    public Jcombobox() {
        // Set up the frame
        setTitle("ComboBox Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        setSize(300, 150);
        
        // Create the JComboBox
        String[] options = {"Option 1", "Option 2", "Option 3", "Option 4"};
        comboBox = new JComboBox<>(options);
        comboBox.setSelectedIndex(0); // Set default selection
        
  
        
        // Add an ActionListener to handle item selection
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateSelectedLabel();
            }
        });

        // Add components to the frame
        add(comboBox);
        add(selectedLabel);
        
        // Make the frame visible
        setVisible(true);
    }

    // Method to update the label based on selected item
    private void updateSelectedLabel() {
        String selectedOption = (String) comboBox.getSelectedItem();
        selectedLabel.setText("Selected: " + selectedOption);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Jcombobox());
    }
}
