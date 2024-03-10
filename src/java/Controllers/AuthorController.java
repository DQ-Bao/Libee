package Controllers;

import Annotations.Authorize;
import Models.Author;
import Models.Product;
import DataAccesses.AuthorDataAccess;
import DataAccesses.Internal.DBProps;
import DataAccesses.ProductDataAccess;
import Utils.ImageUtils;
import java.util.List;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@MultipartConfig
public class AuthorController extends HttpServlet {
    private static String IMAGE_LOCATION;
    private AuthorDataAccess authorDAO;
    private ProductDataAccess productDAO;

    @Override
    public void init() throws ServletException {
        String driverName = getServletContext().getInitParameter("db-driver");
        String connectionString = getServletContext().getInitParameter("db-connection-string");
        DBProps props = new DBProps(driverName, connectionString);
        this.authorDAO = AuthorDataAccess.getInstance(props);
        this.productDAO = ProductDataAccess.getInstance(props);
        IMAGE_LOCATION = getServletContext().getInitParameter("image-location");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        int authorId = Integer.parseInt(req.getParameter("id"));
        Author author = authorDAO.getById(authorId);
        List<Product> productList = productDAO.getAllProductByAuthor(authorId);
        req.setAttribute("author", author);
        req.setAttribute("productList", productList);
        req.getRequestDispatcher("/WEB-INF/Views/Author.jsp").forward(req, resp);
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
        if (action.equals("add-author")) {
            String name = req.getParameter("author-name");
            String description = req.getParameter("author-description");
            Part image = req.getPart("author-image");
            String imageName = null;
            if (image != null && image.getSize() != 0) {
                imageName = ImageUtils.saveImage(image.getInputStream(), IMAGE_LOCATION);
            }
            authorDAO.addOne(Author
                    .getBuilder()
                    .Name(name)
                    .Description(description)
                    .ImagePath(imageName)
                    .Build());
        }
        resp.sendRedirect(req.getContextPath() + "/Admin/Product");
    }
}
