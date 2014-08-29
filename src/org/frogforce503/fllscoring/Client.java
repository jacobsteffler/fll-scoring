package org.frogforce503.fllscoring;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.UIManager.*;

public class Client implements Runnable, ActionListener {
	//GUI element declarations
	private JFrame frame;
	private Container cp;
	private JPanel topPanel;
	private JLabel clock;
	private JTable table;
	private JScrollPane tablePane;
	private Timer tableTimer;

	private boolean fullscreen = false;
	private int pages = 1, currentPage = 1;
	private String[] cols = {"ID", "Team Name", "R1", "R2", "R3", "R4"};

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

		table = new JTable(new Object[10][6], cols);
		table.setEnabled(false);
		table.setFont(new Font("Roboto Lt", Font.BOLD, 24));
		table.setRowHeight(50);
		table.setShowGrid(true);
		table.setGridColor(Color.GRAY);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		setWidths();

		/* Test data
		Team[] newteams = new Team[25];
		for(int i = 0; i < newteams.length; i++) {
			newteams[i] = new Team(i, "Hello");
		}
		setTeams(newteams);
		*/

		JTableHeader header = table.getTableHeader();
		header.setPreferredSize(new Dimension(1024, 68));
		header.setFont(new Font("Roboto Lt", Font.BOLD, 20));
		header.setReorderingAllowed(false);
		header.setResizingAllowed(false);
		((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

		tablePane = new JScrollPane(table);
		tablePane.setBorder(null);
		tablePane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		tablePane.setBounds(0, 200, 1024, 568);
		cp.add(tablePane);

		tableTimer = new Timer(10000, this);
		tableTimer.start();

		Action fsAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				fullscreen = !fullscreen;
				setFullScreen(fullscreen);
			}
		};
		topPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('f'), "fs");
		topPanel.getActionMap().put("fs", fsAction);

		frame.pack();
		frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == tableTimer) {
			currentPage = currentPage == pages ? 1 : currentPage + 1;
			table.scrollRectToVisible(new Rectangle(0, 500 * currentPage - 500, 1024, 500));
		}
	}

	private void setFullScreen(boolean fs) {
		if(fs) {
			frame.dispose();
			frame.setUndecorated(true);
			if(System.getProperty("os.name").toLowerCase().indexOf("win") >= 0) {
				frame.setVisible(true);
				frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
			} else {
				frame.getGraphicsConfiguration().getDevice().setFullScreenWindow(frame);
			}			
		} else {
			frame.dispose();
			frame.setUndecorated(false);
			frame.setExtendedState(JFrame.NORMAL);
			frame.pack();
			frame.setVisible(true);
		}
	}

	private void setTeams(Team[] teams) {
		int total = (int) (10 * Math.ceil(teams.length / 10.0));
		pages = total / 10;

		Object[][] data = new Object[total][6];
		for(int i = 0; i < teams.length; i++) {
			data[i][0] = teams[i].getID();
			data[i][1] = teams[i].getName();
			data[i][2] = teams[i].getR1();
			data[i][3] = teams[i].getR2();
			data[i][4] = teams[i].getR3();
			data[i][5] = teams[i].getR4();
		}

		setData(data);
	}

	private void setData(Object[][] data) {
		table.setModel(new DefaultTableModel(data, cols));
		setWidths();
	}

	private void setWidths() {
		table.getColumnModel().getColumn(0).setPreferredWidth(150);
		table.getColumnModel().getColumn(1).setPreferredWidth(474);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		table.getColumnModel().getColumn(4).setPreferredWidth(100);
		table.getColumnModel().getColumn(5).setPreferredWidth(100);

		DefaultTableCellRenderer cRenderer = new DefaultTableCellRenderer();
		cRenderer.setHorizontalAlignment(JLabel.CENTER);
		for(int i = 0; i < cols.length; i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(cRenderer);
		}
	}

	public Client() {
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
		} catch(Exception e) {}

		try {
			Font font = Font.createFont(Font.TRUETYPE_FONT, ClassLoader.getSystemResourceAsStream("rsc/Roboto-Light.ttf"));
			GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
		} catch(Exception e) {}

		new Client();
	}
}
