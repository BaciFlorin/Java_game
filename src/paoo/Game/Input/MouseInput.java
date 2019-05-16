package paoo.Game.Input;

import paoo.Game.Game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput implements MouseListener {

    public void mouseClicked(MouseEvent e) {
        if(Game.isPause())
        {
            if(e.getY()<250 && e.getX()<700 && e.getY()>200 && e.getX()>400)
            {
                Game.setPause(false);
            }
            if(e.getY()<350 && e.getX()<700 && e.getY()>300 && e.getX()>400)
            {
                Game.setPause(false);
            }
            if(e.getY()<450 && e.getX()<700 && e.getY()>400 && e.getX()>400)
            {
                System.exit(0);
            }
        }
        if(Game.isOver())
        {
            if(e.getY()<550 && e.getX()<700 && e.getY()>500 && e.getX()>400)
            {
                Game.setGameOver(false);
            }
            if(e.getY()<550 && e.getX()<900 && e.getY()>500 && e.getX()>650)
            {
                System.exit(0);
            }
        }
    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }
}
