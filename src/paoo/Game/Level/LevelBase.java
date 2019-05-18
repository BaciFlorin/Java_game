package paoo.Game.Level;

//clasa care imi va da componentele din baza de date specifice unui nivel

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LevelBase {
    private Connection c=null;
    private Statement str=null;
    private ResultSet resultSet=null;
    private int level;
    private List<Integer> types;
    private List<Integer> xpos;
    private List<Integer> ypos;

    public LevelBase(int n)
    {
        level=n;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:game.db");
            c.setAutoCommit(false);
            str = c.createStatement();
        }
        catch (Exception e)
        {
            System.err.println("Exceptie in BaseValues");
        }
        types=new ArrayList<>();
        xpos=new ArrayList<>();
        ypos=new ArrayList<>();
        update();
    }

    public void close()
    {
        try {
            str.close();
            c.close();
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }

    private void update()
    {
        try {
            resultSet = str.executeQuery("SELECT * FROM Level"+level);
            while (resultSet.next())
            {
                types.add(resultSet.getInt("Type"));
                xpos.add(resultSet.getInt("Xpos"));
                ypos.add(resultSet.getInt("Ypos"));
            }
        }
        catch(Exception e)
        {
            System.err.println(e.getMessage());
        }
    }

    public List<Integer> getTypes(){return types;}
    public List<Integer> getXpos(){return xpos;}
    public List<Integer> getYpos(){return ypos;}

    public void changeLevel(int n)
    {
        types=new ArrayList<>();
        xpos=new ArrayList<>();
        ypos=new ArrayList<>();
        level=n;
        update();
    }
}
