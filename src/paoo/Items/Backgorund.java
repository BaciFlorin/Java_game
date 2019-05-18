package paoo.Items;

import javax.swing.*;

public class Backgorund extends Item {
    public Backgorund(int x,int y)
    {
        super(x,y);
        loadImage("images/background1-720.png");
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

    public void changeImage(String simage)
    {
        ImageIcon img=new ImageIcon(simage);
        image=img.getImage();
    }
}
