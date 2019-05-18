package paoo.Game;
import java.sql.*;
public class BaseValues {
    private Connection c=null;
    private Statement str=null;
    private int level;
    private int difficulty;
    private int music;
    private String text;
    private ResultSet resultSet=null;

    public BaseValues()
    {
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

    public void update()
    {
        try {
            resultSet = str.executeQuery("SELECT * FROM SETTINGS");
            while (resultSet.next())
            {
                level=resultSet.getInt("Level");
                difficulty=resultSet.getInt("Difficulty");
                text=resultSet.getString("File");
                music=resultSet.getInt("Music");
            }
        }
        catch(Exception e)
        {
            System.err.println(e.getMessage());
        }
    }
    public int getLevel()
    {
        update();
        return level;
    }

    public int getDifficulty()
    {
        update();
        return difficulty;
    }

    public int getMusic()
    {
        update();
        return music;
    }

    public String getFile()
    {
        update();
        return text;
    }

    public void setLevel(int n)
    {
        try {
            String sql = "UPDATE Settings set Level=" +n+
                    " where ID=1;";
            str.executeUpdate(sql);
            c.commit();
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }

    public void setMusic(boolean n)
    {
        int m=(n)?1:0;
        try {
            String sql = "UPDATE Settings set Music="+m+
                    " where ID=1;";
            str.executeUpdate(sql);
            c.commit();
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }

    public void setDifficulty(int n)
    {
        try {
            String sql = "UPDATE Settings set Difficulty="+n+
                    " where ID=1;";
            str.executeUpdate(sql);
            c.commit();
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }

}
