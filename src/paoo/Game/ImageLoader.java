package paoo.Game;

import javax.swing.*;
import java.awt.*;

public class ImageLoader {
    private final Image background;
    private static ImageLoader instance;

    public static ImageLoader getInstance(){
        if( instance != null){
            return instance;
        }
        else{
            return new ImageLoader();
        }
    }

    public ImageLoader() {
        this.background=loadImage("images/background.png");
    }

    public Image loadImage(String imageAddress) {
        ImageIcon icon = new ImageIcon(imageAddress);
        return icon.getImage();
    }
    public Image GetBG(){return background;}
}
