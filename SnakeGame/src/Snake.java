import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Snake {
    private int length = 3;
    private int x = 5;
    private int y = 10;
    private long beginTime;
    private int snakeMovementFrequency = 170;
    private String headDirection = "right";
    private ArrayList<Integer> bodyX = new ArrayList<>();
    private ArrayList<Integer> bodyY = new ArrayList<>();
    private Boolean collision = false;

    public Snake() {
    	for (int i = 0; i < length; i++) {
            bodyX.add(x - i);
            bodyY.add(y);
        }
    }
    
    public void setBodyCoords() {
        bodyX.add(bodyX.get(bodyX.size()-2));
        bodyY.add(bodyY.get(bodyX.size()-2));
    }
    
    
    public ArrayList<Integer> getBodyX() {
		return bodyX;
	}

	public void setBodyX(ArrayList<Integer> bodyX) {
		this.bodyX = bodyX;
	}

	public ArrayList<Integer> getBodyY() {
		return bodyY;
	}

	public void setBodyY(ArrayList<Integer> bodyY) {
		this.bodyY = bodyY;
	}

	public Boolean getCollision() {
		return collision;
	}

	public void setCollision(Boolean collision) {
		this.collision = collision;
	}

	public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

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

    public long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(long beginTime) {
        this.beginTime = beginTime;
    }

    public int getSnakeMovementFrequency() {
        return snakeMovementFrequency - Board.level*snakeMovementFrequency/10;
    }

    public void setSnakeMovementFrequency(int snakeMovementFrequency) {
        this.snakeMovementFrequency = snakeMovementFrequency;
    }
    
    public String getHeadDirection() {
        return headDirection;
    }

    public void setHeadDirection(String headDirection) {
        this.headDirection = headDirection;
    }
    

    public void update() {
        if (System.currentTimeMillis() - beginTime > getSnakeMovementFrequency()) {
            move();
            beginTime = System.currentTimeMillis();
        }
    }

    public void drawSnake(Graphics g) {
        g.setColor(Color.blue);

        // Draw the head separately
        g.fillRect(x * Board.BLOCK_SIZE, y * Board.BLOCK_SIZE, Board.BLOCK_SIZE, Board.BLOCK_SIZE);

        // Draw the body segments
        g.setColor(Color.green);
        for (int i = 0; i < length - 1; i++) {
            g.fillRect(bodyX.get(i) * Board.BLOCK_SIZE, bodyY.get(i) * Board.BLOCK_SIZE, Board.BLOCK_SIZE, Board.BLOCK_SIZE);
        }
    }

    public void move() {
        // Update body positions
        for (int i = length - 1; i > 0; i--) {
            bodyX.set(i, bodyX.get(i - 1));
            bodyY.set(i, bodyY.get(i - 1));
        }
        bodyX.set(0, x);
        bodyY.set(0, y);

        // Update head position
        if (headDirection.equals("up")) {
            y--;
        } else if (headDirection.equals("down")) {
            y++;
        } else if (headDirection.equals("right")) {
            x++;
        } else {
            x--;
        }
    }

    public void changeDirection(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_W && !headDirection.equals("down")) {
            headDirection = "up";
        } else if (keyCode == KeyEvent.VK_S && !headDirection.equals("up")) {
            headDirection = "down";
        } else if (keyCode == KeyEvent.VK_D && !headDirection.equals("left")) {
            headDirection = "right";
        } else if (keyCode == KeyEvent.VK_A && !headDirection.equals("right")) {
            headDirection = "left";
        }
    }
    
    public void checkCollision() {
    	if (!(x < Board.BOARD_WIDTH && x >=0 && y < Board.BOARD_HEIGHT && y >=0)){
    		collision = true;
    	}
        for (int i = 0; i < length; i++) {
            if (x == bodyX.get(i) && y == bodyY.get(i)) {
                collision = true;
            }
        }
    }
}