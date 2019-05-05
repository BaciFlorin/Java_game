package paoo;

import paoo.Game.GameView;
import paoo.Game.Board;
import paoo.Game.Menu;

public class Main {

    public static void main(String[] args) throws Exception{
        GameView theView = new GameView();

	    Menu panel = new Menu();

        theView.getPanel().add(panel);
        panel.requestFocusInWindow();
        theView.setVisible(true);
    }
}
