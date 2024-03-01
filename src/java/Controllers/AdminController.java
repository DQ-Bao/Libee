package Controllers;

import Annotations.Authorize;
import DataAccesses.CategoryDataAccess;
import DataAccesses.PublisherDataAccess;
import DataAccesses.AuthorDataAccess;
import DataAccesses.Internal.DBProps;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Authorize({"Admin"})
public class AdminController extends HttpServlet {
    private CategoryDataAccess categoryDAO;
    private PublisherDataAccess publisherDAO;
    private AuthorDataAccess authorDAO;

    @Override
    public void init() throws ServletException {
        String driverName = getServletContext().getInitParameter("db-driver");
        String connectionString = getServletContext().getInitParameter("db-connection-string");
        DBProps props = new DBProps(driverName, connectionString);
        this.categoryDAO = CategoryDataAccess.getInstance(props);
        this.publisherDAO = PublisherDataAccess.getInstance(props);
        this.authorDAO = AuthorDataAccess.getInstance(props);
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        req.setAttribute("categories", categoryDAO.getAllCategories());
        req.setAttribute("genres", categoryDAO.getSubCategories("Book"));
        req.setAttribute("publishers", publisherDAO.getAll());
        req.setAttribute("authors", authorDAO.getAll());
        req.getRequestDispatcher("/WEB-INF/Views/Admin/Admin.jsp").forward(req, resp);
    }
}
