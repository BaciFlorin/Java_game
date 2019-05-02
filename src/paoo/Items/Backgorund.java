package paoo.Items;

public class Backgorund extends Item {
    public Backgorund(int x,int y)
    {
        super(x,y);
        loadImage("images/background.png");
        getImageDimensions();
    }

    public void setXY(int x,int y)
    {
        this.x=x;
        this.y=y;
    }

    public void move()
    {
        x-=1;
    }

}
