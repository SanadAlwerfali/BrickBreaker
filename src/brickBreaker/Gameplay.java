package brickBreaker;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Gameplay extends JPanel implements KeyListener, ActionListener{

	private boolean play = false;
	private int score = 0;
	private int totalBricks = 21;
	private Timer timer;
	private int delay = 8;
	private int playerX = 310;
	private int ballPosx = 120;
	private int ballPosy = 350;
	private int ballXdir = -1;
	private int ballYdir = -2;
	private MapGenerator map;
	public Gameplay() {
		map = new MapGenerator(3, 7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer (delay, this);
		timer.start();
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(1, 1, 692, 592);
		
		map.draw((Graphics2D) g);
		
		g.setColor(Color.white);
		g.fillRect(0,0,3,592);
		g.fillRect(0,0,692,3);
		g.fillRect(691,0,3,592);
		
		g.setColor(Color.white);
		g.setFont(new Font ("serif", Font.BOLD, 25));
		g.drawString(""+score, 590,30);
		
		
		g.setColor(Color.white);
		g.fillRect(playerX, 550, 100, 8);
		
		g.setColor(Color.yellow);
		g.fillOval(ballPosx, ballPosy, 20, 20);
		
		if(totalBricks <= 0) {
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.GREEN);
			g.setFont(new Font ("serif", Font.BOLD, 30));
			g.drawString("You won", 190,300);
			g.setFont(new Font ("serif", Font.BOLD, 20));
			g.drawString("Press Enter to Play Again", 230,350);
		}
		
		if (ballPosy > 570) {
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.red);
			g.setFont(new Font ("serif", Font.BOLD, 30));
			g.drawString("Game Over, Your Score is: "+score, 190,300);
			g.setFont(new Font ("serif", Font.BOLD, 20));
			g.drawString("Press Enter to Play Again", 230,350);
		}
		
		g.dispose();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if (play = true) {
			
			if(new Rectangle(ballPosx, ballPosy, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
				ballYdir = -ballYdir;
			}
			
			A: for (int i = 0; i<map.map.length; i++) {
				for (int j = 0; j<map.map[0].length; j++) {
					if(map.map[i][j] >  0) {
						int brickX = j*map.brickwidth + 80;
						int brickY = i * map.brickheight + 50;
						int brickWidth = map.brickwidth;
						int brickheight = map.brickheight;
						
						
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickheight);
						Rectangle ballRect = new Rectangle(ballPosx, ballPosy, 20, 20);
						Rectangle brickRect = rect;
						
						if (ballRect.intersects(brickRect)) {
							map.setBrickValue(0, i, j);
							totalBricks--;
							score +=5;
							
							if (ballPosx + 19 <= brickRect.x || ballPosx + 1 >= brickRect.x + brickRect.width) {
								ballXdir = -ballXdir;
							}else {
								ballYdir = -ballYdir;
							}
							
							break A;
						}
					}
				}
			}
			
			ballPosx += ballXdir;
			ballPosy += ballYdir;
			
			if (ballPosx < 0) {
				ballXdir = -ballXdir;
			}
			if (ballPosy < 0) {
				ballYdir = -ballYdir;
			}
			if (ballPosx > 670) {
				ballXdir = -ballXdir;
			}
		}
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (playerX >= 600) {
				playerX = 600;
			}
			else {
				moveRight();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (playerX < 10) {
				playerX = 10;
			}
			else {
				moveLeft();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(!play) {
				play = true;
				ballPosx = 120;
				ballPosy = 350;
				ballXdir = -1;
				ballYdir = -2;
				playerX = 310;
				score = 0;
				totalBricks = 21;
				map = new MapGenerator (3,7);
				repaint();
			}
		}
	}
	
	public void moveRight() {
		play = true;
		playerX+=20;
	}
	public void moveLeft() {
		play = true;
		playerX-=20;
	}

}
