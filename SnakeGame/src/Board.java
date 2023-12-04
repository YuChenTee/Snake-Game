import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import tetris.WindowGame;



public class Board extends JPanel implements KeyListener{
	public static int BOARD_HEIGHT = 20;
	public static int BOARD_WIDTH = 20;
	public static int BLOCK_SIZE = 20;
	private Snake snake;
	private Food food;
	public long startTime;
	public long elapsedTime;
	private Timer looper;
	private int fps = 30;
	private int delay = 1/fps*1000;
	private int point = 0;
	public static int level = 1;
	public static int STATE_GAME_PLAY = 0;
	public static int STATE_GAME_PAUSE = 1;
	public static int STATE_GAME_OVER = 2;
	private int state = STATE_GAME_PLAY;

	
public Board() {
		
		startTime = System.currentTimeMillis();
		snake = new Snake();
		food = new Food();
	
		looper = new Timer(delay, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				update();
				repaint();
			}
		});
		looper.start();
		
	}
	
	
	public static int getBOARD_HEIGHT() {
	return BOARD_HEIGHT;
}


public static void setBOARD_HEIGHT(int bOARD_HEIGHT) {
	BOARD_HEIGHT = bOARD_HEIGHT;
}


public static int getBOARD_WIDTH() {
	return BOARD_WIDTH;
}


public static void setBOARD_WIDTH(int bOARD_WIDTH) {
	BOARD_WIDTH = bOARD_WIDTH;
}


public static int getBLOCK_SIZE() {
	return BLOCK_SIZE;
}


public static void setBLOCK_SIZE(int bLOCK_SIZE) {
	BLOCK_SIZE = bLOCK_SIZE;
}


public Snake getSnake() {
	return snake;
}


public void setSnake(Snake snake) {
	this.snake = snake;
}


public Food getFood() {
	return food;
}


public void setFood(Food food) {
	this.food = food;
}


public long getStartTime() {
	return startTime;
}


public void setStartTime(long startTime) {
	this.startTime = startTime;
}


public long getElapsedTime() {
	return elapsedTime;
}


public void setElapsedTime(long elapsedTime) {
	this.elapsedTime = elapsedTime;
}


public Timer getLooper() {
	return looper;
}


public void setLooper(Timer looper) {
	this.looper = looper;
}


public int getFps() {
	return fps;
}


public void setFps(int fps) {
	this.fps = fps;
}


public int getDelay() {
	return delay;
}


public void setDelay(int delay) {
	this.delay = delay;
}


public int getPoint() {
	return point;
}


public void setPoint(int point) {
	this.point = point;
}


public int getLevel() {
	return level;
}


public void setLevel(int level) {
	Board.level = level;
}


	private void update() {
		updateTime();
		if (snake.getCollision() == false) {
			snake.update();
			checkEatFood();
			snake.checkCollision();
		}
		else {
			state = STATE_GAME_OVER;
		}
		
	}
	
	private void updateTime() {
		long currentTime = System.currentTimeMillis();
	    elapsedTime = currentTime - startTime;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.darkGray);
		g.fillRect(0, 0, BOARD_WIDTH*BLOCK_SIZE, BOARD_HEIGHT*BLOCK_SIZE);
		snake.drawSnake(g);
		food.drawFood(g);
		
		//draw board
		g.setColor(Color.darkGray);
		for(int row = 0; row < BOARD_HEIGHT; row++) {
			g.drawLine(0, BLOCK_SIZE * row, BLOCK_SIZE * BOARD_WIDTH, BLOCK_SIZE * row);
		}
		
		for(int col = 0; col < BOARD_WIDTH+1; col++) {
			g.drawLine(col * BLOCK_SIZE, 0, col * BLOCK_SIZE, BLOCK_SIZE * BOARD_HEIGHT);
		}
		
		Font font = new Font("Arial", Font.PLAIN, 20);
		g.setFont(font);
		g.setColor(Color.white);
		g.drawString("-----------------------", 420,80);
		g.setColor(Color.red);
		g.drawString("WELCOME", 440,100);
		g.setColor(Color.blue);
		g.drawString("TO", 480,130);
		g.setColor(Color.green);
		g.drawString("SNAKEGAME", 430,160);
		g.setColor(Color.white);
		g.drawString("-----------------------", 420,180);
		font = new Font("Arial", Font.PLAIN, 17);
		g.setFont(font);
		g.drawString("Level: "+level, 440,270);
		g.drawString("Score: "+point, 440,250);
		
		if(state == STATE_GAME_OVER) {
			font = new Font("Arial", Font.PLAIN, 50);
			g.setFont(font);
			g.setColor(Color.black);
			g.fillRect(0, 0, WindowGame.WIDTH, WindowGame.HEIGHT);
			g.setColor(Color.red);
			g.drawString("GAME OVER", 23*WindowGame.WIDTH/100, 40*WindowGame.HEIGHT/100);
			font = new Font("Arial", Font.PLAIN, 20);
			g.setFont(font);
			g.setColor(Color.green);
			g.drawString("Score: "+ point, 40*WindowGame.WIDTH/100, 50*WindowGame.HEIGHT/100);
			g.setColor(Color.blue);
			g.drawString("Level: "+level, 43*WindowGame.WIDTH/100, 60*WindowGame.HEIGHT/100);
			g.setFont(font);
			g.setColor(Color.white);
			g.drawString("Press Enter to Start Again!", 28*WindowGame.WIDTH/100, 70*WindowGame.HEIGHT/100);
		}
	}
		
		
	
	
	public void checkEatFood() {

		if (snake.getX()== food.getX() && snake.getY()==food.getY()) {
			snake.setLength(snake.getLength()+1);
			snake.setBodyCoords();
			food.setNewCoords(snake);
			point +=100;
			level = point/500 +1;
		}

	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		snake.changeDirection(e);
	    
	    if ( e.getKeyCode() == KeyEvent.VK_ENTER && state == STATE_GAME_OVER) {
            snake = new Snake();
            state = STATE_GAME_PLAY;
            point = 0;
            level = 1;
        }
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
