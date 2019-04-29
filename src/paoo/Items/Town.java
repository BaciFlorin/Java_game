package paoo.Items;

public class Town extends Block {
    public boolean gameOver = false;

    public Town(int x, int y) {
        super(x, y);
        loadImage("images/townGrass.png");
        getImageDimensions();
        setHealth(1);
        setType(3);

    }

    public void updateAnimation() {
        if (gameOver == true) {
            loadImage("images/townGrassDestroyed.png");
            getImageDimensions();

        }
    }
}
