package paoo.Items;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import paoo.Game.Map;

public class Monster extends Item {
    private final int BOARD_WIDTH = Map.BOARD_WIDTH;
    private final int BOARD_HEIGHT = Map.BOARD_HEIGHT;
    private int dx;
    private int dy;
    private ArrayList<Rock> rocks;
    public int direction;
    private long lastFired = 0;
    private int health = 2;
    public int starLevel = 0;
    public int lives;
    public boolean shield = false;

    public void upLives() {
        this.lives += 1;
    }

    public int getHealth() {
        return health;
    }

    public void upStarLevel() {
        starLevel += 1;
    }

    public int getLives() {
        return this.lives;
    }

    public void downHealth() {
        if (shield == false) {
            this.health -= 1;
        }
    }

    public Monster(int x, int y, int lives) {
        super(x, y);
        loadImage("image/monsterLeft.png");
        getImageDimensions();
        rocks = new ArrayList<>();
        direction = 0;
        this.lives = lives;
    }

    public void move() {

        Rectangle theMonster = new Rectangle(x + dx, y + dy, width, height);

//        if (CollisionUtility.checkCollisionTankBlocks(theMonster) == false) {
//            x += dx;
//            y += dy;
//        }

        if (x > BOARD_WIDTH - width) {
            x = BOARD_WIDTH - width;
        }

        if (y > BOARD_HEIGHT - height) {
            y = BOARD_HEIGHT - height;
        }
        if (x < 1) {
            x = 1;
        }

        if (y < 1) {
            y = 1;
        }
    }

    public ArrayList<Rock> getBullets() {

        return rocks;
    }

    public void fire() {
        Rock aRock;
        if (direction == 0) {
            aRock = new Rock(x + width / 3, y, 0, false);
        }
        else if (direction == 1) {
            aRock = new Rock(x + width - 3, y + height / 3, 1, false);
        }
        else if (direction == 2) {
            aRock = new Rock(x + width / 3, (y + height) - 3, 2, false);
        }
        else {
            aRock = new Rock(x, y + height / 3, 3, false);
        }
        if (starLevel == 3) {
            aRock.upgrade();
        }
        rocks.add(aRock);
        //SoundUtility.fireSound();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Image getImage() {
        return image;
    }

    public void keyPressed(KeyEvent e) {
        int time;
        int key = e.getKeyCode();
        if (starLevel == 0) {
            time = 700;
        }
        else {
            time = 250;
        }
        if (key == KeyEvent.VK_SPACE && (System.currentTimeMillis() - lastFired) > time) {
            fire();
            lastFired = System.currentTimeMillis();
        }
        else if (key == KeyEvent.VK_LEFT) {
            dx = -1;
            dy = 0;
            if (starLevel > 1) {
                dx = -2;
            }
            ImageIcon icon = new ImageIcon("images/monsterLeft.png");
            image = icon.getImage();
            direction = 3;
        }
        else if (key == KeyEvent.VK_RIGHT) {
            dx = 1;
            dy = 0;
            if (starLevel > 1) {
                dx = 2;
            }
            ImageIcon icon = new ImageIcon("images/monsterRight.png");
            image = icon.getImage();
            direction = 1;
        }
        else if (key == KeyEvent.VK_UP) {
            //ImageIcon ii = new ImageIcon("images/monsterUp.png");
            //image = ii.getImage();
            dy = -1;
            dx = 0;
            if (starLevel > 1) {
                dy = -2;
            }
            direction = 0;
        }
        else if (key == KeyEvent.VK_DOWN) {
            //ImageIcon icon = new ImageIcon("images/monsterDown.png");
            //image = icon.getImage();
            dy = 1;
            dx = 0;
            if (starLevel > 1) {
                dy = 2;
            }
            direction = 2;
        }
    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_UP) {
            dy = 0;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 0;
        }
    }

    public void upHealth() {
        this.health += 1;
    }

    public int getStarLevel() {
        return starLevel;
    }
}
