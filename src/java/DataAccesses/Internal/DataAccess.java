package DataAccesses.Internal;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class DataAccess {
    private static Connection conn;
    public static Connection getConnection() {
        if (conn != null) {
            return conn;
        }
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=Libee;", "sa", "sa");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
