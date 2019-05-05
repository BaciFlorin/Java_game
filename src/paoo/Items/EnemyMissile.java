package paoo.Items;

public class EnemyMissile extends Item {
    private final int BOARD_WIDTH = 1160;
    private final int MISSILE_SPEED = 3;

    public EnemyMissile(int x, int y) {
        super(x, y);

        initMissile();
    }

    private void initMissile() {

        loadImage("images/star.png");
        getImageDimensions();
    }

    public void move() {

        x -= MISSILE_SPEED;

        if (x <0) {
            vis = false;
        }
    }
}
