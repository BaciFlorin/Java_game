package paoo.Game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import paoo.Game.Input.InputHandle;
import paoo.Game.Input.MouseInput;
import paoo.Game.Input.WindowHandle;
import paoo.Game.Level.LevelHandle;
import paoo.Items.*;
import paoo.Menu.Menu;

import javax.swing.Timer;
import javax.swing.JPanel;
/**
 * Board class of the game
 */
public class Game implements Runnable {

    //Settings of the game
    private static final int WIDTH=Map.BOARD_WIDTH;
    private static final int HEIGHT=Map.BOARD_HEIGHT;
    private static final int SCALE=3;
    private static final String NAME="Game";
    private static JFrame frame;


    //Proprieties

    private static boolean changeLevel=false;
    private static boolean pause=false;
    private static boolean gameOver=false;
    private static int map=0;
    private static int fps;
    private static int tps;
    private static int steps;

    //Inputs and componenets
    private static boolean running=false;
    private static InputHandle input;
    private static MouseInput mouse;
    private static WindowHandle window;
    private int tickCount=0;
    private Font font;

    //elements of the game
    private int lives=3;
    private java.util.List<Shoots> incercari;
    private java.util.List<Heart> hearts;
    private static SpaceShip spaceShip;
    private Timer timer;
    private LevelHandle level;


    public void setLives(int n){lives=n;}
    public int getLives(){return lives;}
    public Game() {
        frame=new JFrame();
        frame.setMinimumSize(new Dimension(WIDTH,HEIGHT));
        frame.setMaximumSize(new Dimension(WIDTH,HEIGHT));
        frame.setPreferredSize(new Dimension(WIDTH,HEIGHT));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.requestFocus();
        frame.setName(NAME);
    }

    /**
     * Initialize
     */
    private void init() {
        //background initialization
        input=new InputHandle();
        mouse=new MouseInput();
        window=new WindowHandle();
        level=new LevelHandle();
        level.loadLevelFromSQL();
        hearts=new ArrayList<>();
        for(int i=0;i<lives;i++)
        {
            hearts.add(new Heart(120+i*50,595));
        }
        frame.addKeyListener(input);
        frame.addMouseListener(mouse);
        frame.addWindowListener(window);

        /*backgorunds[0]=new Backgorund(0,0);
        backgorunds[1]=new Backgorund(Map.BOARD_WIDTH,0);*/
        spaceShip=new SpaceShip(30,30);
        incercari=new ArrayList<>();
        for(int i=0;i<spaceShip.getNr_shoots();i++)
        {
            incercari.add(new Shoots(200+i*50,640));
        }
        font=new Font();
    }

    /*
    Start method
     */

    public synchronized void start()
    {
        Game.setRunning(true);
        new Thread(this,"Game").start();
    }

    /*
    Stop method
     */
    public synchronized void stop()
    {
        Game.setRunning(false);
    }

    /*
    Run method
     */

    public void run() {
        long lastTime = System.nanoTime();
        double nsPerTick = 1000000000D / 60D;                // The number of nanoseconds in one tick (number of ticks limited to 60 per update)
        // 1 billion nanoseconds in one second
        int ticks = 0;
        int frames = 0;

        long lastTimer = System.currentTimeMillis();                // Used for updating ticks and frames once every second
        double delta = 0;

        init();

        while (Game.isRunning()) {
            long now = System.nanoTime();                // Current time (now) compared to lastTime to calculate elapsed time
            delta += (now - lastTime) / nsPerTick;                // Elapsed time in seconds multiplied by 60
            lastTime = now;
            boolean shouldRender = false;
            boolean shouldUpdate=false;

            while (delta >= 1) {                                // Once 1/60 seconds or more have passed
                ticks++;
                tick();                                        // Tick updates
                delta -= 1;                                // Delta becomes less than one again and the loop will close
                shouldRender = true;
                shouldUpdate=true;
            }

            try {
                Thread.sleep(2);                        // Delays the thread by 2 milliseconds - prevents the loop from using too much CPU
            } catch (InterruptedException e) {                // If the current thread is interrupted, the interrupted status is cleared
                e.printStackTrace();
            }
            if(shouldUpdate && !Game.isPause() && !Game.isOver())
            {
                updateMissiles();
                updateSpaceShip();
                updateEnemyes();
                checkCollisions();
            }

            if (shouldRender) {
                frames++;
                render();
            }

            if (System.currentTimeMillis() - lastTimer >= 1000) {                     // If elapsed time is greater than or equal to 1 second, update
                lastTimer += 1000;                                                // Updates in another second
                fps = frames;
                tps = ticks;
                frames = 0;                                                       // Reset the frames once per second
                ticks = 0;                                                        // Reset the ticks once per second
            }
        }

    }

    /**
     * This method updates the logic of the game.
     */

    public void tick() {
        setTickCount(getTickCount() + 1);
        level.tick();
    }


    /*
    Render method
     */

