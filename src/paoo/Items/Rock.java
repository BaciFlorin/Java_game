package paoo.Items;

import paoo.Game.Map;

public class Rock extends Item {
    private final int ROCK_SPEED = 3;
    private final int BOARD_WIDTH = Map.BOARD_WIDTH;
    private final int BOARD_HEIGHT = Map.BOARD_HEIGHT;
    private int direction;
    private boolean upgrade = false;
    public boolean isEnemy;

    public Rock(int x, int y, int direction, boolean Enemy) {
        super(x, y);
        this.direction = direction;
        if (direction == 0) {
            loadImage("images/rockUp.png");
        }
        if (direction == 1) {
            loadImage("images/rockRight.png");
        }
        if (direction == 2) {
            loadImage("images/rockDown.png");
        }
        if (direction == 3) {
            loadImage("images/rockLeft.png");
        }
        isEnemy = Enemy;
        getImageDimensions();
    }

    public void move() {
        if (direction == 0) {
            y -= ROCK_SPEED;
        } else if (direction == 1) {
            x += ROCK_SPEED;
        } else if (direction == 2) {
            y += ROCK_SPEED;
        } else if (direction == 3) {
            x -= ROCK_SPEED;
        }
        if (x > BOARD_WIDTH + 100 + width) {
            vis = false;
        }
        if (x < -width - 100) {
            vis = false;
        }
        if (y > BOARD_HEIGHT + height + 100) {
            vis = false;
        }
        if (y < -height - 100) {
            vis = false;
        }
    }

    public void upgrade() {
        this.upgrade = true;
    }

    public boolean getUpgrade() {
        return this.upgrade;
    }
}
