package paoo.Game;

import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import paoo.Game.Input.InputHandle;
import paoo.Game.Input.MouseInput;
import paoo.Game.Input.WindowHandle;
import paoo.Game.Level.LevelHandle;
import paoo.Items.*;
import paoo.Menu.Buttons.MyButton;

import javax.swing.Timer;
/**
 * Board class of the game
 */
public class Game implements Runnable {

    //setarile ferestrei de joc
    private static final int WIDTH=Map.BOARD_WIDTH;
    private static final int HEIGHT=Map.BOARD_HEIGHT;
    private static final int SCALE=3;
    private static final String NAME="Game";
    private static JFrame frame;

    //butoanele care apar pe parcurusul jocului
    private static MyButton quitButton,reloadButton, resumeButton, saveButton;

    //variabile booleene care imi controleaza felul cum se comporta fereastra
    private static boolean changeLevel=false;
    private static boolean pause=false;
    private static boolean gameOver=false;
    private static boolean running=false;
    private static boolean win=false;

    //proprietati
    private static int fps;
    private static int tps;
    private static int steps;

    //Inputs
    private static InputHandle input;
    private static MouseInput mouse;
    private static WindowHandle window;

    //altele
    private int tickCount=0;
    private Font font;

    //componenete ale jocului
    private static int lives=3;
    private static java.util.List<Shoots> incercari;
    private static java.util.List<Heart> hearts;
    private static SpaceShip spaceShip;
    private Timer timer;
    private static LevelHandle level;
    private static int score;
    private static int enemyesLeft;
    private static int nivel;
    private int waitTime=200;

    //variabila asta imi spune daca jocul va fi incarcat din fisier sau din tabel
    private static boolean load=false;
    public static void setLoad(boolean p){load=p;}
    public static boolean getLoad(){return load;}

    //
    public static void setLives(int n){lives=n;}
    public static int getLives(){return lives;}

    //constructorul care imi intializeaza fereastra cu setarile necesare
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
     * functia care imi initializeaza componentele jocului
     */
    private void init() {
        //input
        input=new InputHandle();
        mouse=new MouseInput();
        window=new WindowHandle();

        //numarul de vieti si corespondenta lor grafica
        hearts=new ArrayList<>();
        for(int i=0;i<lives;i++)
        {
            hearts.add(new Heart(120+i*50,595));
        }

        //
        frame.addKeyListener(input);
        frame.addMouseListener(mouse);
        frame.addWindowListener(window);
        //nava
        spaceShip=new SpaceShip(30,30);
        //numarul de trageri ale navei
        incercari=new ArrayList<>();
        for(int i=0;i<spaceShip.getNr_shoots();i++)
        {
            incercari.add(new Shoots(200+i*50,640));
        }
        font=new Font();

        //butoanele
        quitButton=new MyButton("images/buttons/Quit.png",400,400);
        resumeButton=new MyButton("images/buttons/resume.png",400,200);
        saveButton=new MyButton("images/buttons/Save.png",400,300);
        reloadButton=new MyButton("images/buttons/Reload.png",200,500);

        //level handle
        level=new LevelHandle();
        if(!Game.getLoad()) {
            level.loadLevelFromSQL();
        }
        else
        {
            level.loadLevelFromFile();
        }
        score=0;
        enemyesLeft=level.getItems().size();
        nivel=level.getLevel();
    }

    public static synchronized void restore()
    {
        level.loadLevelFromSQL();
        lives=3;
        for(int i=0;i<hearts.size();i++)
        {
            hearts.get(i).setVisible(true);
        }
        score=0;
        spaceShip.setX(30);
        spaceShip.setY(30);
        java.util.List<Shoot> missile=spaceShip.getMissiles();
        for(Shoot i:missile)
        {
            i.setVisible(false);
        }
        enemyesLeft=level.getItems().size();
    }

