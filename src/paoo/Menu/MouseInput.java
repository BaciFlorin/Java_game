package paoo.Menu;
import paoo.Game.Game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput implements MouseListener {
    public void mouseClicked(MouseEvent e)
    {
        if (e.getY() > 38 && e.getY() < 150) {
            Menu.setRunning(false);
            Menu.getFrame().setVisible(false);
            if(!Game.isRunning()) {
                new Game().start();
            }
        }
        // EXIT game
        if (e.getY() > 170 && e.getY() < 280) {
            Menu.setRunning(false);
            Menu.getFrame().setVisible(false);
            Menu.getFrame().stopFrame();
        }

        if(Menu.isReloadMenu())
        {
            if(e.getY()>40 && e.getY()<200)
            {
                Menu.setRunning(false);
                Menu.getFrame().setVisible(false);
                if(!Game.isRunning())
                {
                    new Game().start();
                }
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
