package paoo;
import paoo.Game.Game;
import paoo.Menu.Menu;
import java.sql.*;

public class Main {

    public static void main(String[] args) throws Exception{
        //updatez baza de date cu setarile initiale
        Connection c=null;
        Statement str=null;
        try
        {
            Class.forName("org.sqlite.JDBC");
            c=DriverManager.getConnection("jdbc:sqlite:game.db");
            c.setAutoCommit(false);
            str=c.createStatement();
            String sql="UPDATE Settings set Music=1"+
                    " where ID=1;";
            str.executeUpdate(sql);
            sql="UPDATE Settings set Difficulty=1 where ID=1;";
            str.executeUpdate(sql);
            sql="UPDATE Settings set Level=2 where ID=1;";
            str.executeUpdate(sql);
            sql="UPDATE Settings set File='F:\\Work time\\Facultate\\an2-semestrul 2\\Proiectarea aplicatiilor orientate pe obiect\\Game\\PaooGame\\save' where ID=1;";
            str.executeUpdate(sql);
            c.commit();
            c.close();
            str.close();
        }
        catch (Exception e)
        {
            System.err.println(e.getClass().getName()+":"+e.getMessage());
            System.exit(0);
        }
        new Menu().start();
    }
}
