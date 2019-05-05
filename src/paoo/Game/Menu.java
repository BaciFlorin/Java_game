package paoo.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JPanel{
    private JButton PlayButton;
    private JButton HelpButton;
    private JButton QuitButton;
    private JButton QuitButton2;
    private JButton ReplayButton;
    private JPanel Buttons;
    private JPanel GameOver;
    private boolean inGame=false;
    private Board game;
    public Menu()
    {
        setSize(new Dimension(Map.BOARD_WIDTH,Map.BOARD_HEIGHT));

        //Game over Menu

        GameOver=new JPanel();
        GameOver.setSize(Map.BOARD_WIDTH/2,Map.BOARD_HEIGHT);
        ReplayButton=new JButton("Replay");
        ReplayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game=new Board();
                game.setVisible(true);
                game.repaint();
                game.revalidate();
            }
        });

        QuitButton2=new JButton("Quit");
        QuitButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        GameOver.add(QuitButton2);
        GameOver.add(ReplayButton);
        add(GameOver);
        GameOver.setVisible(false);

        //Others buttons

        Buttons=new JPanel();
        Buttons.setLayout(new GridLayout(3,1));
        Buttons.setLocation(Map.BOARD_WIDTH/2,100);
        PlayButton=new JButton("Play");
        PlayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.setVisible(true);
                Buttons.setVisible(false);
            }
        });
        HelpButton=new JButton("Help");
        QuitButton=new JButton("Quit");
        QuitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        Buttons.add(PlayButton);
        Buttons.add(HelpButton);
        Buttons.add(QuitButton);
        add(Buttons);

        //Game
        game=new Board();
        game.revalidate();
        game.repaint();
        game.requestFocusInWindow();
        add(game);
        game.setVisible(false);


        if(!game.isRunning())
        {
            game.setVisible(false);
            GameOver.setVisible(true);
        }
        setVisible(true);
    }
}
