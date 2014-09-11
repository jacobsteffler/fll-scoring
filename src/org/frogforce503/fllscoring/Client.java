package org.frogforce503.fllscoring;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class Client implements Runnable {
	// GUI element declarations
	private JFrame frame;
	private ClientPanel cp;
	
	private List<Team> teams = new ArrayList<Team>();
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
		
		Firebase fb = new Firebase("https://fll-scoring.firebaseio.com/").child(event);
		fb.addValueEventListener(new ValueEventListener() {
			public void onCancelled(FirebaseError f) {	
			}
			
			public void onDataChange(DataSnapshot d) {
				teams.clear();
				for(DataSnapshot o : d.getChildren()) {
					int id = ((Long) o.child("teamID").getValue()).intValue();
					String name = (String) o.child("name").getValue();
					int r1 = ((Long) o.child("r1").getValue()).intValue();
					int r2 = ((Long) o.child("r2").getValue()).intValue();
					int r3 = ((Long) o.child("r3").getValue()).intValue();
					int r4 = ((Long) o.child("r4").getValue()).intValue();
					teams.add(new Team(id, name, r1, r2, r3, r4));
				}
				
				
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						cp.setTeams(teams.toArray(new Team[0]));
					}
				});
			}
		});

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
