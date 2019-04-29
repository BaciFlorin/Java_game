package paoo.Game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import paoo.Items.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import javax.swing.JPanel;
/**
 * Board class of the game
 */
public class Board extends JPanel implements ActionListener {

    private ArrayList<Item> items = new ArrayList<>();

    public Board() {
        initBoard();
    }

    /**
     * Initialize the board.
     */
    private void initBoard() {
        setFocusable(true);
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(Map.BOARD_WIDTH, Map.BOARD_HEIGHT));

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
                    case GRASS:
                        items.add(new Grass(y * 48, x * 48));
                        break;
                    case SOIL:
                        items.add(new Soil(y * 48, x * 48));
                        break;
                    case WATER:
                        items.add(new Water(y * 48, x * 48));
                        break;
                    case TOWN:
                        items.add(new Town(y * 48, x * 48));
                        break;
                    case TREE:
                        items.add(new Tree(y * 48, x * 48));
                        break;
                    case MOUNTAIN:
                        items.add(new Mountain(y * 48, x * 48));
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
        for (Item a : items) {
            if (a.isVisible()) {
                g.drawImage(a.getImage(), a.getX(), a.getY(), this);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

}
