package DataAccesses;

import Models.Publisher;
import DataAccesses.Internal.DataAccess;
import DataAccesses.Internal.DBProps;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PublisherDataAccess {
    private static PublisherDataAccess INSTANCE;
    private DBProps props;
    
    public static PublisherDataAccess getInstance(DBProps props) {
        if (INSTANCE == null) {
            INSTANCE = new PublisherDataAccess(props);
        }
        return INSTANCE;
    }
    
    private PublisherDataAccess(DBProps props) {
        this.props = props;
    }
    
    public List<Publisher> getAll() {
        String sp = "{call spPublisher_GetAll}";
        List<Publisher> publishers = new ArrayList<>();
        try (CallableStatement statement = DataAccess.getConnection(props).prepareCall(sp)) {
            statement.execute();
            ResultSet res = statement.getResultSet();
            while (res.next()) {
                int id = res.getInt("Id");
                String name = res.getString("Name");
                publishers.add(new Publisher(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return publishers;
    }
    public Publisher getById(int pubId) {
        String sql = "select * from Publisher where Id = ?;";
        Publisher pub = null;
        try (Connection conn = DataAccess.getConnection(props);
            PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, pubId);
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                int id = res.getInt("Id");
                String name = res.getString("Name");
                pub = new Publisher(id, name);
                break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pub;
    }
    
    public void updateOne(Publisher pub) {
        String sp = "{call spPublisher_UpdateOne(?, ?)}";
        try (CallableStatement statement = DataAccess.getConnection(props).prepareCall(sp)) {
            statement.setInt("Id", pub.getId());
            statement.setString("Name", pub.getName());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteOne(int pubId) {
        String sp = "{call spPublisher_DeleteOne(?)}";
        try (CallableStatement statement = DataAccess.getConnection(props).prepareCall(sp)) {
            statement.setInt("Id", pubId);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void addOne(Publisher publisher) {
        String sp = "{call spPublisher_AddOne(?)}";
        try (CallableStatement statement = DataAccess.getConnection(props).prepareCall(sp)) {
            statement.setString("Name", publisher.getName());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
