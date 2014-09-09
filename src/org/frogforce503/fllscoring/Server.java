package org.frogforce503.fllscoring;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import com.firebase.client.Firebase;

public class Server implements Runnable {
	List<Team> teams = new ArrayList<Team>();
	
	public Server(String event) {
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

		new Server("Frog Force Frenzy"); //TODO
	}

	public void run() {
		// UI Stuff.
	}
}
