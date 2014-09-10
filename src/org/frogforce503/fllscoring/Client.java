package org.frogforce503.fllscoring;

import java.awt.Font;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import com.firebase.client.Firebase;

public class Client implements Runnable {
	// GUI element declarations
	private JFrame frame;
	private JPanel cp;
	
	private String event;

	public void run() {
		frame = new JFrame(event);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //TODO

		cp = new ClientPanel();
		frame.setContentPane(cp);

		frame.pack();
		frame.setVisible(true);
	}

	public Client(String event) {
		this.event = event;
		
		Firebase firebase = new Firebase("https://fll-scoring.firebaseio.com/").child(event);

		SwingUtilities.invokeLater(this);
	}

	public static void main(String[] args) {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Font font = Font.createFont(Font.TRUETYPE_FONT,
					ClassLoader.getSystemResourceAsStream("Roboto-Light.ttf"));
			GraphicsEnvironment.getLocalGraphicsEnvironment()
					.registerFont(font);
		} catch (Exception e) {
			e.printStackTrace();
		}

		new Client("Frog Force Frenzy"); //TODO
	}
}
