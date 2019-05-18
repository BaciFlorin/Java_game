package paoo.Items;
import java.util.ArrayList;
import java.util.List;

public class SmallEnemy extends Ship {

    private List<Shoot> missiles;
    private int initial;
    private int time=0;
    private int dy;
    private int waitTime=200;
    private int speed;
    public SmallEnemy(int x, int y)
    {
        super(x,y);
        loadImage("images/enemy1.png");
        getImageDimensions();
        missiles=new ArrayList<>();
        initial=y;
        dy=1;
    }

    @Override
    public void fire()
    {
        y+=dy;
        if(y>initial+height)
        {
            dy=-speed;
        }
        if(y==initial)
        {
            dy=speed;
        }
        time+=1;
        if(time==waitTime)
        {       time=0;
                missiles.add(new EnemyMissile(x - 20, y - height / 2));
        }
    }
    @Override
     public List<Shoot> getShoots()
     {
         return missiles;
     }

     @Override
     public void increaseDifficulty(int n)
     {
        if(n==2)
        {
            waitTime=150;
            speed=2;
        }else if(n==3)
        {
            waitTime=100;
            speed=2;
        }
        else
        {
            waitTime=200;
            speed=1;
        }
     }
}
