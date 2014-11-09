package org.frogforce503.fllscoring;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.Painter;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class ClientPanel extends JPanel implements ActionListener {
	// GUI element declarations
	private JPanel topPanel;
	private JLabel clock;
	private JTable table;
	private JScrollPane tablePane;
	private Timer tableTimer;

	private int pages = 1, currentPage = 1;
	private String[] cols = { "Rank", "ID", "Team Name", "R1", "R2", "R3", "R4" };

	public ClientPanel() {
		setLayout(null);
		setPreferredSize(new Dimension(1024, 768));

		topPanel = new NimbusPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.LINE_AXIS));
		topPanel.setBounds(0, 0, 1024, 200);
		add(topPanel);

		ImageIcon firstLogo = new ImageIcon(
				ClassLoader.getSystemResource("FIRST.png"));
		JLabel first = new JLabel(firstLogo);
		topPanel.add(Box.createHorizontalGlue());
		topPanel.add(first);
		topPanel.add(Box.createHorizontalGlue());

		clock = new Clock();
		clock.setFont(new Font("Roboto Lt", Font.PLAIN, 72));
		clock.setHorizontalAlignment(JLabel.CENTER);
		topPanel.add(clock);
		topPanel.add(Box.createHorizontalGlue());

		ImageIcon fffLogo = new ImageIcon(
				ClassLoader.getSystemResource("FFF.png"));
		JLabel fff = new JLabel(fffLogo);
		topPanel.add(fff);
		topPanel.add(Box.createHorizontalGlue());

		table = new JTable(new Object[10][7], cols);
		table.setEnabled(false);
		table.setFont(new Font("Roboto Lt", Font.BOLD, 24));
		table.setRowHeight(50);
		table.setShowGrid(true);
		table.setGridColor(Color.GRAY);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		formatTable(table);

		JTableHeader header = table.getTableHeader();
		header.setPreferredSize(new Dimension(1024, 68));
		header.setFont(new Font("Roboto Lt", Font.BOLD, 20));
		header.setReorderingAllowed(false);
		header.setResizingAllowed(false);
		((DefaultTableCellRenderer) header.getDefaultRenderer())
				.setHorizontalAlignment(JLabel.CENTER);

		tablePane = new JScrollPane(table);
		tablePane.setBorder(null);
		tablePane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		tablePane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		tablePane.setBounds(0, 200, 1024, 568);
		add(tablePane);

		tableTimer = new Timer(10000, this);
		tableTimer.start();
	}

	public void actionPerformed(ActionEvent e) {
		currentPage = currentPage == pages ? 1 : currentPage + 1;
		table.scrollRectToVisible(new Rectangle(0, 500 * (currentPage - 1),
				1024, 500));
	}

	public void setDelay(int delay) {
		tableTimer.setDelay(delay * 1000);
		tableTimer.restart();
	}

	public void setTeams(Team[] teams) {
		int total = (int) (10 * Math.ceil(teams.length / 10.0));
		if (total == 0)
			total = 10;
		pages = total / 10;
		if (currentPage > pages) {
			currentPage = 1;
			table.scrollRectToVisible(new Rectangle(0, 0, 1024, 500));
		}

		Object[][] data = new Object[total][7];
		for (int i = 0; i < teams.length; i++) {
			data[i][0] = i + 1;
			data[i][1] = teams[i].getTeamID();
			data[i][2] = teams[i].getName();
			data[i][3] = teams[i].getR1();
			data[i][4] = teams[i].getR2();
			data[i][5] = teams[i].getR3();
			data[i][6] = teams[i].getR4();
		}

		table.setModel(new DefaultTableModel(data, cols));
		formatTable(table);
	}

	private void formatTable(JTable table) {
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setPreferredWidth(424);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		table.getColumnModel().getColumn(4).setPreferredWidth(100);
		table.getColumnModel().getColumn(5).setPreferredWidth(100);
		table.getColumnModel().getColumn(6).setPreferredWidth(100);

		DefaultTableCellRenderer cRenderer = new DefaultTableCellRenderer();
		cRenderer.setHorizontalAlignment(JLabel.CENTER);
		for (int i = 0; i < cols.length; i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(cRenderer);
		}
	}

	private class NimbusPanel extends JPanel {
		public NimbusPanel() {
			setBorder(null);
		}

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			@SuppressWarnings("unchecked")
			Painter<Component> painter = (Painter<Component>) UIManager
					.get("TableHeader:\"TableHeader.renderer\"[Enabled].backgroundPainter");

			if (painter != null && g instanceof Graphics2D)
				painter.paint((Graphics2D) g, this, getWidth(), getHeight());
		}
	}
}
