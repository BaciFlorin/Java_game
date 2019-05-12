package paoo.Menu;
import paoo.SimpleFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Menu implements Runnable{
    //details about size and others
    private static final int WIDTH=300;
    private static final int HEIGHT=200;
    private static final String name="Menu";
    private static SimpleFrame frame;

    //boolean
    private static boolean running=false;
    private static boolean gameOver=false;
    private static boolean startSelected=true;
    private static boolean exitSelected=false;
    private static boolean reload=false;

    //input
    private static MouseListener Mouse=new MouseInput();
    private KeyListener Key=new MenuInput();

    public static void play()
    {
        frame = new SimpleFrame(WIDTH, HEIGHT, 2, name);
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
        Graphics g=frame.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, frame.getWidth(), frame.getHeight());
        g.setColor(new Color(0xFF660000));
        g.fillRect(0, 0, WIDTH * 3, HEIGHT * 3);
        g.setColor(new Color(0xFFFF9900));
        //g.setFont(font.getArial());
        if (isGameOver()) {
            g.drawString("GAME OVER... What will you do now?", 35, 30);
        }
        //if(!isReloadMenu()) {
            g.drawLine(0, HEIGHT * 3, 0, 0);
            g.drawLine(0, 0, (WIDTH * 3), 0);
            g.drawLine((WIDTH * 3), 0, (WIDTH * 3), (HEIGHT * 3));
            g.drawLine(0, (HEIGHT * 3), (WIDTH * 3), (HEIGHT * 3));
            // (LEFT,DOWN,WIDTH,HEIGHT)
            //Start button
            if (isStartSelected()) {
                g.setColor(new Color(0xFFBB4400));
            } else {
                g.setColor(new Color(0xFF2B4410));
            }
            g.fillRect(35, 40, (frame.getWidth() - 67), 113);
            g.setColor(Color.BLACK);
            g.drawString("Start", 220, 95);

            //Exit button
            if (isExitSelected()) {
                g.setColor(new Color(0xFFBB4400));
            } else {
                g.setColor(new Color(0xFF2B4410));
            }
            g.fillRect(35, 170, (frame.getWidth() - 70), 110);
            g.setColor(Color.BLACK);
            g.drawString("Exit", 220, 220);

        //}
        if(false)
        {
            g.drawLine(0, HEIGHT * 3, 0, 0);
            g.drawLine(0, 0, (WIDTH * 3), 0);
            g.setColor(new Color(0xFF2B4410));
            g.fillRect(35, 40, (frame.getWidth() - 67), 113);
            g.setColor(Color.BLACK);
            g.drawString("Reload", 220, 95);
        }

        g.dispose();

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

    public static boolean isStartSelected(){return startSelected;}

    public static boolean isExitSelected(){return exitSelected;}

    public static void setStartSelected(boolean start){
        startSelected=start;
    }

    public static void setExitSelected(boolean exit)
    {
        exitSelected=exit;
    }

    public static boolean isReloadMenu(){return reload;}

    public static void  setReloadMenu(boolean reload){Menu.reload=reload;}

}
