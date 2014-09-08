package org.frogforce503.fllscoring;

import java.io.*;
import java.net.*;
import javax.swing.*;
import javax.swing.UIManager.*;

public class Server {
	public Server() {
	}

	public static void main(String[] args) {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
