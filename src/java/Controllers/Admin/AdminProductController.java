package Controllers.Admin;

import Annotations.Authorize;
import DataAccesses.AuthorDataAccess;
import DataAccesses.BookDataAccess;
import DataAccesses.CategoryDataAccess;
import DataAccesses.Internal.DBProps;
import DataAccesses.ProductDataAccess;
import DataAccesses.PublisherDataAccess;
import java.io.IOException;
import java.net.URLEncoder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Authorize({"Admin"})
@MultipartConfig
public class AdminProductController extends HttpServlet {
    private ProductDataAccess productDAO;
    private BookDataAccess bookDAO;
    private CategoryDataAccess categoryDAO;
    private PublisherDataAccess publisherDAO;
    private AuthorDataAccess authorDAO;

    @Override
    public void init() throws ServletException {
        String driverName = getServletContext().getInitParameter("db-driver");
        String connectionString = getServletContext().getInitParameter("db-connection-string");
        DBProps props = new DBProps(driverName, connectionString);
        this.productDAO = ProductDataAccess.getInstance(props);
        this.bookDAO = BookDataAccess.getInstance(props);
        this.categoryDAO = CategoryDataAccess.getInstance(props);
        this.publisherDAO = PublisherDataAccess.getInstance(props);
        this.authorDAO = AuthorDataAccess.getInstance(props);
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        String searchText = req.getParameter("search");
        if (searchText != null && !searchText.isBlank()) {
            req.setAttribute("searchedProductList", productDAO.searchByName(searchText));
        }
        String updateType = req.getParameter("update-type");
        if (updateType != null && !updateType.isBlank()) {
            String updateIdParam = req.getParameter("update-id");
            if (updateIdParam != null) {
                try {
                    int updateId = Integer.parseInt(updateIdParam);
                    if (updateType.equals("Book")) {
                        req.setAttribute("updateItem", bookDAO.getById(updateId));
                    }
                    else {
                        req.setAttribute("updateItem", productDAO.getById(updateId));
                    }
                } catch(NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        req.setAttribute("categories", categoryDAO.getAllCategories());
        req.setAttribute("genres", categoryDAO.getSubCategories("Book"));
        req.setAttribute("publishers", publisherDAO.getAll());
        req.setAttribute("authors", authorDAO.getAll());
        req.getRequestDispatcher("/WEB-INF/Views/Admin/AdminProduct.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        String action = req.getParameter("form-action");
        if (action == null) {
            resp.sendError(400);
            return;
        }
        if (action.equals("search-product")) {
            String searchText = req.getParameter("search-text");
            if (searchText != null && !searchText.isBlank()) {
                String searchParam = URLEncoder.encode(searchText, "UTF-8");
                resp.sendRedirect(req.getContextPath() + "/Admin/Product?search=" + searchParam + "#product-change");
                return;
            }
        }
        resp.sendRedirect(req.getContextPath() + "/Admin/Product");
    }
}
