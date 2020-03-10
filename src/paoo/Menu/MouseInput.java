package paoo.Menu;
import paoo.Game.Game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput implements MouseListener {
    private Menu source;

    public MouseInput(Menu source)
    {
        this.source=source;
    }
    public void mouseClicked(MouseEvent e)
    {
            if (!source.isOptionMenu()) {
                //Play button
                if (source.getPlayButton().isPressed(e.getX(), e.getY())) {
                    source.setRunning(false);
                    source.getFrame().setVisible(false);
                    if (!Game.isRunning()) {
                        new Game().start();
                    }
                }
                // EXIT game
                if (source.getQuitButton().isPressed(e.getX(), e.getY())) {
                    source.getBase().close();
                    source.setRunning(false);
                    source.getFrame().setVisible(false);
                    source.getFrame().stopFrame();
                    System.exit(0);
                }

                //Load from file
                if (source.getLoadButton().isPressed(e.getX(), e.getY())) {
                    if (!Game.isRunning()) {
                        new Game().start();
                    }
                    Game.setLoad(true);
                    source.setRunning(false);
                    source.getFrame().setVisible(false);
                }

                //Options
                if (source.getOptionButton().isPressed(e.getX(), e.getY())) {
                    source.setOptionMenu(true);
                }

            } else {
                //Music set button
                if (source.getMusicButton().isPressed(e.getX(), e.getY())) {
                    source.setMusic(!source.getMusic());
                    if (source.getMusic()) {
                        source.getMusicButton().changeImage("images/buttons/Menu/ON.png");
                        source.setMusic(true);
                    } else {
                        source.setMusic(false);
                        source.getMusicButton().changeImage("images/buttons/Menu/OFF.png");
                    }
                }

                //Difficulty
                if (source.getMinusButton1().isPressed(e.getX(), e.getY())) {
                    source.setDificulty(source.getDificulty() - 1);
                }
                if (source.getPlusButton1().isPressed(e.getX(), e.getY())) {
                    source.setDificulty(source.getDificulty() + 1);
                }

                //level
                if (source.getMinusButton2().isPressed(e.getX(), e.getY())) {
                    source.setLevel(source.getLevel() - 1);
                }
                if (source.getPlusButton2().isPressed(e.getX(), e.getY())) {
                    source.setLevel(source.getLevel() + 1);
                }

                //Back button
                if (source.getBackButton().isPressed(e.getX(), e.getY())) {
                    source.loadSettings();
                    source.setOptionMenu(false);
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
