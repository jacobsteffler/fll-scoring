package org.frogforce503.fllscoring;

import javax.swing.*;

public class Client implements Runnable {
	public void run() {
		
	}

	public static void main(String[] args) {
		Client client = new Client();
		
		SwingUtilities.invokeLater(client);


	}
}