    public static synchronized void nextLvl()
    {
        level.getSbase().saveScore(score,nivel);
        nivel++;
        if(nivel>3)
        {
            nivel=1;
        }
        level.setLevel(nivel);
        enemyesLeft=level.getItems().size();
        lives=3;
        for(int i=0;i<hearts.size();i++)
        {
            hearts.get(i).setVisible(true);
        }
        score=0;
        spaceShip.setX(30);
        spaceShip.setY(30);
        java.util.List<Shoot> missile=spaceShip.getMissiles();
        for(Shoot i:missile)
        {
            i.setVisible(false);
        }
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
    Run
     */

    public void run() {
        long lastTime = System.nanoTime();
        double nsPerTick = 1000000000D / 60D;
        int ticks = 0;
        int frames = 0;

        long lastTimer = System.currentTimeMillis();
        double delta = 0;

        init();

        while (Game.isRunning()) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;
            boolean shouldRender = false;
            boolean shouldUpdate=false;

            while (delta >= 1) {
                ticks++;
                delta -= 1;
                shouldRender = true;
                shouldUpdate=true;
            }

            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(shouldUpdate && !Game.isPause() && !Game.isOver() && !Game.getWin())
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

            if (System.currentTimeMillis() - lastTimer >= 1000) {
                lastTimer += 1000;
                fps = frames;
                tps = ticks;
                frames = 0;
                ticks = 0;
            }
        }

    }

    /**
     * Metoda asta sa o folosesti pentru componentele animate daca ai timp pentru ele
     */


    /*
    Render method
     */

