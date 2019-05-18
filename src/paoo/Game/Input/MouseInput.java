package paoo.Game.Input;

import paoo.Game.Game;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput implements MouseListener {

    public void mouseClicked(MouseEvent e) {
        if(Game.isPause())
        {
            //scoate de pe pauza
            if(Game.getResumeButton().isPressed(e.getX(),e.getY()))
            {
                Game.setPause(false);
            }

            //Salveaza jocul si inchide
            if(Game.getSaveButton().isPressed(e.getX(),e.getY()))
            {
                Game.getLevelHandle().saveLevelToFile();
                System.exit(0);
            }

            //inchide jocul
            if(Game.getQuitButton().isPressed(e.getX(),e.getY()))
            {
                Game.getLevelHandle().getBase().close();
                Game.getLevelHandle().getLbase().close();
                System.exit(0);
            }
        }
        if(Game.isOver())
        {
            //incarca din nou acelasi level si revine
            if(Game.getReloadButton().isPressed(e.getX(),e.getY()))
            {
                Game.restore();
                Game.setGameOver(false);
            }
            //iese din joc
            if(Game.getQuitButton().isPressed(e.getX(),e.getY()))
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