    public void render()
    {

        BufferStrategy bs = frame.getBufferStrategy();
        if (bs == null) {
            frame.createBufferStrategy(3);                                        // Creates a new bs with triple buffering, which reduces tearing and cross-image pixelation
            return;
        }
        Graphics g2d = bs.getDrawGraphics();
        if(Game.isOver()) {
            g2d.drawImage((new ImageIcon("images/gameover.jpg")).getImage(), -100, 0, null);
            g2d.drawImage((new ImageIcon("images/buttons/Reload.png")).getImage(), 200, 500, null);
            g2d.drawImage((new ImageIcon("images/buttons/Quit.png")).getImage(), 650, 500, null);

        }
        else {

            if (!isPause()) {
                level.render(g2d);
                if (spaceShip.isVisible()) {
                    g2d.drawImage(spaceShip.getImage(), spaceShip.getX(),
                            spaceShip.getY(), null);
                }

                java.util.List<Missile> missiles = spaceShip.getMissiles();

                for (Missile missile : missiles) {
                    if (missile.isVisible()) {
                        g2d.drawImage(missile.getImage(), missile.getX(),
                                missile.getY(), null);
                    }
                }

                font.render("Lifes:", g2d, 20, 618);
                for (Heart a : hearts) {
                    if (a.isVisible()) {
                        g2d.drawImage(a.getImage(), a.getX(), a.getY(), null);
                    }
                }
                font.render("Ammunition:", g2d, 20, 660);
                for (Shoots a : incercari) {
                    if (a.isVisible()) {
                        g2d.drawImage(a.getImage(), a.getX(), a.getY(), null);
                    }
                }
            } else {
                g2d.drawImage((new ImageIcon("images/backgr.png")).getImage(), 0, 0, null);
                g2d.drawImage((new ImageIcon("images/buttons/Game_pause.png")).getImage(), 240, 100, null);

                g2d.drawImage((new ImageIcon("images/buttons/resume.png")).getImage(), 400, 200, null);
                g2d.drawImage((new ImageIcon("images/buttons/Save.png")).getImage(), 400, 300, null);
                g2d.drawImage((new ImageIcon("images/buttons/Quit.png")).getImage(), 400, 400, null);

            }
        }
        g2d.dispose();                                                                            // Frees up memory and resources for graphics
        bs.show();
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
                spaceShip.regenerateAmu();
            }
        }

        for(int i=0;i<spaceShip.getNr_shoots();i++)
        {
            incercari.get(i).setVisible(true);
        }
        for(int i=spaceShip.getNr_shoots();i<5;i++)
        {
            incercari.get(i).setVisible(false);
        }

        for(int i=0;i<level.getItems().size();i++) {
            SmallEnemy a=(SmallEnemy)level.getItems().get(i);
            java.util.List<EnemyMissile> missileList = a.GetMissiles();
            for (int j=0;j<missileList.size();j++) {
                EnemyMissile m=missileList.get(j);
                if (m.isVisible()) {
                    m.move();
                } else {
                    missileList.remove(j);
                }
            }
        }
    }


    private void updateEnemyes()
    {
        if(level.getItems().isEmpty())
        {
           stop();
        }

        for(int i=0;i<level.getItems().size();i++)
        {
            SmallEnemy en=(SmallEnemy)level.getItems().get(i);
            if(en.isVisible())
            {
                en.moveAndFire();
            }else
            {
                level.getItems().remove(i);
            }
        }
    }

    /*
        Collisions
     */

    private void checkCollisions()
    {
        Rectangle r3=spaceShip.getBounds();
        for(int i=0;i<level.getItems().size();i++)
        {
            SmallEnemy enemy=(SmallEnemy)level.getItems().get(i);
            Rectangle r2=enemy.getBounds();

            if(r3.intersects(r2))
            {
                spaceShip.setVisible(false);
                enemy.setVisible(false);
                Game.setRunning(false);
                Game.setGameOver(true);
            }

            java.util.List<EnemyMissile> missileList=enemy.GetMissiles();

            for(EnemyMissile m:missileList)
            {
                if(m.isVisible()) {
                    Rectangle r4 = m.getBounds();
                    if (r3.intersects(r4)) {
                        if(lives!=0) {
                            hearts.get(lives - 1).setVisible(false);
                            lives--;
                            m.setVisible(false);
                            if (lives == 0) {
                                Game.setGameOver(true);
                            }
                        }
                    }
                }
            }
        }

        java.util.List<Missile>  ms=spaceShip.getMissiles();

        for(Missile m:ms)
        {
            Rectangle r1=m.getBounds();

            for(int i=0;i<level.getItems().size();i++)
            {
                SmallEnemy enemy=(SmallEnemy)level.getItems().get(i);
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
        Get and set functions
     */
    public static boolean isRunning()
    {
        return running;
    }

    public static void setRunning(boolean running){Game.running=running;}

    public static boolean isChangeLevel(){return changeLevel;}

    public static void setChangeLevel(boolean changeLevel){Game.changeLevel=changeLevel;}

    public static InputHandle getInput(){return input;}

    public static void setInput(InputHandle input){Game.input=input;}

    public static MouseInput getMouse(){return mouse;}

    public static void setMouse(MouseInput mouse){Game.mouse=mouse;}

    public int getTickCount(){return tickCount;}

    public void setTickCount(int tick){tickCount=tick;}
    public static SpaceShip getSpaceShip(){return spaceShip;}

    public static JFrame getFrame(){return frame;}

    public static boolean isPause(){return pause;}

    public static void setPause(boolean p){pause=p;}

    public static void setGameOver(boolean o){gameOver=o;}

    public static boolean isOver(){return gameOver;}
}
