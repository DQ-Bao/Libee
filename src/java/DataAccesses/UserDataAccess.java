package DataAccesses;

import Models.User;
import Models.Role;
import DataAccesses.Internal.DataAccess;
import DataAccesses.Internal.DBProps;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Base64;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;

public class UserDataAccess {
    private static UserDataAccess INSTANCE;
    private DBProps props;
    
    public static UserDataAccess getInstance(DBProps props) {
        if (INSTANCE == null) {
            INSTANCE = new UserDataAccess(props);
        }
        return INSTANCE;
    }
    
    private UserDataAccess(DBProps props) {
        this.props = props;
    }
    
    public boolean register(String firstName, String lastName, String email, String password) {
        String sp = "{call spUser_Register(?, ?, ?, ?, ?)}";
        boolean success = false;
        try (CallableStatement statement = DataAccess.getConnection(props).prepareCall(sp)) {
            byte[] salt = generateSalt();
            String hash = hashPassword(password, salt);
            String dbPassword = hash + ":" + Base64.getEncoder().encodeToString(salt);
            
            statement.setString("FirstName", firstName);
            statement.setString("LastName", lastName);
            statement.setString("Email", email);
            statement.setString("Password", dbPassword);
            statement.registerOutParameter("Success", java.sql.Types.BIT);
            statement.execute();
            success = statement.getBoolean("Success");
        } catch (SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return success;
    }
    
    public User login(String email, String password) {
        String sp = "{call spUser_GetByEmail(?)}";
        User user = null;
        try (CallableStatement statement = DataAccess.getConnection(props).prepareCall(sp)) {
            statement.setString("Email", email);
            statement.execute();
            ResultSet userRes = statement.getResultSet();
            while (userRes.next()) {
                String dbPassword = userRes.getString("Password");
                String[] hashSalt = dbPassword.split(":");
                String storedHash = hashSalt[0];
                byte[] salt = Base64.getDecoder().decode(hashSalt[1]);
                String hash = hashPassword(password, salt);
                if (hash.equals(storedHash)) {
                    int uid = userRes.getInt("Id");
                    String ufirstName = userRes.getString("FirstName");
                    String ulastName = userRes.getString("LastName");
                    String uemail = userRes.getString("Email");
                    LocalDateTime ucreatedDate = userRes.getTimestamp("CreatedDate").toLocalDateTime();
                    user = new User(uid, ufirstName, ulastName, null, uemail, ucreatedDate);
                    break;
                }
            }
            if (user == null) {
                return null;
            }
            statement.getMoreResults();
            ResultSet roleRes = statement.getResultSet();
            List<Role> roles = new ArrayList<>();
            while (roleRes.next()) {
                int rid = roleRes.getInt("Id");
                String rname = roleRes.getString("Name");
                roles.add(new Role(rid, rname));
            }
            user.setRoles(roles);
        } catch (SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return user;
    }
    
    public boolean checkPassword(int userId, String password) {
        String sp = "{call spUser_GetById(?)}";
        try (CallableStatement statement = DataAccess.getConnection(props).prepareCall(sp)) {
            statement.setInt("Id", userId);
            statement.execute();
            ResultSet res = statement.getResultSet();
            while (res.next()) {
                String dbPassword = res.getString("Password");
                String[] hashSalt = dbPassword.split(":");
                String storedHash = hashSalt[0];
                byte[] salt = Base64.getDecoder().decode(hashSalt[1]);
                String hash = hashPassword(password, salt);
                if (hash.equals(storedHash)) return true;
                break;
            }
        } catch (SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public void changePassword(int userId, String newPassword) {
        String sp = "{call spUser_ChangePassword(?, ?)}";
        try (CallableStatement statement = DataAccess.getConnection(props).prepareCall(sp)) {
            statement.setInt("Id", userId);
            byte[] salt = generateSalt();
            String hash = hashPassword(newPassword, salt);
            String dbPassword = hash + ":" + Base64.getEncoder().encodeToString(salt);
            statement.setString("NewPassword", dbPassword);
            statement.execute();
        } catch (SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    
    public void deleteAccount(int userId) {
        String sp = "{call spUser_DeleteAccount(?)}";
        try (CallableStatement statement = DataAccess.getConnection(props).prepareCall(sp)) {
            statement.setInt("Id", userId);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private byte[] generateSalt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return salt;
    }
    
    private String hashPassword(String password, byte[] salt)
            throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.reset();
        md.update(salt);
        byte[] hash = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hash);
    }
}
