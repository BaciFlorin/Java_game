package paoo.Game.Input;

import paoo.Game.Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandle implements KeyListener {
    public void keyPressed(KeyEvent e) {
        Game.getSpaceShip().keyPressed(e);
    }

    public void keyReleased(KeyEvent e) {
        Game.getSpaceShip().keyReleased(e);
    }

    public void keyTyped(KeyEvent e) {

    }
}
