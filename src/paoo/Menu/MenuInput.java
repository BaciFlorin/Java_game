package paoo.Menu;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MenuInput implements KeyListener {
    private int i=0;
    private boolean[] buttons=new boolean[2];
    private Menu source;

    public MenuInput(Menu source)
    {
        this.source=source;
    }
    public void keyTyped(KeyEvent e)
    {

    }

    public void keyPressed(KeyEvent e)
    {
        switch(e.getKeyCode())
        {
            case KeyEvent.VK_UP:
                if(i!=0)
                {
                    buttons[i]=false;
                    i--;
                    buttons[i]=true;
                }
                else
                {
                    buttons[0]=true;
                    buttons[1]=false;
                }
                break;
            case KeyEvent.VK_DOWN:
                if(i!=buttons.length-1)
                {
                    buttons[i]=false;
                    i++;
                    buttons[i]=true;
                }
                else
                {
                    buttons[0]=false;
                    buttons[1]=true;
                }
                break;
            case KeyEvent.VK_ESCAPE:
                System.exit(1);
                break;
        }
        setValues();
    }

    public void keyReleased(KeyEvent e)
    {

    }

    private void setValues()
    {

    }
}
