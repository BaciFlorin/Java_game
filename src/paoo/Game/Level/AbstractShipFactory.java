package paoo.Game.Level;

import paoo.Items.BigEnemy;
import paoo.Items.Ship;
import paoo.Items.SmallEnemy;

public class AbstractShipFactory {
    public static Ship createShip(int type,int x, int y)
    {
        if(type==1)
        {
            return new SmallEnemy(x,y);
        }
        else if(type==2)
        {
            return new BigEnemy(x,y);
        }
        return null;
    }
}
