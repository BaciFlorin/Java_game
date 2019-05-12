package paoo.Items;

import javax.swing.*;
import java.awt.*;

public class AnimatedItem {
    private int x;
    private int y;
    private boolean vis;
    private int nr_animatii;
    private int width;
    private int height;
    private Image[] images;
    private Image image;

    public AnimatedItem(int x, int y, int nr)
    {
        this.x=x;
        this.y=y;
        vis=true;
        this.nr_animatii=nr;
        images=new Image[nr_animatii];
    }

    public void render(Graphics g)
    {

    }

    public void tick()
    {

    }

    public void loadImages(String[] imagini) {
        for (int i = 0; i < imagini.length; i++)
        {
            ImageIcon im=new ImageIcon(imagini[i]);
            images[i]=im.getImage();
        }
        image=images[0];
    }

    public void loadDimensions()
    {
            width=images[0].getWidth(null);
            height=images[0].getHeight(null);
    }

    public Image getImage()
    {
        return image;
    }

}
