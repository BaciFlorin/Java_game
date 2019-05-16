package paoo.Menu;
import paoo.SimpleFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;

public class Menu implements Runnable{
    //details about size and others
    private static final int WIDTH=500;
    private static final int HEIGHT=650;
    private static final String name="Menu";
    private static SimpleFrame frame;
    private static JButton j;

    //boolean
    private static boolean running=false;
    private static boolean gameOver=false;
    private static boolean plusLevel;
    private static boolean minusLevel;
    private static boolean plusDiff;
    private static boolean minusDiff;
    private static boolean reload=false;
    private static boolean music=true;
    private static int level=1;
    private static int dificulty=1;
    private static boolean optionMenu=false;

    public static void setOptionMenu(boolean p)
    {
        optionMenu=p;
    }

    public static void setMusic(boolean p){music=p;}
    public static boolean isMusic(){return music;}


    public static void setPlusLevel(boolean p){plusLevel=p;}
    public static boolean isPlusLevelSel(){return plusLevel;}

    public static void setMinusLevel(boolean p){minusLevel=p;}
    public static boolean isMinusLevelSel(){return minusLevel;}

    public static void setPlusDiff(boolean p){plusDiff=p;}
    public static boolean isPlusDiffSel(){return plusDiff;}

    public static void setMinusDiff(boolean p){minusDiff=p;}
    public static boolean isMinusDiffSel(){return minusDiff;}

    public static int getLevel(){return level;}
    public static void setLevel(int n){
        if(n<=3 && n>=1) {
            level = n;
        }
    }

    public static int getDificulty(){return dificulty;}
    public static void setDificulty(int n){
        if(n<=3 && n>=1)
        {
            dificulty=n;
        }
    }


    public static boolean isOptionMenu(){return optionMenu;}

    //input
    private static MouseListener Mouse=new MouseInput();
    private KeyListener Key=new MenuInput();

    public static void play()
    {
        frame = new SimpleFrame(WIDTH, HEIGHT, 1, name);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.requestFocus();
        frame.setVisible(true);
    }

    //Run method
    public void run() {
        long lastTime = System.nanoTime();
        double nsPerTick = 1000000000D / 30D;

        int ticks = 0;
        int frames = 0;

        long lastTimer = System.currentTimeMillis();
        double delta = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;
            boolean shouldRender = false;

            while (delta >= 1) {
                ticks++;
                delta -= 1;
                shouldRender = true;
            }

            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (shouldRender) {
                frames++;
                render();
            }

            if (System.currentTimeMillis() - lastTimer >= 1000) {
                lastTimer += 1000;
                frame.setTitle(
                        "Frames: " + frames + " Ticks: " + ticks);
                frames = 0;
                ticks = 0;
            }
        }
    }

    //render functions
    private void render() {
        // frame.getFrame().getContentPane().setBackground(Color.GREEN);
        frame.addMouseListener(Mouse);
        frame.addKeyListener(Key);
        BufferStrategy bs = frame.getBufferStrategy();
        if (bs == null) {
            frame.createBufferStrategy(3);                                        // Creates a new bs with triple buffering, which reduces tearing and cross-image pixelation
            return;
        }
        Graphics g = bs.getDrawGraphics();
        if(!optionMenu) {
            g.drawImage((new ImageIcon("images/backgr.png")).getImage(), 0, 0, null);
            g.drawImage((new ImageIcon("images/buttons/Menu/play.png")).getImage(), 50, 100, null);
            g.drawImage((new ImageIcon("images/buttons/Menu/load.png")).getImage(), 50, 200, null);
            g.drawImage((new ImageIcon("images/buttons/Menu/options.png")).getImage(), 50, 300, null);
            g.drawImage((new ImageIcon("images/buttons/Menu/help.png")).getImage(), 50, 400, null);
            g.drawImage((new ImageIcon("images/buttons/Menu/quit.png")).getImage(), 50, 500, null);
        }
        else
        {
            g.drawImage((new ImageIcon("images/backgr.png")).getImage(), 0, 0, null);
            g.drawImage((new ImageIcon("images/buttons/Menu/music.png")).getImage(), 50, 100, null);
            if(Menu.isMusic())
            {
                g.drawImage((new ImageIcon("images/buttons/Menu/ON.png")).getImage(), 300, 100, null);
            }
            else {
                g.drawImage((new ImageIcon("images/buttons/Menu/OFF.png")).getImage(), 300, 100, null);
            }
            g.drawImage((new ImageIcon("images/buttons/Menu/dificulty.png")).getImage(), 50, 200, null);
            g.drawImage((new ImageIcon("images/buttons/plus.png")).getImage(), 330, 220, null);
            String temp="";
            if(dificulty==1)
            {
                temp="eazy";
            }
            else if(dificulty==2)
            {
                temp="normal";
            }
            else
            {
                temp="hard";
            }
            g.setFont(new Font("Arial", java.awt.Font.BOLD, 27));
            g.setColor(Color.white);
            g.drawString(temp,380,240);
            g.drawImage((new ImageIcon("images/buttons/minus.png")).getImage(), 450, 220, null);

            g.drawImage((new ImageIcon("images/buttons/Menu/level.png")).getImage(), 50, 300, null);
            g.drawImage((new ImageIcon("images/buttons/Menu/back.png")).getImage(), 50, 400, null);
        }
        g.dispose();
        bs.show();
    }
    //Start functions
    public synchronized void start() {
        running = true;
        play();
        new Thread(this, "MENU").start();
    }

    public static boolean isGameOver(){return gameOver;}

    public static boolean isRunning(){return running;}

    public static void setRunning(boolean running){Menu.running=running;}

    public static void setGameOver(boolean gameOver){Menu.gameOver=gameOver;}

    public static SimpleFrame getFrame(){return frame;}

    public static void setFrame(SimpleFrame frame){Menu.frame=frame;}


    public static boolean isReloadMenu(){return reload;}

    public static void  setReloadMenu(boolean reload){Menu.reload=reload;}

}
