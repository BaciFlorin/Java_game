package paoo.Items;

public class Missile extends Item {

        private final int BOARD_WIDTH = 1160;
        private final int MISSILE_SPEED = 3;

        public Missile(int x, int y) {
            super(x, y);

            initMissile();
        }

        private void initMissile() {

            loadImage("images/Bullet/Bullet (1).png");
            getImageDimensions();
        }

        public void move() {

            x += MISSILE_SPEED;

            if (x > BOARD_WIDTH) {
                vis = false;
            }
        }
}
