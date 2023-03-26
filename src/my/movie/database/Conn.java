package my.movie.database;

import java.sql.*;

public class Conn {

    Connection c;
    Statement s;

    public Conn() {
        try {
            c = DriverManager.getConnection("jdbc:mysql:///mymoviedatabase", "root", "Evil@123");
            s = c.createStatement();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

