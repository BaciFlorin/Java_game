package paoo.Items;

import javax.swing.*;
import java.awt.*;

abstract public class Ship {
    public int x;
    public int y;
    public int width;
    public int height;
    public boolean vis;
    public Image image;

    public Ship(int x, int y) {

        this.x = x;
        this.y = y;
        vis = true;
    }

    protected void getImageDimensions() {
        width = image.getWidth(null);
        height = image.getHeight(null);
    }

    protected void loadImage(String imageName) {
        ImageIcon i = new ImageIcon(imageName);
        image = i.getImage();
    }

    public Image getImage() {
        return image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isVisible() {
        return vis;
    }

    public void setVisible(Boolean visible) {
        vis = visible;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void setX(int x)
    {
        this.x=x;
    }

    public void setY(int y)
    {
        this.y=y;
    }

    abstract public void increaseDifficulty(int n);

    abstract public void fire();

    abstract public java.util.List<Shoot> getShoots();

}