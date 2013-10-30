package com.mochilo.bouncing;

import javax.swing.JFrame;

public class BouncingBall extends JFrame implements Commons {
	
	public BouncingBall() {
		add(new Board());
		setTitle("Bouncing Ball");
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    setSize(BOARD_WIDTH, BOARD_HEIGTH);
	    setLocationRelativeTo(null);
	    setVisible(true);
	    setResizable(false);
	}  
	    
	public static void main(String[] args) {
		new BouncingBall();
	}

}
