package paoo.Game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import paoo.Items.*;

import javax.swing.Timer;
import javax.swing.JPanel;
/**
 * Board class of the game
 */
public class Board extends JPanel implements ActionListener {

    private ArrayList<Item> items = new ArrayList<>();
    private SpaceShip spaceShip;
    private Timer timer;

    public Board() {
        initBoard();
    }

    /**
     * Initialize the board.
     */
    private void initBoard() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(Map.BOARD_WIDTH, Map.BOARD_HEIGHT));
        spaceShip=new SpaceShip(20,20);
        timer=new Timer(10,this);
        timer.start();
        initBlocks();
    }

    /**
     * Initialize blocks according to the map.
     */
    void initBlocks() {
        int type;
        for (int x = 0; x < Map.level.length; x++) {
            for (int y = 0; y < Map.level[0].length; y++) {
                type = Map.level[x][y];
                BlockType bType = BlockType.getTypeFromInt(type);
                switch (bType) {
                    case BACKGROUND:
                        items.add(new Backgorund(y * 1366, x * 720));
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawObjects(g);
        Toolkit.getDefaultToolkit().sync();
    }

    /**
     * Draw objects on the board.
     */
    private void drawObjects(Graphics g) {
        Graphics2D g2d=(Graphics2D) g;
        for (Item a : items) {
            if (a.isVisible()) {
                g2d.drawImage(a.getImage(), a.getX(), a.getY(), this);
            }
        }
        g2d.drawImage(spaceShip.getImage(), spaceShip.getX(),
                spaceShip.getY(), this);

        java.util.List<Missile> missiles = spaceShip.getMissiles();

        for (Missile missile : missiles) {

            g2d.drawImage(missile.getImage(), missile.getX(),
                    missile.getY(), this);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        updateMissiles();
        updateSpaceShip();
        repaint();
    }
    private void updateMissiles() {
        java.util.List<Missile> missiles = spaceShip.getMissiles();

        for (int i = 0; i < missiles.size(); i++) {

            Missile missile = missiles.get(i);

            if (missile.isVisible()) {

                missile.move();
            } else {

                missiles.remove(i);
            }

        }
    }

    private void updateSpaceShip() {

        spaceShip.move();
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            spaceShip.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            spaceShip.keyPressed(e);
        }
    }
}
