package paoo.Items;
import java.util.ArrayList;
import java.util.List;

public class BigEnemy extends Ship {
    private List<Shoot> bombs;
    private int time=0;
    private int waitTime=220;
    private int speed;
    public BigEnemy(int x, int y)
    {
        super(x,y);
        loadImage("images/enemy2.png");
        getImageDimensions();
        bombs=new ArrayList<>();
    }

    @Override
    public void fire()
    {
        time+=1;
        if(time==waitTime)
        {       time=0;
            bombs.add(new Bombs(x - 20, y - height / 2));
        }
    }

    @Override
    public void increaseDifficulty(int n)
    {
        if(n==2)
        {
            waitTime=150;
        }else if(n==3)
        {
            waitTime=100;
        }
        else
        {
            waitTime=220;
        }
    }

    @Override
    public List<Shoot> getShoots(){return bombs;}
}
