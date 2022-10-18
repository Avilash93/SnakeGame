package sankegame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;


import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener{
	//panel size
	static final int screen_weight=600;
	static final int screen_height=600;
	//how big we want the object size in terms of matrix
	//single object size in terms of matrix
	static final int unit_size=25;
	//How many object(matrix) can we fit on this screen.
	static final int game_unit=(screen_weight * screen_height)/unit_size;
	//speed of snake movement.
	static final int delay=75;
	//creates an array of X and Y.Each of an array are going to hold all of the coordinates of 
	//all body parts of the snake.
	final int[] x=new int[game_unit]; //hold X coordinate of snake body parts.
	final int[] y=new int[game_unit];//hold Y coordinate of snake body parts.
	//declare initial snake body parts.
	int bodyparts=6;
	//count of apple eaten
	int applesEaten;
	//Hold X and Y coordinates of apple where it is located
	int appleX;
	int appleY;
	//initial direction of snake movements.
	char direction='R';	
	//running or not
	boolean running=false;
	//Timer class to auto start
	Timer timer;
	//generates random coordinates for the apple.
	Random random;
	
	

	GamePanel(){
		random=new Random();
		this.setPreferredSize(new Dimension(screen_weight,screen_height));
		this.setBackground(Color.black);
                this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
		
		
	}
	
	public void startGame() {
		running=true;
		newApple();
		timer=new Timer(delay,this);
		timer.start();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	public void draw(Graphics g) {
		if (running) {
			for (int i = 0; i < screen_height / unit_size; i++) {
				//Matrix of size 25
				g.drawLine(i * unit_size, 0, i * unit_size, screen_height);
				g.drawLine(0, i * unit_size, screen_weight, i * unit_size);
			}
			//creates apple in random coordinates
			g.setColor(Color.red);
			g.fillOval(appleX, appleY, unit_size, unit_size);

			//draw snake body parts
			for (int i = 0; i < bodyparts; i++) {
				//snake head
				if (i == 0) {
					g.setColor(Color.blue);
					g.fillRect(x[i], y[i], unit_size, unit_size);
				}
				//body part
				else {
					g.setColor(Color.green);
					g.fillRect(x[i], y[i], unit_size, unit_size);
				}
			}
			g.setColor(Color.red);
			g.setFont(new Font("Ink free",Font.BOLD,40));
			FontMetrics metrics=getFontMetrics(g.getFont());
			g.drawString("Score: "+applesEaten, (screen_weight - metrics.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
		}

		else{
			gameOver(g);
		}
	}
		
	
	public void newApple() {
		appleX=random.nextInt(screen_height/unit_size) * unit_size;
		appleY=random.nextInt(screen_height/unit_size) * unit_size;
	}
	public void move() {
		//Snake body direction
		for (int i=bodyparts;i>0;i--) {
			x[i]=x[i-1];
			y[i]=y[i-1];
		}
		// Snake head direction
		switch(direction) {
		case 'U':
			y[0]=y[0] -unit_size;
			break;
		case 'D':
			y[0]=y[0] +unit_size;
			break;
		case 'L':
			x[0]=x[0] -unit_size;
			break;
		case 'R':
			x[0]=x[0] + unit_size; //by default direction
			break;
			
		}
		
	}
	public void checkApple() {
		if((x[0]==appleX) && (y[0]==appleY)){
			bodyparts++;
			applesEaten++;
			newApple();
		}
	}
	public void checkCollision() {
		//check if head collides with body
		for(int i=bodyparts;i>0;i--){
			if((x[0]== x[i]) && (y[0]==y[i])){
				running=false;
			}
		}
		//check if body touches left border
		if(x[0]<0){
			running =false;
		}
		//check if body touches right border
		if(x[0]>screen_weight){
			running =false;
		}//check if body touches up border
		if(y[0]<0){
			running =false;
		}//check if body touches down border
		if(y[0]>screen_height){
			running =false;
		}
		if(!running){
			timer.stop();
		}
	}
	public void gameOver(Graphics g) {
		//Score
		g.setColor(Color.red);
		g.setFont(new Font("Ink free",Font.BOLD,40));
		//font metrics useful for lining up text in the center of screen
		FontMetrics metrics1=getFontMetrics(g.getFont()); //Gets the FontMetrics for the specified Font.
	//(metrics1.stringWidth("Score: "+applesEaten))/2 --> This will tell us text width according to Font size and style
		g.drawString("Score: "+ applesEaten, (screen_weight - metrics1.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
//		g.drawString("Score: "+applesEaten,250,450);

		//Game over Text
		g.setColor(Color.red);
		g.setFont(new Font("Ink free",Font.BOLD,100));
		FontMetrics metrics2=getFontMetrics(g.getFont());
		g.drawString("Game Over", (screen_weight - metrics2.stringWidth("Game Over"))/2, screen_weight/2);

		System.out.println(metrics2.stringWidth("Game Over"));

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(running) {
			move();
			checkApple();
			checkCollision();
		}
		repaint();
		
	}
	
	private class MyKeyAdapter extends KeyAdapter{
		@Override 
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()){
				case KeyEvent.VK_LEFT :
					if (direction != 'R') {
						direction='L';
					}
					break;
					case KeyEvent.VK_RIGHT :
					if (direction != 'L') {
						direction='R';
					}
					break;
					case KeyEvent.VK_UP :
					if (direction != 'D') {
						direction='U';
					}
					break;case KeyEvent.VK_DOWN :
					if (direction != 'U') {
						direction='D';
					}
					break;
			}
		}
	}
	
}
