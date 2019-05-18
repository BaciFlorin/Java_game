package paoo.Menu.Buttons;

import javax.swing.*;
import java.awt.*;

public class MyButton {

    private int x;
    private int y;
    private int width;
    private int height;

    private Image image;

    public MyButton(String imagine,int x, int y)
    {
        image=(new ImageIcon(imagine)).getImage();
        this.x=x;
        this.y=y;
        this.height=image.getHeight(null);
        this.width=image.getWidth(null);
    }

    public void render(Graphics g)
    {
        g.drawImage(image,x,y,null);
    }

    public boolean isPressed(int xx,int yy)
    {
        return xx>x && xx<x+width && yy>y && yy<y+height;
    }

    public void changeImage(String imagine)
    {
        image=(new ImageIcon(imagine)).getImage();
    }

    public void changeLocation(int x, int y){this.x=x; this.y=y;}
}
