package paoo.Items;

import paoo.Game.Map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class SpaceShip extends Item {
    private int dx;
    private int dy;
    private List<Missile> missiles;

    public SpaceShip(int x, int y)
    {
        super(x, y);
        initSpaceShip();
    }

    private void initSpaceShip()
    {
        missiles=new ArrayList<>();
        loadImage("images/Plane/Fly (1).png");
        getImageDimensions();
    }

    public void move(){
        x += dx;
        if(x<0)
        {
            x=0;
        }
        if(x>(Map.BOARD_WIDTH/2)-width)
        {
            x=(Map.BOARD_WIDTH/2)-width;
        }
        y += dy;
        if(y<0) {
           y=0;
        }
        if(y>Map.BOARD_HEIGHT-height-30)
        {
            y=Map.BOARD_HEIGHT-height-30;
        }
        if(x%2==0)
        {
            ImageIcon _new=new ImageIcon("images/Plane/Fly (2).png");
            image=_new.getImage();
        }
        else
        {
            ImageIcon _new=new ImageIcon("images/Plane/Fly (1).png");
            image=_new.getImage();
        }

        if(y%2==0)
        {
            ImageIcon _new=new ImageIcon("images/Plane/Fly (2).png");
            image=_new.getImage();
        }
        else
        {
            ImageIcon _new=new ImageIcon("images/Plane/Fly (1).png");
            image=_new.getImage();
        }
    }

    public void keyPressed(KeyEvent e)
    {
        int key=e.getKeyCode();

        switch (key)
        {
            case KeyEvent.VK_SPACE:fire();
            break;
            case KeyEvent.VK_LEFT:dx=-2;
            break;
            case KeyEvent.VK_RIGHT:dx=2;
            break;
            case KeyEvent.VK_DOWN:dy=2;
            break;
            case KeyEvent.VK_UP:dy=-2;
            break;
        }
    }
    public List<Missile> getMissiles()
    {
        return missiles;
    }

    public void fire()
    {
        missiles.add(new Missile(x+width,y+height/2));
    }

    public void updateImg(Image img)
    {
        image=img;
    }

    public void keyReleased(KeyEvent e)
    {
        int key=e.getKeyCode();

        switch (key)
        {
            case KeyEvent.VK_LEFT:dx=0;
                break;
            case KeyEvent.VK_RIGHT:dx=0;
                break;
            case KeyEvent.VK_DOWN:dy=0;
                break;
            case KeyEvent.VK_UP:dy=0;
                break;
        }
    }


}
