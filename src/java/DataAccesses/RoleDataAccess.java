package DataAccesses;

import Models.Role;
import DataAccesses.Internal.DBProps;
import DataAccesses.Internal.DataAccess;
import java.util.ArrayList;
import java.util.List;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleDataAccess {
    private static RoleDataAccess INSTANCE;
    private DBProps props;
    
    public static RoleDataAccess getInstance(DBProps props) {
        if (INSTANCE == null) {
            INSTANCE = new RoleDataAccess(props);
        }
        return INSTANCE;
    }

    private RoleDataAccess(DBProps props) {
        this.props = props;
    }
    
    public List<Role> getAll() {
        List<Role> list = new ArrayList<>();
        String sp = "{call spRole_GetAll}";
        try (CallableStatement statement = DataAccess.getConnection(props).prepareCall(sp)) {
            statement.execute();
            ResultSet res = statement.getResultSet();
            while (res.next()) {
                int id = res.getInt("Id");
                String name = res.getString("Name");
                list.add(new Role(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public void addOne(String roleName) {
        String sp = "{call spRole_AddOne(?)}";
        try (CallableStatement statement = DataAccess.getConnection(props).prepareCall(sp)) {
            statement.setString("Name", roleName);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void updateOne(Role role) {
        String sp = "{call spRole_UpdateOne(?, ?)}";
        try (CallableStatement statement = DataAccess.getConnection(props).prepareCall(sp)) {
            statement.setInt("Id", role.getId());
            statement.setString("Name", role.getName());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void deleteOne(int roleId) {
        String sp = "{call spRole_DeleteOne(?)}";
        try (CallableStatement statement = DataAccess.getConnection(props).prepareCall(sp)) {
            statement.setInt("Id", roleId);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
