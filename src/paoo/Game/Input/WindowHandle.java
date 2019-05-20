package paoo.Game.Input;

import paoo.Game.Game;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class WindowHandle implements WindowListener {

    @Override
    public void windowActivated(WindowEvent event) {
    }

    @Override
    public void windowClosed(WindowEvent event) {

    }

    @Override
    public void windowClosing(WindowEvent event) {

    }

    @Override
    public void windowDeactivated(WindowEvent event) {
        Game.setPause(true);
    }

    @Override
    public void windowDeiconified(WindowEvent event) {

    }

    @Override
    public void windowIconified(WindowEvent event) {

    }

    @Override
    public void windowOpened(WindowEvent event) {

    }
}
