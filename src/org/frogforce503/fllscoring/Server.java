package org.frogforce503.fllscoring;

import java.io.*;
import java.net.*;
import javax.swing.*;

public class Server {
	private ServerSocket ssock;
	private int port = 0;

	public Server() throws IOException {
		//TODO Error checking
		port = Integer.parseInt(JOptionPane.showInputDialog(null, "Please enter a port number:", "Port", JOptionPane.QUESTION_MESSAGE));

		ssock = new ServerSocket(port);

		Socket client;
		while(true) {
			client = ssock.accept();
		}
	}

	public static void main(String[] args) {
		try {
			new Server();
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
