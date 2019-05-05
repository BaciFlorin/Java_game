package paoo.Items;
import java.util.ArrayList;
import java.util.List;

public class SmallEnemy extends Item {

    private List<EnemyMissile> missiles;
    private int initial;
    private int time=0;
    private int dy;
    public SmallEnemy(int x, int y)
    {
        super(x,y);
        loadImage("images/enemy1.png");
        getImageDimensions();
        missiles=new ArrayList<>();
        initial=y;
        dy=1;
    }

    public void moveAndFire()
    {
        y+=dy;
        if(y>initial+height)
        {
            dy=-1;
        }
        if(y==initial)
        {
            dy=1;
        }
        time+=1;
        if(time==120)
        {       time=0;
                missiles.add(new EnemyMissile(x - 20, y - height / 2));
        }
    }
     public List<EnemyMissile> GetMissiles()
     {
         return missiles;
     }

}
