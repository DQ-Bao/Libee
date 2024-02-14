package DataAccesses;

import Models.Author;
import Models.AuthorOfBook;
import DataAccesses.Internal.DataAccess;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorDataAccess {
    private static AuthorDataAccess INSTANCE;
    
    public static AuthorDataAccess getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AuthorDataAccess();
        }
        return INSTANCE;
    }
    
    private AuthorDataAccess() {
    }
    
    public List<AuthorOfBook> getAuthorsOfBook(int bookId) {
        List<AuthorOfBook> list = new ArrayList<>();
        String sp = "{call spBook_GetAuthors(?)}";
        try (CallableStatement statement = DataAccess.getConnection().prepareCall(sp)) {
            statement.setInt("BookId", bookId);
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                int id = res.getInt("Id");
                String name = res.getString("Name");
                String description = res.getString("Description");
                String imagePath = res.getString("ImagePath");
                list.add(new AuthorOfBook(bookId, id, name, imagePath, description));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}