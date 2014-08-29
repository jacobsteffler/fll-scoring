package org.frogforce503.fllscoring;

import java.awt.event.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;

public class Clock extends JLabel implements ActionListener {
	private DateFormat format;

	public Clock() {
		format = new SimpleDateFormat("h:mm a");

		new Timer(500, this).start();
	}

	public void actionPerformed(ActionEvent e) {
		setText(format.format(new Date()));
	}
}
