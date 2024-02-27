package DataAccesses;

import Models.Publisher;
import DataAccesses.Internal.DataAccess;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PublisherDataAccess {
    private static PublisherDataAccess INSTANCE;
    
    public static PublisherDataAccess getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PublisherDataAccess();
        }
        return INSTANCE;
    }
    
    private PublisherDataAccess() {
    }
    
    public List<Publisher> getAll() {
        String sp = "{call spPublisher_GetAll}";
        List<Publisher> publishers = new ArrayList<>();
        try (CallableStatement statement = DataAccess.getConnection().prepareCall(sp)) {
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
        try (Connection conn = DataAccess.getConnection();
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
    
    public boolean updateOne(Publisher pub) {
        String sql = "update Publisher set [Name] = ? where [Id] = ?;";
        boolean success = false;
        try (Connection conn = DataAccess.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, pub.getName());
            statement.setInt(2, pub.getId());
            if (statement.executeUpdate() == 1) {
                success = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }
    public boolean deleteOne(int pubId) {
        String sql = "delete from Publisher where [Id] = ?;";
        boolean success = false;
        try (Connection conn = DataAccess.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, pubId);
            if (statement.executeUpdate() == 1) {
                success = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }
    
    public void addOne(Publisher publisher) {
        String sp = "{call spPublisher_AddOne(?)}";
        try (CallableStatement statement = DataAccess.getConnection().prepareCall(sp)) {
            statement.setString("Name", publisher.getName());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
