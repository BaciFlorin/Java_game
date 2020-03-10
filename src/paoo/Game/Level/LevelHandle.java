package paoo.Game.Level;

import paoo.Game.BaseValues;
import paoo.Game.ScoreBase;
import paoo.Items.*;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LevelHandle {

    private int nrLevel;
    private int difficulty;
    private boolean enableMusic;
    private String saveFile;

    //obiectul care comunica cu baza de date
    private BaseValues base;
    private LevelBase lbase;
    private ScoreBase sbase;
    private List<Ship> items=new ArrayList<>();
    private Backgorund backgorund;

    public LevelHandle()
    {
        //baza de date
        base=new BaseValues();
        sbase=new ScoreBase();

        //Initializarea imaginii din spate
        backgorund=new Backgorund(0,0);

        //initializarea setarilor jocului
        nrLevel=base.getLevel();
        difficulty=base.getDifficulty();
        saveFile=base.getFile();
        enableMusic=(base.getMusic()==1);

        //tabelul din baza de date care imi contine tipul inamicilor si pozitiile acestora
        lbase=new LevelBase(nrLevel);
    }

    public void render(Graphics g)
    {
        if(nrLevel==2)
        {
            backgorund.changeImage("images/background3-720.png");
        }
        else if(nrLevel==3)
        {
            backgorund.changeImage("images/snow.png");
        }
        else
        {
            backgorund.changeImage("images/background1-720.png");
        }
        g.drawImage(backgorund.getImage(),backgorund.getX(),backgorund.getY(),null);
        for(int i=0;i<items.size();i++)
        {
                Ship item = items.get(i);
                if (item.isVisible()) {
                    g.drawImage(item.getImage(), item.getX(), item.getY(), null);
                    java.util.List<Shoot> missileList = item.getShoots();
                    for (Shoot e : missileList) {
                        if (e.isVisible()) {
                            g.drawImage(e.getImage(), e.getX(), e.getY(), null);
                        }
                    }
                }
        }
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", java.awt.Font.BOLD, 27));
        g.drawString("Level:"+nrLevel,500,60);
    }

    public void saveLevelToFile()
    {
        String str = "";
        str+=nrLevel+"\n"+items.size()+"\n"+difficulty+"\n";
        String types="";
        String xpos="";
        String ypos="";
        for(int i=0;i<items.size();i++)
        {
            if(items.get(i).isVisible()) {
                if (i != items.size() - 1) {
                    if (items.get(i) instanceof SmallEnemy) {
                        types += 1 + ",";
                    }
                    if (items.get(i) instanceof BigEnemy) {
                        types += 2 + ",";
                    }
                    xpos += items.get(i).x + ",";
                    ypos += items.get(i).y + ",";
                } else {
                    if (items.get(i) instanceof SmallEnemy) {
                        types += 1;
                    }
                    if (items.get(i) instanceof BigEnemy) {
                        types += 1;
                    }
                    xpos += items.get(i).x;
                    ypos += items.get(i).y;
                }
            }
        }
        str=str+types+"\n"+xpos+"\n"+ypos+"\n";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile));
            writer.write(str);
            writer.close();
        }
        catch (IOException e)
        {
            System.err.println(e.getMessage());
        }
    }

    public void loadLevelFromFile()
    {
        FileReader f=null;
        String buff="";
        int c;
        try{
            f=new FileReader(saveFile);
            while((c=f.read())!=-1)
            {
                buff+=(char)c;
            }
        }
        catch(IOException e) {
            System.out.println(e.toString());
        }
        if(buff.equals(""))
        {
            this.loadLevelFromSQL();
        }
        else
        {
            String[] parse=buff.split("\n");
            System.out.println("Level:"+parse[0]);
            nrLevel=Integer.parseInt(parse[0]);
            System.out.println("Numar de entitati:"+parse[1]);
            int nr_entitati=Integer.parseInt(parse[1]);
            System.out.println("Dificultatea:"+parse[2]);
            difficulty=Integer.parseInt(parse[2]);
            String[] tipuri=parse[3].split(",");
            System.out.println("Tipuri:"+parse[3]);
            System.out.println("Xpos:"+parse[4]);
            String[] xpos=parse[4].split(",");
            System.out.println("Ypos:"+parse[5]);
            String[] ypos=parse[5].split(",");

            List<Ship> temp=new ArrayList<>();
            for(int i=0;i<nr_entitati;i++)
            {
                temp.add(AbstractShipFactory.createShip(Integer.parseInt(tipuri[i]),Integer.parseInt(xpos[i]),Integer.parseInt(ypos[i])));
            }
            items=temp;
        }
        setDifficulty();
    }


    public void loadLevelFromSQL()
    {
        List<Integer> types=lbase.getTypes();
        List<Integer> xpos=lbase.getXpos();
        List<Integer> ypos=lbase.getYpos();

        List<Ship> temp=new ArrayList<>();
        for(int i=0;i<types.size();i++)
        {
            int x=xpos.get(i);
            int y=ypos.get(i);
            temp.add(AbstractShipFactory.createShip(types.get(i),x,y));
        }
        items=temp;
        setDifficulty();
    }

    private void setDifficulty()
    {
        for(Ship m:items)
        {
            m.increaseDifficulty(difficulty);
        }
    }

    public void setLevel(int nr)
    {
        if(nr>0 && nr<4) {
            nrLevel = nr;
        }
        else
        {
            nrLevel=1;
        }
        lbase.changeLevel(nrLevel);
        loadLevelFromSQL();
    }

    public int getLevel()
    {
        return nrLevel;
    }

    public List<Ship> getItems()
    {
        return items;
    }


    public LevelBase getLbase(){return lbase;}
    public BaseValues getBase(){return base;}
    public ScoreBase getSbase(){return sbase;}
}
