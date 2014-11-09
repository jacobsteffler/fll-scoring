package org.frogforce503.fllscoring;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.Timer;

public class Clock extends JLabel implements ActionListener {
	private DateFormat format;

	public Clock() {
		format = new SimpleDateFormat("h:mm a");
		setText(format.format(new Date()));
		
		new Timer(500, this).start();
	}

	public void actionPerformed(ActionEvent e) {
		setText(format.format(new Date()));
	}
}
