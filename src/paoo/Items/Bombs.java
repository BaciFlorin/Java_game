package paoo.Items;

public class Bombs extends Shoot{
    private boolean explosion=false;
    private int waitTime=100;

    public Bombs(int x, int y) {
        super(x, y);
        initMissile();
    }

    private void initMissile() {

        loadImage("images/Bullet/bomb.png");
        getImageDimensions();
    }

    @Override
    public void move() {
        if(!explosion) {
            x -= MISSILE_SPEED;
        }
        else
        {
            loadImage("images/explosion0.png");
            waitTime--;
            if(waitTime==0)
            {
                vis=false;
            }
        }
        if (x ==100) {
            explosion = true;
        }
    }
}
