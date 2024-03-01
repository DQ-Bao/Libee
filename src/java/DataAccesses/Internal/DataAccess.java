package DataAccesses.Internal;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class DataAccess {
    private static Connection conn;
    
    public static Connection getConnection(DBProps props) {
        if (conn != null) {
            return conn;
        }
        try {
            Class.forName(props.getDriverName());
            conn = DriverManager.getConnection(props.getConnectionString());
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
