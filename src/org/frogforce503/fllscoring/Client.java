package org.frogforce503.fllscoring;

import java.awt.*;
import javax.swing.*;
import javax.swing.UIManager.*;

public class Client implements Runnable {
	//GUI element declarations
	private JFrame frame;
	private Container cp;

	public void run() {
		frame = new JFrame("Scores");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		cp = frame.getContentPane();
		cp.setLayout(null);
		cp.setPreferredSize(new Dimension(1024, 768));

		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {}

		Client client = new Client();

		SwingUtilities.invokeLater(client);
	}
}
