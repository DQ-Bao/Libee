package DataAccesses;

import Models.Book;
import Models.Author;
import Models.Publisher;
import DataAccesses.Internal.DataAccess;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class BookDataAccess {

    public BookDataAccess() {
    }
    
    public List<Book> getAll() {
        String sql = "select * from Book;";
        List<Book> books = new ArrayList<>();
        try (Connection conn = DataAccess.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql)) {
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                int id = res.getInt("Id");
                String isbn10 = res.getString("ISBN10");
                String isbn13 = res.getString("ISBN13");
                String title = res.getString("Title");
                String language = res.getString("Language");
                String coverPath = res.getString("CoverPath");
                String buyLink = res.getString("BuyLink");
                String description = res.getString("Description");
                int publiserId = res.getInt("PublisherId");
                java.util.Date publicationDate = res.getDate("PublicationDate");
                books.add(new Book(id, isbn10, isbn13, title, language, coverPath, buyLink, description, publiserId, publicationDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
    public boolean addOne(String isbn10, String isbn13, String title, String language, String coverPath, String buyLink, String description, int publisherId, java.util.Date publicationDate) {
        String sql = "insert into Book([ISBN10], [ISBN13], [Title], [Language], [CoverPath], [BuyLink], [Description], [PublisherId], [PublicationDate]) values (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        boolean success = false;
        try (Connection conn = DataAccess.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, isbn10);
            statement.setString(2, isbn13);
            statement.setString(3, title);
            statement.setString(4, language);
            statement.setString(5, coverPath);
            statement.setString(6, buyLink);
            statement.setString(7, description);
            statement.setInt(8, publisherId);
            statement.setDate(9, new Date(publicationDate.getTime()));
            if (statement.executeUpdate() == 1) {
                success = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }
    public Publisher getPublisher(int bookId) {
        String sql = "select p.[Id], p.[Name] from Book as b join Publisher as p on b.[PublisherId] = p.[Id] where b.[Id] = ?;";
        Publisher pub = null;
        try (Connection conn = DataAccess.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, bookId);
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
    
    public List<Author> getAuthors(int bookId) {
        String sql = "select a.[Id], a.[Name], a.[ImagePath], a.[Description] from Author as a join AuthorOfBook as aob on a.Id = aob.AuthorId where aob.BookId = ?;";
        List<Author> authors = new ArrayList<>();
        try (Connection conn = DataAccess.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, bookId);
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                int id = res.getInt("Id");
                String name = res.getString("Name");
                String imagePath = res.getString("ImagePath");
                String description = res.getString("Description");
                authors.add(new Author(id, name, imagePath, description));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authors;
    }
}
