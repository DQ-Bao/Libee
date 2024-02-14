package DataAccesses;

import Models.Book;
import Models.AuthorOfBook;
import Models.SubCategoryOfBook;
import Models.Author;
import Models.Publisher;
import Models.Category;
import DataAccesses.Internal.DataAccess;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookDataAccess {
    private static BookDataAccess INSTANCE = null;
    private CategoryDataAccess cateDao;
    private AuthorDataAccess authorDao;
    
    public static BookDataAccess getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BookDataAccess();
        }
        return INSTANCE;
    }
    
    private BookDataAccess() {
        this.cateDao = CategoryDataAccess.getInstance();
        this.authorDao = AuthorDataAccess.getInstance();
    }
    
    public List<Book> getAll() {
        List<Book> list = new ArrayList<>();
        String sp = "{call spBook_GetAll}";
        try (CallableStatement statement = DataAccess.getConnection().prepareCall(sp)) {
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                int id = res.getInt("Id");
                String name = res.getString("Name");
                double price = res.getDouble("Price");
                String description = res.getString("Description");
                int categoryId = res.getInt("CategoryId");
                String categoryName = res.getString("CategoryName");
                int quantityInStock = res.getInt("QuantityInStock");
                String imagePath = res.getString("ImagePath");
                String isbn10 = res.getString("ISBN10");
                String isbn13 = res.getString("ISBN13");
                String language = res.getString("Language");
                int publisherId = res.getInt("PublisherId");
                String publisherName = res.getString("PublisherName");
                LocalDateTime publicationDate = res.getTimestamp("PublicationDate").toLocalDateTime();
                List<AuthorOfBook> authors = authorDao.getAuthorsOfBook(id);
                List<SubCategoryOfBook> genres = cateDao.getSubCategoriesOfBook(id);
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
}
