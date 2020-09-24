package world.ucode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.DatabaseMetaData;

public class db {

    public static Connection conn = null;
    public static Statement statmt = null;
    public static ResultSet resSet = null;
    public static PreparedStatement pstmt = null;
    public static boolean saving = true;


    public static void Conn() throws ClassNotFoundException, SQLException
    {
        conn = null;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:uscore.s3db");
        System.out.println(saving);
    }

    public static void CreateDB() throws ClassNotFoundException, SQLException
    {
        Conn();
        statmt = conn.createStatement();


        DatabaseMetaData dbm = conn.getMetaData();
        ResultSet tables = dbm.getTables(null, null, "hiscore", null);
        if (tables.next()) {
            resSet = statmt.executeQuery("SELECT score FROM hiscore WHERE id = 1");
            int  tb_score = resSet.getInt("score");
            Main.old_score = tb_score;
            System.out.println(tb_score);
        }
        else {
            statmt.execute("CREATE TABLE if not exists 'hiscore' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'score' INTEGER);");
            statmt.execute("INSERT INTO hiscore VALUES (1,0);");
            Main.old_score = 0;
            System.out.println("not");
        }
        CloseDB();
    }

    public static void WriteDB(int scoren) throws ClassNotFoundException, SQLException  {
        if (saving == true) {
            Conn();
            String executes = "Update hiscore SET score = ? WHERE id = 1; ";
            pstmt = conn.prepareStatement(executes);
            pstmt.setInt(1, scoren);
            pstmt.executeUpdate();

            CloseDB();
        }
    }

    public static void CloseDB() throws ClassNotFoundException, SQLException
    {

        if (resSet != null) {
            resSet.close();
            resSet = null;
        }
        if (statmt != null) {
            statmt.close();
            statmt = null;
        }
        if (pstmt != null) {
            pstmt.close();
            pstmt = null;
        }
        if (conn != null) {
            conn.close();
            conn = null;
        }
    }


}
