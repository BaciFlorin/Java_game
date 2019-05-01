package paoo.Items;

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

    public void move()
    {
        x+=dx;
        y+=dy;
    }

    public void keyPressed(KeyEvent e)
    {
        int key=e.getKeyCode();

        switch (key)
        {
            case KeyEvent.VK_SPACE:fire();
            break;
            case KeyEvent.VK_LEFT:dx=-1;
            break;
            case KeyEvent.VK_RIGHT:dx=1;
            break;
            case KeyEvent.VK_DOWN:dy=1;
            break;
            case KeyEvent.VK_UP:dy=-1;
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
