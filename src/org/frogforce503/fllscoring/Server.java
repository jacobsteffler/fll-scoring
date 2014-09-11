package org.frogforce503.fllscoring;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.firebase.client.Firebase;

public class Server implements Runnable, ActionListener {
	// GUI element declarations
	private JFrame frame;
	private JToolBar tb;
	private JButton load, save, rankings, accept;
	private JToggleButton onTop;
	private JComboBox<Team> cb;
	private JTextField r1, r2, r3, r4;
	private final JFileChooser fc = new MyFileChooser(frame); // TODO Custom?

	private List<Team> teams = new ArrayList<Team>();
	private Firebase fb;
	private String event;
	private boolean populated = false;

	public Server(String event) {
		this.event = event;

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

		String event = JOptionPane
				.showInputDialog(
						null,
						"Enter a name for this event.\n(Must be exactly the same for server and all clients.)",
						"Event Name", JOptionPane.QUESTION_MESSAGE);

		if (event != null) {
			new Server(event);
		}
	}

	public void run() {
		frame = new JFrame(event);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		tb = new JToolBar();
		tb.setFloatable(false);
		tb.setRollover(true);
		tb.setBorderPainted(false);
		tb.setFocusCycleRoot(true); // TODO
		frame.add(tb, BorderLayout.PAGE_START);

		ImageIcon loadIcon = new ImageIcon(
				ClassLoader.getSystemResource("Load.png"));
		load = new JButton(loadIcon);
		load.setToolTipText("Load scores");
		load.addActionListener(this);
		tb.add(Box.createHorizontalGlue());
		tb.add(load);
		tb.add(Box.createHorizontalGlue());

		ImageIcon saveIcon = new ImageIcon(
				ClassLoader.getSystemResource("Save.png"));
		save = new JButton(saveIcon);
		save.setToolTipText("Save scores");
		save.addActionListener(this);
		tb.add(save);
		tb.add(Box.createHorizontalGlue());

		ImageIcon starIcon = new ImageIcon(
				ClassLoader.getSystemResource("Star.png"));
		rankings = new JButton(starIcon);
		rankings.setToolTipText("View rankings");
		rankings.addActionListener(this);
		tb.add(rankings);
		tb.add(Box.createHorizontalGlue());

		ImageIcon onTopIcon = new ImageIcon(
				ClassLoader.getSystemResource("OnTop.png"));
		onTop = new JToggleButton(onTopIcon);
		onTop.setToolTipText("Keep this window on top");
		onTop.addActionListener(this);
		tb.add(onTop);
		tb.add(Box.createHorizontalGlue());

		JPanel chooser = new JPanel();
		chooser.setLayout(new BoxLayout(chooser, BoxLayout.PAGE_AXIS));
		cb = new JComboBox<Team>();
		cb.addActionListener(this);
		chooser.add(Box.createVerticalStrut(5));
		chooser.add(cb);
		chooser.add(Box.createVerticalStrut(5));
		frame.add(chooser, BorderLayout.CENTER);

		JPanel input = new JPanel();
		input.setLayout(new BoxLayout(input, BoxLayout.LINE_AXIS));
		input.setFocusCycleRoot(true);
		frame.add(input, BorderLayout.PAGE_END);

		input.add(new JLabel("R1: "));
		r1 = new JTextField(3);
		r1.addActionListener(this);
		input.add(r1);
		input.add(Box.createHorizontalStrut(5));

		input.add(new JLabel("R2: "));
		r2 = new JTextField(3);
		r2.addActionListener(this);
		input.add(r2);
		input.add(Box.createHorizontalStrut(5));

		input.add(new JLabel("R3: "));
		r3 = new JTextField(3);
		r3.addActionListener(this);
		input.add(r3);
		input.add(Box.createHorizontalStrut(5));

		input.add(new JLabel("R4: "));
		r4 = new JTextField(3);
		r4.addActionListener(this);
		input.add(r4);
		input.add(Box.createHorizontalStrut(5));

		for (final JTextField i : new JTextField[] { r1, r2, r3, r4 }) {
			i.addFocusListener(new FocusAdapter() {
				public void focusGained(FocusEvent e) {
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							i.selectAll();
						}
					});
				}
			});
		}

		accept = new JButton("Accept");
		accept.addActionListener(this);
		input.add(accept);
		
		fc.setFileFilter(new FileNameExtensionFilter("Plain text files (*.txt)", "txt"));

		((JPanel) frame.getContentPane()).setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		r1.requestFocusInWindow();
	}

	private void messageClients() {
		List<Team> sorted = new ArrayList<Team>(teams);
		Collections.sort(sorted, Collections.reverseOrder());
		fb.setValue(sorted);
	}

	private void populateCB() {
		populated = false;
		cb.removeAllItems();

		for (Team t : teams) {
			cb.addItem(t);
		}

		if (teams.size() > 0) {
			populated = true;
			cb.setSelectedIndex(0);
		} else {
			r1.setText("");
			r2.setText("");
			r3.setText("");
			r4.setText("");
		}
	}

	// TODO Structural change
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

	private void saveFile(File file) throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter(file));

		for (int i = 0; i < teams.size(); i++) {
			Team t = teams.get(i);
			out.write("" + t.getTeamID() + "::" + t.getName() + "::"
					+ t.getR1() + "::" + t.getR2() + "::" + t.getR3() + "::"
					+ t.getR4());
			if (i != teams.size() - 1)
				out.newLine();
		}

		out.flush();
		out.close();
	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source == load) {
			if (fc.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
				try {
					setTeams(fc.getSelectedFile());
					messageClients();
					populateCB();
				} catch (FileNotFoundException e1) {
					JOptionPane.showMessageDialog(frame,
							"The file could not be found.", "File Not Found",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		} else if (source == save) {
			if (fc.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
				try {
					saveFile(fc.getSelectedFile());
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(frame,
							"The file could not be opened.", "IO Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		} else if (source == rankings) {
			// TODO
		} else if (source == onTop) {
			frame.setAlwaysOnTop(onTop.isSelected());
		} else if (source == cb) {
			if (populated) {
				r1.setText(Integer.toString(((Team) cb.getSelectedItem())
						.getR1()));
				r2.setText(Integer.toString(((Team) cb.getSelectedItem())
						.getR2()));
				r3.setText(Integer.toString(((Team) cb.getSelectedItem())
						.getR3()));
				r4.setText(Integer.toString(((Team) cb.getSelectedItem())
						.getR4()));
			}
		} else if (source == accept || source == r1 || source == r2
				|| source == r3 || source == r4) {
			int newR1, newR2, newR3, newR4;
			try {
				newR1 = Integer.parseInt(r1.getText());
				newR2 = Integer.parseInt(r2.getText());
				newR3 = Integer.parseInt(r3.getText());
				newR4 = Integer.parseInt(r4.getText());

				if (newR1 < 0 || newR2 < 0 || newR3 < 0 || newR4 < 0)
					throw new NumberFormatException("Cannot be less than 0.");
			} catch (NumberFormatException e1) {
				Toolkit.getDefaultToolkit().beep();
				r1.setText(String.valueOf(((Team) cb.getSelectedItem()).getR1()));
				r2.setText(String.valueOf(((Team) cb.getSelectedItem()).getR2()));
				r3.setText(String.valueOf(((Team) cb.getSelectedItem()).getR3()));
				r4.setText(String.valueOf(((Team) cb.getSelectedItem()).getR4()));

				return;
			}

			((Team) cb.getSelectedItem()).setR1(newR1);
			((Team) cb.getSelectedItem()).setR2(newR2);
			((Team) cb.getSelectedItem()).setR3(newR3);
			((Team) cb.getSelectedItem()).setR4(newR4);

			messageClients();
		}
	}

	private class MyFileChooser extends JFileChooser {
		private JFrame parent;

		public MyFileChooser(JFrame parent) {
			this.parent = parent;
		}

		public void approveSelection() {
			if (getDialogType() == SAVE_DIALOG) {
				File selectedFile = getSelectedFile();
				if ((selectedFile != null) && selectedFile.exists()) {
					int response = JOptionPane
							.showConfirmDialog(
									parent,
									"The file "
											+ selectedFile.getName()
											+ " already exists. Do you want to replace the existing file?",
									"Overwrite file?",
									JOptionPane.YES_NO_OPTION,
									JOptionPane.WARNING_MESSAGE);
					if (response != JOptionPane.YES_OPTION)
						return;
				}
			}

			super.approveSelection();
		}
	}
}
