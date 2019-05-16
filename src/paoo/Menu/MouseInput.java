package paoo.Menu;
import paoo.Game.Game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput implements MouseListener {
    public void mouseClicked(MouseEvent e)
    {
        if(!Menu.isOptionMenu()) {
            if (e.getY() > 100 && e.getY() < 150) {
                Menu.setRunning(false);
                Menu.getFrame().setVisible(false);
                if (!Game.isRunning()) {
                    new Game().start();
                }
            }
            // EXIT game
            if (e.getY() > 500 && e.getY() < 550) {
                Menu.setRunning(false);
                Menu.getFrame().setVisible(false);
                Menu.getFrame().stopFrame();
                System.exit(0);
            }
            //Load
            if (e.getY() > 200 && e.getY() < 250) {

            }

            //Options
            if (e.getY() > 300 && e.getY() < 350) {
                Menu.setOptionMenu(true);
            }

            //Help

            if (e.getY() > 400 && e.getY() < 450) {

            }
        }
        else
        {
            if (e.getY() > 400 && e.getY() < 450) {
                Menu.setOptionMenu(false);
            }

            if(e.getX()>300 && e.getX()<400 && e.getY()>100 && e.getY()<200)
            {
                if(Menu.isMusic())
                {
                    Menu.setMusic(false);
                }
                else
                {
                    Menu.setMusic(true);
                }
            }

            if(e.getX()>330 && e.getX()<362 && e.getY()>220 && e.getY()<252)
            {
                Menu.setDificulty(Menu.getDificulty()+1);
                System.out.println("Diff:"+Menu.getDificulty());
            }
            if(e.getX()>450 && e.getX()<482 && e.getY()>220 && e.getY()<252)
            {
                Menu.setDificulty(Menu.getDificulty()-1);
                System.out.println("Diff:"+Menu.getDificulty());
            }
        }
    }

    public void mousePressed(MouseEvent e)
    {

    }

    public void mouseReleased(MouseEvent e)
    {

    }
    public void mouseEntered(MouseEvent e)
    {

    }

    public void mouseExited(MouseEvent e)
    {

    }
}
