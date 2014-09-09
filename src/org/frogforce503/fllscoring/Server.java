package org.frogforce503.fllscoring;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import com.firebase.client.Firebase;

public class Server implements Runnable {
	List<Team> teams = new ArrayList<Team>();
	Firebase fb;

	public Server(String event) {
		fb = new Firebase("https://fll-scoring.firebaseio.com/").child(event);

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

		new Server("Frog Force Frenzy"); // TODO
	}

	public void run() {
		// UI Stuff.
	}

	private void messageClients() {
		// Sort and send
	}

	private void setTeams(File file) throws FileNotFoundException {
		Scanner scan = new Scanner(new FileReader(file));
		String[] info;
		int line = 1;

		teams.clear();

		while (scan.hasNextLine()) {
			info = Pattern.compile("::", Pattern.LITERAL)
					.split(scan.nextLine());

			try {
				Team team;
				team = new Team(Math.abs(Integer.parseInt(info[0])), info[1],
						Math.abs(Integer.parseInt(info[2])), Math.abs(Integer
								.parseInt(info[3])), Math.abs(Integer
								.parseInt(info[4])), Math.abs(Integer
								.parseInt(info[5])));

				teams.add(team);
				System.out.println("Team added: " + team);
			} catch (Exception e) {
				System.out.println("Problem parsing line " + line
						+ ", skipping.");
			} finally {
				line++;
			}
		}

		scan.close();
	}
}
