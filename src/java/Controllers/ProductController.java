package Controllers;

import DataAccesses.Internal.DBProps;
import Models.Product;
import DataAccesses.ProductDataAccess;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

public class ProductController extends HttpServlet {
    private ProductDataAccess productDAO;

    @Override
    public void init() throws ServletException {
        String driverName = getServletContext().getInitParameter("db-driver");
        String connectionString = getServletContext().getInitParameter("db-connection-string");
        DBProps props = new DBProps(driverName, connectionString);
        this.productDAO = ProductDataAccess.getInstance(props);
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Product> list = productDAO.getAll();
        req.setAttribute("product_list", list);
        req.getRequestDispatcher("/WEB-INF/Views/ProductDisplay.jsp").forward(req, resp);
    }
}
