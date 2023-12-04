import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Food {
	private int x = 10;
	private int y = 10;
	private Random random = new Random();
	
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Food() {
		
	}
	
	public void drawFood(Graphics g) {
		g.setColor(Color.red);
		g.fillOval(x*Board.BLOCK_SIZE, y*Board.BLOCK_SIZE, Board.BLOCK_SIZE, Board.BLOCK_SIZE);
	}
	
	public void setNewCoords(Snake snake) {
		int ranNum;
		while (true) {
			ranNum = random.nextInt(Board.BOARD_HEIGHT-1);
			x = ranNum;
			Boolean repeated = false;
			for(int xcoords : snake.getBodyX()) {
				if (xcoords == ranNum) {
					ranNum = random.nextInt(Board.BOARD_HEIGHT-1);
					y = ranNum;
					for(int ycoords : snake.getBodyX()) {
						if (ycoords == ranNum) {
							repeated = true;
							break;
						}
					}		
				}
			}
			if (repeated == false) {
				break;
			}
		}		
	}
}
