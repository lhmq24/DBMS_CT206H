/*Switch frame 
between system files*/




package demo;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Main Frame");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JButton switchButton = new JButton("Go to Second Frame");
        switchButton.addActionListener((ActionEvent e) -> {
            // Hide the current frame and show the second frame
            this.setVisible(false);
            SecondFrame secondFrame = new SecondFrame(this);
            secondFrame.setVisible(true);
        });

        add(switchButton);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}
