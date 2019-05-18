package paoo.Items;

abstract public class Shoot extends Item {
    public final int BOARD_WIDTH = 1160;
    public final int MISSILE_SPEED = 3;

    public Shoot(int x, int y) {
        super(x,y);
    }
    abstract public void move();
}
