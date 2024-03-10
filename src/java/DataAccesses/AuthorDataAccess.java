package DataAccesses;

import Models.Author;
import Models.AuthorOfBook;
import DataAccesses.Internal.DataAccess;
import DataAccesses.Internal.DBProps;
import java.util.List;
import java.util.ArrayList;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorDataAccess {
    private static AuthorDataAccess INSTANCE;
    private DBProps props;
    
    public static AuthorDataAccess getInstance(DBProps props) {
        if (INSTANCE == null) {
            INSTANCE = new AuthorDataAccess(props);
        }
        return INSTANCE;
    }
    
    private AuthorDataAccess(DBProps props) {
        this.props = props;
    }
    
    public List<Author> getAll() {
        String sp = "{call spAuthor_GetAll}";
        List<Author> authors = new ArrayList<>();
        try (CallableStatement statement = DataAccess.getConnection(props).prepareCall(sp)) {
            statement.execute();
            ResultSet res = statement.getResultSet();
            while (res.next()) {
                int id = res.getInt("Id");
                String name = res.getString("Name");
                String description = res.getString("Description");
                String imagePath = res.getString("ImagePath");
                authors.add(new Author(id, name, imagePath, description));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authors;
    }
    
    public Author getById(int authorId) {
        String sp = "{call spAuthor_GetById(?)}";
        Author author = null;
        try (CallableStatement statement = DataAccess.getConnection(props).prepareCall(sp)) {
            statement.setInt("Id", authorId);
            statement.execute();
            ResultSet res = statement.getResultSet();
            while (res.next()) {
                int id = res.getInt("Id");
                String name = res.getString("Name");
                String description = res.getString("Description");
                String imagePath = res.getString("ImagePath");
                author = new Author(id, name, imagePath, description);
                break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return author;
    }
    
    public List<AuthorOfBook> getAuthorsOfBook(int bookId) {
        List<AuthorOfBook> list = new ArrayList<>();
        String sp = "{call spBook_GetAuthors(?)}";
        try (CallableStatement statement = DataAccess.getConnection(props).prepareCall(sp)) {
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
    
    public void addOne(Author author) {
        String sp = "{call spAuthor_AddOne(?, ?, ?)}";
        try (CallableStatement statement = DataAccess.getConnection(props).prepareCall(sp)) {
            statement.setString("Name", author.getName());
            statement.setString("Description", author.getDescription());
            statement.setString("ImagePath", author.getImagePath());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
