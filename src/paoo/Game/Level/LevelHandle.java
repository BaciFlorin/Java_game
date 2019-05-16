package paoo.Game.Level;

import paoo.Items.Backgorund;
import paoo.Items.EnemyMissile;
import paoo.Items.Item;
import paoo.Items.SmallEnemy;

import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LevelHandle {

    private int nr_level;
    private List<Item> items=new ArrayList<>();
    private Backgorund backgorund;

    public LevelHandle()
    {
        backgorund=new Backgorund(0,0);
    }

    public void render(Graphics g)
    {
        g.drawImage(backgorund.getImage(),backgorund.getX(),backgorund.getY(),null);
        for(int i=0;i<items.size();i++)
        {
            SmallEnemy item=(SmallEnemy)items.get(i);
            g.drawImage(item.getImage(),item.getX(),item.getY(),null);
                java.util.List<EnemyMissile> missileList = item.GetMissiles();
                for (EnemyMissile e : missileList) {
                    if (e.isVisible()) {
                        g.drawImage(e.getImage(), e.getX(), e.getY(), null);
                    }
                }
        }
    }

    public void addItem(Item nou)
    {
        items.add(nou);
    }

    public void saveLevelToSQL()
    {

    }

    public void loadLevelFromSQL()
    {
        FileReader f=null;
        String buff=" ";
        int c;
        try{
            f=new FileReader("F:\\Work time\\Facultate\\an2-semestrul 2\\Proiectarea aplicatiilor orientate pe obiect\\Game\\PaooGame\\src\\paoo\\Game\\Level\\level");
            while((c=f.read())!=-1)
            {
                buff+=(char)c;
            }
        }
        catch(IOException e)
        {
            System.out.println(e.toString());
        }

        String[] parse=buff.split("&");
        System.out.println("Nume:"+parse[0]);
       // backgorund.changeImage(parse[0]);
        System.out.println("Nr entitati:"+parse[0]);
        int nr_entitati=Integer.parseInt(parse[1]);
        System.out.println("Tipuri:"+parse[2]);
        String[] tipuri=parse[2].split(" ");
        System.out.println("Pozitii:"+parse[3]);
        String[] pozitii=parse[3].split(",");
        for(int i=0;i<nr_entitati;i++)
        {
            int x=Integer.parseInt(pozitii[2*i]);
            int y=Integer.parseInt(pozitii[2*i+1]);
            switch (tipuri[i])
            {
                case "1":
                    addItem(new SmallEnemy(x,y));
                    break;
            }
        }
    }

    public void setLevel(int nr)
    {
        nr_level=nr;
    }

    public int getLevel()
    {
        return nr_level;
    }

    public List<Item> getItems()
    {
        return items;
    }

    //asta cu tick e faina, se apeleaza si in game la fiecare tick de fps

    public void tick()
    {
        for(Item i:items)
        {
            i.tick();
        }
    }
}
