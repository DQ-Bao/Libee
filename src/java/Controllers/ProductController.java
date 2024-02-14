package Controllers;

import Models.Product;
import DataAccesses.ProductDataAccess;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

public class ProductController extends HttpServlet {
    private ProductDataAccess dao;

    @Override
    public void init() throws ServletException {
        dao = ProductDataAccess.getInstance();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        List<Product> list = dao.getAll();
        request.setAttribute("product_list", list);
        request.getRequestDispatcher("Views/ProductDisplay.jsp").forward(request, response);
    }
}
