package paoo.Items;

public class Tree extends Block{
    public Tree(int x, int y) {
        super(x, y);
        loadImage("images/tree.png");
        getImageDimensions();
        setType(5);
        setHealth(1);
    }
}
