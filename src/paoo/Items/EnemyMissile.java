package paoo.Items;

public class EnemyMissile extends Shoot {

    public EnemyMissile(int x, int y) {
        super(x, y);
        initMissile();
    }

    private void initMissile() {

        loadImage("images/star.png");
        getImageDimensions();
    }

    @Override
    public void move() {

        x -= MISSILE_SPEED;

        if (x <0) {
            vis = false;
        }
    }
}
