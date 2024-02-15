package DataAccesses;

import Models.User;
import Models.Role;
import DataAccesses.Internal.DataAccess;
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
    
    public static UserDataAccess getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserDataAccess();
        }
        return INSTANCE;
    }
    
    private UserDataAccess() {
    }
    
    public boolean register(String firstName, String lastName, String email, String password) {
        String sp = "{call spUser_Register(?, ?, ?, ?, ?)}";
        boolean success = false;
        try (CallableStatement statement = DataAccess.getConnection().prepareCall(sp)) {
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
        try (CallableStatement statement = DataAccess.getConnection().prepareCall(sp)) {
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
