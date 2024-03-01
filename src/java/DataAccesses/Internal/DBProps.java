package DataAccesses.Internal;

public class DBProps {
    private final String driverName;
    private final String connectionString;

    public DBProps(String driverName, String connectionString) {
        this.driverName = driverName;
        this.connectionString = connectionString;
    }

    public String getDriverName() {
        return driverName;
    }

    public String getConnectionString() {
        return connectionString;
    }
}
