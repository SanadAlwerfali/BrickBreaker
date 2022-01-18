package brickBreaker;

import javax.swing.*;

public class main {

	public static void main(String[] args) {
		// creating a JFrame 
		JFrame game = new JFrame();
		Gameplay gamePlay = new Gameplay();
		game.setBounds(10, 10, 700, 600);
		game.setTitle("Brick Breaker");
		game.setBackground(null);
		game.setResizable(false);
		game.setVisible(true);
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.add(gamePlay);
	}

}
