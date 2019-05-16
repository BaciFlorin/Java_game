package paoo.Game.Input;

import paoo.Game.Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandle implements KeyListener {
    public void keyPressed(KeyEvent e) {
        Game.getSpaceShip().keyPressed(e);
        switch (e.getKeyCode())
        {
            case KeyEvent.VK_ESCAPE:
                if(Game.isPause())
                {
                    Game.setPause(false);
                }
                else
                {
                    Game.setPause(true);
                }
                break;
            case KeyEvent.VK_F1:
                if(Game.isOver())
                {
                    Game.setGameOver(false);
                }
                else
                {
                    Game.setGameOver(true);
                }
                break;
        }
    }

    public void keyReleased(KeyEvent e) {
        Game.getSpaceShip().keyReleased(e);
    }

    public void keyTyped(KeyEvent e) {

    }
}
