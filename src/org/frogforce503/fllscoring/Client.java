package org.frogforce503.fllscoring;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.UIManager.*;

public class Client implements Runnable {
	//GUI element declarations
	private JFrame frame;
	private JPanel cp;

	private boolean fullscreen = false;

	public void run() {
		frame = new JFrame("Scores");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		cp = new ClientPanel();
		frame.setContentPane(cp);

		frame.pack();
		frame.setVisible(true);
	}

	public Client() {
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
		} catch(Exception e) {}

		try {
			Font font = Font.createFont(Font.TRUETYPE_FONT, ClassLoader.getSystemResourceAsStream("rsc/Roboto-Light.ttf"));
			GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
		} catch(Exception e) {}

		new Client();
	}
}
