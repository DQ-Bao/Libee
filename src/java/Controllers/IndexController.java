package Controllers;

import DataAccesses.Internal.DBProps;
import DataAccesses.ProductDataAccess;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class IndexController extends HttpServlet {
    private ProductDataAccess productDAO;

    @Override
    public void init() throws ServletException {
        String driverName = getServletContext().getInitParameter("db-driver");
        String connectionString = getServletContext().getInitParameter("db-connection-string");
        this.productDAO = ProductDataAccess.getInstance(new DBProps(driverName, connectionString));
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        req.setAttribute("productList", productDAO.getAll());
        req.getRequestDispatcher("/WEB-INF/Views/index.jsp").forward(req, resp);
    }
}
