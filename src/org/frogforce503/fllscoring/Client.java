package org.frogforce503.fllscoring;

import java.awt.*;
import javax.swing.*;
import javax.swing.UIManager.*;

public class Client implements Runnable {
	//GUI element declarations
	private JFrame frame;
	private Container cp;
	private JPanel topPanel;
	private JLabel clock;
	private JTable table;
	private JScrollPane tablePane;

	private String[] header = {"ID", "Team Name", "R1", "R2", "R3", "R4"};

	public void run() {
		frame = new JFrame("Scores");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		cp = frame.getContentPane();
		cp.setLayout(null);
		cp.setPreferredSize(new Dimension(1024, 768));

		topPanel = new TablePanel();
		topPanel.setLayout(new GridLayout(1, 3));
		topPanel.setBounds(0, 0, 1024, 200);
		cp.add(topPanel);

		clock = new Clock();
		clock.setFont(new Font("Roboto Lt", Font.PLAIN, 72));
		clock.setHorizontalAlignment(JLabel.CENTER);
		topPanel.add(clock);

		table = new JTable(new Team[10][6], header);
		table.setEnabled(false);
		table.getTableHeader().setPreferredSize(new Dimension(1024, 68));
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);

		tablePane = new JScrollPane(table);
		tablePane.setBorder(null);
		tablePane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		tablePane.setBounds(0, 200, 1024, 568);
		cp.add(tablePane);

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
		} catch(Exception e) {}

		try {
			Font font = Font.createFont(Font.TRUETYPE_FONT, ClassLoader.getSystemResourceAsStream("rsc/Roboto-Light.ttf"));
			GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
		} catch(Exception e) {}

		Client client = new Client();

		SwingUtilities.invokeLater(client);
	}
}
