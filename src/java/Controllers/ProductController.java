package Controllers;

import Annotations.Authorize;
import DataAccesses.Internal.DBProps;
import Models.Product;
import DataAccesses.ProductDataAccess;
import DataAccesses.BookDataAccess;
import Models.AuthorOfBook;
import Models.Book;
import Models.Category;
import Models.Publisher;
import Models.SubCategoryOfBook;
import Utils.ImageUtils;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;

@MultipartConfig
public class ProductController extends HttpServlet {
    private static String IMAGE_LOCATION;
    private ProductDataAccess productDAO;
    private BookDataAccess bookDAO;

    @Override
    public void init() throws ServletException {
        String driverName = getServletContext().getInitParameter("db-driver");
        String connectionString = getServletContext().getInitParameter("db-connection-string");
        DBProps props = new DBProps(driverName, connectionString);
        this.productDAO = ProductDataAccess.getInstance(props);
        this.bookDAO = BookDataAccess.getInstance(props);
        IMAGE_LOCATION = getServletContext().getInitParameter("image-location");
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Product> list = productDAO.getAll();
        req.setAttribute("product_list", list);
        req.getRequestDispatcher("/WEB-INF/Views/ProductDisplay.jsp").forward(req, resp);
    }

    @Override
    @Authorize({"Admin"})
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            resp.sendError(400);
            return;
        }
        if (action.equals("add-product")) {
            String name = req.getParameter("product-name");
            double price = Double.parseDouble(req.getParameter("product-price"));
            String description = req.getParameter("product-description");
            int categoryId = Integer.parseInt(req.getParameter("product-categoryId"));
            int quantityInStock = Integer.parseInt(req.getParameter("product-quantityInStock"));

            Part image = req.getPart("product-image");
            String imageName = null;
            if (image != null && image.getSize() != 0) {
                imageName = ImageUtils.saveImage(image.getInputStream(), IMAGE_LOCATION);
            }
            
            String categoryName = req.getParameter("product-categoryName");
            req.getServletContext().log(String.valueOf(categoryId));
            req.getServletContext().log(categoryName);
            if (categoryName != null && categoryName.equals("Book")) {
                String isbn10 = req.getParameter("book-isbn10");
                String isbn13 = req.getParameter("book-isbn13");
                String language = req.getParameter("book-language");
                int publisherId = Integer.parseInt(req.getParameter("book-publisherId"));
                LocalDateTime publicationDate = LocalDate.parse(req.getParameter("book-publicationDate")).atStartOfDay();
                String[] bookAuthorIdsStr = req.getParameterValues("book-author");
                int primaryGenreId = Integer.parseInt(req.getParameter("book-genre-primary"));
                String[] genreIdsStr = req.getParameterValues("book-genre");
                Book.BookBuilder builder = Book
                                        .getBuilder()
                                        .Name(name)
                                        .Price(price)
                                        .Description(description)
                                        .Category(Category
                                                .getBuilder()
                                                .Id(categoryId)
                                                .Name(categoryName))
                                        .QuantityInStock(quantityInStock)
                                        .ImagePath(imageName)
                                        .ISBN10(isbn10)
                                        .ISBN13(isbn13)
                                        .Language(language)
                                        .Publisher(Publisher
                                                .getBuilder()
                                                .Id(publisherId))
                                        .PublicationDate(publicationDate);
                for (int i = 0; i < bookAuthorIdsStr.length; i++) {
                    int authorId = Integer.parseInt(bookAuthorIdsStr[i]);
                    builder = builder.Author(AuthorOfBook
                                            .getBuilder()
                                            .AuthorId(authorId));
                }
                for (int i = 0; i < genreIdsStr.length; i++) {
                    int genreId = Integer.parseInt(genreIdsStr[i]);
                    builder = builder.Genre(SubCategoryOfBook
                                        .getBuilder()
                                        .SubCategoryId(genreId)
                                        .Primary(genreId == primaryGenreId));
                }
                bookDAO.addOne(builder.Build());
            }
            else {
                Product product = Product
                        .getBuilder()
                        .Name(name)
                        .Price(price)
                        .Description(description)
                        .QuantityInStock(quantityInStock)
                        .ImagePath(imageName)
                        .Build();
                productDAO.addOne(product);
            }
        }
        resp.sendRedirect(req.getContextPath() + "/Admin");
    }
}
