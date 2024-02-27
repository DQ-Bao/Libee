package Controllers;

import Annotations.Authorize;
import Services.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Authorize({"Admin"})
public class AdminController extends HttpServlet {
    private ProductService productSVC;

    @Override
    public void init() throws ServletException {
        this.productSVC = ProductService.getInstance();
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        req.setAttribute("categories", productSVC.getAllCategories());
        req.setAttribute("genres", productSVC.getSubCategories("Book"));
        req.setAttribute("publishers", productSVC.getAllBookPublishers());
        req.setAttribute("authors", productSVC.getAllBookAuthors());
        req.getRequestDispatcher("/WEB-INF/Views/Admin/Admin.jsp").forward(req, resp);
    }
}
