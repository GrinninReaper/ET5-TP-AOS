package com.rlc.jtetris.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class ScoreComponent extends JLabel {

	private int score;
	
	public ScoreComponent() {
		super();
		setForeground(Color.white);
		setHorizontalAlignment(JLabel.CENTER);
		setFont(new Font("Arial", Font.BOLD, 20));
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(0, 5, getWidth()-1, getHeight()-6);
		g.setColor(Color.black);
		g.fillRect(3,8, getWidth()-7,getHeight()-13);
		g.fillRect(5,0, getWidth() / 2, 8);
		g.setColor(Color.white);
		char[] msg = "Score".toCharArray();
		g.setFont(new Font("Arial", Font.PLAIN, 16));
		g.drawChars(msg, 0, msg.length, 10, 12);
		
		setText(String.valueOf(score));
		super.paint(g);
	}
	
	public void reinitScore() {
		score = 0;
		repaint();
	}
	
	public void addToScore(int val) {
		score += val;
		repaint();
	}
}
