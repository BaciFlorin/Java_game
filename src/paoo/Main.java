package paoo;

import paoo.Game.GameView;
import paoo.Game.Board;

public class Main {

    public static void main(String[] args) throws Exception{
	    GameView theView = new GameView();

	    Board panel = new Board();
        panel.revalidate();
        panel.repaint();

        theView.getPanel().add(panel);
        panel.requestFocusInWindow();
        theView.setVisible(true);
    }
}
