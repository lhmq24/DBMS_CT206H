package view;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.connector;

//import javax.swing.JFrame;


public class Main {

	public static void main(String[] args) {
		connector conn = new connector("localhost","demo","root");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LogIn frame = new LogIn();
					frame.setVisible(true);			
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		conn.close();
		System.out.println("Connection is closed");
	}

}
