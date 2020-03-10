package paoo.Menu;

import paoo.Game.BaseValues;
import paoo.Menu.Buttons.MyButton;
import paoo.SimpleFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;

public class Menu implements Runnable{
    //detalii despre fereastra
    private final int WIDTH=500;
    private final int HEIGHT=650;
    private  final String name="Menu";
    private  SimpleFrame frame;

    //butoanele de pe ecranul principal
    private  MyButton playButton, helpButton, optionButton, quitButton, loadButton;

    //butoanele de pe ecranul cu optiuni
    private  MyButton musicButton, plusButton1, minusButton1, backButton,plusButton2,minusButton2;

    //clasa care imi interactioneaza cu baza de date
    private BaseValues base=new BaseValues();

    //atributele booleene care imi controleaza felul cum fereastra evolueaza
    private  boolean running=false;
    private  boolean gameOver=false;
    private  boolean optionMenu=false;

    //setarile jocului
    private  int level=1;
    private  int dificulty=1;
    private  boolean music=true;
    private String saveFile;

    //input
    private MouseListener Mouse=new MouseInput(this);
    private KeyListener Key=new MenuInput(this);

    //functia care imi initializeaza toate atributele
    public void play()
    {
        playButton=new MyButton("images/buttons/Menu/play.png",50,100);
        loadButton=new MyButton("images/buttons/Menu/load.png",50,200);
        optionButton=new MyButton("images/buttons/Menu/options.png",50,300);
        quitButton=new MyButton("images/buttons/Menu/quit.png",50,400);

        musicButton=new MyButton("images/buttons/Menu/ON.png",250,100);
        backButton=new MyButton("images/buttons/Menu/back.png",50,400);
        plusButton1=new MyButton("images/buttons/plus.png",320,220);
        minusButton1=new MyButton("images/buttons/minus.png",460,220);
        plusButton2=new MyButton("images/buttons/plus.png",320,320);
        minusButton2=new MyButton("images/buttons/minus.png",460,320);
        frame = new SimpleFrame(WIDTH, HEIGHT, 1, name);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       // frame.requestFocus();
        frame.addMouseListener(Mouse);
        frame.addKeyListener(Key);
        frame.setVisible(true);

        loadSettings();
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

    //render
    private void render() {
        BufferStrategy bs = frame.getBufferStrategy();
        if (bs == null) {
            frame.createBufferStrategy(3);                                        // Creates a new bs with triple buffering, which reduces tearing and cross-image pixelation
            return;
        }
        Graphics g = bs.getDrawGraphics();
        if(!optionMenu) {
            g.drawImage((new ImageIcon("images/backgr.png")).getImage(), 0, 0, null);
            playButton.render(g);
            loadButton.render(g);
            optionButton.render(g);
            quitButton.render(g);
        }
        else
        {
            g.drawImage((new ImageIcon("images/backgr.png")).getImage(), 0, 0, null);
            g.drawImage((new ImageIcon("images/buttons/Menu/music.png")).getImage(), 50, 100, null);
            g.drawImage((new ImageIcon("images/buttons/Menu/dificulty.png")).getImage(),50,200,null);
            g.drawImage((new ImageIcon("images/buttons/Menu/level.png")).getImage(),50,300,null);
            musicButton.render(g);
            plusButton1.render(g);
            minusButton1.render(g);
            plusButton2.render(g);
            minusButton2.render(g);
            backButton.render(g);
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
            g.drawString(""+level,380,340);
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

    //functii care imi dau acces la atributele clasei

    public  SimpleFrame getFrame(){return frame;}
    public  void setFrame(SimpleFrame frame){this.frame=frame;}

    public  int getLevel(){return level;}
    public  void setLevel(int n){
        if(n<=3 && n>=1) {
            level = n;
        }
    }

    public  int getDificulty(){return dificulty;}
    public  void setDificulty(int n){
        if(n<=3 && n>=1)
        {
            dificulty=n;
        }
    }

    public void loadSettings()
    {
        base.setDifficulty(dificulty);
        base.setMusic(music);
        base.setLevel(level);
    }

    public BaseValues getBase(){return base;}


    //functiile cu ajutorul carora pot accesa butoanele de pe ecranul principal si inafara
    public  MyButton getPlayButton() {return playButton;}

    public  MyButton getHelpButton(){return helpButton;}

    public  MyButton getOptionButton(){return optionButton;}

    public  MyButton getQuitButton(){return quitButton;}

    public  MyButton getLoadButton(){return loadButton;}

    //functiile cu ajutorul corora pot accesa butoanele din optiuni si inafara
    public  MyButton getBackButton(){return backButton;}

    public  MyButton getMusicButton(){return musicButton;}

    public  MyButton getPlusButton1(){return plusButton1;}

    public MyButton getMinusButton1(){return minusButton1;}

    public  MyButton getPlusButton2(){return plusButton2;}

    public MyButton getMinusButton2(){return minusButton2;}

    //functiile care imi permit accesul si modificarea atributelor booleene
    public  void setMusic(boolean o){music=o;}
    public boolean getMusic(){return music;}

    public void setOptionMenu(boolean p)
    {
        optionMenu=p;
    }
    public boolean isOptionMenu(){return optionMenu;}

    public  boolean isRunning(){return running;}
    public  void setRunning(boolean running){this.running=running;}

    public  boolean isGameOver(){return gameOver;}
    public  void setGameOver(boolean gameOver){this.gameOver=gameOver;}

}
