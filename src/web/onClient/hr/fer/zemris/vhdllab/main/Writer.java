package hr.fer.zemris.vhdllab.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Writer extends JPanel {

	private static final long serialVersionUID = 5853551043423675268L;
	private JTextArea text;
	
	public Writer() {
		text = new JTextArea("This is writer area!");
		text.setPreferredSize(new Dimension(1000, 1000));
		this.add(text, BorderLayout.CENTER);
		this.setBackground(Color.RED);
	}
	
}