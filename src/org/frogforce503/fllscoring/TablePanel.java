package org.frogforce503.fllscoring;

import java.awt.*;
import javax.swing.*;

public class TablePanel extends JPanel {
	public TablePanel() {
		setBorder(null);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		@SuppressWarnings("unchecked")
		Painter<Component> painter = (Painter<Component>) UIManager.get("TableHeader:\"TableHeader.renderer\"[Enabled].backgroundPainter");

		if(painter != null && g instanceof Graphics2D)
			painter.paint((Graphics2D) g, this, getWidth(), getHeight());
	}
}
