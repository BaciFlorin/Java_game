package paoo.Game;

import javax.swing.*;
import java.awt.*;

public class ImageLoader {
    private final Image background;
    private final Image[] spaceShip;
    private static ImageLoader instance;
    private final Image missile;

    public static ImageLoader getInstance(){
        if( instance != null){
            return instance;
        }
        else{
            return new ImageLoader();
        }
    }

    public ImageLoader() {
        this.spaceShip=new Image[2];
        this.spaceShip[0]=loadImage("images/Plane/Fly (1).png");
        this.spaceShip[1]=loadImage("images/Plane/Fly (2).png");
        this.background=loadImage("images/background.png");
        this.missile=loadImage("images/Bullet/Bullet (1).png");
    }

    public Image loadImage(String imageAddress) {
        ImageIcon icon = new ImageIcon(imageAddress);
        return icon.getImage();
    }
    public Image GetBG(){
        return background;
    }
    public Image[] GetSpaceShip(){
        return spaceShip;
    }
    public Image GetMissile()
    {
        return missile;
    }
}
