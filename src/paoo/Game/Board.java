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

    private SpaceShip spaceShip;
    private Timer timer;
    private Backgorund[] backgorunds;
    private ArrayList<SmallEnemy> stage1;
    private boolean inGame;

    public Board() {
        inGame=true;
        spaceShip=new SpaceShip(20,20);
        timer=new Timer(20,this);
        timer.start();
        initBoard();
    }

    /**
     * Initialize the board.
     */
    private void initBoard() {
        //background initialization
        backgorunds=new Backgorund[2];
        backgorunds[0]=new Backgorund(0,0);
        backgorunds[1]=new Backgorund(Map.BOARD_WIDTH,0);

        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(Map.BOARD_WIDTH, Map.BOARD_HEIGHT));
        setMaximumSize(new Dimension(Map.BOARD_WIDTH, Map.BOARD_HEIGHT));
        initEnemyes();
    }

    /**
     * Initialize enemyes according to the map.
     */
    private void initEnemyes() {
        stage1=new ArrayList<>();

        for(int[]p:Map.enemyesPos)
        {
            stage1.add(new SmallEnemy(p[0],p[1]));
        }
    }


    /*
        Paint component
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(inGame) {
            drawObjects(g);
        }
        else
        {
            drawGameOver(g);
        }
        Toolkit.getDefaultToolkit().sync();
    }

    /**
     * Draw objects on the board.
     */
    private void drawObjects(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;


            for (Backgorund a : backgorunds) {
                if (a.isVisible()) {
                    g2d.drawImage(a.getImage(), a.getX(), a.getY(), this);
                }
            }
            if (spaceShip.isVisible()) {
                g2d.drawImage(spaceShip.getImage(), spaceShip.getX(),
                        spaceShip.getY(), this);
            }

            java.util.List<Missile> missiles = spaceShip.getMissiles();

            for (Missile missile : missiles) {
                if (missile.isVisible()) {
                    g2d.drawImage(missile.getImage(), missile.getX(),
                            missile.getY(), this);
                }
            }

            for (SmallEnemy a : stage1) {
                g2d.drawImage(a.getImage(), a.getX(), a.getY(), this);
                java.util.List<EnemyMissile> missileList = a.GetMissiles();

                for (EnemyMissile e : missileList) {
                    if (e.isVisible()) {
                        g2d.drawImage(e.getImage(), e.getX(), e.getY(), this);
                    }
                }
            }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(this.isVisible()) {
            ingame();
            updateMissiles();
            updateSpaceShip();
            updateBackground();
            updateEnemyes();
            checkCollisions();
            repaint();
        }
    }

    //Game over text

    private void drawGameOver(Graphics g) {

        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics fm = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (Map.BOARD_WIDTH - fm.stringWidth(msg)) / 2,
                Map.BOARD_HEIGHT / 2);
    }

    /*
        Update functions
     */

    private void updateSpaceShip() {
        if(spaceShip.isVisible()) {
            spaceShip.move();
        }
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

        for(SmallEnemy a:stage1) {
            java.util.List<EnemyMissile> missileList = a.GetMissiles();
            for (int i=0;i<missileList.size();i++) {
                EnemyMissile m=missileList.get(i);
                if (m.isVisible()) {
                    m.move();
                } else {
                    missileList.remove(i);
                }
            }
        }
    }

    private void updateBackground()
    {
        for(int i=0;i<backgorunds.length;i++)
        {
            backgorunds[i].move();
            if(backgorunds[i].getX()<-Map.BOARD_WIDTH)
            {
                backgorunds[i].setXY(backgorunds[backgorunds.length-i-1].getX()+Map.BOARD_WIDTH,backgorunds[backgorunds.length-i-1].getY());
            }
        }
    }

    private void updateEnemyes()
    {
        if(stage1.isEmpty())
        {
            inGame=false;
        }

        for(int i=0;i<stage1.size();i++)
        {
            SmallEnemy en=stage1.get(i);
            if(en.isVisible())
            {
                en.moveAndFire();
            }else
            {
                stage1.remove(i);
            }
        }
    }

    /*
        Checks if game is still running and stops the timer
     */

    private void ingame() {

        if (!inGame) {
            timer.stop();
        }
    }

    /*
        Collisions
     */

    private void checkCollisions()
    {
        Rectangle r3=spaceShip.getBounds();
        for(SmallEnemy enemy:stage1)
        {
            Rectangle r2=enemy.getBounds();

            if(r3.intersects(r2))
            {
                spaceShip.setVisible(false);
                enemy.setVisible(false);
                inGame=false;
            }

            java.util.List<EnemyMissile> missileList=enemy.GetMissiles();

            for(EnemyMissile m:missileList)
            {
                Rectangle r4=m.getBounds();
                if(r3.intersects(r4))
                {
                    spaceShip.setVisible(false);
                    m.setVisible(false);
                    inGame=false;
                }
            }
        }

        java.util.List<Missile>  ms=spaceShip.getMissiles();

        for(Missile m:ms)
        {
            Rectangle r1=m.getBounds();

            for(SmallEnemy enemy:stage1)
            {
                Rectangle r2=enemy.getBounds();

                if(r1.intersects(r2))
                {
                    m.setVisible(false);
                    enemy.setVisible(false);
                }
            }
        }
    }

    /*
        Key Adapter
     */

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
    /*
        New functions to add
     */
    public boolean isRunning()
    {
        return inGame;
    }
}
