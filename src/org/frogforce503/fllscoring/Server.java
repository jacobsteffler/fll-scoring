package org.frogforce503.fllscoring;

import java.io.*;
import java.net.*;
import javax.swing.*;
import javax.swing.UIManager.*;

public class Server implements Runnable {
	//GUI element declarations
	private JFrame frame;
	private JPanel sp;

	private ServerSocket ssock = new ServerSocket();
	private int port = 0;

	public void run() {
		frame = new JFrame("Scoring server (port " + port + ")");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		sp = new ServerPanel();
		frame.setContentPane(sp);

		frame.pack();
		frame.setVisible(true);
	}

	public Server(int portIn) throws IOException {
		port = portIn;
		try {
			if(port > 0 && port <= 65535) ssock = new ServerSocket(port);
		} catch(BindException e)  {
			port = 0;
		}

		while(!ssock.isBound()) {
			while(port < 1 || port > 65535) {
				String ports = (JOptionPane.showInputDialog(null, "Please enter a port number (1-65535):", "Port", JOptionPane.QUESTION_MESSAGE));
				if(ports == null) System.exit(0);
				try {
					port = Integer.parseInt(ports);
				} catch(NumberFormatException e) {}
			}


			try {
				ssock = new ServerSocket(port);
			} catch(BindException e) {
				JOptionPane.showMessageDialog(null, "Port " + port + " is not available. Please choose another.", "Bind error", JOptionPane.ERROR_MESSAGE);
				port = 0;
			}
		}
		System.out.println("Listening for clients on port " + port + "...");

		SwingUtilities.invokeLater(this);

		Socket client;
		while(true) {
			client = ssock.accept();
		}
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

		int port = 0;
		if(args.length > 0) {
			try {
				port = Integer.parseInt(args[0]);
			} catch(NumberFormatException e) {}
		}

		try {
			new Server(port);
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
