package org.frogforce503.fllscoring;

import java.io.*;
import java.net.*;
import javax.swing.*;
import javax.swing.UIManager.*;

public class Server {
	private ServerSocket ssock = new ServerSocket();
	private int port = 0;

	public Server() throws IOException {
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

		try {
			new Server();
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
