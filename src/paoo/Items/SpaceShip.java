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
    private List<Shoot> missiles;
    private int nr_shoots;

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
        nr_shoots=5;
    }

    public void move(){
        x += dx;
        if(x<10)
        {
            x=10;
        }
        if(x>(Map.BOARD_WIDTH/2)-width)
        {
            x=(Map.BOARD_WIDTH/2)-width;
        }
        y += dy;
        if(y<100) {
           y=100;
        }
        if(y>Map.BOARD_HEIGHT-height-110)
        {
            y=Map.BOARD_HEIGHT-height-110;
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
    public List<Shoot> getMissiles()
    {
        return missiles;
    }

    public synchronized void fire()
    {
        if(nr_shoots!=0) {
            missiles.add(new Missile(x + width, y + height / 2));
            nr_shoots--;
        }
    }

    public synchronized void regenerateAmu()
    {
        if(nr_shoots<5) {
            nr_shoots++;
        }
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
    public int getNr_shoots(){return nr_shoots;}
    public void setNr_shoots(int n){nr_shoots=n;}
}
