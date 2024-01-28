package DataAccesses.Internal;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class DataAccess {
    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=Libee;", "sa", "sa");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
