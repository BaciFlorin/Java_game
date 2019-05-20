package paoo.Game;

import java.time.LocalDate;
import java.time.LocalTime;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ScoreBase {
    private Connection c=null;
    private Statement str=null;
    private int level;
    private int difficulty;
    private int music;
    private String text;
    private ResultSet resultSet=null;

    public ScoreBase()
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

    public void saveScore(int score,int nivel)
    {
        LocalDate date=LocalDate.now();
        LocalTime time=LocalTime.now();
        try {
            String sql="INSERT INTO Score (Timp,Scor,Nivel) VALUES('"+date.toString()+" "+time.toString()+
                    "',"+score+","+nivel+");";
            str.executeUpdate(sql);
            c.commit();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
