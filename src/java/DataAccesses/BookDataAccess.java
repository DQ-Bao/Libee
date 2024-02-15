package DataAccesses;

import Models.Book;
import Models.AuthorOfBook;
import Models.SubCategoryOfBook;
import Models.Publisher;
import Models.Category;
import DataAccesses.Internal.DataAccess;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.sql.Statement;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookDataAccess {
    private static BookDataAccess INSTANCE = null;
    
    public static BookDataAccess getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BookDataAccess();
        }
        return INSTANCE;
    }
    
    private BookDataAccess() {
    }
    
    public List<Book> getAll() {
        List<Book> list = new ArrayList<>();
        String sp = "{call spBook_GetAll}";
        try (CallableStatement statement = DataAccess.getConnection().prepareCall(sp, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            statement.execute();
            ResultSet bookRes = statement.getResultSet();
            statement.getMoreResults(Statement.KEEP_CURRENT_RESULT);
            ResultSet authorRes = statement.getResultSet();
            statement.getMoreResults(Statement.KEEP_CURRENT_RESULT);
            ResultSet genreRes = statement.getResultSet();
            while (bookRes.next()) {
                int id = bookRes.getInt("Id");
                String name = bookRes.getString("Name");
                double price = bookRes.getDouble("Price");
                String description = bookRes.getString("Description");
                int categoryId = bookRes.getInt("CategoryId");
                String categoryName = bookRes.getString("CategoryName");
                int quantityInStock = bookRes.getInt("QuantityInStock");
                String imagePath = bookRes.getString("ImagePath");
                String isbn10 = bookRes.getString("ISBN10");
                String isbn13 = bookRes.getString("ISBN13");
                String language = bookRes.getString("Language");
                int publisherId = bookRes.getInt("PublisherId");
                String publisherName = bookRes.getString("PublisherName");
                LocalDateTime publicationDate = bookRes.getTimestamp("PublicationDate").toLocalDateTime();
                List<AuthorOfBook> authors = getAuthors(id, authorRes);
                List<SubCategoryOfBook> genres = getGenres(id, genreRes);
                list.add(
                    new Book(
                        id, name, price, description, categoryId, new Category(categoryId, categoryName), quantityInStock, imagePath, 
                        isbn10, isbn13, language, new Publisher(publisherId, publisherName), publicationDate, authors, genres
                    )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    private List<AuthorOfBook> getAuthors(int id, ResultSet res)
        throws SQLException {
        List<AuthorOfBook> list = new ArrayList<>();
        while (res.next()) {
            int bookId = res.getInt("BookId");
            if (bookId == id) {
                int authorId = res.getInt("AuthorId");
                String name = res.getString("Name");
                String description = res.getString("Description");
                String imagePath = res.getString("ImagePath");
                list.add(new AuthorOfBook(bookId, authorId, name, imagePath, description));
            }
        }
        res.beforeFirst();
        return list;
    }
    
    private List<SubCategoryOfBook> getGenres(int id, ResultSet res)
        throws SQLException {
        List<SubCategoryOfBook> list = new ArrayList<>();
        while (res.next()) {
            int bookId = res.getInt("BookId");
            if (bookId == id) {
                int genreId = res.getInt("SubCategoryId");
                String name = res.getString("Name");
                boolean primary = res.getBoolean("Primary");
                list.add(new SubCategoryOfBook(bookId, genreId, name, primary));
            }
        }
        res.beforeFirst();
        return list;
    }
}
