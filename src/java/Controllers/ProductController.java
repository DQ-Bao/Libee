package Controllers;

import DataAccesses.Internal.DBProps;
import DataAccesses.ProductDataAccess;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

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
        String searchText = req.getParameter("search");
        if (searchText != null && !searchText.isBlank()) {
            req.setAttribute("productList", productDAO.searchByName(searchText));
        }
        else {
            req.setAttribute("productList", productDAO.getAll());
        }
        req.getRequestDispatcher("/WEB-INF/Views/ProductDisplay.jsp").forward(req, resp);
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
                resp.sendRedirect(req.getContextPath() + "/Product?search=" + searchParam);
                return;
            }
        }
        resp.sendRedirect(req.getContextPath() + "/Product");
    }
}
