package DataAccesses;

import Models.Book;
import Models.Book.BookBuilder;
import Models.AuthorOfBook;
import Models.SubCategoryOfBook;
import Models.Publisher;
import Models.Category;
import DataAccesses.Internal.DataAccess;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
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
    
    public Book getById(int bookId) {
        String sp = "{call spBook_GetById(?)}";
        Book book = null;
        try (CallableStatement statement = DataAccess.getConnection().prepareCall(sp)) {
            BookBuilder builder = null;
            statement.setInt("BookId", bookId);
            statement.execute();
            ResultSet res = statement.getResultSet();
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
                builder = Book
                        .getBuilder()
                        .Id(id)
                        .Name(name)
                        .Price(price)
                        .Description(description)
                        .Category(Category
                                .getBuilder()
                                .Id(categoryId)
                                .Name(categoryName))
                        .QuantityInStock(quantityInStock)
                        .ImagePath(imagePath)
                        .ISBN10(isbn10)
                        .ISBN13(isbn13)
                        .Language(language)
                        .Publisher(Publisher
                                .getBuilder()
                                .Id(publisherId)
                                .Name(publisherName))
                        .PublicationDate(publicationDate);
            }
            
            if (builder == null) {
                return null;
            }
            
            statement.getMoreResults();
            res = statement.getResultSet();
            while (res.next()) {
                int bId = res.getInt("BookId");
                int genreId = res.getInt("SubCategoryId");
                String name = res.getString("Name");
                boolean primary = res.getBoolean("Primary");
                builder = builder
                        .Genre(SubCategoryOfBook
                                .getBuilder()
                                .BookId(bId)
                                .SubCategoryId(genreId)
                                .SubCategoryName(name)
                                .Primary(primary));
            }
            
            statement.getMoreResults();
            res = statement.getResultSet();
            while (res.next()) {
                int bId = res.getInt("BookId");
                int authorId = res.getInt("AuthorId");
                String name = res.getString("Name");
                String description = res.getString("Description");
                String imagePath = res.getString("ImagePath");
                builder = builder
                        .Author(AuthorOfBook
                                .getBuilder()
                                .BookId(bId)
                                .AuthorId(authorId)
                                .AuthorName(name)
                                .AuthorDescription(description)
                                .AuthorImagePath(imagePath));
            }
            book = builder.Build();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }
    
    public void addOne(Book book) {
        String authorList = book.getAuthors().stream().map(author -> String.valueOf(author.getAuthorId())).collect(Collectors.joining(","));
        String genreList = book.getGenres().stream().map(genre ->  String.valueOf(genre.getSubCategoryId() + "-" + (genre.isPrimary() ? "1" : "0"))).collect(Collectors.joining(","));
        String sp = "{call spBook_AddOne(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        try (CallableStatement statement = DataAccess.getConnection().prepareCall(sp)) {
            statement.setString("Name", book.getName());
            statement.setDouble("Price", book.getPrice());
            statement.setString("Description", book.getDescription());
            statement.setInt("CategoryId", book.getCategory().getId());
            statement.setInt("QuantityInStock", book.getQuantityInStock());
            statement.setString("ImagePath", book.getImagePath());
            statement.setString("ISBN10", book.getIsbn10());
            statement.setString("ISBN13", book.getIsbn13());
            statement.setString("Language", book.getLanguage());
            statement.setInt("PublisherId", book.getPublisher().getId());
            statement.setTimestamp("PublicationDate", Timestamp.valueOf(book.getPublicationDate()));
            statement.setString("AuthorList", authorList);
            statement.setString("SubCategoryList", genreList);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