    public  void render()
    {
        BufferStrategy bs = frame.getBufferStrategy();
        if (bs == null) {
            frame.createBufferStrategy(3);
            return;
        }
        Graphics g2d = bs.getDrawGraphics();
        if(!getWin()) {
            if (isOver()) {
                //Daca e gameover va afisa ceva
                g2d.drawImage((new ImageIcon("images/gameover.jpg")).getImage(), -100, 0, null);
                reloadButton.changeLocation(200,500);
                reloadButton.render(g2d);
                quitButton.changeLocation(650, 500);
                quitButton.render(g2d);
            } else {
                if (!isPause()) {
                    //daca nu e pauza va afisa jocul efectiv
                    level.render(g2d);
                    if (spaceShip.isVisible()) {
                        g2d.drawImage(spaceShip.getImage(), spaceShip.getX(),
                                spaceShip.getY(), null);
                    }

                    java.util.List<Shoot> missiles = spaceShip.getMissiles();

                    for (Shoot missile : missiles) {
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
                    g2d.setColor(Color.BLACK);
                    g2d.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 27));
                    g2d.drawString("Scor:"+score,700,60);
                    g2d.drawString("Inamici ramasi:"+level.getItems().size(),200,60);
                } else {
                    //daca e pauza va afisa meniul specific
                    g2d.drawImage((new ImageIcon("images/backgr.png")).getImage(), 0, 0, null);
                    g2d.drawImage((new ImageIcon("images/buttons/Game_pause.png")).getImage(), 240, 100, null);

                    resumeButton.render(g2d);
                    saveButton.render(g2d);
                    quitButton.changeLocation(400, 400);
                    quitButton.render(g2d);
                }
            }
        }
        else
        {
            g2d.drawImage((new ImageIcon("images/black.jpg")).getImage(), -100, 0, null);
            g2d.setColor(Color.white);
            g2d.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 50));
            g2d.drawString("Felicitari!",400,300);
            g2d.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 40));
            g2d.drawString("Scor:"+score,400,400);
            g2d.drawString("Se incarca urmatorul nivel....",350,500);
            waitTime--;
            if(waitTime==0)
            {
                waitTime=200;
                nextLvl();
                win=false;
            }
        }
        g2d.dispose();
        bs.show();
    }

    /*
       Functiile care imi updateaza componentele din program
     */

    //nava
    private void updateSpaceShip() {
        if(spaceShip.isVisible()) {
            spaceShip.move();
        }
    }

    //proiectilele navei
    private void updateMissiles() {
        java.util.List<Shoot> missiles = spaceShip.getMissiles();
        for (int i = 0; i < missiles.size(); i++) {

            Shoot missile = missiles.get(i);

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
            Ship a=level.getItems().get(i);
            java.util.List<Shoot> missileList = a.getShoots();
            for (int j=0;j<missileList.size();j++) {
                Shoot m=missileList.get(j);
                if (m.isVisible()) {
                    m.move();
                } else {
                    missileList.remove(j);
                }
            }
        }
    }

    //inamicii
    private void updateEnemyes()
    {
        if(level.getItems().isEmpty())
        {
           stop();
        }

        for(int i=0;i<level.getItems().size();i++)
        {
            Ship en=level.getItems().get(i);
            if(en.isVisible())
            {
                en.fire();
            }else
            {
               en.setVisible(false);
            }
        }
    }

    /*
        Functia care imi verifica coliziunile dintre obiecte
     */

    private void checkCollisions()
    {
        //dintre nava si inamici
        Rectangle r3=spaceShip.getBounds();
        for(int i=0;i<level.getItems().size();i++)
        {
            Ship enemy=level.getItems().get(i);
            Rectangle r2=enemy.getBounds();

            if(r3.intersects(r2))
            {
                spaceShip.setVisible(false);
                enemy.setVisible(false);
                Game.setRunning(false);
                Game.setGameOver(true);
            }

            //dintre nava si proiectilele inamicilor
            java.util.List<Shoot> missileList=enemy.getShoots();

            for(Shoot m:missileList)
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
                            for(int xx=0;xx<lives;xx++)
                            {
                                hearts.get(xx).setVisible(true);
                            }
                        }
                    }
                }
            }
        }

        //dintre inamici si proiectilele navei
        java.util.List<Shoot>  ms=spaceShip.getMissiles();

        for(Shoot m:ms)
        {
            Rectangle r1=m.getBounds();
            for(int i=0;i<level.getItems().size();i++)
            {
                Ship enemy=level.getItems().get(i);
                Rectangle r2=enemy.getBounds();

                if(r1.intersects(r2))
                {
                    m.setVisible(false);
                    enemy.setVisible(false);
                    level.getItems().remove(i);
                    if(enemy instanceof BigEnemy)
                    {
                        score=score+200*lives;
                    }
                    if(enemy instanceof SmallEnemy)
                    {
                        score=score+100*lives;
                    }
                    enemyesLeft--;
                }
            }
            if(enemyesLeft==0)
            {
                win=true;
            }
        }
    }

    /*
        Functii care imi seteaza si imi dau accesul la atributele booleene
     */
    public static boolean isRunning() { return running; }
    public static void setRunning(boolean running){Game.running=running;}

    public static boolean isChangeLevel(){return changeLevel;}
    public static void setChangeLevel(boolean changeLevel){Game.changeLevel=changeLevel;}

    public static boolean isPause(){return pause;}
    public static void setPause(boolean p){pause=p;}

    public static void setGameOver(boolean o){gameOver=o;}
    public static boolean isOver(){return gameOver;}

    public static void setWin(boolean o){win=o;}
    public static boolean getWin(){return win;}

    //functii care imi dau acces si imi seteaza variabilele input
    public static InputHandle getInput(){return input;}
    public static void setInput(InputHandle input){Game.input=input;}

    public static MouseInput getMouse(){return mouse;}
    public static void setMouse(MouseInput mouse){Game.mouse=mouse;}

    //functii care imi dau acces la membrii clasei
    public int getTickCount(){return tickCount;}
    public void setTickCount(int tick){tickCount=tick;}

    public static SpaceShip getSpaceShip(){return spaceShip;}

    public static JFrame getFrame(){return frame;}

    public static MyButton getQuitButton(){return quitButton;}

    public static MyButton getReloadButton(){return reloadButton;}

    public static MyButton getResumeButton(){return resumeButton;}

    public static MyButton getSaveButton(){return saveButton;}

    public static LevelHandle getLevelHandle(){return level;}

}
