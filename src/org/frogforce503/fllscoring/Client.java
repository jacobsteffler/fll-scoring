package org.frogforce503.fllscoring;

import java.awt.*;
import javax.swing.*;

public class Client implements Runnable {
	//GUI element declarations
	private JFrame frame;
	private Container cp;
	
	public void run() {
		frame = new JFrame("Scores");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		cp = frame.getContentPane();
		cp.setPreferredSize(new Dimension(1024, 768));

		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		Client client = new Client();
		
		SwingUtilities.invokeLater(client);
	}
}
