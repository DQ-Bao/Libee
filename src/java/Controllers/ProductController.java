package Controllers;

import Annotations.Authorize;
import Services.ProductService;
import Models.Product;
import DataAccesses.ProductDataAccess;
import Models.AuthorOfBook;
import Models.Book;
import Models.Category;
import Models.Publisher;
import Models.SubCategoryOfBook;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;

@MultipartConfig
public class ProductController extends HttpServlet {
    private ProductDataAccess dao;
    private ProductService productSVC;

    @Override
    public void init() throws ServletException {
        dao = ProductDataAccess.getInstance();
        this.productSVC = ProductService.getInstance();
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Product> list = dao.getAll();
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
                imageName = image.getSubmittedFileName();
                // TODO: Abstract properties in Configuration class
                Properties props = new Properties();
                InputStream in = req.getServletContext().getResourceAsStream("/WEB-INF/app.properties");
                props.load(in);
                String imageAbsolutePath = props.getProperty("file.image.location");
                String uploadPath = imageAbsolutePath + "/" + imageName;
                productSVC.saveImage(image.getInputStream(), uploadPath);
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
                productSVC.addOneBook(builder.Build());
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
                productSVC.addOneProduct(product);
            }
        }
        resp.sendRedirect(req.getContextPath() + "/Admin");
    }
}
