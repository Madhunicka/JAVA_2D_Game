package brickBracker;

import brickBracker.network.UDPServer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Gameplay extends JPanel implements KeyListener, ActionListener {
	//staring play
	private boolean play = false;
	private int score = 0;
	private int totalbricks= 21;
	private Timer timer;
	UDPServer server;
	private int delay = 4;
	//setting up the position of the scrolling bar and the ball
	
	private int playerX= 310;
	
	private int ballposX = 120;
	private int ballposY = 350;
	private int ballXdir = -1;
	private int ballYdir = -2;
	
	private MapGenerator map;
	
	public Gameplay() {
		map = new MapGenerator(3,7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
//		server.start();
		this.server = new UDPServer(this);
		this.server.start();
	}
	public void paint(Graphics g) {
		
		//background
		g.setColor(Color.black);
		g.fillRect(1, 1, 692, 592);
		//drawing map
		
		map.draw((Graphics2D)g);
		
		
		//borders
		g.setColor(Color.green);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(683, 0, 3, 592);
		
		//scores
//		
		g.setColor(Color.black);
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString(""+ score, 590,30);
		
		
		
		//the paddle
		g.setColor(Color.yellow);
		g.fillRect(playerX, 550, 100, 8);
		
		//the ball
		g.setColor(Color.yellow);
		g.fillOval(ballposX, ballposY, 20, 20);
		
		if(totalbricks<=0) {
			play=false;
			ballXdir=0;
			ballYdir=0;
			g.setColor(Color.red);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("You won "+ score, 260,300);
			
			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Press Enter to Restart ", 230,350);
		}
		
		if(ballposY>570) {
			play=false;
			ballXdir=0;
			ballYdir=0;
			g.setColor(Color.red);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("Game over Scores: "+ score, 190,300);
			
			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Press Enter to Restart "+ score, 230,350);
		}
		
		g.dispose();
		
	}
	
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		//to move the ball
		if(play) {  //if we we press right or left the play will be true , to detect the ball is touching top left or right 
			
			//intersection with the paddle
			
			if(new Rectangle(ballposX,ballposY, 20,20).intersects(new Rectangle(playerX, 550, 100, 8)))
			{
				ballYdir = - ballYdir;	
			}
			
			A: for(int i=0; i<map.map.length; i++) {
				for(int j=0; j<map.map[0].length; j++) {
					if(map.map[i][j]>0) {
						int brickX=j*map.brickWidth+80;
						int brickY=i*map.brickHeight+50;
						int brickWidth=map.brickWidth;
						int brickHeight=map.brickHeight;
						
						Rectangle rect= new Rectangle(brickX, brickY,brickWidth, brickHeight );
						Rectangle ballRect= new Rectangle(ballposX, ballposY, 20,20);
						Rectangle brickRect = rect;
						if(ballRect.intersects(brickRect)) {
							System.out.println("intersect");
							map.setBrickValue(0, i, j);
							totalbricks--;
							score+=5;
							
							if(ballposX +19 <= brickRect.x|| ballposX +1 >= brickRect.x + brickRect.width) {
								ballXdir = -ballXdir;
							}
							else {
								ballYdir=-ballYdir;
							}
							break A;
						}
						
					}
					
				}
			}
			
			ballposX+=ballXdir;
			ballposY+=ballYdir;
			if(ballposX<0) {//left
				ballXdir = -ballXdir;
				
			}
			if(ballposY<0) {//top
				ballYdir = -ballYdir;
				
			}
			if(ballposX>670) {//right
				
				ballXdir = -ballXdir;
			}
			repaint();
			
			
		}
		repaint();  //call paint function again and draw everything again
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	//export the methods to move the paddle

	public void rightKey() {
		KeyEvent e = new KeyEvent(
				this,
				KeyEvent.KEY_PRESSED,
				System.currentTimeMillis(),
				0,
				KeyEvent.VK_RIGHT,
				KeyEvent.CHAR_UNDEFINED);

		this.keyPressed(e);
	}



	@Override
	public void keyPressed(KeyEvent e) {
//		System.out.println(e.getKeyCode()== KeyEvent.VK_LEFT);
		if(e.getKeyCode()== KeyEvent.VK_RIGHT) {
			if(playerX >=600) {
				playerX = 600;
			}
			else{
				moveRight();
			}
			
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			if(playerX <10) {
				playerX = 10;
			}
			else{
				moveLeft();
			}
				
		}
		
		if(e.getKeyCode()==KeyEvent.VK_ENTER) {
			if(!play) {
				play=true;
				ballposX=120;
				ballposY=350;
				ballXdir=-1;
				ballYdir=-2;
				playerX=310;
				score=0;
				totalbricks=21;
				map = new MapGenerator(3,7);
				repaint();
			}
		}
		
	}
	
	public void moveRight() {
		this.play=true;
		playerX+=20;
		
		
	}
	public void moveLeft() {
		this.play=true;
		playerX-=20;
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
