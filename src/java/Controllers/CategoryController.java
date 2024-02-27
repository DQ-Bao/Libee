package Controllers;

import Annotations.Authorize;
import Models.Category;
import Models.SubCategory;
import Services.ProductService;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CategoryController extends HttpServlet {
    private ProductService productSVC;
    
    @Override
    public void init() throws ServletException {
        this.productSVC = ProductService.getInstance();
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
        if (action.equals("add-category")) {
            String name = req.getParameter("category-name");
            productSVC.addOneCategory(Category.getBuilder().Name(name).Build());
        }
        else if (action.equals("add-subCategory")) {
            String name = req.getParameter("subCategory-name");
            int parentId = Integer.parseInt(req.getParameter("subCategory-parentId"));
            productSVC.addOneSubCategory(SubCategory.getBuilder().Name(name).ParentId(parentId).Build());
        }
        resp.sendRedirect(req.getContextPath() + "/Admin");
    }
}
